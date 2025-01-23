package leiphotos.domain.core.views;

import leiphotos.domain.core.*;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Represents a view of a library that extends the ILibraryView interface
 * with a new method for defining the sorting criterion used.
 */
public class MainLibraryView extends ALibraryView implements Listener<LibraryEvent> {

    private final Collection<IPhoto> cache;
    private final Predicate<IPhoto> predicate;

    /**
     * Constructor
     * @param library Library
     * @param predicate Predicate
     */
    public MainLibraryView(MainLibrary library, Predicate<IPhoto> predicate) {
        super(library, predicate);

        library.registerListener(this);

        this.predicate = predicate;
        this.cache = new ArrayList<>(library
                .getPhotos()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }

    /**
     * Processes an event
     *
     * @param libraryEvent the event to process
     */
    @Override
    public void processEvent(LibraryEvent libraryEvent) {
        if (libraryEvent instanceof PhotoAddedLibraryEvent) {
            handlePhotoAddedEvent(libraryEvent);
        } else if (libraryEvent instanceof PhotoDeletedLibraryEvent) {
            handlePhotoDeletedEvent(libraryEvent);
        } else if (libraryEvent instanceof PhotoChangedLibraryEvent) {
            handlePhotoChangedEvent(libraryEvent);
        }
    }

    /**
     * Handles PhotoAddedEvent
     * @param libraryEvent LibraryEvent
     */
    private void handlePhotoAddedEvent(LibraryEvent libraryEvent) {
        IPhoto photo = libraryEvent.getPhoto();

        if (predicate.test(photo)) {
            cache.add(photo);
        }
    }

    /**
     * Handles PhotoDeletedEvent
     * @param libraryEvent LibraryEvent
     */
    private void handlePhotoDeletedEvent(LibraryEvent libraryEvent) {
        IPhoto photo = libraryEvent.getPhoto();

        if (predicate.test(photo)) {
            cache.remove(photo);
        }
    }

    /**
     * Handles PhotoChangedEvent
     * @param libraryEvent LibraryEvent
     */
    private void handlePhotoChangedEvent(LibraryEvent libraryEvent) {
        /*IPhoto photo = libraryEvent.getPhoto();

        if (predicate.test(photo)) {
            cache.remove(photo);
            cache.add(photo);
        }*/

        // Event is never fired
        // We would need the old photo and the new photo

        return;
    }

    /**
     * Returns the number of photos that belong to the view
     * @return number of photos
     */
    @Override
    public int numberOfPhotos() {
        return cache.size();
    }

    /**
     * Returns the photos in the view in the order determined by the current sorting criterion
     * @return List of photos
     */
    @Override
    public List<IPhoto> getPhotos() {
        return new ArrayList<>(cache);
    }

    /**
     * Returns the photos in the view that match the given regular expression, in the order determined by the current sorting criterion
     * @param regexp Regular expression
     * @return List of photos
     */
    @Override
    public List<IPhoto> getMatches(String regexp) {
        return cache
                .stream()
                .filter(p -> p.matches(regexp))
                .sorted(comparator)
                .toList();
    }
}
