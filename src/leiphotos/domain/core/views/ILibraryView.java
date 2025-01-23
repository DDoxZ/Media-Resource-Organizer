package leiphotos.domain.core.views;

import leiphotos.domain.facade.IPhoto;

import java.util.Comparator;
import java.util.List;

/**
 * Represents a view of a library, which is a subset of the photos in the library.
 * The view can be sorted according to a given criterion.
 */
public interface ILibraryView {

    /**
     * Define the sorting criterion used
     * @param c Comparator
     */
    void setComparator(Comparator<IPhoto> c);

    /**
     * Returns the number of photos that belong to the view
     * @return number of photos
     */
    int numberOfPhotos();

    /**
     * Returns the photos in the view in the order determined by the current sorting criterion
     * @return List of photos
     */
    List<IPhoto> getPhotos();

    /**
     * Returns the photos in the view that match the given regular expression, in the order determined by the current sorting criterion
     * @param regexp Regular expression
     * @return List of photos
     */
    List<IPhoto> getMatches(String regexp);
}
