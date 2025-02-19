package main;

import java.util.ArrayList;
import java.util.Arrays;
import entity.Entity;
import maps.Map;

public class CollisionChecker {

	GamePanel gp;
	
	int[][] WorldGrid = new int[15][20];
	
	
	ArrayList<Integer> topWorldGrid = new ArrayList<>();
	ArrayList<Integer> bottomWorldGrid = new ArrayList<>();
	ArrayList<Integer> rightWorldGrid = new ArrayList<>();
	ArrayList<Integer> leftWorldGrid = new ArrayList<>();
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
		
		setGameGrid();
	}
	
	public void setGameGrid() {
		for(int x = 0; x < 15; x++) {
			//System.out.println(x); //top side of box
			topWorldGrid.add(x * gp.tileSize);
		}
		
		for(int x = 1; x < 16; x++) {
			//System.out.println(x*gp.tileSize); //bottom side of box
			bottomWorldGrid.add(x * gp.tileSize);
		}

		
		for(int x = 1; x < 21; x++) {
			//System.out.println(x*gp.tileSize); //right side of box
			rightWorldGrid.add(x * gp.tileSize);
		}
		
		for(int x = 0; x < 20; x++) {
			//System.out.println(x*gp.tileSize); //left side of box
			leftWorldGrid.add(x * gp.tileSize);
		}
		
		
	}
	
	public void getCollisions(Entity entity) {
		
		
	}
	
	public void checkCollisions(Entity player, Entity enemy, Map map) {
	    // Only check for collisions if the enemy is in the current area
	    if (map.currentArea.equals("town") && map.currentSubArea == 1) {
	        if (player.hitBox.intersects(enemy.hitBox)) {
	            gp.gameState = gp.battleState; // Switch to battle screen when touching
	        }
	        }
	    }
	}

