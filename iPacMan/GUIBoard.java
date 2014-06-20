package iPacMan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.StringTokenizer;
import javax.swing.*;

/**
 * Class representing a Graphic Game board.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public class GUIBoard extends JLabel implements ActionListener,KeyListener{

	private Game game; //Game to which the board belongs.
	private GUICell[][] grid; //board's Cell grid.
	private Timer moveTimer; //board's character movement Timer.
	private int nextDirection = 0; //player's next direction.
	private PseudoBoard pseudoBoard; //underlying pseudo-board for the board.

	private double systemTime = 0;
	private int deltaSpeed = 0;

	public int numOfChips = 0; //number of remaining chip on the board.
	private boolean gameOver = false; //Game Over indicator.
	private GridBagLayout gridBagLayout = new GridBagLayout(); //board's layout.
	private GridBagConstraints gridBagConstraints = new GridBagConstraints(); //board's layout constraints.
	private String BoardBuildingString; //String from which the board is built.

	//All Game's characters.
	Apples player;
	Droids clyde;
	Droids inky;
	Droids pinky;
	Droids blinky;


	/**
	 * Constructor for the GUIBoard.
	 * @param game - board's Game.
	 * @param boardBuildingString - String from which the board is built.
	 */
	public GUIBoard(Game game, String boardBuildingString){

		super(new SourcedImageIcon().image("boardBG.gif"));
		BoardBuildingString = boardBuildingString;
		pseudoBoard = new PseudoBoard(boardBuildingString);
		this.game = game;
		this.setLayout(gridBagLayout);
		moveTimer = new Timer(Game.GAME_TIMER_RATE,this);
		grid = new GUICell[31][28];

		moveTimer.setCoalesce(true);

		player = new NormalApple(this,pseudoBoard,13*Game.CELL_SIZE,23*Game.CELL_SIZE);
		clyde = new WeakDroid(this,pseudoBoard,13*Game.CELL_SIZE,11*Game.CELL_SIZE);
		inky = new WeakDroid(this,pseudoBoard,14*Game.CELL_SIZE,11*Game.CELL_SIZE);
		pinky = new ToughDroid(this,pseudoBoard,12*Game.CELL_SIZE,11*Game.CELL_SIZE);
		blinky = new ToughDroid(this,pseudoBoard,15*Game.CELL_SIZE,11*Game.CELL_SIZE);

		placeChips(boardBuildingString);
	}

	/**
	 * Places chips on the board according to the board building string.
	 * @param boardBuildString.
	 */
	public void placeChips(String boardBuildString){
		StringTokenizer tokenizer = new StringTokenizer(boardBuildString);
		for (int i=0; i < grid.length; i++){
			String row = tokenizer.nextToken();
			for (int j=0; j < grid[i].length; j++){
				if (row.charAt(j) == '0'){
					grid[i][j] = new EmptyCell();
				}
				if (row.charAt(j) == '1'){
					grid[i][j] = new NormalCell();
					numOfChips++;
				}
				if (row.charAt(j) == '2'){
					grid[i][j] = new EmptyCell();
				}
				if (row.charAt(j) == '3'){
					grid[i][j] = new SuperCell();
					numOfChips++;
				}
				if (row.charAt(j) == '4'){
					grid[i][j] = new MegaCell();
					numOfChips++;
				}

				grid[i][j].setPreferredSize(new Dimension(Game.CELL_SIZE,Game.CELL_SIZE));
				gridBagConstraints.gridx = j;
				gridBagConstraints.gridy = i;					
				gridBagLayout.setConstraints(grid[i][j], gridBagConstraints);
				this.add(grid[i][j]);
			}
		}
	}

	/**
	 * KeyPressed event handler(37 - 1 = left /40 - 2 = down /39 - 3 = right /38 - 4 = up)
	 */
	public void keyPressed(KeyEvent e) {
		if (!player.isDead()){
			if (!gameOver) moveTimer.start();

			if (e.getKeyCode() == 37){
				nextDirection = 1;
			}
			if (e.getKeyCode() == 38){
				nextDirection = 4;
			}
			if (e.getKeyCode() == 39) {
				nextDirection = 3;
			}
			if (e.getKeyCode() == 40){
				nextDirection = 2;
			}
		}
	}

	/**
	 * Override Paint method and paint on character on the board.
	 */
	public void paint(Graphics g){
		super.paint(g);
		clyde.getImage().paintIcon(this, g, clyde.getX(), clyde.getY());
		inky.getImage().paintIcon(this, g, inky.getX(), inky.getY());
		pinky.getImage().paintIcon(this, g, pinky.getX(), pinky.getY());
		blinky.getImage().paintIcon(this, g, blinky.getX(), blinky.getY());
		player.getImage().paintIcon(this, g, player.getX(), player.getY());
	}

	public int getSpeedCorrection(){
		return deltaSpeed;
	}

	/**
	 * ActionPerformed event handler for the Game's timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (systemTime != 0){
			deltaSpeed = (int)((System.currentTimeMillis() - systemTime)/Game.CELL_SIZE*Game.STANDARD_SPEED) - Game.STANDARD_SPEED;
		}
		if (deltaSpeed <= player.getSpeed()/4){
			deltaSpeed = 0;
		}
		else{
			if (deltaSpeed >= player.getSpeed()/4 && deltaSpeed < player.getSpeed()*3/4){
				deltaSpeed = player.getSpeed()/2;
			}
			else{
				if (deltaSpeed >= player.getSpeed()*3/4){
					deltaSpeed = player.getSpeed();
				}
			}
		}

		systemTime = System.currentTimeMillis();

		player.move();
		clyde.move();
		inky.move();
		pinky.move();
		blinky.move();
		checkInteractions();
		repaint();
	}

	/**
	 * Checks characters interactions and acts accordingly.
	 */
	private void checkInteractions(){
		if (haveMet(player,clyde))
		{
			clyde.fight(player);
		}
		if (haveMet(player,inky))
		{
			inky.fight(player);
		}
		if (haveMet(player,pinky))
		{
			pinky.fight(player);
		}
		if (haveMet(player,blinky))
		{
			blinky.fight(player);
		}
	}

	/**
	 * Checks whether given player met a given droid.
	 * @param a - player.
	 * @param d - enemy.
	 * @return true/false.
	 */
	private boolean haveMet(Apples a, Droids d){
		return Math.abs(d.getX() - a.getX()) < Game.CELL_SIZE/2 && Math.abs(d.getY() - a.getY()) < Game.CELL_SIZE/2;
	}

	/**
	 * Getter for board's Cell grid.
	 * @return grid.
	 */
	public GUICell[][] getGrid(){
		return grid;
	}

	/**
	 * Stops game timer.
	 */
	public void stopTimer(){
		moveTimer.stop();
	}

	/**
	 * Starts game timer.
	 */
	public void startTimer(){
		moveTimer.start();
	}

	/**
	 * Reduces number of chips by 1 and checks win condition.
	 */
	public void reduceNumOfChips(){
		numOfChips--;
		game.checkWin(numOfChips);
	}

	/**
	 * Ends the Game.
	 */
	public void endGame() {
		stopTimer();
		setEnabled(false);
		gameOver = true;
		systemTime = 0;
		deltaSpeed = 0;
	}

	/**
	 * Returns current board's Game.
	 * @return game.
	 */
	public Game getGame(){
		return game;
	}
	/**
	 * Getter for board's player character.
	 * @return player.
	 */
	public Apples getPlayer() {
		return player;
	}

	/**
	 * Setter for player's next direction of movement.
	 * @return nextDirection.
	 */
	public int getNextDirection(){
		return nextDirection;
	}

	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Resets Droids position and state.
	 */
	public void resetDroids(){
		blinky.resetPosition(15*Game.CELL_SIZE,11*Game.CELL_SIZE);
		pinky.resetPosition(12*Game.CELL_SIZE,11*Game.CELL_SIZE);
		inky.resetPosition(14*Game.CELL_SIZE,11*Game.CELL_SIZE);
		clyde.resetPosition(13*Game.CELL_SIZE,11*Game.CELL_SIZE);
		blinky.setVulnerable(false);
		clyde.setVulnerable(false);
		inky.setVulnerable(false);
		pinky.setVulnerable(false);
	}

	/**
	 * Setter for the player type.
	 * @param a - player.
	 */
	public void setAppleType(Apples a){
		player = a;
	}

	/**
	 * Getter for Inky.
	 * @return inky.
	 */
	public Droids getInky() {
		return inky;
	}

	/**
	 * Getter for clyde.
	 * @return clyde.
	 */
	public Droids getClyde() {
		return clyde;
	}

	/**
	 * Getter for pinky.
	 * @return pinky.
	 */
	public Droids getPinky() {
		return pinky;
	}

	/**
	 * Getter for blinky.
	 * @return blinky.
	 */
	public Droids getBlinky() {
		return blinky;
	}

	/**
	 * Resets board to initial state.
	 */
	public void reset() {
		for (int i=0; i < grid.length; i++){
			for (int j=0; j < grid[i].length; j++){
				this.remove(grid[i][j]);
				pseudoBoard.getGrid()[i][j].reset();
			}	
		}
		revalidate();
		repaint();
		grid = new GUICell[31][28];
		numOfChips = 0;
		moveTimer.stop();
		placeChips(BoardBuildingString);
		resetDroids();
		player = new NormalApple(this,pseudoBoard,13*Game.CELL_SIZE,23*Game.CELL_SIZE);
		player.setDead(false);
		gameOver = false;
		nextDirection = 0;
		setEnabled(true);
	}
}
