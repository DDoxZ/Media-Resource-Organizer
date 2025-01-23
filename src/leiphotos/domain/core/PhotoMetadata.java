package leiphotos.domain.core;

import leiphotos.utils.RegExpMatchable;
import java.time.LocalDateTime;


public record PhotoMetadata(GPSLocation location, LocalDateTime data, String camara, String fabricante) implements RegExpMatchable{

  @Override
  public boolean matches (String regexp) {
    return toString().matches(regexp);
  }

  @Override
  public String toString() {
    StringBuilder bob = new StringBuilder();

    bob.append("[");
    if (location == null) {
      bob.append("No Location");
    } else {
      bob.append(location.toString());
    }
    bob.append(", ").append(data);
    bob.append(", ").append(camara);
    bob.append(", ").append(fabricante);
    bob.append("]");

    return bob.toString();
  }
}
