package leiphotos.domain.core;

import java.util.Collection;

import leiphotos.domain.facade.IPhoto;

/**
 * A library of photos. 
 * Photos can be added and removed from the library.
 * It is possible to get the photos in the libary but this might have side-effects 
 * in the library, namely in terms of removals.
 * 
 * @author antonialopes
 */

public interface Library {
	
	/**
	 * Returns the number of photos in the library
	 * 
	 * @return number of photos 
	 * @ensures \result >= 0 
	 */
	int getNumberOfPhotos();
	
	/**
	 * Adds a photo to the library,
	 * if does not exist yet (based on equals)
	 * 
	 * @param photo Photo to add
	 * @requires photo != null
	 * @returns if the photo was added
	 */
	boolean addPhoto(IPhoto photo);
	
	/**
	 * Removes a photo from the library (based on equals)
	 * if present in the library
	 * 
	 * @param photo Photo to be removed
	 * @requires photo != null
	 * @returns if the photo was removed
	 */	
	boolean deletePhoto(IPhoto photo);

	/**
	 * Returns a collection with the photos in the library.
	 * May have side-effects:  some, undefined, criteria over
	 * the photos can be checked when this method is call
	 * and lead some of them to be removed from the library   
	 * 
	 * @return A collection of the photos in the library
	 * @ensures \result != null
	 */
	public Collection<IPhoto> getPhotos ();
	
	/**
	 * Returns a collection with the photos in the library 
	 * that match the regexp
	 * 
	 * @param regexp The regular expression
	 * @requires regexp is a regular expression
	 * @return A collection with the photos in the library matching the expression
	 * @ensures \result != null
	 */
	public Collection<IPhoto> getMatches(String regexp);
	
	/**
	 * Returns a String with a textual representation of the library
	 * with the number of photos and their representation. 
	 * 
	 * Alphabetic order of path to files in the disk  
	 * defines the presentation order. 
	 * 
	 * @return A String with a textual representation of the library
	 * @ensures \result != null
	 */
 	 @Override
	String toString();
	
}