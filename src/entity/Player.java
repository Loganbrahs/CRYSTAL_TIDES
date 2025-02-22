package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	////////////////////////////////////////////////////////////	
	GamePanel gp;
	KeyHandler keyH;
	
	private BufferedImage playerImage;
	public String Area;
	public int SubArea;
	public int itemAmount;
	public int luminiteAmount;
	
	public String worldPos;
	public int worldX;
	public int worldY;
	public int offSet;
	public int warriorHP;
	public int archerHP;
	public int wizardHP;
	public int potionAmount;
	////////////////////////////////////////////////////////////
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
	}
	////////////////////////////////////////////////////////////
	public void setDefaultValues() {
		hp = 100;
		statPower = 5;
		
		Area = "town";
		SubArea = 1;
		itemAmount = 0;
		luminiteAmount = 1212412;
		
		x = 7 * gp.tileSize;
		y = 8 * gp.tileSize;
		speed = 4;
		direction = "down";
		
		hitBox.x = x;
		hitBox.y = y + gp.tileSize;
		hitBox.width = gp.tileSize;
		hitBox.height = gp.tileSize;
	}
	////////////////////////////////////////////////////////////
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightBack-walk1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightBack-walk2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightFront-walk1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightFront-walk2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideL-walk1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideL-walk2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideR-walk1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideR-walk2.png"));
			
			idle_up1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightBack-idle1.png"));
			idle_up2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightBack-idle2.png"));
			idle_down1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightFront-idle1.png"));
			idle_down2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightFront-idle2.png"));
			idle_left1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideL-idle1.png"));
			idle_left2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideL-idle2.png"));
			idle_right1 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideR-idle1.png"));
			idle_right2 = ImageIO.read(getClass().getResourceAsStream("/knight/knightSideR-idle2.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////////////////////
	public void update() {
		if(keyH.upPressed == true) {
			direction = "up";
			y -= speed;
		}
		
		else if(keyH.downPressed == true) {
			direction = "down";
			y += speed;
		}
		
		else if(keyH.leftPressed == true) {
			direction = "left";
			x -= speed;
		}
		
		else if(keyH.rightPressed == true) {
			direction = "right";
			x += speed;
		}
		
		spriteCounter++;
		
		if(spriteCounter > 12) {
			if(spriteNumber == 1) {
				spriteNumber = 2;
			}
			
			else if(spriteNumber == 2) {
				spriteNumber = 1;
			}
			
			spriteCounter = 0;
		}
		
		hitBox.x = x;
		hitBox.y = y + gp.tileSize;
	}
	////////////////////////////////////////////////////////////
	public void draw(Graphics2D g2) {		
		if(keyH.upPressed == false || keyH.downPressed == false || keyH.leftPressed == false || keyH.rightPressed == false) {
			switch(direction) {
			case "up":
				if(spriteNumber == 1) {
					playerImage = idle_up1;
				}
				
				if(spriteNumber == 2) {
					playerImage = idle_up2;
				}
				break;
				
			case "down":
				if(spriteNumber == 1) {
					playerImage = idle_down1;
				}
				
				if(spriteNumber == 2) {
					playerImage = idle_down2;
				}
				break;
				
			case "left":
				if(spriteNumber == 1) {
					playerImage = idle_left1;
				}
				
				if(spriteNumber == 2) {
					playerImage = idle_left2;
				}
				break;
				
			case "right":
				if(spriteNumber == 1) {
					playerImage = idle_right1;
				}
				
				if(spriteNumber == 2) {
					playerImage = idle_right2;
				}
				break;
			}
		}
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			switch(direction) {
			case "up":
				if(spriteNumber == 1) {
					playerImage = up1;
				}
				
				if(spriteNumber == 2) {
					playerImage = up2;
				}
				break;
				
			case "down":
				if(spriteNumber == 1) {
					playerImage = down1;
				}
				
				if(spriteNumber == 2) {
					playerImage = down2;
				}
				break;
				
			case "left":
				if(spriteNumber == 1) {
					playerImage = left1;
				}
				
				if(spriteNumber == 2) {
					playerImage = left2;
				}
				break;
				
			case "right":
				if(spriteNumber == 1) {
					playerImage = right1;
				}
				
				if(spriteNumber == 2) {
					playerImage = right2;
				}
				break;
			}
		}
		
		g2.drawImage(playerImage, x, y, 48, 96, null);
		g2.setColor(Color.pink);
		g2.draw(hitBox);
	}
	public void freeze() {
	    System.out.println("⚡ Player is frozen during battle.");
	    this.speed = 0; // Prevents player movement during battle
	}

	////////////////////////////////////////////////////////////
}
