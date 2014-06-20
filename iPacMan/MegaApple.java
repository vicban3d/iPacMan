package iPacMan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class representing a Mega Apple.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public class MegaApple extends NormalApple implements ActionListener{

	/**
	 * Mega Apple constructor.
	 * @param gameBoard.
	 * @param pseudoBoard.
	 * @param x - Apple's x position.
	 * @param y = Apple's y position.
	 */
	public MegaApple(GUIBoard gameBoard, PseudoBoard pseudoBoard, int x, int y){
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
	 * Getter for player's bonus timer.
	 * @return bonusTimer
	 */
	//public Timer getBonusTimer(){
	//	return bonusTimer;
	//}
	
	/**
	 * ActionPerformed event handler for animation and calculations.
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

		getImage().setImage(new SourcedImageIcon().image("megaApple_"+c+""+getAnimationIndex()+".png").getImage());
		}
		
		if (e.getSource() == getBonusTimer()){
			setBonusCounter(super.getBonusCounter()+1);
			getBoard().getGame().setBuffTimer(Game.BONUS_TIME - getBonusCounter());			

			if (getBonusCounter() == Game.BONUS_TIME && !getBoard().getPlayer().isDead()){
				getBonusTimer().stop();
				setBonusCounter(0);
				setSpeed(Game.STANDARD_SPEED);
				getBoard().getGame().setBuffTimer(0);
				getBoard().setAppleType(new NormalApple(getBoard(), getPseudoBoard(), getBoard().getPlayer().getX(),getBoard().getPlayer().getY()));
				getBoard().getBlinky().setVulnerable(false);
				getBoard().getClyde().setVulnerable(false);
				getBoard().getInky().setVulnerable(false);
				getBoard().getPinky().setVulnerable(false);
			}
		}
	}
	
	/**
	 * Accept method for Visitor pattern.
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	/**
	 * Visit method for CellVisitor pattern.
	 */
	@Override
	public void visit(SuperCell c) {
		super.visit(c);
		getBonusTimer().stop();
		setBonusCounter(0);
	}
	
	/**
	 * Visit method for CellVisitor pattern.
	 */
	@Override
	public void visit(MegaCell c) {
		super.visit(c);
		getBonusTimer().stop();
		setBonusCounter(0);
	}
}