package iPacMan;

/**
 * Visitor Class for Droids and Apples.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
public interface Visitor {
	public void visit(NormalApple a);
	public void visit(SuperApple a);
	public void visit(MegaApple a);
	public void visit(WeakDroid a);
	public void visit(ToughDroid a);
}
