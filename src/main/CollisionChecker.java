package main;

import java.util.ArrayList;
import java.util.Arrays;

import entity.Enemy;
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
	    if (enemy instanceof Enemy) {
	        Enemy e = (Enemy) enemy;
	        if (e.isDefeated || gp.gameState == gp.battleState) {
	            return; // ✅ Skip collision if enemy is defeated or battle is active
	        }
	    }

	    if (map.currentArea.equals("town") && map.currentSubArea == 1) {
	        if (player.hitBox.intersects(enemy.hitBox)) {
	            System.out.println("⚔ Collision detected! Battle starts.");
	            gp.gameState = gp.battleState;
	        }
	    }
	}

}

	      
