package ooad.finalVersion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


public class PuzzlePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;	
    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resized;    
    private Image image;
    private int width, height;    
    private List<MyButton> buttons;
    private List<Point> solution;
    private int maxScore;
    public static int seconds       = 0;
    private int numberOfMoves       = 0;
    private final int DESIRED_WIDTH = 400; 
    public boolean firstClick       = false;
    public int lastClicked 			= 0;
    public boolean isFinished 		= false;
    User user   = Login.getUser();
    Timer timer = new Timer();
    private JButton backButton;

    /**
     * Constructor
     * @param HEIGHT = Integer value for dimension
     * @param WIDTH  = Integer value for dimension
     * @param S      = Name of the image to be solved
     */
    public PuzzlePanel(int HEIGHT,int WIDTH, String S) {
        initUI(HEIGHT,WIDTH,S);
        maxScore = (HEIGHT*WIDTH) * 1000;
        user.setLevel(HEIGHT);
        JPanel buttonPanel = new JPanel();
        add(buttonPanel);
        buttonPanel.setBounds(300, 400, 200, 50);
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBounds(0, 0, 200, 50);
        buttonPanel.add(backButton);
    }//end constructor puzzlePanel

    /**
     * Makes the pieces of puzzle move from one place to another.
     * Starts the timer for counting score logic.
     * @param HEIGHT = Integer value for dimension
     * @param WIDTH  = Integer value for dimension
     * @param S      = Name of the image to be solved
     */
    private void initUI(int HEIGHT, int WIDTH, String S) {
        timer.schedule(new addSecond(), 0 , 1000);//Thread aspect of Java Swings.
        solution = new ArrayList<>();
 
        for(int i = 0; i < HEIGHT; i++) {
        	for(int j = 0; j < WIDTH; j ++) {
        		solution.add(new Point(i, j));
        	}
        }
        
        buttons = new ArrayList<>();
        panel   = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(HEIGHT, WIDTH, 0, 0));

        try {
            source = loadImage(S);
            int h = getNewHeight(source.getWidth(), source.getHeight());
            resized = resizeImage(source, DESIRED_WIDTH, h,
                    BufferedImage.TYPE_INT_ARGB);
        } catch (IOException ex) {
            Logger.getLogger(PuzzlePanel.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        width  = resized.getWidth(null);
        height = resized.getHeight(null);
        add(panel, BorderLayout.CENTER);

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                image = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / WIDTH, i * height / HEIGHT,
                                (width / WIDTH), height / HEIGHT)));
                MyButton button = new MyButton(image);
                button.putClientProperty("position", new Point(i, j));
                buttons.add(button);    
            }
        }

        Collections.shuffle(buttons);
        int NUMBER_OF_BUTTONS = WIDTH * HEIGHT;
        
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            MyButton btn = buttons.get(i);
            panel.add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
            btn.addActionListener(new ClickAction());
        }
    }//end method initUI()
    
    private int getNewHeight(int w, int h) {
        double ratio = DESIRED_WIDTH / (double) w;
        int newHeight = (int) (h * ratio);
        return newHeight;
    }//end method getnewHeight()

    private BufferedImage loadImage(String S) throws IOException {
        BufferedImage bimg = ImageIO.read(new File("src/"+S+".jpg"));
        return bimg;
    }//end method loadImage()

    private BufferedImage resizeImage(BufferedImage originalImage, int width,
            int height, int type) throws IOException {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }//end method resizeImage()

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == backButton) {
            goBack();
        }
    }

    /**
     * Takes you back to Main Menu.
     * Resets the timer.
     */
    private void goBack() {
        timer.cancel();
        seconds = 0;
        Container parent = this.getParent();
        parent.removeAll();
        parent.add(MenuForm.getMenuForm());
        BoardManager.jFrame.getContentPane().add(MenuForm.getPanel());
        parent.validate();
        parent.repaint();
    }

    /**
     * Internal class checks whether the current state if image
     * is similar to the original image. Swaps the image pieces 
     * to location where user moves them.
     */
    private class ClickAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		@Override
        public void actionPerformed(ActionEvent e) {	
            checkButton(e);
            try {
                checkSolution();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }//end method actionPerformed()

        private void checkButton(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int bidx 	   = buttons.indexOf(button);           
        	if(firstClick == true) {
                Collections.swap(buttons, bidx, lastClicked);
                numberOfMoves++; //increment the counter for moves
                firstClick = false;
                updateButtons();
                return;
        	} else {
        		firstClick = true;
        		button.setBorder(BorderFactory.createLineBorder(Color.cyan));
        	}
            lastClicked = bidx;
        }//end method checkButton()
    }//end class ClickAction

    private void updateButtons() {
        panel.removeAll();
        for (JComponent btn : buttons) {
            panel.add(btn);
        }
        panel.validate();
    }//end method updateButtons()
    
    /**
     * Calculates scores on the basis of number of moves made
     * and seconds taken for solving the puzzle. For each second
     * 100 points are reduced. For each move 100 points are reduced.
     * Points are different as per level selected.
     * @return int: score calculated
     */
    private int calculateScore(){
        return (maxScore - 100*(numberOfMoves) - 100*(seconds));
    }//end method claculateScore()
    
    /**
     * Checks if the puzzle has been solved successfully or not.
     * Displays score.
     * @throws IOException
     */
    private void checkSolution() throws IOException {
        List<Point> current = new ArrayList<>();
        
        for (JComponent btn : buttons) {
            current.add((Point) btn.getClientProperty("position"));
        }

        if (compareList(solution, current)) {
            timer.cancel();
            int score = calculateScore();
            JOptionPane.showMessageDialog(panel, "You finished in "+ seconds +" seconds\n and it took "+
            							 numberOfMoves+" moves\n Your score is "+ score,
            							 "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            seconds          = 0;
            Container parent = this.getParent();
            parent.removeAll();
            user.setScore(score);
            ScoreMenu.getScoreMenu().updateScore();//constructor called
            parent.add(ScoreMenu.getScoreMenu());
            BoardManager.jFrame.getContentPane().add(ScoreMenu.getPanel());
			ScoreBoard.write(user);
            parent.validate();
            parent.repaint();
        }
    }//end method checkSolution()

    public static boolean compareList(List<Point> ls1, List<Point> ls2) {        
        return ls1.toString().contentEquals(ls2.toString());
    }//end method compareList()
    
}//end class PuzzlePanel

/**
 * Increments the timer for each second.
 */
class addSecond extends TimerTask{
    public void run() {
        PuzzlePanel.seconds++;
    }//end method run()
}//end class addSecond