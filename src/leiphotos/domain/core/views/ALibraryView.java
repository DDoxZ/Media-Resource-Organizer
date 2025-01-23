package leiphotos.domain.core.views;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a view of a library that extends the ILibraryView interface
 * with a new method for defining the sorting criterion used.
 */
public abstract class ALibraryView implements ILibraryView {
    private final Predicate<IPhoto> predicate;
    private final Library library;
    protected Comparator<IPhoto> comparator;

    /**
     * Constructor
     * @param library Library
     * @param predicate Predicate
     */
    protected ALibraryView(Library library, Predicate<IPhoto> predicate) {
        this.library = library;
        this.predicate = predicate;

        // default comparator
        setComparator(Comparator.comparing(IPhoto::size));
    }

    /**
     * Define the sorting criterion used
     * @param c Comparator
     */
    @Override
    public void setComparator(Comparator<IPhoto> c) {
        this.comparator = c;
    }

    /**
     * Returns the number of photos that belong to the view
     * @return number of photos
     */
    @Override
    public int numberOfPhotos() {
        return (int) library
                .getPhotos()
                .stream()
                .filter(predicate)
                .count();
    }

    /**
     * Returns the photos in the view in the order determined by the current sorting criterion
     * @return List of photos
     */
    @Override
    public List<IPhoto> getPhotos() {
        return library
                .getPhotos()
                .stream()
                .filter(predicate)
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
        return library
                .getPhotos()
                .stream()
                .filter(predicate)
                .filter(p -> p.matches(regexp))
                .sorted(comparator)
                .toList();
    }
}
