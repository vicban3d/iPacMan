package iPacMan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * Class representing Tough Droids.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public class ToughDroid extends Droids implements ActionListener {

	private GUIBoard board;
	private PseudoBoard pBoard;
	private int direction; //Droid's direction.
	private ImageIcon droidImage; //Droid's image.
	private Timer penaltyTimer = new Timer(1000,this); //penalty time after death.
	private int penaltyCounter = 0; //penalty time counter.
	private Timer animate = new Timer(200,this); //animation timer.
	private int animationIndex; //animation index.

	/**
	 * Constructor for the tough droid.
	 * @param gameBoard
	 * @param pseudoBoard
	 * @param x - droid's x position.
	 * @param y - droid's y position.
	 */
	public ToughDroid(GUIBoard gameBoard, PseudoBoard pseudoBoard, int x, int y){
		board = gameBoard;
		pBoard = pseudoBoard;
		setX(x);
		setY(y);
		droidImage = new ImageIcon();
		animate.start();
		direction = 1+(int)(Math.random()*4);
	}

	/**
	 * Getter for droid's image.
	 */
	public ImageIcon getImage(){
		return droidImage;
	}

	/**
	 * Movement method for the tough droid(seeks the player).
	 */
	public void move(){
		if (!isDead() ){
			alignToGrid();

			int dx = getX() - board.getPlayer().getX(); //x distance to player.
			int dy = getY() - board.getPlayer().getY(); //y distance to player.

			//get surrounding cells coordinates.		
			int topCellX = board.getComponentAt(getX(), getY() - 1).getX();
			int bottomCellX = board.getComponentAt(getX(), getY() + Game.CELL_SIZE+1).getX();
			int leftCellY = board.getComponentAt(getX() - 1, getY()).getY();
			int cellRightY = board.getComponentAt(getX() + Game.CELL_SIZE+1, getY()).getY();

			Cell rightCell = pBoard.getGrid()[cellRightY/Game.CELL_SIZE][(getX()+Game.CELL_SIZE)/Game.CELL_SIZE];
			Cell leftCell = pBoard.getGrid()[leftCellY/Game.CELL_SIZE][(getX()-1)/Game.CELL_SIZE];
			Cell topCell = pBoard.getGrid()[(getY()-1)/Game.CELL_SIZE][topCellX/Game.CELL_SIZE];
			Cell bottomCell = pBoard.getGrid()[(getY()+Game.CELL_SIZE)/Game.CELL_SIZE][bottomCellX/Game.CELL_SIZE];

			//check whether droid can move in each direction and aligned with the turn.
			boolean canMoveRight = !rightCell.isWall() && getY() == cellRightY;
			boolean canMoveDown = !bottomCell.isWall() && getX() == bottomCellX;
			boolean canMoveLeft = !leftCell.isWall() && getY() == leftCellY;
			boolean canMoveUp = !topCell.isWall() && getX() == topCellX;

			//move as necessary.
			if (Math.abs(dx) > Math.abs(dy)){
				if (dx > 0){
					if (!isVulnerable() && canMoveLeft){
						direction = 1;
					}
					else{ 
						if (isVulnerable() && canMoveRight){
							direction = 3;
						}
						else{
							if (canMoveUp && direction != 2){
								direction = 4;
							}
							else{
								if (canMoveDown && direction != 4){
									direction = 2;
								}
							}
						}
					}
				}
				else{
					if (dx <=0){
						if (!isVulnerable() && canMoveRight){
							direction = 3;
						}
						else{
							if (isVulnerable() && canMoveLeft){
								direction = 1;
							}
							else{
								if (canMoveUp && direction != 2){
									direction = 4;
								}
								else{
									if (canMoveDown && direction != 4){
										direction = 2;
									}
								}
							}
						}	
					}
				}
			}
			else{
				if (Math.abs(dx) < Math.abs(dy)){
					if (dy > 0){
						if (!isVulnerable() && canMoveUp){
							direction = 4;
						}
						else{ 
							if (isVulnerable() && canMoveDown){
								direction = 2;
							}
							else{

								if (canMoveLeft && direction != 3){
									direction = 1;
								}
								else{
									if (canMoveRight && direction != 1){
										direction =3;
									}
								}
							}
						}
					}
					else{
						if (dy <=0){
							if (!isVulnerable() && canMoveDown){
								direction = 2;
							}
							else{ 
								if (isVulnerable() && canMoveUp){
									direction = 4;
								}
								else{
									if (canMoveLeft && direction != 3){
										direction = 1;
									}
									else{
										if (canMoveRight && direction != 1){
											direction =3;
										}
									}
								}
							}
						}
					}
				}
				else{
					if(Math.abs(dx) == Math.abs(dy)){
						direction = (int)(Math.random()*4);
					}
				}
			}

			if (direction == 1 && canMoveLeft){
				if (getX()%Game.CELL_SIZE-Game.STANDARD_SPEED > 0 && getX()%Game.CELL_SIZE-Game.STANDARD_SPEED < board.getSpeedCorrection()){
					setX(getX() - getX()%Game.CELL_SIZE);
				}
				else{
					setX(getX() - Game.STANDARD_SPEED - board.getSpeedCorrection());
				}	
			}
			if (direction == 2 && canMoveDown){
				if (Game.CELL_SIZE - getY()%Game.CELL_SIZE-Game.STANDARD_SPEED > 0 && Game.CELL_SIZE - getY()%Game.CELL_SIZE-Game.STANDARD_SPEED < board.getSpeedCorrection()){
					setY(getY() + Game.CELL_SIZE - getY()%Game.CELL_SIZE);
				}
				else{
					setY(getY() + Game.STANDARD_SPEED + board.getSpeedCorrection());
				}
			}
			if (direction == 3 && canMoveRight){
				if (Game.CELL_SIZE - getX()%Game.CELL_SIZE-Game.STANDARD_SPEED > 0 && Game.CELL_SIZE - getX()%Game.CELL_SIZE-Game.STANDARD_SPEED < board.getSpeedCorrection()){
					setX(getX() + Game.CELL_SIZE - getX()%Game.CELL_SIZE);
				}
				else{
					setX(getX() + Game.STANDARD_SPEED + board.getSpeedCorrection());
				}
			}
			if (direction == 4 && canMoveUp){
				if (getY()%Game.CELL_SIZE-Game.STANDARD_SPEED > 0 && getY()%Game.CELL_SIZE-Game.STANDARD_SPEED < board.getSpeedCorrection()){
					setY(getY() - getY()%Game.CELL_SIZE);
				}
				else{
					setY(getY() - Game.STANDARD_SPEED - board.getSpeedCorrection());
				}
			}
		}
	}

	/**
	 * Aligns droid's sprite to board's grid.
	 */
	private void alignToGrid() {
		if(direction == 1 && getX()%Game.CELL_SIZE < Game.STANDARD_SPEED){
			setX(getX() - getX()%Game.CELL_SIZE);
		}
		if(direction == 2 && Game.CELL_SIZE-getY()%Game.CELL_SIZE < Game.STANDARD_SPEED){
			setY(getY() + Game.CELL_SIZE-getY()%Game.CELL_SIZE);
		}
		if(direction == 3 && Game.CELL_SIZE-getX()%Game.CELL_SIZE < Game.STANDARD_SPEED){
			setX(getX() + Game.CELL_SIZE-getX()%Game.CELL_SIZE);
		}
		if(direction == 4 && getY()%Game.CELL_SIZE < Game.STANDARD_SPEED){
			setY(getY()-getY()%Game.CELL_SIZE);
		}
	}

	/**
	 * Event handler for animation and death.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == animate){
			animationIndex = (animationIndex+1) % 4;
			if(!isDead()){
				if (!isVulnerable()){
					droidImage.setImage(new SourcedImageIcon().image("toughDroid"+animationIndex+".png").getImage());	
				}
				else{
					droidImage.setImage(new SourcedImageIcon().image("toughDroidv"+animationIndex+".png").getImage());	
				}
			}
		}
		if (e.getSource() == penaltyTimer){
			penaltyCounter++;

			if (penaltyCounter == 5){
				setX(13*Game.CELL_SIZE);
				setY(14*Game.CELL_SIZE);
			}
			if (penaltyCounter == 10){
				setX(13*Game.CELL_SIZE);
				setY(11*Game.CELL_SIZE);
				penaltyTimer.stop();
				penaltyCounter = 0;
				setDead(false);
			}
		}
	}

	/**
	 * Kills the droid.
	 */
	public void die() {
		setDead(true);
		penaltyTimer.start();
		droidImage.setImage(new SourcedImageIcon().image("scrap.png").getImage());	
	}

	/**
	 * Visit method for NormalApple.
	 */
	@Override
	public void visit(NormalApple a) {
		if (!isDead() && !a.isDead()){
			a.die();
		}
	}
	
	/**
	 * Visit method for SuperApple.
	 */
	@Override
	public void visit(SuperApple a) {
		if (!isDead() && !a.isDead()){
			a.die();
			a.getBonusTimer().stop();
		}
	}
	
	/**
	 * Visit method for MegaApple.
	 */
	@Override
	public void visit(MegaApple a) {
		if (!isDead()){
			die();	
			board.getGame().increaseScore(Game.TOUGH_DROID_SCORE);
		}
	}
	
	/**
	 * Visit method for WeakDroid.
	 */
	@Override
	public void visit(WeakDroid a) {
		direction = 1+(int)(Math.random()*4);
	}
	
	/**
	 * Visit method for ToughDroid.
	 */
	@Override
	public void visit(ToughDroid a) {
		direction = 1+(int)(Math.random()*4);
	}

}