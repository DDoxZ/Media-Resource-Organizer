package leiphotos.domain.core;

import leiphotos.domain.facade.IPhoto;

/**
 * @author malopes
 *
 * A photo removed event
 */
public class PhotoAddedLibraryEvent extends LibraryEvent {

	/**
	 * Constructs a photo added library event.
	 * 
	 * @param photo The photo upon which the event has happened
	 * @param lib The library upon which the event has happened	  
	 */
	PhotoAddedLibraryEvent(IPhoto photo, Library lib) {
		super(photo,lib);
	}

}
