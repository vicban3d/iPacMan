package iPacMan;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Class representing a Graphic Cell.
 * @author Victor Banshats.
 * @author Gavriel Giladov.
 */
@SuppressWarnings("serial")
public abstract class GUICell extends JLabel implements CellVisited{
	
	/**
	 * Cunstructor for the GUICell.
	 * @param img - GUICell image.
	 */
	public GUICell(ImageIcon img){
		super(img);
	}
	
	/**
	 * Sets GUICell as eaten.
	 */
	public void becomeEaten(){
		this.setIcon(null);
	}
}
