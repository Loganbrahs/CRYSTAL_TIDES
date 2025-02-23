package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	////////////////////////////////////////////////////////////
	GamePanel gp;
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	public boolean isKeyPressed;
	////////////////////////////////////////////////////////////
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	////////////////////////////////////////////////////////////
	public void keyTyped(KeyEvent e) {
		//nothing
	}
	////////////////////////////////////////////////////////////
	public void keyPressed(KeyEvent e) {
		isKeyPressed = true;
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_ENTER) {
	        enterPressed = true;
	        System.out.println("🔵 ENTER key detected in KeyHandler!");  // 🔴 Debug
	    }
		
		if(gp.gameState == gp.titleState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
		}
		
		if(gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			
			if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			
			if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
		}
		
		if (gp.gameState == gp.battleState) {
		    if (code == KeyEvent.VK_ENTER) {
		        enterPressed = true;
		        System.out.println("⚔ ENTER detected for attack!");
		    }

		    if (code == KeyEvent.VK_LEFT) {
		        leftPressed = true;
		        System.out.println("⬅ LEFT detected for enemy selection!");
		    }

		    if (code == KeyEvent.VK_RIGHT) {
		        rightPressed = true;
		        System.out.println("➡ RIGHT detected for enemy selection!");
		    }
		}
		
		if(gp.gameState == gp.menuState) {
			//menu logic goes here
		}
		
		if(gp.gameState == gp.pauseState) {
			//pause logic goes here
		}
	}
	////////////////////////////////////////////////////////////
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		
		int code = e.getKeyCode();
		
		if(gp.gameState == gp.titleState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = false;
			}
			
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = false;
			}
		}
		
		if(gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = false;
			}
			
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			
			if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			
			if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
		}
		
		if (gp.gameState == gp.battleState) {
		    if (code == KeyEvent.VK_ENTER) {
		        enterPressed = false;
		    }

		    if (code == KeyEvent.VK_LEFT) {
		        leftPressed = false;
		    }

		    if (code == KeyEvent.VK_RIGHT) {
		        rightPressed = false;
		    }
		}

		if(gp.gameState == gp.menuState) {
			//menu logic goes here
		}
		
		if(gp.gameState == gp.pauseState) {
			//pause logic goes here
		}
	}
	////////////////////////////////////////////////////////////
}
