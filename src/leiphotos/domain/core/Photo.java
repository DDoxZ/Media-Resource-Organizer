package leiphotos.domain.core;

import java.time.LocalDateTime;
import java.io.File;
import java.util.Optional;
import java.util.regex.Pattern;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.RegExpMatchable;

public class Photo implements IPhoto, RegExpMatchable{
  
  private String title;
  private LocalDateTime dataAddedLib;
  private PhotoMetadata meta;
  private File pathToFile;
  private Boolean favourite;

  public Photo(String title, LocalDateTime dateAddedLib, PhotoMetadata meta, File pathToFile) {
    this.title = title;
    this.dataAddedLib = dateAddedLib;
    this.meta = meta;
    this.pathToFile = pathToFile;
    this.favourite = false;
  }

  @Override
  public String title() {
    return this.title;
  }

  @Override
  public LocalDateTime capturedDate() {
    return this.meta.data();
  }

  @Override
  public LocalDateTime addedDate() {
    return this.dataAddedLib;
  }

  @Override
  public boolean isFavourite() {
    return this.favourite;
  }

  @Override
  public void toggleFavourite() {
    this.favourite = !(this.favourite);
  }

  @Override
  public Optional<? extends GPSCoordinates> getPlace() {
    return Optional.ofNullable(meta.location());
  }

  @Override
  public long size() {
    return pathToFile.length();
  }

  @Override
  public File file() {
    return this.pathToFile;
  }

  @Override
  public boolean matches(String regexp) {
      return Pattern.compile(regexp, Pattern.MULTILINE).matcher(this.toString()).find();
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Photo)) {
      return false;
    }

    // check every attribute
    Photo other = (Photo) obj;
    return this.title.equals(other.title) && this.dataAddedLib.equals(other.dataAddedLib) && this.meta.equals(other.meta) && this.pathToFile.equals(other.pathToFile) && this.favourite.equals(other.favourite);
  }

  @Override
  public String toString() {
    StringBuilder bob = new StringBuilder();

    bob.append("File:").append(pathToFile.toString());
    bob.append("\n");
    bob.append("Title:").append(title);
    bob.append(" Added:").append(dataAddedLib.toString());
    bob.append(" Size:").append(size());
    bob.append("\n");
    bob.append(meta.toString());
    if (isFavourite()) {
      bob.append(" FAV");
    }

    return bob.toString();
  }
}
