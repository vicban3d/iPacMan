package iPacMan;
import java.util.StringTokenizer;

/**
 * Class representing a pseudo-board for the game.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public class PseudoBoard {

	private Cell[][] grid; //a board of Cell.

	/**
	 * Constructor for the pseudo-board.
	 * @param boardBuildingString - String from which the board is built.
	 */
	public PseudoBoard(String boardBuildingString){
		grid = new Cell[31][28];
		StringTokenizer tok = new StringTokenizer(boardBuildingString);
		//Traverse the String and assign cells accordingly.
		for (int i=0; i<grid.length; i++){
			String row = tok.nextToken();
			for (int j=0; j<grid[i].length; j++){
				if (row.charAt(j) == '0'){
					grid[i][j] = new Cell(true);
				}
				else{
					grid[i][j] = new Cell(false);
				}
			}
		}
	}
	
	/**
	 * Getter for board's grid.
	 * @return grid.
	 */
	public Cell[][] getGrid(){
		return grid;
	}
}
