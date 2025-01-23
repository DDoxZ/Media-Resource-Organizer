package leiphotos.domain.core;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Event;

/**
 * @author malopes
 *
 * A library event
 */
public abstract class LibraryEvent implements Event {
	
	/**
	 * The photo upon which the event has happened 
	 * and the library 
	 */
	private IPhoto photo;
	private Library lib;
	
	
	/**
	 * @param photo the photo upon which the event has happened
	 */
	LibraryEvent (IPhoto photo, Library lib) {
		this.photo = photo;
		this.lib = lib;
	}

	/**
	 * @return the photo upon which the event has happened
	 */
	public IPhoto getPhoto() {
		return photo;
	}
	
	/**
	 * @return the library upon which the event has happened
	 */
	public Library getLibrary() {
		return lib;
	}
}
