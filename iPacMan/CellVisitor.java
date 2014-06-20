package iPacMan;

/**
 * Visitor class for Cell.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public interface CellVisitor {

	//Various visitor methods for all kinds of Apples.
	public void visit(NormalCell c);
	public void visit(SuperCell c);
	public void visit(MegaCell c);
	public void visit(EmptyCell c);

}
