package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Enemy extends Entity {
	
	GamePanel gp;
    private BufferedImage enemyImage;
    public String name; //enemy name
	public int expWorth; //determines the amount of experience points given
	int enemyAmount;
	int enemyType;
	String enemyPosition;
	
	public Enemy(GamePanel gp) {
		this.gp = gp;
        setDefaultValues();
        getEnemyImage();
	}
	
	public void setDefaultValues() {
        x = 10 * gp.tileSize;  // Adjust position as needed
        y = 6 * gp.tileSize;
        hitBox = new Rectangle();
        hitBox.x = x;
        hitBox.y = y;
        hitBox.width = gp.tileSize;
        hitBox.height = gp.tileSize; // Double the height to match the sprite
    }
	
	public void getEnemyImage() {
        try {
            enemyImage = ImageIO.read(getClass().getResourceAsStream("/monsters/orc_front.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void update() {
		hitBox.x = x;
	    hitBox.y = y;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(enemyImage, x, y, gp.tileSize, gp.tileSize, null);
	}
	
	public void freeze() {
	    System.out.println("⚡ Enemy is frozen during battle.");
	    this.speed = 0; // Prevents enemy movement during battle
	}
	
}
