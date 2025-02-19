package main;

import java.awt.Toolkit;
import javax.swing.JFrame;

public class Main {
	////////////////////////////////////////////////////////////
	public static void main(String[] args) {
	JFrame window = new JFrame();
	
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable(false);
	window.setTitle("CRYSTAL TIDES");
	window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon/icon.png")));
	
	GamePanel gp = new GamePanel();
	window.add(gp);
	
	window.pack();
	
	window.setLocationRelativeTo(null);
	window.setVisible(true);
	
	gp.start();
	}
	////////////////////////////////////////////////////////////
}
