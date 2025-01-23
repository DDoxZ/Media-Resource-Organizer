package leiphotos.domain.facade;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.utils.AbsSubject;

import java.util.Optional;
import java.util.Set;

/**
 * The interface for the Libraries Controller in the LeiPhotos application.
 * This interface provides methods to import, delete, empty trash, toggle favorite, 
 * and search photos in libraries.
 */
public interface ILibrariesController {

	/**
	 * Imports a photo with the given title and path to the photo file.
	 * 
	 * @param title The title of the photo.
	 * @param pathToPhotoFile The path to the photo file.
	 * @return An Optional containing the imported photo, or an empty Optional if the import fails.
	 */
	Optional<IPhoto> importPhoto(String title, String pathToPhotoFile);

	/**
	 * Moves the selected photos from the main library to the trash library
	 * Selected photos that do not exist in the main library are just ignored.
	 * 
	 * @param selectedPhotos The set of selected photos to be deleted.
	 */
	void deletePhotos(Set<IPhoto> selectedPhotos);
	
	/**
	 * Empties the trash by permanently deleting all photos in the trash.
	 */
	void emptyTrash();

	/**
	 * Toggles the favorite status of the selected photos that are in the main library.
	 * Selected photos that do not exist in the main library are just ignored.
	 * 
	 * @param selectedPhotos The set of selected photos to toggle the favorite status.
	 */
	void toggleFavourite(Set<IPhoto> selectedPhotos);
	
	/**
	 * Searches for photos that match the given regular expression.
	 * 
	 * @param regExp The regular expression to match against the photo titles.
	 * @requires regExp != null and regExp is a regular expression.
	 * @return An Iterable containing the photos that match the regular expression.
	 */
	Iterable<IPhoto> getMatches(String regExp);
}