package ooad.finalVersion;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

public class BoardManager {
	public static JFrame jFrame; //Main Window
	
	/**
	 * Constructor
	 * @throws IOException 
	 */
	public  BoardManager() throws IOException {
		jFrame = new JFrame("Puzzle");
		jFrame.add(Login.getLogin());
        jFrame.setTitle("Puzzle");
        
        // make the frame half the height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        int height = screenSize.height;
        int width = screenSize.width;
        jFrame.setSize(width/2, height/2);
        jFrame.setLocationRelativeTo(null);
		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(700, 600);
		jFrame.setLayout(null);
		jFrame.setResizable(true);
		jFrame.setVisible(true);
	}//end constructor

}//end class BoardManager