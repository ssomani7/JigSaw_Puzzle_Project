package ooad.finalVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static User user;
    JButton loginButton;
    private JPanel loginPanel;
    public JLabel greetMsg 		   = new JLabel("Hi there!");
    public JLabel nameMsg  		   = new JLabel("Enter your name to get started.");
    public JTextField uneditedText = new JTextField();
    private static Login login     = new Login(); //Singleton Object
    
    public static Login getLogin() {
    	return login;
    }

    /**
     * Constructor
     * @throws IOException 
     */
    private Login(){    	    	
    	loginPanel = new JPanel();
    	loginPanel.setVisible(true);
		loginPanel.setSize(700,600);
		loginPanel.setLayout(null);
    	
    	loginButton = new JButton("Start Playing");
        loginPanel.add(greetMsg);
        loginPanel.add(nameMsg);
        loginPanel.add(uneditedText);
        loginPanel.add(loginButton);
        
        greetMsg.setBounds(350, 62, 200, 20);
        nameMsg.setBounds(275, 185, 300, 20);
        uneditedText.setBounds(250, 250, 250, 40);
       
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setOpaque(true);
        loginButton.setForeground(Color.BLACK);
        loginButton.setBounds(250, 375, 250, 50);
        
        JLabel backgroundImage = new JLabel("New label");
        backgroundImage.setIcon(new ImageIcon("puzzle_image_background.jpg"));
        backgroundImage.setBounds(0, 0, 700, 600);
        loginPanel.add(backgroundImage);
        
        BoardManager.jFrame.add(loginPanel);
        loginButton.addActionListener(this);        
    }//end constructor Login

    public static User getUser () {
        return  user;
    }//end method getUser()

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == loginButton) {
            doLogin();
        }
    }//end method actionPerformed()

    /**
     * Accepts the user name in the textbox. If not provided
     * will show a error pop up message and won't let user proceed
     * unless he enters a user name. On successful validation this 
     * method calls the method which displays Menu Form.
     */
    private void doLogin() {
        String name = uneditedText.getText();
        if(name.equals("")) {
        	JOptionPane.showMessageDialog(loginPanel, "Enter User Name to Continue", "User Name", 
					JOptionPane.INFORMATION_MESSAGE);
        } else {
          user = new User().setName(name);
          goToMenuForm();
        }
    }//end method doLogin()
        
    /**
     * Repaints current JFrame with JPanel displaying MenuForm
     */
    private void goToMenuForm() {
        Container parent = this.getParent();
        parent.removeAll();
        parent.add(MenuForm.getMenuForm());
        BoardManager.jFrame.getContentPane().add(MenuForm.getPanel());
        parent.validate();
        parent.repaint();
    }
}//end class Login