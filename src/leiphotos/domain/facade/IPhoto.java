package leiphotos.domain.facade;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Objects of this type represent photos.
 * 
 * @author malopes
 */
public interface IPhoto {

	/**
	 * Returns the photo's title
	 *
	 * @return the photo's title 
	 * @ensures \result != null
	 */
	String title();
	
	/**
	 * Returns the photo's date
	 * 
	 * @return the photo's date
	 * @ensures \result != null 
	 */
	LocalDateTime capturedDate();

	/**
	 * Returns the date of addition of 
	 * the photo to the library 
	 * 
	 * @return the photo's date
	 * @ensures \result != null 
	 */
	LocalDateTime addedDate();
	
	/**
	 * Checks if the photo is currently favourite 
	 * 
	 * @return if the photo is favourite
	 */
	boolean isFavourite();
	
	/**
	 * Toggle classification as a favourite photo
	 */
	void toggleFavourite();

	/**
	 * Returns the photo's place
	 *
	 * @return the photo's place 
	 * @ensures \result != null
	 */
	Optional<? extends GPSCoordinates> getPlace();

	/**	
	 * Returns the photo's size
	 * 
	 * @return the photo's size file 
	 * @ensures \result >= 0
	 */
	long size();

	/**	
	 * @return the file containing this photo. 
	 */
	File file();

	/**
	 * Checks if any photo data matches the given regular expression
	 *  
	 * @param regexp the regular expression to be used
	 * @requires regexp != null
	 * @return whether some data of the song matches with the given regexp
	 */
	boolean matches(String regexp);

}