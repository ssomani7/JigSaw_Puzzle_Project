package ooad.finalVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ScoreMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public JButton scoreButton;
    public JButton mainMenuButton;
    public JButton exitButton;
    User user;
    private JLabel backgroundImage;
    private static JPanel scoreMenuPanel;
    JLabel name  = new JLabel();
    JLabel score = new JLabel();
    private static ScoreMenu scoreMenuObject;
    
    /**
     * Gets instance of ScoreMenu
     * @return object: scoreMenuObject
     */
    public static ScoreMenu getScoreMenu() {
    	if (scoreMenuObject == null) {
    	    scoreMenuObject =  new ScoreMenu();
        }
        return scoreMenuObject;
    }//end method getScoreMenu()

    public static JPanel getPanel() {
        return scoreMenuPanel;
    }
    /**
     * Constructor: Parameterized
     */
    private ScoreMenu() {
    	scoreMenuPanel = new JPanel();
    	scoreMenuPanel.setVisible(true);
    	scoreMenuPanel.setSize(700,600);
    	scoreMenuPanel.setLayout(null);

        this.user      = Login.getUser();
        scoreButton    = new JButton("Score");
        mainMenuButton = new JButton("MainMenu");
        exitButton     = new JButton("Exit");
     
        scoreMenuPanel.add(name);
        scoreMenuPanel.add(score);
        scoreMenuPanel.add(scoreButton);
        scoreMenuPanel.add(mainMenuButton);
        scoreMenuPanel.add(exitButton);
        
        name.setBounds(200, 150, 400, 50);     
        score.setBounds(200, 190, 400, 50); 
        scoreButton.setBounds(250, 300, 200, 50);
        mainMenuButton.setBounds(250, 400, 200, 50);
        exitButton.setBounds(250, 500, 200, 50);
        
		backgroundImage = new JLabel("New label");
		backgroundImage.setIcon(new ImageIcon("puzzle_image_background.jpg"));
		backgroundImage.setBounds(0, 0, 700, 600);
		scoreMenuPanel.add(backgroundImage);
		
		scoreButton.addActionListener(this);
        mainMenuButton.addActionListener(this);
        exitButton.addActionListener(this);
    }//end constructor

    /**
     * Updates the Score
     */
    public void updateScore() {
        name.setText("Congratulations " + user.getName() + "!!!");
        name.setFont(new Font("Serif", Font.PLAIN, 35));
        name.setForeground(new Color(0xffffdd));
        score.setText("Your Score : " + user.getScore());
        score.setFont(new Font("Serif", Font.PLAIN, 35));
        score.setForeground(new Color(0xffffdd));
    }//end method updateScore()

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == scoreButton) {
            try {
                scoreButton();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (src == mainMenuButton) {
            mainMenuButton();
        } else if (src == exitButton) {
            System.exit(0);
        }
    }//end method actionPerformed()

    private void scoreButton() throws IOException {
        Container parent = this.getParent();
        parent.removeAll();
        ScoreBoard.getScoreBoard().showScore();
        parent.add(ScoreBoard.getScoreBoard());
        parent.validate();
        parent.repaint();

    }//end method scoreButton()

    private void mainMenuButton() {
        Container parent = this.getParent();
        parent.removeAll();
        parent.add(MenuForm.getMenuForm());
        BoardManager.jFrame.getContentPane().add(MenuForm.getPanel());
        parent.validate();
        parent.repaint();
    }//end method mainMenuButton()
}//end class ScoreMenu