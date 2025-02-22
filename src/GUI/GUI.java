package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;
import maps.Map;

public class GUI {
	////////////////////////////////////////////////////////////
	GamePanel gp;
	KeyHandler keyH;
	Player player;
	Map map;
	
	public BufferedImage titleImg, hudImg, mapImg, optionImg, areaImg;
	private BufferedImage healthFull, healthHalf, healthEmpty;
	private String titlePath, hudPath, mapPath, areaPath;
	private String itemFormat, luminiteFormat;
	private int itemNumber, luminiteNumber;
	private int itemLength, luminiteLength;
	public int optionState;
	public BufferedImage battleScreen;
	public int playerTurnState;
	
	////////////////////////////////////////////////////////////
	public GUI(GamePanel gp, KeyHandler keyH, Player player, Map map) {
		this.gp = gp;
		this.keyH = keyH;
		this.player = player;
		this.map = map;
		
		setDefaultValues();
	}
	////////////////////////////////////////////////////////////
	public void setDefaultValues() {
		gp.gameState = 1;
		optionState = 1;
		
		itemNumber = 2;
		luminiteNumber = 6;
	}
	////////////////////////////////////////////////////////////
	public void getGUIImage() {            
	    try {
	        titleImg = ImageIO.read(getClass().getResourceAsStream(titlePath));
	        hudImg = ImageIO.read(getClass().getResourceAsStream(hudPath)); // ✅ Load HUD
	        mapImg = ImageIO.read(getClass().getResourceAsStream(mapPath));
	        areaImg = ImageIO.read(getClass().getResourceAsStream(areaPath));

	        if (battleScreen == null) {  // ✅ Only load if it's not already loaded
	            battleScreen = ImageIO.read(getClass().getResourceAsStream("/hud/battle.png"));
	            System.out.println("✅ battleScreen loaded successfully.");
	        }

	        if (hudImg != null) {
	            System.out.println("✅ game-hud.png loaded successfully.");
	        }

	        healthFull = ImageIO.read(getClass().getResourceAsStream("/health/healthFull.png"));
	        healthHalf = ImageIO.read(getClass().getResourceAsStream("/health/healthHalf.png"));
	        healthEmpty = ImageIO.read(getClass().getResourceAsStream("/health/healthEmpty.png"));
	    }
	    catch(IOException e) {
	        e.printStackTrace();
	    }
	}
	
