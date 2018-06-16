package ooad.finalVersion;
import java.awt.*;
//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
//import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
//import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class MenuForm extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static JPanel panel;
	private String name;
	private JRadioButton dolphinRadioButton;
	private JRadioButton cowRadioButton;
	private JRadioButton nasaRadioButton;
	private JPanel panelSelectImage;
	private JButton submitButton;
	private JLabel labelSelectDimension;
	private JLabel labelSelectImage;
	private JPanel panelSelectDimension;
	private JRadioButton rdbtn2By2;
	private JRadioButton rdbtn3By3;
	private JRadioButton rdbtn5By5;
	private int height;
	private int width;	
	private JLabel labelIntroSelection;
	private JLabel backgroundImage;
	private static MenuForm menuForm; //Singleton Object
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String getName() {
		return name;
	}
	
	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public static MenuForm getMenuForm() { 
		if (menuForm == null) {
			menuForm = new MenuForm();
		}
		return menuForm;
	}

	public static JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Constructor
	 * Creates the application.
	 */
	private MenuForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the JPanel for MenuForm.
	 */
	private void initialize() {
		panel = new JPanel();
		panel.setVisible(true);
		panel.setSize(700,600);
		panel.setLayout(null);
		BoardManager.jFrame.getContentPane().add(panel);
		labelIntroSelection = new JLabel("Pick an image and a dimension to start!!!!");
		labelIntroSelection.setBounds(200, 30, 400, 50);
		panel.add(labelIntroSelection);
		
		labelSelectImage = new JLabel("Select Image :");
		labelSelectImage.setBounds(150, 180, 120, 40);
		panel.add(labelSelectImage);
			
		panelSelectImage = new JPanel();
		panelSelectImage.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		panelSelectImage.setBounds(290, 180, 250, 40);
		panel.add(panelSelectImage);
		
		dolphinRadioButton = new JRadioButton("Dolphin");		
		cowRadioButton     = new JRadioButton("Cow");
		nasaRadioButton    = new JRadioButton("Nasa");
		
		panelSelectImage.add(cowRadioButton);
		panelSelectImage.add(dolphinRadioButton);	
		panelSelectImage.add(nasaRadioButton);		
		groupButtonImage();//allows only one selection among the radio buttons for image
						
		labelSelectDimension = new JLabel("Select Dimension :");
		labelSelectDimension.setBounds(150, 240, 140, 40);
		panel.add(labelSelectDimension);
		
		panelSelectDimension = new JPanel();
		panelSelectDimension.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		panelSelectDimension.setBounds(290, 240, 250, 40);
		panel.add(panelSelectDimension);
		
		rdbtn2By2 = new JRadioButton("2x2");
		rdbtn3By3 = new JRadioButton("3x3");
		rdbtn5By5 = new JRadioButton("5x5");
		
		panelSelectDimension.add(rdbtn2By2);		
		panelSelectDimension.add(rdbtn3By3);
		panelSelectDimension.add(rdbtn5By5);
		groupButtonDimension();//allows only one selection among the radio buttons for dimension.
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(250, 380, 200, 50);
		submitButton.setBackground(Color.DARK_GRAY);
		submitButton.setOpaque(true);
		panel.add(submitButton);
				
		backgroundImage = new JLabel("New label");
		backgroundImage.setIcon(new ImageIcon("puzzle_image_background.jpg"));
		backgroundImage.setBounds(0, 0, 700, 600);
		panel.add(backgroundImage);
		
		submitButton.addActionListener(this);
	}//end method initialize()
	
	/**
	 * Launch the Puzzle when submit button is clicked.
	 * If any radio button between "Image" or "Dimension" is not
	 * selected, then it shows a pop up message to select one.
	 */	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object src = event.getSource();
		if(src == submitButton) {
			if(imageRadioButtonSelected()) {
				if(dimRadioButtonSelected()) {
		            PuzzlePanel puzzle3x3 = new PuzzlePanel(getHeight(),getWidth(),getName());
		            puzzle3x3.setVisible(true);
		            puzzle3x3.setSize(700, 600);
					Container parent = this.getParent();
					parent.removeAll();
					parent.add(puzzle3x3);
					parent.validate();
					parent.repaint();
				} else {
					JOptionPane.showMessageDialog(panel, "Select Dimension Radio Button!!!", "Dimension Radio", 
											JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(panel, "Select Image Radio Button!!!", "Image Radio", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}//end method actionperformed()
		
	/**
	 * Checks which radio button from the Image section is selected.
	 * @return boolean: status of radioCheck
	 */
	private boolean imageRadioButtonSelected() {
		boolean radioCheck = false;
		if(cowRadioButton.isSelected()) {
			this.name  = "cow";
			radioCheck = true; 
		} else if (dolphinRadioButton.isSelected()) {
			this.name = "dolphin";
			radioCheck = true;
		} else if (nasaRadioButton.isSelected()) {
			this.name = "Nasa";
			radioCheck = true;
		} else {
			radioCheck = false;
		}
		return radioCheck;
	}//end method imageRadioButtonSelected()
	
	/**
	 * Checks which radio button from the Dimension section is selected. 
	 * @return boolean: status of radioCheck
	 */
	private boolean dimRadioButtonSelected() {
		boolean radioCheck = false;
		if(rdbtn2By2.isSelected()) {
			this.height = 2;
			this.width  = 2;
			radioCheck  = true;
		} else if (rdbtn3By3.isSelected()) {
			this.height = 3;
			this.width  = 3;
			radioCheck = true;
		} else if (rdbtn5By5.isSelected()) {
			this.height = 5;
			this.width  = 5;
			radioCheck = true;
		} else {
			radioCheck = false;
		}
		return radioCheck;
	}//end method dimRadioButtonSelected()
	
	/**
	 * Makes sure that only one radio button from the image section is selected.
	 */
	private void groupButtonImage( ) {		
		ButtonGroup bgImage = new ButtonGroup( );	
		bgImage.add(dolphinRadioButton);
		bgImage.add(cowRadioButton);
		bgImage.add(nasaRadioButton);	
	}//end method groupButton()
	
	/**
	 * Makes sure that only one radio button from the dimension section is selected.
	 */
	private void groupButtonDimension( ) {		
		ButtonGroup bgDim = new ButtonGroup( );	
		bgDim.add(rdbtn2By2);
		bgDim.add(rdbtn3By3);
		bgDim.add(rdbtn5By5);	
	}//end method groupButtonDimension()
	
}//end class MenuForm