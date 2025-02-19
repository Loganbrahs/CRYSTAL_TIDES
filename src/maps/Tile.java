package maps;

import main.GamePanel;

public class Tile {
	GamePanel gp;

	public int x = gp.tileSize;
	public int y = gp.tileSize;
	
	boolean isColliding;
	
	
	public Tile(GamePanel gp) {
		this.gp = gp;
		
	}
	
	
	
	
}
