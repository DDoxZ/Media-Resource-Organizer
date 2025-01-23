package leiphotos.domain.core.views;

import leiphotos.domain.core.*;
import leiphotos.domain.facade.IPhoto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a view of a library that extends the ILibraryView interface
 * with a new method for defining the sorting criterion used.
 */
public class TrashLibraryView extends ALibraryView {

    private final Collection<IPhoto> cache;

    /**
     * Constructor
     * @param library Library
     * @param predicate Predicate
     */
    public TrashLibraryView(TrashLibrary library, Predicate<IPhoto> predicate) {
        super(library, predicate);

        this.cache = new ArrayList<>(library
                .getPhotos()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList()));
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
        return cache.stream()
                .sorted(comparator)
                .toList();
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
