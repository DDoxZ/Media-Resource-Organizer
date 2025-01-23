package leiphotos.domain.albums;

import leiphotos.domain.facade.IPhoto;

import java.util.Set;
import java.util.List;

public interface IAlbumsCatalog {

  boolean createAlbum(String albumName);

  boolean deleteAlbum(String albumName);

  boolean containsAlbum(String albumName);

  boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos);

  boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos);

  List<IPhoto> getPhotos(String albumName);

  Set<String> getAlbumsNames();
  
}
