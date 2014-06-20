package iPacMan;

import javax.swing.ImageIcon;

/**
 * Abstract class representing Droids(enemies) in general.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public abstract class Droids implements Visitor{

	private int x; //Droid's x position.
	private int y; //Droid's y position.
	private boolean dead = false; //Droid's death indicator.
	private boolean vulnerable; //Droid's vulnerability indicator.
	private ImageIcon image = new ImageIcon(); //Droid's image.

	/**
	 * Getter for the Droid's image.
	 * @return image.
	 */
	public ImageIcon getImage(){
		return image;
	}

	/**
	 * Droid's move method implemented differently by each Droid type.
	 */
	public abstract void move();

	/**
	 * Setter for droids position.
	 * @param x.
	 * @param y.
	 */
	public void resetPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for Droid's x position.
	 * @return x.
	 */
	public int getX(){
		return x;	
	}
	

	/**
	 * Getter for Droid's y position.
	 * @return y.
	 */
	public  int getY(){
		return y;
	}
	
	/**
	 * Setter for Droid's x position.
	 * @param newX.
	 */
	public void setX(int newX){
		x = newX;
	}
	
	/**
	 * Setter for Droid's y position.
	 * @param newY.
	 */
	public void setY(int newY){
		y = newY;
	}

	/**
	 * Fight method uses the visitor pattern.
	 * @param v - the visited to fight.
	 */
	public void fight(Visited v){
		v.accept(this);
	}
	
	/**
	 * Setter for Droid's vulnerability.
	 * @param b - true/false.
	 */
	public void setVulnerable(boolean b) {
		vulnerable = b;
	}
	
	/**
	 * Checks whether the Droid is dead.
	 * @return dead.
	 */
	public boolean isDead(){
		return dead;
	}
	
	/**
	 * Checks whether the Droid is vulnerable.
	 * @return vulnerable.
	 */
	public boolean isVulnerable(){
		return vulnerable;
	}
	
	/**
	 * Setter for Droid's death indicator
	 * @param b - true/false.
	 */
	public void setDead(boolean b){
		dead = b;
	}
}