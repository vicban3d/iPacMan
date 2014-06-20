package iPacMan;
import javax.swing.ImageIcon;

/**
 * Class thats assists with image resource handling.
 * @author Victor Banshats
 * @author Gavriel Giladov
 */
public class SourcedImageIcon{
	/**
	 * This method gets an image resource from a given String path.
	 * @param path - path of the given image.
	 * @return new ImageIcon with the given path as a Resource.
	 */
	public ImageIcon image(String path){
		return new ImageIcon(getClass().getClassLoader().getResource(path));
	}
}
