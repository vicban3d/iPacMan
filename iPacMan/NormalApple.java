package iPacMan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Class representing normal Apples.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public class NormalApple extends Apples implements ActionListener{

	private GUIBoard board; //Game's board.
	private PseudoBoard pBoard;	//Game's PseudoBoard.
	private int direction = 0; //player's movement direction.
	private ImageIcon pacmanImage; //player's image.
	private int bonusCounter = 0; //counter for bonus time.
	private Timer bonusTimer = new Timer(1000,this); //bonus timer.
	private Timer animate = new Timer(200,this); //animation timer.
	private int animationIndex; //animation index.
	private Timer deathTimer = new Timer(1000,this); //death timer.
	private int deathCounter = 0; //death counter.


	/**
	 * Constructor for the Normal Apple.
	 * @param gameBoard
	 * @param pseudoBoard
	 * @param x - player's x position.
	 * @param y - player's y position.
	 */
	public NormalApple(GUIBoard gameBoard, PseudoBoard pseudoBoard, int x, int y){
		board = gameBoard;
		pBoard = pseudoBoard;
		setX(x);
		setY(y);
		pacmanImage = new SourcedImageIcon().image("normalApple_r0.png");
		animate.start();
	}

	/**
	 * Getter for the game's board.
	 * @return board.
	 */
	public GUIBoard getBoard(){
		return board;
	}

	/**
	 * Getter for the game's pseudo board.
	 * @return pBoard.
	 */
	public PseudoBoard getPseudoBoard(){
		return pBoard;
	}

	/**
	 * Player's movement method.
	 */
	public void move(){

		if (!isDead()){
			int nextDirection = board.getNextDirection();

			alignToGrid();

			//get surrounding cells coordinates.		
			int topCellX = board.getComponentAt(getX(), getY() - 1).getX();
			int bottomCellX = board.getComponentAt(getX(), getY() + Game.CELL_SIZE+1).getX();
			int leftCellY = board.getComponentAt(getX() - 1, getY()).getY();
			int cellRightY = board.getComponentAt(getX() + Game.CELL_SIZE+1, getY()).getY();

			Cell rightCell = pBoard.getGrid()[cellRightY/Game.CELL_SIZE][(getX()+Game.CELL_SIZE)/Game.CELL_SIZE];
			Cell leftCell = pBoard.getGrid()[leftCellY/Game.CELL_SIZE][(getX()-1)/Game.CELL_SIZE];
			Cell topCell = pBoard.getGrid()[(getY()-1)/Game.CELL_SIZE][topCellX/Game.CELL_SIZE];
			Cell bottomCell = pBoard.getGrid()[(getY()+Game.CELL_SIZE)/Game.CELL_SIZE][bottomCellX/Game.CELL_SIZE];

			//check whether player can move in each direction and aligned with the turn.
			boolean canMoveRight = !rightCell.isWall() && getY() == cellRightY;
			boolean canMoveDown = !bottomCell.isWall() && getX() == bottomCellX;
			boolean canMoveLeft = !leftCell.isWall() && getY() == leftCellY;
			boolean canMoveUp = !topCell.isWall() && getX() == topCellX;

			//change player's direction if possible.
			if (nextDirection == 1 && canMoveLeft){
				direction = nextDirection;	
			}
			if (nextDirection == 2 && canMoveDown){
				direction = nextDirection;	
			}
			if (nextDirection == 3 && canMoveRight){
				direction = nextDirection;	
			}
			if (nextDirection == 4 && canMoveUp){
				direction = nextDirection;
			}

			//change player's position.
			if (direction == 1 && canMoveLeft){
				if (getX()%Game.CELL_SIZE-getSpeed() > 0 && getX()%Game.CELL_SIZE-getSpeed() < board.getSpeedCorrection()){
					setX(getX() - getX()%Game.CELL_SIZE);
				}
				else{
					setX(getX() - getSpeed() - board.getSpeedCorrection());
				}	
			}
			if (direction == 2 && canMoveDown){
				if (Game.CELL_SIZE - getY()%Game.CELL_SIZE-getSpeed() > 0 && Game.CELL_SIZE - getY()%Game.CELL_SIZE-getSpeed() < board.getSpeedCorrection()){
					setY(getY() + Game.CELL_SIZE - getY()%Game.CELL_SIZE);
				}
				else{
					setY(getY() + getSpeed() + board.getSpeedCorrection());
				}
			}
			if (direction == 3 && canMoveRight){
				if (Game.CELL_SIZE - getX()%Game.CELL_SIZE-getSpeed() > 0 && Game.CELL_SIZE - getX()%Game.CELL_SIZE-getSpeed() < board.getSpeedCorrection()){
					setX(getX() + Game.CELL_SIZE - getX()%Game.CELL_SIZE);
				}
				else{
					setX(getX() + getSpeed() + board.getSpeedCorrection());
				}
			}
			if (direction == 4 && canMoveUp){
				if (getY()%Game.CELL_SIZE-getSpeed() > 0 && getY()%Game.CELL_SIZE-getSpeed() < board.getSpeedCorrection()){
					setY(getY() - getY()%Game.CELL_SIZE);
				}
				else{
					setY(getY() - getSpeed() - board.getSpeedCorrection());
				}
			}
			eatChip();
			checkTeleport();
		}
	}

	/**
	 * Aligns player's sprite to board's grid.
	 */
	private void alignToGrid(){

		if(direction == 1 && getX()%Game.CELL_SIZE <= super.getSpeed()){
			setX(getX() - getX()%Game.CELL_SIZE);
		}
		if(direction == 2 && Game.CELL_SIZE-getY()%Game.CELL_SIZE <= super.getSpeed()){
			setY(getY() + Game.CELL_SIZE-getY()%Game.CELL_SIZE);
		}
		if(direction == 3 && Game.CELL_SIZE-getX()%Game.CELL_SIZE <= super.getSpeed()){
			setX(getX() + Game.CELL_SIZE-getX()%Game.CELL_SIZE);
		}
		if(direction == 4 && getY()%Game.CELL_SIZE <= super.getSpeed()){
			setY(getY()-getY()%Game.CELL_SIZE);
		}
	}

	/**
	 * Allows a player to eat a chip from the board.
	 */
	private void eatChip(){
		int tx = getX()/Game.CELL_SIZE;
		int ty = getY()/Game.CELL_SIZE;

		if (direction == 1 && !(getX() == 24 && getY() == 336)) tx = tx+1; 		//adjust for teleport.
		if (direction == 4) ty = ty+1;

		if (!pBoard.getGrid()[ty][tx].isEmpty()){
			pBoard.getGrid()[ty][tx].setEmpty();
			eatChip(board.getGrid()[ty][tx]);	
		}
	}

	/**
	 * Checks whether the player has reached a teleport.
	 */
	private void checkTeleport(){
		if (getY() == 336){
			if (getX() == 624){
				setX(24);
			}
			else{
				if (getX() == 24){
					setX(624);	
				}
			}
		}
	}

	/**
	 * Event handler for animation and death.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == animate){
			animationIndex = (animationIndex+1) % 4;
			String c="";
			if (direction == 1) c = "l";
			if (direction == 2) c = "d";
			if (direction == 3 || direction == 0) c = "r";
			if (direction == 4) c = "u";
			pacmanImage.setImage(new SourcedImageIcon().image("normalApple_"+c+""+animationIndex+".png").getImage());	
		}
		if (e.getSource() == deathTimer){
			deathCounter++;
			if(deathCounter == 2){
				deathCounter = 0;
				setX(13*Game.CELL_SIZE);
				setY(23*Game.CELL_SIZE);
				board.setEnabled(true);
				board.resetDroids();
				board.startTimer();
				animate.start();
				deathTimer.stop();
				setDead(false);
			}
		}
	}

	/**
	 * Kills the player.
	 */
	public void die() {
		animate.stop();
		setSpeed(Game.STANDARD_SPEED);
		board.endGame();
		if (!isDead()) board.getGame().reduceLife();
		setDead(true);
		board.getGame().setBuffTimer(0);
		pacmanImage.setImage(new SourcedImageIcon().image("rip.png").getImage());	
		if (board.getGame().getLife() >= 0){
			deathTimer.start();
		}
	}


	/**
	 * Accept method for visitor pattern.
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	/**
	 * Visit method for NormalCell.
	 */
	@Override
	public void visit(NormalCell c) {
		c.becomeEaten();	
		board.reduceNumOfChips();
		board.getGame().increaseScore(Game.NORMAL_CELL_SCORE);
	}

	/**
	 * Visit method for SuperCell.
	 */
	@Override
	public void visit(SuperCell c) {
		board.setAppleType(new SuperApple(board, pBoard, getX(),getY()));	
		super.setSpeed(Game.BONUS_SPEED);
		board.getInky().setVulnerable(true); 
		board.getClyde().setVulnerable(true); 
		board.getBlinky().setVulnerable(false);
		board.getPinky().setVulnerable(false);
		c.becomeEaten();
		board.reduceNumOfChips();
		board.getGame().increaseScore(Game.SUPER_CELL_SCORE);

	}

	/**
	 * Visit method for MegaCell.
	 */
	@Override
	public void visit(MegaCell c) {
		board.setAppleType(new MegaApple(board, pBoard, getX(),getY()));
		super.setSpeed(Game.BONUS_SPEED);
		board.getInky().setVulnerable(true); 
		board.getClyde().setVulnerable(true); 
		board.getPinky().setVulnerable(true); 
		board.getBlinky().setVulnerable(true); 
		c.becomeEaten();
		board.reduceNumOfChips();
		board.getGame().increaseScore(Game.MEGA_CELL_SCORE);
	}

	/**
	 * Visit method for EmptyCell.
	 */
	@Override
	public void visit(EmptyCell c) {
	}

	/**
	 * Setter for game board.
	 * @param gameBoard.
	 */
	public void setBoard(GUIBoard gameBoard) {
		board = gameBoard;
	}

	/**
	 * Setter for game PseudoBoard.
	 * @param pseudoBoard.
	 */
	public void setPseudoBoard(PseudoBoard pseudoBoard) {
		pBoard = pseudoBoard;	
	}

	/**
	 * Getter for bonus Timer.
	 * @return bonusTimer.
	 */
	public Timer getBonusTimer(){
		return bonusTimer;
	}

	/**
	 * Getter for player's image.
	 */
	public ImageIcon getImage(){
		return pacmanImage;
	}

	/**
	 * Setter for player's image.
	 * @param imageIcon.
	 */
	public void setImage(ImageIcon imageIcon) {
		pacmanImage = imageIcon;
	}

	/**
	 * Getter for animation timer.
	 * @return animate.
	 */
	public Timer getAnimationTimer() {
		return animate;
	}

	/**
	 * Getter for animation index.
	 * @return animationIndex.
	 */
	public int getAnimationIndex() {
		return animationIndex;
	}

	/**
	 * Setter for animation index.
	 * @param i.
	 */
	public void setAnimationIndex(int i) {
		animationIndex = i;
	}

	/**
	 * Setter for bonus counter.
	 * @param i.
	 */
	public void setBonusCounter(int i) {
		bonusCounter = i;	
	}

	/**
	 * Getter for bonus counter.
	 * @return bonusCounter.
	 */
	public int getBonusCounter() {
		return bonusCounter;
	}

	/**
	 * Setter for player's direction.
	 * @param i.
	 */
	public void setDirection(int i) {
		direction = i;	
	}

	/**
	 * Getter for player's current direction.
	 * @return direction.
	 */
	public int getDirection() {
		return direction;
	}

}
