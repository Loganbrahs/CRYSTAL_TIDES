package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import GUI.GUI;
import entity.Enemy;
import entity.Player;
import maps.Map;

public class GamePanel extends JPanel implements Runnable {
	////////////////////////////////////////////////////////////
	//SCREEN RESOLUTION
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 20;
	final int maxScreenRow = 15;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	//FRAMES PER SECOND
	int FPS = 60;
	
	//GAME STATE
	public int gameState = 1;
	public int titleState = 1;
	public int playState = 2;
	public int menuState = 3;
	public int pauseState = 4;
	public int battleState = 5;
	////////////////////////////////////////////////////////////
	Thread gameThread;
		
	KeyHandler keyH = new KeyHandler(this);
					
	Player player = new Player(this, keyH);
	
	Enemy enemy = new Enemy(this);
	
	CollisionChecker CollisionC = new CollisionChecker(this);
		
	Map map = new Map(this, player);
	
	GUI gui = new GUI(this, keyH, player, map);
	////////////////////////////////////////////////////////////
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	////////////////////////////////////////////////////////////
	public void start() {
		gameThread = new Thread(this); //pass GamePanel to Thread()
		gameThread.start();
	}
	////////////////////////////////////////////////////////////
	public void run() {
		double drawInterval = 1_000_000_000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	////////////////////////////////////////////////////////////
	public void update() {
		if(gameState == titleState) {
			gui.update();
		}
		
		if(gameState == playState) {
			gui.update();
			map.update();
			player.update();
			enemy.update();
			CollisionC.checkCollisions(player, enemy, map);

		}
		
		if(gameState == menuState) {
			gui.update();
		}
		
		if(gameState == pauseState) {
			gui.update();
		}
	}
	////////////////////////////////////////////////////////////
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
				
		if(gameState == titleState) {
			gui.draw(g2);
		}
		
		if(gameState == playState) {
			gui.draw(g2);
			player.draw(g2);
			if (map.currentArea.equals("town") && map.currentSubArea == 1) {
	            enemy.draw(g2);
	        }
		}
		
		if(gameState == menuState) {
			gui.draw(g2);
		}
		
		if(gameState == pauseState) {
			gui.draw(g2);
		}
		
		if(gameState == battleState) {
			
		}
		
		g2.dispose();
	}
	////////////////////////////////////////////////////////////
}
