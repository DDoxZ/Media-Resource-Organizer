package leiphotos.domain.facade;

import java.util.Comparator;
import java.util.List;

/**
 * The interface for the Views Controller in the LeiPhotos application.
 * It provides methods to retrieve photos based on different views and sorting criteria.
 */
public interface IViewsController {

	/**
	 * Retrieves a list of photos based on the specified view type.
	 * The list has the photos of the view sorted according to 
	 * the sorting criteria set for the view type.
	 *
	 * @param viewType the type of view to retrieve photos from
	 * @return a list of photos
	 */
	List<IPhoto> getPhotos(ViewsType viewType);

	/**
	 * Retrieves a list of photos based on the specified view type and regular expression.
	 * The list has the photos sorted according to 
	 * the sorting criteria set for the view type.
	 *
	 * @param viewType the type of view to retrieve photos from
	 * @param regexp the regular expression to match against photo properties
	 * @return a list of photos that match the regular expression
	 */
	List<IPhoto> getMatches(ViewsType viewType, String regexp);

	/**
	 * Sets the sorting criteria for the specified view type.
	 *
	 * @param v the type of view to set the sorting criteria for
	 * @param criteria the comparator to use for sorting the photos
	 */
	void setSortingCriteria(ViewsType v, Comparator<IPhoto> criteria);
	
}
