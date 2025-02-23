package battle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import entity.Enemy;
import entity.Player;
import GUI.GUI;

public class Battle {

    GamePanel gp;
    KeyHandler keyH;
    GUI gui;
    Player[] players;
    Enemy[] enemies; 

    private int[] enemyHP = {3, 3, 3}; // HP for 3 enemies
    public boolean isBattleActive = false;
    private int currentPlayerTurn = 0; // Tracks whose turn it is (0 = Warrior, 1 = Archer, 2 = Wizard)
    private boolean[] isEnemyDead = {false, false, false};
    private int selectedEnemyIndex = 0; // Default to Enemy 1
    private boolean[] isPlayerDead = {false, false, false};  // ✅ Track if we announced deaths
    public BufferedImage battleScreen; 

    public Battle(GamePanel gp, KeyHandler keyH, GUI gui, Player[] players, Enemy[] enemies) {
        this.gp = gp;
        this.keyH = keyH;
        this.gui = gui;
        this.players = players;
        this.enemies = enemies;
        loadBattleAssets(); 
    }

    public void loadBattleAssets() {
        if (battleScreen == null) {  
            try {
                battleScreen = ImageIO.read(getClass().getResourceAsStream("/hud/battle.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDefaultValues() {
        System.out.println("🔄 Setting default battle values...");
        players[0].warriorHP = 3;
        players[1].archerHP = 3;
        players[2].wizardHP = 3;
        
        for (int i = 0; i < 3; i++) {
            enemyHP[i] = 3; 
            isEnemyDead[i] = false;
        }

        currentPlayerTurn = 0;  // Start with the Warrior
        isBattleActive = true;
    }

    public void startBattle() {
        System.out.println("🔥 Battle Started!");
        setDefaultValues();
        isBattleActive = true;
        keyH.enterPressed = false; // 🛑 Prevents auto-attacking from title screen
    }


    public void update() {
        if (!isBattleActive) return;

        if (keyH.enterPressed || keyH.leftPressed || keyH.rightPressed) {
            handlePlayerTurn();
        }
    }

    private void handlePlayerTurn() {
        System.out.println("✅ ENTER Detected in Battle!");

        // **Skip dead players**
        while (players[currentPlayerTurn].warriorHP <= 0) {
            System.out.println("🚫 " + getPlayerName(currentPlayerTurn) + " is dead. Skipping turn.");
            currentPlayerTurn++;

            if (currentPlayerTurn >= players.length) {
                currentPlayerTurn = 0; // Reset to first player if we reach the end
            }
        }

        System.out.println("🎯 " + getPlayerName(currentPlayerTurn) + "'s Turn!");
        System.out.println("⏳ Waiting for input... (Enter = Attack, Left = E2, Right = E3)");

        // Move selection left (Enemy 2)
        if (keyH.leftPressed) {
            keyH.leftPressed = false;
            selectedEnemyIndex = (selectedEnemyIndex == 0) ? 2 : selectedEnemyIndex - 1;
            System.out.println("🔄 Selected Enemy " + (selectedEnemyIndex + 1));
        }

        // Move selection right (Enemy 3)
        if (keyH.rightPressed) {
            keyH.rightPressed = false;
            selectedEnemyIndex = (selectedEnemyIndex == 2) ? 0 : selectedEnemyIndex + 1;
            System.out.println("🔄 Selected Enemy " + (selectedEnemyIndex + 1));
        }

        // If the selected enemy is dead, find the next available enemy
        if (isEnemyDead[selectedEnemyIndex]) {
            selectNextAvailableEnemy();
        }

        // If Enter is pressed, attack the selected enemy
        if (keyH.enterPressed) {
            keyH.enterPressed = false;
            attack(currentPlayerTurn, selectedEnemyIndex);
            handleEnemyTurn(selectedEnemyIndex);
            currentPlayerTurn++;

            if (currentPlayerTurn >= players.length) {
                currentPlayerTurn = 0;
            }
        }
    }
    
 // Select an enemy that is alive
    private void selectNextAvailableEnemy() {
        for (int i = 0; i < isEnemyDead.length; i++) {
            if (!isEnemyDead[i]) {
                selectedEnemyIndex = i;
                System.out.println("✅ Auto-selected Enemy " + (i + 1));
                return;
            }
        }
        System.out.println("⚠ No living enemies left!");
        selectedEnemyIndex = -1; // ✅ Prevents infinite loop if no enemies are left
    }

    private void handleEnemyTurn(int enemyIndex) {
        if (!isBattleActive || isEnemyDead[enemyIndex]) {
            return; // Stop if the battle has ended or the enemy is dead
        }

        System.out.println("👹 Enemy " + (enemyIndex + 1) + "'s Turn!");

        int target = findWeakestPlayer(); // Enemies attack weakest player

        // 10% chance for 3 damage, 50% for 2 damage, otherwise 1
        int damage;
        double roll = Math.random();
        if (roll < 0.30) { // Increase crit chance from 10% to 30%
            damage = 3; 
        } else if (roll < 0.65) { // Adjust mid-range hits
            damage = 2; 
        } else {
            damage = 1;
        }

        // Apply damage to the target
        if (target == 0) players[target].warriorHP -= damage;
        if (target == 1) players[target].archerHP -= damage;
        if (target == 2) players[target].wizardHP -= damage;

        System.out.println("💥 Enemy " + (enemyIndex + 1) + " attacked " + getPlayerName(target) + " for " + damage + " damage! " + getPlayerName(target) + " HP: " + getPlayerHP(target));

        // Check if the player is dead
        checkPlayerHP();
    }

    private void endBattle() {
        System.out.println("🏆 Battle Over. Returning to Play State.");

        // ✅ Move all defeated enemies off-screen and force update
        for (Enemy enemy : enemies) {
            if (enemy != null) {
                enemy.isDefeated = true;
                enemy.update(); // ✅ Force immediate position change
            }
        }

        // ✅ Reset all player HP (so they don’t stay dead in the next battle)
        for (Player player : players) {
            if (player != null) {
                player.warriorHP = 3;
                player.archerHP = 3;
                player.wizardHP = 3;
            }
        }

        // ✅ Reset all enemy states for the next battle
        for (int i = 0; i < enemyHP.length; i++) {
            enemyHP[i] = 3;
            isEnemyDead[i] = false;
        }

        gp.gameState = gp.playState; // ✅ Return to play state instead of game over
        isBattleActive = false;
        gp.battleCooldown = 180; // ✅ Prevent instant re-entry into battle
    }

    public void attack(int playerIndex, int targetIndex) {
    	// Ensure the selected enemy is alive
    	if (isEnemyDead[selectedEnemyIndex]) {
    	    selectNextAvailableEnemy();
    	}

    	if (isEnemyDead[targetIndex]) {
            System.out.println("⚔ Target is already defeated! Choose another enemy.");
            return;
        }

        // 50% chance for 1.5 damage, 50% for 1 damage
        double damage = (Math.random() < 0.3) ? 1.5 : 1.0;
        enemyHP[targetIndex] -= damage;

        System.out.println("⚔ " + getPlayerName(playerIndex) + " dealt " + damage + " damage to Enemy " + (targetIndex + 1) + "! HP: " + enemyHP[targetIndex]);

        if (enemyHP[targetIndex] <= 0) {
            isEnemyDead[targetIndex] = true;
            System.out.println("💀 Enemy " + (targetIndex + 1) + " has been defeated!");
        }

        checkEnemyHP();
    }

    public void checkEnemyHP() {
    	if (!isBattleActive) return;
        System.out.println("⚔ Checking Enemy HP...");

        boolean allEnemiesDead = true;
        for (boolean dead : isEnemyDead) {
            if (!dead) {
                allEnemiesDead = false;
                break;
            }
        }

        if (allEnemiesDead) {
            System.out.println("🎉 All enemies defeated. YOU WIN!");
            endBattle();
            return; // ✅ Prevents any extra messages after battle ends
        }
    }

    private void checkPlayerHP() {
        System.out.println("🛡 Checking Player HP...");
        boolean allPlayersDead = true;

        for (int i = 0; i < players.length; i++) {
            if (getPlayerHP(i) <= 0) {  // ✅ Now correctly checks all three players
                if (!isPlayerDead[i]) {  
                    System.out.println("💀 " + getPlayerName(i) + " has been defeated!");
                    isPlayerDead[i] = true;
                }
            } else {
                allPlayersDead = false;
            }
        }

        if (allPlayersDead) {
            System.out.println("💀 All players have fallen! GAME OVER.");
            isBattleActive = false;

            // ✅ Reset everything for next battle
            for (int i = 0; i < enemyHP.length; i++) {
                enemyHP[i] = 3;
                isEnemyDead[i] = false;
            }

            for (Player player : players) {
                player.warriorHP = 3;
                player.archerHP = 3;
                player.wizardHP = 3;
            }

            // ✅ Send back to title state
            gp.gameState = gp.titleState;  
            gp.battleCooldown = 180; // ✅ Prevent instant battle re-entry
            keyH.enterPressed = false; // ✅ Ensure Enter isn't detected immediately
        }
    }
    
    private String getPlayerName(int index) {
        return switch (index) {
            case 0 -> "Warrior";
            case 1 -> "Archer";
            case 2 -> "Wizard";
            default -> "Unknown";
        };
    }
    
    private int getPlayerHP(int index) {
        return switch (index) {
            case 0 -> players[index].warriorHP;
            case 1 -> players[index].archerHP;
            case 2 -> players[index].wizardHP;
            default -> 0;
        };
    }
    
    private int findWeakestPlayer() {
        int weakestIndex = -1;
        int lowestHP = Integer.MAX_VALUE;

        for (int i = 0; i < players.length; i++) {
            int currentHP = getPlayerHP(i);

            if (currentHP == 1) { // PRIORITIZE FINISHING WEAK PLAYERS
                return i;
            }

            if (currentHP > 0 && currentHP < lowestHP) {
                lowestHP = currentHP;
                weakestIndex = i;
            }
        }

        return (weakestIndex != -1) ? weakestIndex : getRandomLivingPlayer();
    }

    private int getRandomLivingPlayer() {
        int target;
        do {
            target = (int) (Math.random() * players.length);
        } while (getPlayerHP(target) <= 0); // Keep picking until we get a living player
        return target;
    }

}
