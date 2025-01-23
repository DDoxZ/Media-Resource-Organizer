package leiphotos.domain.core;

import leiphotos.domain.facade.IPhoto;

/**
 * @author malopes
 *
 * A photo changed event
 */
public class PhotoChangedLibraryEvent extends LibraryEvent {

	/**
	 * Constructs a song changed event.
	 * 
	 * @param photo The photo upon which the event has happened
	 * @param lib The library upon which the event has happened	  
	 */
	PhotoChangedLibraryEvent(IPhoto photo, Library lib) {
		super(photo,lib);
	}

}
