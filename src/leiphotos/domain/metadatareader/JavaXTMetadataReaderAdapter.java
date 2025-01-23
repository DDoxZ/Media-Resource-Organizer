package leiphotos.domain.metadatareader;

import java.io.File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import leiphotos.services.JavaXTJpegMetadataReader;

 // Usamos composicao e nao heranca pq getDate() tem um tipo de retorno diferente aqui
 // alem de outras varias razoes (mais flexivel a mudanca e JaxaXTJpegMetadataReader
 // nao diz que foi desenhado especificamente para ser extendido). 
public class JavaXTMetadataReaderAdapter implements JpegMetadataReader {

  private JavaXTJpegMetadataReader metadataReader;

  public JavaXTMetadataReaderAdapter (File file) {
      metadataReader = new JavaXTJpegMetadataReader(file);
  }

  @Override
  public String getCamera() {
    return metadataReader.getCamara();
  }

  @Override
  public String getManufacturer() {
    return metadataReader.getManufacturer();
  }

  @Override
  public LocalDateTime getDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
    
    try {
      return LocalDateTime.parse(metadataReader.getDate(), formatter);
    } catch (Exception e){
      return LocalDateTime.of(1970, 1, 1, 0, 0);
    }
  }

  @Override
  public String getAperture() {
    return metadataReader.getAperture();
  }

  @Override
  public double[] getGpsLocation () {
    return metadataReader.getGPS();
  }
}
