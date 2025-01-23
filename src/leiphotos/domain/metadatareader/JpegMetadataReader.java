package leiphotos.domain.metadatareader;

import java.time.LocalDateTime;

/**
 * This interface represents the interface for a reader for JPEG metadata.
 */
public interface JpegMetadataReader {

	/**
	 * Retrieves the camera information from the JPEG metadata.
	 *
	 * @return the camera information
	 */
	String getCamera();

	/**
	 * Retrieves the manufacturer information from the JPEG metadata.
	 *
	 * @return the manufacturer information
	 */
	String getManufacturer();

	/**
	 * Retrieves the date information from the JPEG metadata.
	 *
	 * @return the date information
	 */
	LocalDateTime getDate();

	/**
	 * Retrieves the aperture information from the JPEG metadata.
	 *
	 * @return the aperture information
	 */
	String getAperture();

	/**
	 * Retrieves the GPS location information from the JPEG metadata.
	 *
	 * @return the GPS location information as an array of doubles
	 * @ensures \result is null only if information is not available
	 * otherwise, \result.lenght == 2 and  \result[0] is longitude and
	 * \result[1] is latitude
	 */
	double[] getGpsLocation();
}
