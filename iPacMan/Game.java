package iPacMan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class representing a iPacMan game window.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener{

	private GUIBoard guiBoard; //Graphic game board.
	private ScoreBar scores; //Game's scores bar.
	private GridBagLayout layout = new GridBagLayout(); //window's layout.
	private GridBagConstraints cons = new GridBagConstraints(); //layout constraints.
	private JLabel winLabel = new JLabel(new SourcedImageIcon().image("winBG.png"));//Victory label.
	private JLabel loseLabel = new JLabel(new SourcedImageIcon().image("loseBG.png"));//Loss label.	
	
	private final int GAME_WIDTH = 840;
	private final int GAME_HEIGHT = 1030;
	public static int CELL_SIZE = 24; //size of each cell.
	public static int STANDARD_SPEED = 5;//in pixels
	public static int BONUS_SPEED = STANDARD_SPEED*6/5; //in pixels.
	public static int GAME_TIMER_RATE = CELL_SIZE;
	public static int INITIAL_LIVES = 3;
	public static int BONUS_TIME = 10; //in seconds.
	public static int NORMAL_CELL_SCORE = 10;
	public static int SUPER_CELL_SCORE = 50;
	public static int MEGA_CELL_SCORE = 100;
	public static int WEAK_DROID_SCORE = 150;
	public static int TOUGH_DROID_SCORE = 250;
	

	//String used to build the game board(0 = wall / 1 = chip cell / 2 = empty cell / 3 = super buff / 4 = mega buff).
	private String boardBuildString = 
			"0000000000000000000000000000 " +
					"0111111111111001111111111110 " +
					"0100001000001001000001000010 " +
					"0400001000001001000001000030 " +
					"0100001000001001000001000010 " +
					"0111111111111111111111111110 " +
					"0100001001000000001001000010 " +
					"0100001001000000001001000010 " +
					"0111111001111001111001111110 " +
					"0000001000002002000001000000 " +
					"0000001000002002000001000000 " +
					"0000001002222222222001000000 " +
					"0000001002000000002001000000 " +
					"0000001002000000002001000000 " +
					"0222221222000000002221222220 " +
					"0000001002000000002001000000 " +
					"0000001002000000002001000000 " +
					"0000001002222222222001000000 " +
					"0000001002000000002001000000 " +
					"0000001002000000002001000000 " +
					"0111111111111001111111111110 " +
					"0100001000001001000001000010 " +
					"0100001000001001000001000010 " +
					"0111001111111221111111001110 " +
					"0001001001000000001001001000 " +
					"0001001001000000001001001000 " +
					"0111111001111001111001111110 " +
					"0100000000001001000000000010 " +
					"0300000000001001000000000040 " +
					"0111111111111111111111111110 " +
					"0000000000000000000000000000";

	/**
	 * Game's empty constructor - sets all initial preferences.
	 */
	public Game(){
		super("iPacMan");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(layout);
		scores = new ScoreBar(this);
		guiBoard = new GUIBoard(this,boardBuildString);
		this.addKeyListener(guiBoard);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setResizable(false);

		setContainerConstraints(scores,0,0);
		setContainerConstraints(guiBoard,0,1);
		setContainerConstraints(loseLabel,0,1);
		setContainerConstraints(winLabel,0,1);
		
		this.getContentPane().add(loseLabel);
		this.getContentPane().add(winLabel);
		this.getContentPane().add(scores);
		this.getContentPane().add(guiBoard);
		
		loseLabel.setVisible(false);
		winLabel.setVisible(false);

		this.pack();
		this.setVisible(true);
	}

	/**
	 * Increases game score by a given amount.
	 * @param change - amount to increase by.
	 */
	public void increaseScore(int change){
		scores.increseScore(change);
	}

	/**
	 * sets buff timer of the scores to the given time.
	 * @param time.
	 */
	public void setBuffTimer(int time){
		scores.setBuffTimer(time);
	}

	/**
	 * Resets game scores.
	 */
	public void reset(){
		scores.reset();
	}

	/**
	 * Reduces 1 life from count and checks lose condition.
	 */
	public void reduceLife(){
		scores.reduceLife();
		if (scores.getLives() < 0){
			guiBoard.endGame();
			guiBoard.setVisible(false);
			loseLabel.setVisible(true);
			repaint();
		}
	}

	/**
	 * Checks win condition.
	 * @param numOfChips.
	 */
	public void checkWin(int numOfChips) {
		if (numOfChips == 0){
			guiBoard.endGame();
			guiBoard.setVisible(false);
			winLabel.setVisible(true);
			repaint();
		}
	}

	/**
	 * Return's remaining lives.
	 * @return scores.getLives().
	 */
	public int getLife() {
		return scores.getLives();
	}

	/**
	 * Returns current score.
	 * @return scores.getScore().
	 */
	public String getScore() {
		return scores.getScore();
	}

	/**
	 * Resets the game to initial settings.
	 */
	public void restartGame() {
		scores.reset();
		guiBoard.reset();
		this.getContentPane().repaint();
	}

	/**
	 * Defines actions performed by buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == scores.getRestartButton()){
			restartGame();
			winLabel.setVisible(false);
			loseLabel.setVisible(false);
			guiBoard.setVisible(true);
			this.requestFocus();
			repaint();
		}
		if (e.getSource() == scores.getQuitButton()){
			System.exit(0);
		}	
	}

	/**
	 * Sets container constraints for the layout.
	 * @param c - Container.
	 * @param gridx - Container's x position.
	 * @param gridy - Contaoner's y position.
	 * @param anchor - Containers alignment.
	 */
	public void setContainerConstraints(Container c, int gridx , int gridy, int anchor ){	
		cons.gridx = gridx;
		cons.gridy = gridy;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = anchor;
		layout.setConstraints(c, cons);
	}

	/**
	 * Sets container constraints for the layout.
	 * @param c - Container.
	 * @param gridx - Container's x position.
	 * @param gridy - Contaoner's y position.
	 */
	public void setContainerConstraints(Container c, int gridx , int gridy){	
		cons.gridx = gridx;
		cons.gridy = gridy;
		layout.setConstraints(c, cons);
	}
}
