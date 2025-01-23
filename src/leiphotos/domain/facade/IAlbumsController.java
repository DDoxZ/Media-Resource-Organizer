/**
 * The interface for managing albums in the in the LeiPhotos application.
 * 
 * This interface provides methods for creating, removing, and selecting albums,
 * as well as adding and removing photos from albums. It also allows for the creation
 * of smart albums based on specified criteria. Additionally, it provides methods
 * for retrieving the list of photos in the currently selected album, the name of
 * the currently selected album, and the names of all existing albums.
 * 
 * @author malopes
 */
package leiphotos.domain.facade;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The interface for managing albums in the photo application.
 * 
 * 
 * @author malopes
 */
public interface IAlbumsController {

	/**
	 * Creates a new album with the specified name.
	 * If an album already exists with the same name, 
	 * it is not created.
	 *
	 * @param name the name of the album
	 * @return true if the album was created, false otherwise
	 */
	public boolean createAlbum(String name);

	/**
	 * Removes the currently selected album.
	 * If no album is selected, nothing happens.
	 */
	public void removeAlbum();

	/**
	 * Selects the album with the specified name.
	 * If no album exists with the specified name, 
	 * nothing happens.
	 *
	 * @param name the name of the album to select
	 */
	public void selectAlbum(String name);

	/**
	 * Adds the selected photos to the currently selected album.
	 * If no album is selected, nothing happens.
	 * 
	 * @param selectedPhotos the set of photos to add
	 */
	public void addPhotos(Set<IPhoto> selectedPhotos);

	/**
	 * Removes the selected photos from the currently selected album.
	 * If no album is selected, nothing happens.
	 * 
	 * @param selectedPhotos the set of photos to remove
	 */
	public void removePhotos(Set<IPhoto> selectedPhotos);

	/**
	 * Returns the list of photos in the currently selected album. 
	 * If no album is selected, an empty list is returned.
	 * 
	 * @return the list of photos
	 * @ensures \result != null 
	 */
	public List<IPhoto> getPhotos();

	/**
	 * Returns the name of the currently selected album, if any.
	 * If no album is selected, returns empty.
	 *
	 * @return an optional containing the name of the selected album, 
	 * or empty if no album is selected
	 */
	public Optional<String> getSelectedAlbum();

	/**
	 * Creates a new smart album with the specified name and criteria.
	 * If an album already exists with the same name, it is not created.
	 *
	 * @param name     the name of the smart album
	 * @param criteria the criteria used to select photos for the smart album
	 * @return true if the album was created, false otherwise
	 */
	boolean createSmartAlbum(String name, Predicate<IPhoto> criteria);

	/**
	 * Returns the names of all existing albums.
	 *
	 * @return a set of album names
	 */
	 public Set<String> getAlbumNames();
}
