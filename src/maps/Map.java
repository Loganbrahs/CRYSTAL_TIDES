package maps;

import entity.Player;
import main.GamePanel;

public class Map { 
	GamePanel gp;
	Player player;
	
	public String currentArea;
	public int currentSubArea;
		
	public int topBorder = 48 + 48,
			bottomBorder = 13 * 48, 
			leftBorder = 48 + 48, 
			rightBorder = 18 * 48;
	
	public Map(GamePanel gp, Player player) {
		this.gp = gp;
		this.player = player;
		
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		currentArea = "town";
		currentSubArea = 1;
	}
	
	public void setMapBorders() {
		
	}
	
	public void update() {
		currentArea = player.Area;
		currentSubArea = player.SubArea;
		
		switch(currentArea) {
		case "town": Town(); break;
		case "fields": Fields(); break;
		case "coast": Coast(); break;
		case "cove": Cove(); break;
		}
	}
	
	public void Town() {
		if(player.x == rightBorder && currentSubArea == 1) { //1 -> 2
			player.SubArea = 2;
			player.x = 96;
		}
		
		if(player.x == rightBorder && currentSubArea == 2) { //2 -> 3
			player.SubArea = 3;
			player.x = 96;
		}
		
		if(player.x == leftBorder - 48 && currentSubArea == 2) { //2 -> 1
			player.SubArea = 1;
			player.x = 816;
		}
		
		if(player.x == leftBorder - 48 && currentSubArea == 3) { //3 -> 2
			player.SubArea = 2;
			player.x = 816;
		}
	}
	
	public void Fields() {
		
	}
	
	public void Coast() {
		
	}
	
	public void Cove() {
		
	}
}
