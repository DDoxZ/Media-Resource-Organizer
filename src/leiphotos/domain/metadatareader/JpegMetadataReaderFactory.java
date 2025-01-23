package leiphotos.domain.metadatareader;

import java.io.File;
import java.io.FileNotFoundException;

public enum JpegMetadataReaderFactory {

  INSTANCE;
  
  private JpegMetadataReader metadataReader;

  private JpegMetadataReaderFactory(){}  // private pq singleton

  public JpegMetadataReader createMetadataReader(File file) throws JpegMetadataException, FileNotFoundException{
    if (!file.exists() || !file.isFile()) {
      throw new FileNotFoundException("Error: File not found when trying to create metadata reader!");
    }
    
    int indexOfExtension = file.toString().lastIndexOf(".");
    String fileExtension = file.toString().substring(indexOfExtension + 1).toLowerCase();

    if (!fileExtension.equals("jpeg") && !fileExtension.equals("jpg")) {
      throw new JpegMetadataException("Error: Invalid file extension!");
    }

    INSTANCE.metadataReader = new JavaXTMetadataReaderAdapter(file);
    return INSTANCE.metadataReader;
  }
}