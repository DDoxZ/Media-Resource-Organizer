package leiphotos.domain.facade;

import leiphotos.domain.albums.AlbumsCatalog;
import leiphotos.domain.albums.IAlbumsCatalog;
import leiphotos.domain.controllers.AlbumsController;
import leiphotos.domain.controllers.LibrariesController;
import leiphotos.domain.controllers.ViewsController;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.RecentlyDeletedLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.core.views.IViewsCatalog;
import leiphotos.domain.core.views.ViewsCatalog;

/**
 * The LEIPhotos class represents the startup object for the LEIPhotos application.
 * It creates all required objects in the domain and provides access to them.
 * 
 * @author malopes
 */
public class LEIPhotos {
	private MainLibrary mainLib;
	private TrashLibrary trashLib;
	private IViewsCatalog views;
	private IAlbumsCatalog albumsCatalog;
	
	private ILibrariesController libControler;
	private IViewsController viewsController;
	private IAlbumsController albumsController;

	/**
	 * Constructs a new instance of the LEIPhotos class.
	 * Initializes the main library, trash library, views catalog, and controllers.
	 */
	public LEIPhotos (){
		
		//libs and their controller
		mainLib = new MainLibrary();		
		trashLib = new RecentlyDeletedLibrary();
		libControler = new LibrariesController(mainLib,trashLib);

		//views and their controller
		views = new ViewsCatalog(mainLib, trashLib);
		viewsController = new ViewsController(views);

		//albums and their controller
		albumsCatalog = new AlbumsCatalog(mainLib);
		albumsController = new AlbumsController(albumsCatalog);
	}

	/**
	 * Returns the controller for managing libraries.
	 * @return The libraries controller.
	 */
	public ILibrariesController libsController() {
		return libControler;
	}

	/**
	 * Returns the controller for managing views.
	 * @return The views controller.
	 */
	public IViewsController viewsController() {
		return viewsController;
	}

	/**
	 * Returns the controller for managing albums.
	 * @return The albums controller.
	 */
	public IAlbumsController albumsController() {
		return albumsController;
	}

	/**
	 * Returns the catalog of views for libraries.
	 * @return The views catalog.
	 */
	public IViewsCatalog librariesViews() {
		return views;
	}
}
