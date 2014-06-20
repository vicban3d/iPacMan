package iPacMan;

/**
 * Class representing Super GUICell.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public class SuperCell extends GUICell{
	
	/**
	 * Standard constructor.
	 */
	public SuperCell(){
		super(new SourcedImageIcon().image("superChip.png"));
	}
	
	/**
	 * Accept method for visitor pattern.
	 */
	public void accept(CellVisitor v) {
		v.visit(this);
	}
}
