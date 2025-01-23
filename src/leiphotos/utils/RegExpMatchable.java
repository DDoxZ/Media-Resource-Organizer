package leiphotos.utils;

/**
 *
 * Represents objects that can be asked whether they match 
 * with a regular expression.
 * 
 * @author malopes
 */

public interface RegExpMatchable {

	/**
	 * Checks if this object matches a regular expression
	 * 
	 * @param regexp a regular expression used to check the matches
	 * @requires regexp != null
	 * @return true if this object matches regexp, false otherwise.
	 */
	boolean matches (String regexp);
}
