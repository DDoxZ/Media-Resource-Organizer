package leiphotos.domain.core;


import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.AbsSubject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * Represents a main library that extends the Library interface
 * with a new method for getting photos that match a regular expression.
 */
public class MainLibrary extends AbsSubject<LibraryEvent> implements Library {

    Collection<IPhoto> photos;

    /**
     * Constructor
     */
    public MainLibrary() {
        photos = new ArrayList<>();
    }

    /**
     * Returns the number of photos in the library
     * @return number of photos
     */
    @Override
    public int getNumberOfPhotos() {
        return photos.size();
    }

    /**
     * Adds a photo to the library, if does not exist yet (based on equals)
     * @param photo Photo to add
     * @return if the photo was added
     */
    @Override
    public boolean addPhoto(IPhoto photo) {
        if (photos.contains(photo)) { // contains is calling photo.equals() methods
            return false;
        }

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
     * Returns a collection with the photos in the library. May have side-effects: some, undefined, criteria over the photos can be checked when this method is call and lead some of them to be removed from the library
     * @return A collection of the photos in the library
     */
    @Override
    public Collection<IPhoto> getPhotos() {
        // return a copy of the photos collection to avoid side effects and NOT the original collection
        return new ArrayList<>(photos);
    }

    /**
     * Returns a collection with the photos in the library that match the regexp
     * @param regexp The regular expression
     * @return A collection with the photos in the library matching the expression
     */
    @Override
    public Collection<IPhoto> getMatches(String regexp) {
        Collection<IPhoto> matches = new ArrayList<>();

        for (IPhoto photo : photos) {
            if (photo.matches(regexp)) {
                matches.add(photo);
            }
        }

        return matches;
    }

    /**
     * Returns a String with a textual representation of the library
     * with the number of photos and their representation.
     *
     * Alphabetic order of path to files in the disk
     * defines the presentation order.
     *
     * @return A String with a textual representation of the library
     * @ensures \result != null
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Main Library\n");
        sb.append("Number of photos: ").append(getNumberOfPhotos()).append("\n");

        // Sorted by file path
        photos.stream()
                .sorted(Comparator.comparing(p -> p.file().getPath()))
                .forEach(photo -> sb.append(photo).append("\n"));

        return sb.toString();
    }
}
