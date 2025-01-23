package leiphotos.domain.core.views;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ViewsType;

import java.time.ZoneOffset;

/**
 * Represents a catalog of views that implements the IViewsCatalog interface
 * with a new method for returning the view in the catalog with a given type.
 */
public class ViewsCatalog implements IViewsCatalog {
	private final MainLibrary mainLib;
	private final TrashLibrary trashLib;

	/**
	 * Constructor
	 * @param mainLib MainLibrary
	 * @param trashLib TrashLibrary
	 */
	public ViewsCatalog(MainLibrary mainLib, TrashLibrary trashLib) {
		this.mainLib = mainLib;
		this.trashLib = trashLib;
	}

	/**
	 * Returns the view in the catalog with a given type
	 * @param t ViewsType
	 * @return ILibraryView
	 */
	@Override
	public ILibraryView getView(ViewsType t) {
		switch (t) {
			case ALL_MAIN -> {
				return new MainLibraryView(mainLib, photo -> true);
			}
			case ALL_TRASH -> {
				return new TrashLibraryView(trashLib, photo -> true);
			}
			case FAVOURITES_MAIN -> {
				return new MainLibraryView(mainLib, IPhoto::isFavourite);
			}
			case MOST_RECENT -> {
				//Photos were capturedDate in the last year
				long oneYearAgo = System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 365;

				return new MainLibraryView(mainLib, photo -> photo
						/*.addedDate()
						.toInstant(ZoneOffset.UTC)
						.toEpochMilli() > oneDayAgo);*/
						.capturedDate()
						.toInstant(ZoneOffset.UTC)
						.toEpochMilli() > oneYearAgo);
			}
			default -> throw new IllegalArgumentException("Invalid ViewsType: " + t);
		}
	}
}