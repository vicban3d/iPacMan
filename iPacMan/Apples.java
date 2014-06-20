package iPacMan;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 * Abstract class representing Apples(player) in general.
 * @author Victor Banshats
 * @author Gavriel Giladov.
 */
public abstract class Apples implements Visited, CellVisitor, ActionListener{
	private int x; 												    //player's x position.
	private int y; 							 					    //player's y position.
	private static int  speed = Game.STANDARD_SPEED;			    //players advancement in pixels.
	private boolean dead = false; 								    //player's death indicator.

	/**
	 * Getter for player's image.
	 * @return player's image.
	 */
	public abstract ImageIcon getImage();

	/**
	 * Abstract movement method implemented differently by each player type.
	 */
	public abstract void move();
	
	/**
	 * Setter for player's speed.
	 * @param sp - new speed.
	 */
	public void setSpeed(int sp){
		speed = sp;
	}

	/**
	 * Getter for player's speed.
	 * @return speed.
	 */
	public int getSpeed(){
		return speed;
	}

	/**
	 * Getter for player's x position.
	 * @return x.
	 */
	public int getX(){

		return x;	
	}

	/**
	 * Getter for player's y position.
	 * @return y.
	 */
	public  int getY(){
		return y;
	}

	/**
	 * Setter for player's x position.
	 * @param newX.
	 */
	public void setX(int newX){
		x = newX;
	}

	/**
	 * Setter for player's y position.
	 * @param newY.
	 */
	public void setY(int newY){
		y = newY;
	}

	/**
	 * Method to eat a chip from the game board using visitor pattern. 
	 * @param v - the visited cell.
	 */
	public void eatChip(CellVisited v){
		v.accept(this);
	}

	/**
	 * Check whether the player is dead.
	 * @return dead.
	 */
	public boolean isDead(){
		return dead;
	}
	
	/**
	 * Setter for player's death indicator.
	 * @param b - true/false.
	 */
	public void setDead(boolean b){
		dead = b;
	}
}
