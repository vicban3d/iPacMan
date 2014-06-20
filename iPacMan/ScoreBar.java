package iPacMan;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

/**
 * Class representing a scores bar for the Game.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public class ScoreBar extends JLabel{

	//score bar's layout.
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints cons = new GridBagConstraints();

	//Labels and buttons.
	private JLabel scoreLabel;	
	private JLabel scoreText;	
	private JLabel buffLabel;	
	private JLabel lifeLabel;	
	private JLabel lifeText;
	private JButton restart;
	private JButton quit;

	//counters for statistics.
	private int bonusTime = Game.BONUS_TIME;
	private int score = 0;
	private int life = Game.INITIAL_LIVES;

	/**
	 * Constructor for the ScoreBar.
	 * @param game - the game to which the bar belongs.
	 */
	public ScoreBar(Game game){
		super(new SourcedImageIcon().image("scoresBG.png"));
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(672,50));

		//initiate components.
		scoreLabel = new JLabel("   0   ",JLabel.CENTER);
		scoreText = new JLabel("SCORE",JLabel.CENTER);
		buffLabel = new JLabel("NO BONUS",JLabel.CENTER);
		lifeLabel = new JLabel(" "+life,JLabel.CENTER);
		lifeText = new JLabel("LIVES",JLabel.CENTER);
		restart = new JButton("Restart");
		quit = new JButton("Quit");

		scoreLabel.setPreferredSize(new Dimension(40,40));
		scoreText.setPreferredSize(new Dimension(40,40));
		buffLabel.setPreferredSize(new Dimension(60,40));
		lifeLabel.setPreferredSize(new Dimension(40,40));
		lifeText.setPreferredSize(new Dimension(20,40));
		restart.setPreferredSize(new Dimension(40,40));
		quit.setPreferredSize(new Dimension(40,40));

		restart.setFocusable(false);
		quit.setFocusable(false);

		restart.addActionListener(game);
		quit.addActionListener(game);

		scoreLabel.setFont(new Font(scoreLabel.getFont().getName(),Font.BOLD,18));
		buffLabel.setFont(new Font(scoreLabel.getFont().getName(),Font.BOLD,18));
		scoreText.setFont(new Font(scoreLabel.getFont().getName(),Font.BOLD,18));
		lifeLabel.setFont(new Font(scoreLabel.getFont().getName(),Font.BOLD,18));
		lifeText.setFont(new Font(scoreLabel.getFont().getName(),Font.BOLD,18));

		setConstraints(0,quit,GridBagConstraints.WEST);
		setConstraints(1,scoreText,GridBagConstraints.WEST);
		setConstraints(2,scoreLabel,GridBagConstraints.WEST);
		setConstraints(3,buffLabel,GridBagConstraints.CENTER);
		setConstraints(4,lifeText,GridBagConstraints.EAST);
		setConstraints(5,lifeLabel,GridBagConstraints.EAST);
		setConstraints(6,restart,GridBagConstraints.EAST);

		this.add(quit);
		this.add(scoreText);
		this.add(scoreLabel);
		this.add(buffLabel);
		this.add(lifeText);
		this.add(lifeLabel);
		this.add(restart);

	}

	/**
	 * Sets GridBagConstraints for the given Container.
	 * @param i - x position.
	 * @param c - container.
	 * @param anchor - alignment.
	 */
	private void setConstraints(int i, Container c, int anchor) {
		cons.gridx = i;
		cons.gridy = 0;
		cons.ipadx = 40;
		cons.weightx = 1;
		cons.anchor = anchor;
		layout.setConstraints(c, cons);
	}

	/**
	 * Increases Game score by a given amount.
	 * @param change - the score to add.
	 */
	public void increseScore(int change){
		score = score + change;
		scoreLabel.setText(""+score);
		repaint();
	}

	/**
	 * Setter for remaining bonus time.
	 * @param time.
	 */
	public void setBuffTimer(int time){
		if (time != 0){
			bonusTime = time;
			buffLabel.setText(""+bonusTime);
		}
		else{
			buffLabel.setText("NO BONUS");
		}
		repaint();
	}

	/**
	 * Resets score bar to initial values.
	 */
	public void reset(){
		bonusTime = Game.BONUS_TIME;
		score = 0;
		life = Game.INITIAL_LIVES;
		buffLabel.setText("NO BONUS");
		scoreLabel.setText("0");
		lifeLabel.setText(" "+Game.INITIAL_LIVES);
		repaint();
	}

	/**
	 * Deducts one life.
	 */
	public void reduceLife(){
		life --;
		if (life == -1){
			lifeLabel.setText("--");
		}
		else{
			lifeLabel.setText(""+life);
		}
		repaint();
	}

	/**
	 * Getter for the remaining lives.
	 * @return life.
	 */
	public int getLives() {
		return life;
	}

	/**
	 * Getter for Game's current score.
	 * @return scoreLabel text.
	 */
	public String getScore() {
		return scoreLabel.getText();
	}

	/**
	 * Getter for the score bar's restart button.
	 * @return restart.
	 */
	public JButton getRestartButton(){
		return restart;
	}

	/**
	 * Getter for the score bar's quit button.
	 * @return quit.
	 */
	public JButton getQuitButton(){
		return quit;
	}

}
