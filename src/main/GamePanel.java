package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import battle.Battle;
import GUI.GUI;
import entity.Enemy;
import entity.Player;
import maps.Map;

public class GamePanel extends JPanel implements Runnable {
    ////////////////////////////////////////////////////////////
    // SCREEN RESOLUTION
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public int battleCooldown;

    // FRAMES PER SECOND
    int FPS = 60;

    // GAME STATE
    public int gameState = 1;
    public final int titleState = 1;
    public final int playState = 2;
    public final int menuState = 3;
    public final int pauseState = 4;
    public final int battleState = 5;
    public final int gameOverState = 6; // ✅ Added Game Over State
    ////////////////////////////////////////////////////////////
    Thread gameThread;

    KeyHandler keyH = new KeyHandler(this);
    
    Player[] players = {
    	    new Player(this, keyH), // Warrior
    	    new Player(this, keyH), // Archer
    	    new Player(this, keyH)  // Wizard
    	};

    Enemy enemy = new Enemy(this);

    CollisionChecker CollisionC = new CollisionChecker(this);

    Map map = new Map(this, players[0]); // Only use Warrior for map logic

    GUI gui = new GUI(this, keyH, players[0], map);

    Enemy[] enemies = {new Enemy(this), new Enemy(this), new Enemy(this)}; // Three enemies in a battle

    public Battle battle; // ✅ Declare battle here

    ////////////////////////////////////////////////////////////
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        battle = new Battle(this, keyH, gui, players, enemies); // ✅ Use Player array
    }
    ////////////////////////////////////////////////////////////
    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    ////////////////////////////////////////////////////////////
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
    ////////////////////////////////////////////////////////////
    public void update() {
    	if (gameState == titleState) {
    	    gui.update();
    	    
    	    // 🛑 Detect Enter key to start the game from the title screen
    	    if (keyH.enterPressed) {
    	        System.out.println("🎮 Starting New Game...");
    	        gameState = playState;  // ✅ Sends player to playState instead of battle
    	        keyH.enterPressed = false;  // ✅ Prevents instant battle
    	        
    	        // ✅ Reset everything to avoid instant battle
    	        battle.isBattleActive = false;
    	        battleCooldown = 180; // ✅ Prevents immediate re-entry into battle
    	    }
    	}
    	
    	if (gameState == playState) {
    	    gui.update();
    	    map.update();
    	    players[0].update(); // Only update the Warrior for movement

    	    if (keyH.enterPressed) {
    	        keyH.enterPressed = false;  // ✅ Prevent instant battle trigger
    	    }

    	    // ✅ Prevent battle from starting immediately after leaving title screen
    	    if (battleCooldown > 0) {
    	        battleCooldown--;
    	    } else {
    	        for (Enemy enemy : enemies) {
    	            if (enemy != null && !enemy.isDefeated) {  
    	                CollisionC.checkCollisions(players[0], enemy, map);
    	                enemy.update();
    	            }
    	        }
    	    }
    	}

        // ✅ Ensure battle starts only ONCE
        if (gameState == battleState) {
            if (!battle.isBattleActive) {
                battle.startBattle();
            }

            gui.update();
            battle.update();
            repaint();
        }

        if (gameState == gameOverState) { 
            System.out.println("❌ GAME OVER! Returning to title screen...");
            gameState = titleState;
            battle.isBattleActive = false;
            battle.setDefaultValues();

            // ✅ Reset player positions and states
            for (Player player : players) {
                if (player != null) {
                    player.warriorHP = 3;
                    player.archerHP = 3;
                    player.wizardHP = 3;
                }
            }

            // ✅ Reset enemies
            for (Enemy enemy : enemies) {
                if (enemy != null) {
                    enemy.isDefeated = false;
                }
            }

            // ✅ Reset key press to prevent instant battle
            keyH.enterPressed = false;
            battleCooldown = 180; // Prevent instant re-entry into battle
        }
    }


    ////////////////////////////////////////////////////////////
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 🛑 Clear the screen to ensure we're starting fresh
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, screenWidth, screenHeight);

        if (gameState == titleState) {
            gui.draw(g2);
        }

        if (gameState == playState) {
            gui.draw(g2);
            players[0].draw(g2); // Only draw Warrior in map screen
            
            if (map.currentArea.equals("town") && map.currentSubArea == 1) {
                for (Enemy enemy : enemies) { // ✅ Ensure only ALIVE enemies are drawn
                    if (!enemy.isDefeated) {
                        enemy.draw(g2);
                    }
                }
            }
        }

        if (gameState == battleState) {
            // 🔹 Step 1: Draw the HUD first
            if (gui.hudImg != null) {
                g2.drawImage(gui.hudImg, 0, 0, screenWidth, screenHeight, null);
            }

            // 🔹 Step 2: Draw the battle screen on TOP
            if (battle.battleScreen != null) {
                g2.drawImage(battle.battleScreen, 0, 0, screenWidth, screenHeight, null);
            }

            // 🔹 Step 3: Draw additional UI elements (health, numbers)
            gui.drawHealth(g2);
            gui.drawNumbers(g2);
        }

        if (gameState == gameOverState) { // ✅ Display Game Over Screen
            g2.setColor(Color.RED);
            g2.setFont(g2.getFont().deriveFont(50f));
            g2.drawString("GAME OVER", screenWidth / 2 - 150, screenHeight / 2);
        }

        g2.dispose();
    }
}
