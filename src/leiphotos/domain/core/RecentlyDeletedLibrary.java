package leiphotos.domain.core;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.metadatareader.JpegMetadataException;

/**
 * Represents a recently deleted library that extends the Library interface
 * with a new method for emptying the trash. In this type of library,
 * photos are automatically removed (for instance, after a certain
 * period of time).
 */
public class RecentlyDeletedLibrary extends ATrashLibrary implements Library, TrashLibrary {

	private LocalDateTime lastCleaned = LocalDateTime.now();
	private final int CLEANING_TIME = 10; // seconds
	private final int CLEANING_AFTER = 15; // seconds

	// addedDates for the photos in the library
	private HashMap<IPhoto, LocalDateTime> addedDates = new HashMap<>();

	/**
	 * Constructor, takes no arguments
	 */
	public RecentlyDeletedLibrary() {
		super(new ArrayList<>());
	}

	/**
	 * Returns the number of photos in the library
	 * May not be accurate if the photos are supposed to be deleted
	 * @return number of photos
	 */
	@Override
	public int getNumberOfPhotos() {
		return photos.size();
	}

	/**
	 * Adds a photo to the library, if it does not exist yet (based on equals)
	 * @param photo Photo to add
	 * @return if the photo was added
	 */
	@Override
	public boolean addPhoto(IPhoto photo) {
		if (photos.contains(photo)) { // contains is calling photo.equals() methods
			return false;
		}

		addedDates.put(photo, LocalDateTime.now());
		photos.add(photo);

		// emit event
		emitEvent(new PhotoAddedLibraryEvent(photo, this));

		return true;
	}

	/**
	 * Removes a photo from the library (based on equals) if present in the library
	 * @param photo Photo to be removed
	 * @return if the photo was removed
	 */
	@Override
	public boolean deletePhoto(IPhoto photo) {
		if (!photos.contains(photo)) { // contains is calling photo.equals() methods
			return false;
		}

		photos.remove(photo);

		// emit event
		emitEvent(new PhotoDeletedLibraryEvent(photo, this));

		return true;
	}

	/**
	 * Returns a collection of the photos in the trash library.
	 * Has side-effects: some, undefined, criteria over
	 * the photos is checked when this method is call
	 * and may result in photos to be removed from the library
	 *
	 * @return A collection of the photos in the library
	 * @ensures \result != null
	 */
	@Override
	public Collection<IPhoto> getMatches(String regexp) {
		Collection<IPhoto> p = getPhotos();
		Collection<IPhoto> matches = new ArrayList<>();

		for (IPhoto photo : p) {
			if (photo.matches(regexp)) {
				matches.add(photo);
			}
		}

		return matches;
	}

	/**
	 * Cleans the trash library
	 */
	@Override
	protected void clean() {
		if (!cleaningTime()) {
			return;
		}

		Iterator<IPhoto> iterator = photos.iterator();

		while (iterator.hasNext()) {
			IPhoto photo = iterator.next();
			LocalDateTime addedDate = addedDates.get(photo);

			if (addedDate.plusSeconds(CLEANING_AFTER).isBefore(LocalDateTime.now())) {
				iterator.remove();
			}
		}

		lastCleaned = LocalDateTime.now();
	}

	/**
	 * Checks if it is time to clean the trash library
	 *
	 * @return if it is time to clean the trash library
	 */
	@Override
	protected boolean cleaningTime() {
		return lastCleaned.plusSeconds(CLEANING_TIME).isBefore(LocalDateTime.now());
	}

}
