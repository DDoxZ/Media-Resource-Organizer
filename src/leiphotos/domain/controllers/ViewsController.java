package leiphotos.domain.controllers;

import java.util.Comparator;
import java.util.List;

import leiphotos.domain.core.views.ILibraryView;
import leiphotos.domain.core.views.IViewsCatalog;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.IViewsController;
import leiphotos.domain.facade.ViewsType;


/**
 * Represents a controller for the views.
 */
public class ViewsController implements IViewsController {

	private final IViewsCatalog views;

	/**
	 * Constructor
	 * @param views Views catalog
	 */
	public ViewsController(IViewsCatalog views) {
		this.views = views;
	}

	/**
	 * Retrieves a list of photos based on the specified view type.
	 * The list has the photos of the view sorted according to
	 * the sorting criteria set for the view type.
	 *
	 * @param viewType the type of view to retrieve photos from
	 * @return a list of photos
	 */
	@Override
	public List<IPhoto> getPhotos(ViewsType viewType) {
		ILibraryView view = views.getView(viewType);
		return view.getPhotos();
	}

	/**
	 * Retrieves a list of photos based on the specified view type and regular expression.
	 * The list has the photos sorted according to
	 * the sorting criteria set for the view type.
	 *
	 * @param viewType the type of view to retrieve photos from
	 * @param regexp the regular expression to match against photo properties
	 * @return a list of photos that match the regular expression
	 */
	@Override
	public List<IPhoto> getMatches(ViewsType viewType, String regexp) {
		ILibraryView view = views.getView(viewType);
		return view.getMatches(regexp);
	}

	/**
	 * Sets the sorting criteria for the specified view type.
	 *
	 * @param v the type of view to set the sorting criteria for
	 * @param criteria the comparator to use for sorting the photos
	 */
	@Override
	public void setSortingCriteria(ViewsType v, Comparator<IPhoto> criteria) {
		ILibraryView view = views.getView(v);
		view.setComparator(criteria);
	}

	/**
	 * Returns a string representation of the different views.
	 *
	 * @return a string representation of the different views
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("****** VIEWS *****\n");

		sb.append("***** VIEW ALL_MAIN: ").append(views.getView(ViewsType.ALL_MAIN).getPhotos().size()).append(" photos *****\n");
		views.getView(ViewsType.ALL_MAIN).getPhotos().forEach(photo -> sb.append(photo.file().getPath()).append("\n"));

		sb.append("***** VIEW ALL_TRASH: ").append(views.getView(ViewsType.ALL_TRASH).getPhotos().size()).append(" photos *****\n");
		views.getView(ViewsType.ALL_TRASH).getPhotos().forEach(photo -> sb.append(photo.file().getPath()).append("\n"));

		sb.append("***** VIEW FAVOURITES_MAIN: ").append(views.getView(ViewsType.FAVOURITES_MAIN).getPhotos().size()).append(" photos *****\n");
		views.getView(ViewsType.FAVOURITES_MAIN).getPhotos().forEach(photo -> sb.append(photo.file().getPath()).append("\n"));

		sb.append("***** VIEW MOST_RECENT: ").append(views.getView(ViewsType.MOST_RECENT).getPhotos().size()).append(" photos *****\n");
		views.getView(ViewsType.MOST_RECENT).getPhotos().forEach(photo -> sb.append(photo.file().getPath()).append("\n"));
		return sb.toString();
	}
}
