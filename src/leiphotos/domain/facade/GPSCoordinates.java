package leiphotos.domain.facade;

/**
 * This interface represents GPS coordinates consisting of latitude and longitude.
 * 
 * @author malopes
 */
public interface GPSCoordinates {
	/**
	 * Returns the latitude value of the GPS coordinates.
	 *
	 * @return the latitude value
	 */
	double latitude();

	/**
	 * Returns the longitude value of the GPS coordinates.
	 *
	 * @return the longitude value
	 */
	double longitude();
}