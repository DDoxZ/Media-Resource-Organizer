package leiphotos.domain.albums;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;
import leiphotos.domain.core.LibraryEvent;

import java.util.Set;
import java.util.List;

public interface IAlbum extends Listener<LibraryEvent> {

  int numberOfPhotos();

  String getName();

  List<IPhoto> getPhotos();

  boolean addPhotos(Set<IPhoto> selectedPhotos);

  boolean removePhotos(Set<IPhoto> selectedPhotos);

}
