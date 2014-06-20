package iPacMan;

/**
 * Class representing a GUICell with a mega buff.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public class MegaCell extends GUICell{

	/**
	 * Standard constructor.
	 */
	public MegaCell(){
		super(new SourcedImageIcon().image("megaChip.png"));
	}
	
	/**
	 * Accept method for visitor pattern.
	 * @param v - CellVisitor.
	 */
	public void accept(CellVisitor v) {
		v.visit(this);
	}
}
