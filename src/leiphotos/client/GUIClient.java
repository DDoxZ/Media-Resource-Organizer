package leiphotos.client;

import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import leiphotos.domain.facade.LEIPhotos;
import leiphotos.ui.UI;
/**
 * Class responsible for starting up the system (GUI version)
 * 
 * @author antonialopes
 */
public class GUIClient {

	/**
	 * The method for the start up of the system 
	 * @param args String[]
	 */
	public static void main(String[] args) {
	    try {
	        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    
	    // Set default button properties
	    UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 12));

	    final LEIPhotos leiPhoto = new LEIPhotos();

	    LoaderPhotos.load(leiPhoto.libsController());
	    System.out.println("Photos loaded");
	    
	    SwingUtilities.invokeLater(() -> {
	        UI ui = new UI(leiPhoto.libsController(),
	                leiPhoto.viewsController(), leiPhoto.albumsController());
	        ui.setVisible(true);
	        ui.loadAlbuns();
	    });
	}

}

