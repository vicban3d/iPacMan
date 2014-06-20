package iPacMan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class representing Super Apples.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public class SuperApple extends NormalApple implements ActionListener{
	
	private Timer deathTimer = new Timer(1000,this); //player's death timer.
	private int deathCounter = 0; //death counter.

	/**
	 * Constructor for the Super Apple.
	 * @param gameBoard
	 * @param pseudoBoard
	 * @param x - player's x position.
	 * @param y - player's y position.
	 */
	public SuperApple(GUIBoard gameBoard, PseudoBoard pseudoBoard, int x, int y){
		super(gameBoard,pseudoBoard,x,y);
		setBoard(gameBoard);
		setPseudoBoard(pseudoBoard);
		setX(x);
		setY(y);
		getBonusTimer().start();
		setBonusCounter(0);
		setImage(new ImageIcon());
		getAnimationTimer().start();
	}
	
	/**
	 * Event handler for animation, bonus timer and death.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getAnimationTimer()){
		setAnimationIndex((getAnimationIndex()+1) % 4);
		String c="";
		if (getDirection() == 1) c = "l";
		if (getDirection() == 2) c = "d";
		if (getDirection() == 3 || getDirection() == 0) c = "r";
		if (getDirection() == 4) c = "u";

		getImage().setImage(new SourcedImageIcon().image("superApple_"+c+""+getAnimationIndex()+".png").getImage());	
		}
		
		if (e.getSource() == getBonusTimer()){
			setBonusCounter(getBonusCounter()+1);
			getBoard().getGame().setBuffTimer(Game.BONUS_TIME - getBonusCounter());			

			if (getBonusCounter() == Game.BONUS_TIME && !getBoard().getPlayer().isDead()){
				getBonusTimer().stop();
				setBonusCounter(0);
				super.setSpeed(Game.STANDARD_SPEED);
				getBoard().getGame().setBuffTimer(0);
				getBoard().setAppleType(new NormalApple(getBoard(), getPseudoBoard(), getBoard().getPlayer().getX(),getBoard().getPlayer().getY()));
				getBoard().getBlinky().setVulnerable(false);
				getBoard().getClyde().setVulnerable(false);
				getBoard().getInky().setVulnerable(false);
				getBoard().getPinky().setVulnerable(false);
			}
		}
		if (e.getSource() == deathTimer){
			deathCounter++;
			if (deathCounter == 3){
				deathCounter = 0;
				setDead(false);
				setX(13*Game.CELL_SIZE);
				setY(23*Game.CELL_SIZE);
				getBoard().setAppleType(new NormalApple(getBoard(), getPseudoBoard(),getX() ,getY()));
				getBoard().setEnabled(true);
				getBoard().resetDroids();
				getBoard().startTimer();
				getAnimationTimer().start();
				deathTimer.stop();
			}
		}
	}
	
	/**
	 * Kills the player.
	 */
	public void die() {
		getAnimationTimer().stop();
		super.setSpeed(Game.STANDARD_SPEED);
		getBoard().endGame();
		setDead(true);
		setBonusCounter(10);
		setDirection(0);
		getBoard().getGame().reduceLife();
		getBoard().getGame().setBuffTimer(0);
		getImage().setImage(new SourcedImageIcon().image("rip.png").getImage());	
		deathTimer.start();
	}

	/**
	 * Accept method for visitor pattern.
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	/**
	 * Visit method for SuperCell.
	 */
	@Override
	public void visit(SuperCell c) {
		super.visit(c);
		getBonusTimer().stop();
		setBonusCounter(0);
		getBoard().getGame().increaseScore(Game.SUPER_CELL_SCORE);
	}


	/**
	 * Visit method for MegaCell.
	 */
	@Override
	public void visit(MegaCell c) {
		super.visit(c);
		getBonusTimer().stop();
		setBonusCounter(0);
		getBoard().getGame().increaseScore(Game.MEGA_CELL_SCORE);
	}
}