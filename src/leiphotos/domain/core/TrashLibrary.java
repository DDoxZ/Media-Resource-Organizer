package leiphotos.domain.core;

import java.util.Collection;

import leiphotos.domain.facade.IPhoto;

/**
 * Represents a trash library that extends the Library interface
 * with a new method for emptying the trash. In this type of library, 
 * photos are automatically removed (for instance, after a certain 
 * period of time). 
 * 
 * @version malopes
 */
public interface TrashLibrary extends Library{

	/**
	 * Returns a collection of the photos in the trash library.
	 * Has side-effects: some, undefined, criteria over
	 * the photos is checked when this method is call
	 * and may result in photos to be removed from the library   
	 * 
	 * @return A collection of the photos in the library
	 * @ensures \result != null
	 */
	@Override
	public Collection<IPhoto> getPhotos();

	/**
	 * Removes all photos in the trash library
	 *
	 * @returns if some photo was removed
	 */	
	public boolean deleteAll();
}
