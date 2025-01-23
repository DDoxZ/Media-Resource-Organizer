package leiphotos.services;

import java.io.File;
import javaxt.io.Image;

/**
 * The JavaXTJpegMetadataReader class is responsible for reading metadata from a JPEG image file.
 * It provides methods to retrieve information such as camera model, manufacturer, date, aperture, 
 * exposure time, and GPS coordinates. 
 * 
 * The class uses the JavaXT library to extract the metadata.
 * 
 * @author malopes
 */
public class JavaXTJpegMetadataReader{

	private String camara;
	private String manufacturer;
	private String date;
	private String aperture;
	private String exposureTime;
	private double[] coordinates;

	/**
	 * Constructs a new JavaXTJpegMetadataReader object with the specified file.
	 * 
	 * @param file the JPEG image file to read metadata from
	 */
	public JavaXTJpegMetadataReader(File file){	
		Image image = new Image(file);

		java.util.HashMap<Integer, Object> exifTags = image.getExifTags();
		if(exifTags != null) {
			camara = exifTags.get(0x0110) != null ? exifTags.get(0x0110).toString() : null;
			manufacturer = exifTags.get(0x010F) != null ? exifTags.get(0x010F).toString() : null;
			date = exifTags.get(0x0132) != null ? exifTags.get(0x0132).toString() : null;
			aperture = exifTags.get(0x9202) != null ? exifTags.get(0x9202).toString() : null;
			exposureTime = exifTags.get(0x829A) != null ? exifTags.get(0x829A).toString() : null;
		}
		coordinates = image.getGPSCoordinate();
	}

	/**
	 * Retrieves the camera information.
	 *
	 * @return the camera information as a String.
	 */
	public String getCamara() {
		return camara;
	}

	/**
	 * Retrieves the manufacturer of the image.
	 *
	 * @return the manufacturer of the image as a String.
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Returns the date associated with the image.
	 *
	 * @return the date as a String
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Returns the aperture value of the photo.
	 *
	 * @return the aperture value as a String
	 */
	public String getAperture() {
		return aperture;
	}

	/**
	 * Returns the exposure time of the photo.
	 *
	 * @return the exposure time as a String
	 */
	public String getExposureTime() {
		return exposureTime;
	}

	/**
	 * Returns the GPS coordinates as an array of doubles.
	 *
	 * @return the GPS coordinates as an array of doubles
	 */
	public double[] getGPS() {
		return coordinates;
	}
}
