package ooad.finalVersion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ScoreBoard extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	static final String dbLocation = "db.txt";
	JButton backButton;
	List<User> users;
	List<JTextField> texts = new ArrayList<>();
	private static ScoreBoard scoreBoard;
	
	/**
	 * Gets the instance of scoreboard.
	 * @return object: scoreboard
	 */
	public static ScoreBoard getScoreBoard() {
		if (scoreBoard == null) {
			scoreBoard = new ScoreBoard();
		}
		return scoreBoard;
	}

	/**
	 * Constructor
	 */
	private ScoreBoard() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setPreferredSize(new Dimension(100, 50));
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 700, 550);
		add(Box.createRigidArea(new Dimension(20,40)));
		add(backButton);
		
		for (int i = 0; i < 10; i++) {
			JTextField textField = new JTextField();
			texts.add(textField);
			add(textField);
		}
	}//end constructor

	/**
	 * Displays score.
	 */
	public void showScore() {
		users = readAll();
		List<User> ten = getTopTen();

		for (int i = 0; i < ten.size(); i++) {
			JTextField textField = texts.get(i);
			User user = ten.get(i);
			textField.setText("Name: " + user.getName() + "  Score: " + user.getScore() + "   Level: " +
								user.getLeveL() );
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setEditable(false);
		}
	}//end method showScore()

	/**
	 * Reads the Username and Score from the database file.
	 */
	public List<User> readAll() {
		List<User> result = new ArrayList<>();
		String line;
	    User temp;
	    try (BufferedReader br = new BufferedReader(new FileReader(dbLocation))) {
	    	while ((line = br.readLine()) != null) {
	    		String[] Info = line.split(",");
	    		String name = Info[0];
	    		int score   = Integer.valueOf(Info[1]);
				int level   = Integer.valueOf(Info[2]);
	            temp  		=  new User ().setName(name).setLevel(level).setScore(score);
	            result.add(temp);
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		return result;
	}//end method readAll()

	/**
	 * Displays top 10 scores.
	 * @return object: user
	 */
	public List<User> getTopTen() {
		users.sort((o1,o2) -> o2.getScore() - o1.getScore());
		if (users.size() >= 10) {
			return users.subList(0, 10);
		} else {
			return users;
		}
	}//end method getTopten()

	/**
	 * Writes User's details to database.
	 * @param newUser
	 * @throws IOException
	 */
	public static void write(User newUser) throws IOException {// name=a, score=1
        FileWriter writer = new FileWriter(dbLocation, true);
        writer.append(newUser.toString() + "\n");
		writer.flush();
		writer.close();
	}//end method write()
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == backButton) {
			doBack();
	    } 
	}//end method actionPerformed()
	
	public  void doBack() {
		Container parent = this.getParent();// get the board
		parent.removeAll();
		parent.add(ScoreMenu.getScoreMenu());
		BoardManager.jFrame.getContentPane().add(ScoreMenu.getPanel());
		parent.validate();
		parent.repaint();
	}//end method doBack()
}//end class ScoreBoard