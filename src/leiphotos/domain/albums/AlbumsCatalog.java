package leiphotos.domain.albums;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.facade.IPhoto;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AlbumsCatalog implements IAlbumsCatalog {

	private Map<String, IAlbum> albumCatalog;
	private MainLibrary mainLibrary;

	public AlbumsCatalog(MainLibrary mainlib) {
		albumCatalog = new HashMap<>();
		mainLibrary = mainlib;
	}

	@Override
	public boolean createAlbum(String albumName) {
		if(containsAlbum(albumName)) {
			return false;
		}

		IAlbum novoAlbum = new Album(albumName);

		mainLibrary.registerListener(novoAlbum);
		albumCatalog.put(albumName, novoAlbum);

		return true;
	}

	@Override
	public boolean deleteAlbum(String albumName) {
		mainLibrary.unregisterListener(albumCatalog.get(albumName));
		return albumCatalog.remove(albumName) != null;
	}

	@Override
	public boolean containsAlbum(String albumName) {
		return albumCatalog.containsKey(albumName);
	}

	@Override
	public boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos) {
		if(containsAlbum(albumName)) {
			albumCatalog.get(albumName).addPhotos(selectedPhotos);
			return true;
		}
		return false;
	}

	@Override
	public boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos) {
		if(containsAlbum(albumName)) {
			albumCatalog.get(albumName).removePhotos(selectedPhotos);
			return true;
		}
		return false;
	}

	@Override
	public List<IPhoto> getPhotos(String albumName) {
		if(containsAlbum(albumName)) {
			return albumCatalog.get(albumName).getPhotos();
		}
		return new ArrayList<>();
	}

	@Override
	public Set<String> getAlbumsNames() {
		Set<String> albumNames = new HashSet<>();

		for (Map.Entry<String, IAlbum> set : albumCatalog.entrySet()) {
			albumNames.add(set.getValue().getName());
		}

		return albumNames;
	}

}
