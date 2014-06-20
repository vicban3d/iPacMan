package iPacMan;

/**
 * Class representing an empty Cell.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public class EmptyCell extends GUICell{
	
	/**
	 * Cell's constructor.
	 */
	public EmptyCell(){
		super(null);
	}
	
	/**
	 * Accept method for visitor pattern.
	 *  @param v - Cell's visitor.
	 */
	public void accept(CellVisitor v) {
		v.visit(this);
	}
}
