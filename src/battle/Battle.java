package battle;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.KeyHandler;

public class Battle {
    
    GamePanel gp;
    KeyHandler keyH;
    public BufferedImage battleScreen;

    public Battle(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        loadBattleAssets();
    }

    public void loadBattleAssets() {
        if (battleScreen == null) {  // 🔹 Only load if it's null
            try {
                battleScreen = ImageIO.read(getClass().getResourceAsStream("/hud/battle.png"));
                System.out.println("✅ battle.png loaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (battleScreen != null) {
            g2.drawImage(battleScreen, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            System.out.println("⚠️ battleScreen is NULL!");
        }
    }

    public void update() {
        // Placeholder for battle logic
    }
}
