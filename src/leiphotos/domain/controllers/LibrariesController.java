package leiphotos.domain.controllers;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import leiphotos.domain.core.*;
import leiphotos.domain.facade.ILibrariesController;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.metadatareader.JpegMetadataException;
import leiphotos.utils.AbsSubject;

/**
 * Represents a controller for the libraries.
 */
public class LibrariesController implements ILibrariesController {

	private final MainLibrary mainLib;
	private final TrashLibrary trashLib;

	/**
	 * Constructor
	 * @param mainLib Main library
	 * @param trashLib Trash library
	 */
	public LibrariesController(MainLibrary mainLib, TrashLibrary trashLib) {
		this.mainLib = mainLib;
		this.trashLib = trashLib;
	}

	/**
	 * Imports a photo with the given title and path to the photo file.
	 *
	 * @param title The title of the photo.
	 * @param pathToPhotoFile The path to the photo file.
	 * @return An Optional containing the imported photo, or an empty Optional if the import fails.
	 */
	@Override
	public Optional<IPhoto> importPhoto(String title, String pathToPhotoFile) {
		// use createPhoto from PhotoFactory
		PhotoFactory photoFactory = PhotoFactory.INSTANCE;

		// Create photo
		Optional<IPhoto> photo;

		try {
			photo = Optional.ofNullable(photoFactory.createPhoto(title, pathToPhotoFile));
		} catch (FileNotFoundException | JpegMetadataException e) {
			photo = Optional.empty();
		}

		// Add photo to main library
		photo.ifPresent(mainLib::addPhoto);

		return photo;
	}

	/**
	 * Moves the selected photos from the main library to the trash library.
	 * Selected photos that do not exist in the main library are just ignored.
	 *
	 * @param selectedPhotos The set of selected photos to be deleted.
	 */
	@Override
	public void deletePhotos(Set<IPhoto> selectedPhotos) {
		selectedPhotos.forEach(photo -> {
			mainLib.deletePhoto(photo);
			trashLib.addPhoto(photo);
		});
	}

	/**
	 * Empties the trash by permanently deleting all photos in the trash.
	 */
	@Override
	public void emptyTrash() {
		trashLib.deleteAll();
	}

	/**
	 * Toggles the favourite status of the selected photos.
	 *
	 * @param selectedPhotos The set of selected photos to toggle the favourite status.
	 */
	@Override
	public void toggleFavourite(Set<IPhoto> selectedPhotos) {
		selectedPhotos.forEach(photo -> {
			photo.toggleFavourite();

			// Implementado DEPOIS do teste:

			// even after extending AbsSubject, the PhotoChangedLibraryEvent is not public,
			// so we cant emit it from here
			// emitEvent(new PhotoChangedLibraryEvent(photo, this));
			// The best solution would be for the event to be emmited by the photo itself

		});
	}

	/**
	 * Returns the main library.
	 *
	 * @return The main library.
	 */
	@Override
	public Iterable<IPhoto> getMatches(String regExp) {
		return mainLib.getMatches(regExp);
	}

	/**
	 * Returns the string representation of the two libraries.
	 *
	 * @return The string representation of the two libraries.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("****** MAIN PHOTO LIBRARY: ").append(mainLib.getNumberOfPhotos()).append(" photos *****\n");
		mainLib.getPhotos().forEach(photo -> sb.append(photo).append("\n"));
		sb.append("****** TRASH PHOTO LIBRARY: ").append(trashLib.getNumberOfPhotos()).append(" photos *****\n");
		trashLib.getPhotos().forEach(photo -> sb.append(photo).append("\n"));

		return sb.toString();
	}

}
