package leiphotos.domain.core;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.AbsSubject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a trash library that extends the Library interface
 * with a new method for emptying the trash. In this type of library,
 * photos are automatically removed (for instance, after a certain
 * period of time).
 */
public abstract class ATrashLibrary extends AbsSubject<LibraryEvent> implements TrashLibrary {
    // make photos protected so that it can be accessed by subclasses
    protected Collection<IPhoto> photos;

    /**
     * Constructor
     */
    public ATrashLibrary(Collection<IPhoto> photos) {
        this.photos = photos;
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
    public Collection<IPhoto> getPhotos() {
        if (cleaningTime()) {
            clean();
        }

        return new ArrayList<>(photos);
    }

    /**
     * Removes all photos in the trash library
     *
     * @returns if some photo was removed
     */
    @Override
    public boolean deleteAll() {
        if (photos.isEmpty()) {
            return false;
        }

        photos.clear();

        return true;
    }

    /**
     * Cleans the trash library
     */
    protected abstract void clean();

    /**
     * Checks if it is time to clean the trash library
     *
     * @return if it is time to clean the trash library
     */
    protected abstract boolean cleaningTime();
}