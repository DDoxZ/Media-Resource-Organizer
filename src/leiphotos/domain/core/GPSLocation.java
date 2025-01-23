package leiphotos.domain.core;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.utils.RegExpMatchable;

public record GPSLocation(double latitude, double longitude, String descricao) implements GPSCoordinates, RegExpMatchable {

  @Override
  public boolean matches(String regexp) {
    return toString().matches(regexp);
  }

  @Override
  public String toString() {
    StringBuilder bob = new StringBuilder();

    bob.append("{Lat:").append(String.format("%.2f",latitude));
    bob.append(" Long:").append(String.format("%.2f",longitude));
    bob.append(" Desc:").append(descricao);
    bob.append("}");

    return bob.toString();
  }

}
