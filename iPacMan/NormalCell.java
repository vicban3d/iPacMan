package iPacMan;

/**
 * Class representing normal GUICell.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public class NormalCell extends GUICell{
	
	/**
	 * Standard constructor.
	 */
	public NormalCell(){
		super(new SourcedImageIcon().image("token.png"));
	}
	
	/**
	 * Accept method for visitor pattern.
	 */
	public void accept(CellVisitor v) {
		v.visit(this);
	}	
}