	////////////////////////////////////////////////////////////
	public void getGUIPath() {
		titlePath = "/title/title.png";
		hudPath = "/hud/game-hud.png";
		mapPath = "/map/map-" + map.currentArea + map.currentSubArea + ".png";
		areaPath = "/area/" + map.currentArea + ".png";
	}
	////////////////////////////////////////////////////////////
	public void getOptionPath() {
		try {
			switch(optionState) {
			case 1: optionImg = ImageIO.read(getClass().getResourceAsStream("/title/startSelect.png")); break;
			case 2: optionImg = ImageIO.read(getClass().getResourceAsStream("/title/continueSelect.png")); break;
			case 3: optionImg = ImageIO.read(getClass().getResourceAsStream("/title/exitSelect.png")); break;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////////////////////
	public void drawHealth(Graphics2D g2) {
		if(player.hp <= 100 && player.hp > 80) {
			g2.drawImage(healthFull, 240, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthFull, 288, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthFull, 336, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthFull, 384, 672, gp.tileSize, gp.tileSize, null);
			
			if(player.hp <= 90) {
				g2.drawImage(healthHalf, 432, 672, gp.tileSize, gp.tileSize, null);
			} else {
				g2.drawImage(healthFull, 432, 672, gp.tileSize, gp.tileSize, null);
			}
		}
		
		if(player.hp <= 80 && player.hp > 60) {
			g2.drawImage(healthFull, 240, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthFull, 288, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthFull, 336, 672, gp.tileSize, gp.tileSize, null);
			
			if(player.hp <= 70) {
				g2.drawImage(healthHalf, 384, 672, gp.tileSize, gp.tileSize, null);
			} else {
				g2.drawImage(healthFull, 384, 672, gp.tileSize, gp.tileSize, null);
			}
			
			g2.drawImage(healthEmpty, 432, 672, gp.tileSize, gp.tileSize, null);
		}
		
		if(player.hp <= 60 && player.hp > 40) {
			g2.drawImage(healthFull, 240, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthFull, 288, 672, gp.tileSize, gp.tileSize, null);
			
			if(player.hp <= 50) {
				g2.drawImage(healthHalf, 336, 672, gp.tileSize, gp.tileSize, null);
			} else {
				g2.drawImage(healthFull, 336, 672, gp.tileSize, gp.tileSize, null);
			}
			
			g2.drawImage(healthEmpty, 384, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthEmpty, 432, 672, gp.tileSize, gp.tileSize, null);
		}
		
		if(player.hp <= 40 && player.hp > 20) {
			g2.drawImage(healthFull, 240, 672, gp.tileSize, gp.tileSize, null);
			
			if(player.hp <= 30) {
				g2.drawImage(healthHalf, 288, 672, gp.tileSize, gp.tileSize, null);
			} else {
				g2.drawImage(healthFull, 288, 672, gp.tileSize, gp.tileSize, null);
			}
			
			g2.drawImage(healthEmpty, 336, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthEmpty, 384, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthEmpty, 432, 672, gp.tileSize, gp.tileSize, null);
		}
		
		if(player.hp <= 20 && player.hp > 0) {
			if(player.hp <= 10) {
				g2.drawImage(healthHalf, 240, 672, gp.tileSize, gp.tileSize, null);
			} else {
				g2.drawImage(healthFull, 240, 672, gp.tileSize, gp.tileSize, null);
			}

			g2.drawImage(healthEmpty, 288, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthEmpty, 336, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthEmpty, 384, 672, gp.tileSize, gp.tileSize, null);
			g2.drawImage(healthEmpty, 432, 672, gp.tileSize, gp.tileSize, null);
		}
	}
	////////////////////////////////////////////////////////////
	public void drawNumbers(Graphics2D g2) {
		itemFormat = "";
		luminiteFormat = "";
		itemLength = String.valueOf(player.itemAmount).length();
		luminiteLength = String.valueOf(player.luminiteAmount).length();
		
		for(int i = 0; i < itemNumber - itemLength; i++) {
			itemFormat += "0";
		}
		itemFormat += player.itemAmount;
				
		for(int i = 0; i < luminiteNumber - luminiteLength; i++) {
			luminiteFormat += "0";
		}
		luminiteFormat += player.luminiteAmount;
		
		g2.setFont(new Font("Alkhemikal", Font.PLAIN, 48)); 
		g2.setColor(Color.white);
		
		g2.drawString(itemFormat, 615, 708);
		g2.drawString(luminiteFormat, 732, 708);
	}
	////////////////////////////////////////////////////////////
	public void update() {
	    if (gp.gameState == gp.titleState) {
	        getGUIPath();
	        getGUIImage(); // ✅ Ensure title screen loads
	        getOptionPath();
	        
	        if (keyH.upPressed && optionState > 1) {
	            optionState--;
	        }
	        if (keyH.downPressed && optionState < 3) {
	            optionState++;
	        }
	        if (keyH.enterPressed) {
	            switch (optionState) {
	                case 1: gp.gameState = gp.playState; break;
	                case 2: gp.gameState = gp.playState; break; // Placeholder for save logic
	                case 3: System.exit(0); break; // Exits game
	            }
	        }
	    }

	    if (gp.gameState == gp.playState) {
	        getGUIPath();
	        getGUIImage(); // ✅ Ensure play state assets (town, etc.) load
	    }

	    if (gp.gameState == gp.battleState) {
	        if (battleScreen == null) {
	            System.out.println("🔄 Loading battle assets...");
	            getGUIPath();
	            getGUIImage(); // ✅ Load battle assets ONCE
	        }
	    }
	}

	////////////////////////////////////////////////////////////
	public void draw(Graphics2D g2) {
	    if (gp.gameState == gp.titleState) {
	        g2.drawImage(titleImg, 0, 0, 960, 720, null);
	        g2.drawImage(optionImg, 0, 0, 960, 720, null);
	    }

	    if (gp.gameState == gp.playState) {
	        g2.drawImage(hudImg, 0, 0, 960, 720, null);
	        g2.drawImage(mapImg, 0, 0, 960, 720, null);
	        g2.drawImage(areaImg, 0, 0, 960, 720, null);
	        drawHealth(g2);
	        drawNumbers(g2);
	    }

	    if (gp.gameState == gp.battleState) {
	        // ✅ Draw battle screen background
	        if (battleScreen != null) {
	            g2.drawImage(battleScreen, 0, 0, gp.screenWidth, gp.screenHeight, null);
	        } else {
	            System.out.println("❌ battle.png is NULL!");
	        }

	        // ✅ Draw HUD elements on top of battle.png
	        drawHealth(g2);
	        drawNumbers(g2);
	    }
	}
	
	/////////////////////////////////////////////////////////////
	public void drawBattleBackground(Graphics2D g2) {
	    if (gp.gameState == gp.battleState) {
	        if (battleScreen != null) {
	            g2.drawImage(battleScreen, 0, 0, gp.screenWidth, gp.screenHeight, null);
	        } else {
	            System.out.println("❌ battle.png is NULL!");
	        }
	    }
	}

	////////////////////////////////////////////////////////////
}
