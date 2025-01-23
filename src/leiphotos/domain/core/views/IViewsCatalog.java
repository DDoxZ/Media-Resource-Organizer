package leiphotos.domain.core.views;

import leiphotos.domain.facade.ViewsType;

/**
 * Represents a catalog of views
 */
public interface IViewsCatalog {
    /**
     * Returns the view in the catalog with a given type
     * @param t ViewsType
     * @return ILibraryView
     */
    ILibraryView getView(ViewsType t);
}
