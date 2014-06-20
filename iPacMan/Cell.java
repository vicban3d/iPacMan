package iPacMan;

/**
 * Class representing a pseudo-cell on the game board.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public class Cell {

	private boolean wall; //indicates whether the Cell is a wall.
	private boolean empty; //indicated whether the cell is empty.

	/**
	 * Constructor for the Cell.
	 * @param isWall - set this cell as a wall or a normal cell.
	 */
	public Cell(boolean isWall){
		wall = isWall;
	}

	/**
	 * Checks whether the Cell is a wall.
	 * @return wall.
	 */
	public boolean isWall(){
		return wall;
	}

	/**
	 * Sets this Cell as an empty cell.
	 */
	public void setEmpty() {
		empty = true;
	}

	/**
	 * Returns cell to non-empty state.
	 */
	public void reset(){
		empty = false;
	}

	/**
	 * Checks whether the cell is empty.
	 * @return - empty.
	 */
	public boolean isEmpty(){
		return empty;
	}
}
