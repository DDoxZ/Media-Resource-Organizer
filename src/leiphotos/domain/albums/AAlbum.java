package leiphotos.domain.albums;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.PhotoDeletedLibraryEvent;


public abstract class AAlbum implements IAlbum{
   
  private String albumName;
  private Set<IPhoto> albumPhotos;

  protected AAlbum (String albumName) {
    this.albumName = albumName;
    this.albumPhotos = new HashSet<>();
  }

  @Override
  public int numberOfPhotos() {
    return albumPhotos.size();
  }

  @Override
  public String getName() {
    return albumName;
  }

  @Override
  public List<IPhoto> getPhotos() {
    return new ArrayList<>(albumPhotos);
  }

  @Override
  public boolean addPhotos(Set<IPhoto> selectedPhotos) {
    return albumPhotos.addAll(selectedPhotos);
  }

  @Override
  public boolean removePhotos(Set<IPhoto> selectedPhotos) {
    return albumPhotos.removeAll(selectedPhotos);
  } 

  @Override
  public void processEvent(LibraryEvent e) {
    if(e instanceof PhotoDeletedLibraryEvent) {
      Set<IPhoto> photosToDelete = new HashSet<>();
      photosToDelete.add(e.getPhoto());
      removePhotos(photosToDelete);
    }
  }
  
}
