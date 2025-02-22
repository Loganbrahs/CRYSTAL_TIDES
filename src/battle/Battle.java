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
    Player player;
    Enemy enemy;
    
    private int enemyAmount;
    private int enemyHP1, enemyHP2, enemyHP3;
    private int skillChargeCount;
    private int attackOptionState;
        
    private boolean isTauntActive = false;    
    private boolean isBattleActive = false;
    private boolean playerTurn, enemyTurn;
    private boolean isWarriorDead, isArcherDead, isWizardDead;
    private boolean isEnemy1Dead, isEnemy2Dead, isEnemy3Dead;
    
    public BufferedImage battleScreen; // Battle UI

    public Battle(GamePanel gp, KeyHandler keyH, GUI gui, Player player, Enemy enemy) {
        this.gp = gp;
        this.keyH = keyH;
        this.gui = gui;
        this.player = player;
        this.enemy = enemy;
        loadBattleAssets(); // Load battle UI assets
    }

    public void loadBattleAssets() {
        if (battleScreen == null) {  // Only load if it's null
            try {
                battleScreen = ImageIO.read(getClass().getResourceAsStream("/hud/battle.png"));
                System.out.println("✅ battle.png loaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDefaultValues() {
        skillChargeCount = 0;
        
        isWarriorDead = false;
        isArcherDead = false;
        isWizardDead = false;
        
        isEnemy1Dead = false;
        isEnemy2Dead = false;
        isEnemy3Dead = false;
        
        playerTurn = true;
        enemyTurn = false;
        isBattleActive = true;
    }

    public void startBattle() {
        setDefaultValues();
        gp.gameState = gp.battleState; // Ensure battle state is set
    }

    public void update() {
        if (!isBattleActive) return; // Don't update if battle is over

        if (playerTurn) {
            handlePlayerTurn();
        } else if (enemyTurn) {
            handleEnemyTurn();
        }

        checkPlayerHP();
        checkEnemyHP();
        checkBattle(); // Check if battle ends
    }

    private void handlePlayerTurn() {
        if (keyH.leftPressed && gui.optionState > 1) {
            gui.optionState--;
        }
        if (keyH.rightPressed && gui.optionState < 4) {
            gui.optionState++;
        }
        if (keyH.enterPressed) {
            switch (gui.optionState) {
                case 1: attack(); break;
                case 2: skill(); break; 
                case 3: potion(); break; 
                case 4: run(); break;
            }
        }
        playerTurn = false;
        enemyTurn = true;
    }

    private void handleEnemyTurn() {
        for (int i = 0; i < enemyAmount; i++) {
            // Enemy attack logic here
        }
        enemyTurn = false;
        playerTurn = true;
    }

    public void checkPlayerHP() {
        if (player.warriorHP <= 0) {
            isWarriorDead = true;
        }
        if (player.archerHP <= 0) {
            isArcherDead = true;
        }
        if (player.wizardHP <= 0) {
            isWizardDead = true;
        }
    }

    public void checkEnemyHP() {
        if (enemyHP1 <= 0) {
            isEnemy1Dead = true;
        }
        if (enemyHP2 <= 0) {
            isEnemy2Dead = true;
        }
        if (enemyHP3 <= 0) {
            isEnemy3Dead = true;
        }
    }

    public void checkBattle() {
        if (isWarriorDead && isArcherDead && isWizardDead) {
            gp.gameState = 6; // Player loses
            isBattleActive = false;
        }
        if (isEnemy1Dead && isEnemy2Dead && isEnemy3Dead) {
            gp.gameState = 2; // Player wins
            isBattleActive = false;
        }
    }

    public void attack() {
        if (keyH.leftPressed && attackOptionState > 1) {
            attackOptionState--;
        }
        if (keyH.rightPressed && attackOptionState < 3) {
            attackOptionState++;
        }
        if (keyH.enterPressed) {
            switch (attackOptionState) {
                case 1: enemyHP1 -= 1; break;
                case 2: enemyHP2 -= 1; break;
                case 3: enemyHP3 -= 1; break;
            }
        }
    }

    public void skill() {
        if (skillChargeCount == 3) {
            skillChargeCount = 0;
            switch (gui.playerTurnState) {
                case 1: warriorSkill(); break; 
                case 2: archerSkill(); break;
                case 3: wizardSkill(); break;
            }
        } else {
            // Play error sound here
        }
    }

    public void warriorSkill() {
        isTauntActive = true;
    }

    public void archerSkill() {
        player.warriorHP += 1;
        player.archerHP += 1;
        player.wizardHP += 1;
    }

    public void wizardSkill() {
        if (enemyAmount == 1) {
            enemyHP1 -= 1;
        } else if (enemyAmount == 2) {
            enemyHP1 -= 1;
            enemyHP2 -= 1;
        } else if (enemyAmount == 3) {
            enemyHP1 -= 1;
            enemyHP2 -= 1;
            enemyHP3 -= 1;
        }
    }

    public void potion() {
        if (player.potionAmount > 0) {
            switch (gui.optionState) {
                case 1: player.warriorHP += 2; player.potionAmount -= 1; break;
                case 2: player.archerHP += 2; player.potionAmount -= 1; break;
                case 3: player.wizardHP += 2; player.potionAmount -= 1; break;
            }
        } else {
            // Play error sound here
        }
    }

    public void run() {
        gp.gameState = 1;
        isBattleActive = false;
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.battleState) {
            if (battleScreen != null) {
                g2.drawImage(battleScreen, 0, 0, gp.screenWidth, gp.screenHeight, null);
                System.out.println("✅ Drawing battle screen.");
            } else {
                System.out.println("⚠️ battleScreen is NULL!");
            }
        }
    }
}
