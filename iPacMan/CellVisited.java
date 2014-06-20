package iPacMan;

/**
 * Visited Class for the Cell.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public interface CellVisited {

	/**
	 * Accept methos for visitor pattern.
	 * @param v - visitor.
	 */
	public void accept(CellVisitor v);
}
