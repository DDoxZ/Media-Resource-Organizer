package leiphotos.domain.core;

import java.io.File;
import java.io.FileNotFoundException;

import leiphotos.domain.metadatareader.*;
import java.time.LocalDateTime;

public enum PhotoFactory {
  
  INSTANCE;

  private Photo photo;
  private String camera;
  private String manufacturer;
  private GPSLocation gpslocation;

  private PhotoFactory(){}

  public Photo createPhoto(String title, String pathToPhotoFile) throws java.io.FileNotFoundException, JpegMetadataException {
    File file = new File(pathToPhotoFile);
    JpegMetadataReader metadataReader = JpegMetadataReaderFactory.INSTANCE.createMetadataReader(file);

    camera = metadataReader.getCamera();
    manufacturer = metadataReader.getManufacturer();
    double[] coordinates = metadataReader.getGpsLocation();
    if (coordinates == null) {
      gpslocation = null;
    } else {
      gpslocation = new GPSLocation(coordinates[1], coordinates[0], "");
    }

    if(camera == null) {
      camera = " ";
    }
    if(manufacturer == null) {
      manufacturer = " ";
    }
  
    //Falta o "No Location"

    PhotoMetadata meta = new PhotoMetadata(gpslocation, metadataReader.getDate(), camera, manufacturer);
    INSTANCE.photo = new Photo(title, LocalDateTime.now(), meta, file);
    
    return photo;
  }
}
