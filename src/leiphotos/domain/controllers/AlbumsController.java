package leiphotos.domain.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.ArrayList;

import leiphotos.domain.albums.IAlbum;
import leiphotos.domain.albums.AlbumsCatalog;
import leiphotos.domain.albums.IAlbumsCatalog;
import leiphotos.domain.facade.IAlbumsController;
import leiphotos.domain.facade.IPhoto;

public class AlbumsController implements IAlbumsController {

	private IAlbumsCatalog albumsCatalog;
	private String selectedAlbum;

	public AlbumsController(IAlbumsCatalog albumsCatalog) {
		this.albumsCatalog = albumsCatalog;
	}

	@Override
	public boolean createAlbum(String name) {
		return albumsCatalog.createAlbum(name);
	}

	@Override
	public void removeAlbum() {
		if (selectedAlbum != null) {
			albumsCatalog.deleteAlbum(selectedAlbum);
		}
	}
	
	@Override
	public void selectAlbum(String name) {
		if (albumsCatalog.containsAlbum(name)) {
			this.selectedAlbum = name;
		}
	}

	@Override
	public void addPhotos(Set<IPhoto> selectedPhotos) {
		if (selectedAlbum != null) {
			albumsCatalog.addPhotos(selectedAlbum, selectedPhotos);
		}
	}

	@Override
	public void removePhotos(Set<IPhoto> selectedPhotos) {
		if (selectedAlbum != null) {
			albumsCatalog.removePhotos(selectedAlbum, selectedPhotos);
		}
	}

	@Override
	public List<IPhoto> getPhotos() {
		if (selectedAlbum != null) {
			return albumsCatalog.getPhotos(selectedAlbum);
		}
		return new ArrayList<>();
	}

	@Override
	public Optional<String> getSelectedAlbum() {
			return Optional.ofNullable(selectedAlbum);
	}

	@Override
	public boolean createSmartAlbum(String name, Predicate<IPhoto> criteria) {
		// Não faz parte do trabalho, apenas do desenho
		return false;
	}

	@Override
	public Set<String> getAlbumNames() {
		return albumsCatalog.getAlbumsNames();
	}

	/**
	 * Representa em forma de string o conteúdo do albuns selecionados
	 */
	@Override
	public String toString() {
		/****** ALBUMS *****
		***** Album Oppenheimer: 3 photos *****
				photos/Office.jpg
				photos/Book.jpeg
				photos/Cars.jpg
						***** Album JoanaVasconcelos: 2 photos *****
				photos/SapatoJVasconcelos.JPG
				photos/Polvo.jpeg*/

		StringBuilder sb = new StringBuilder();

		for (String albumName : albumsCatalog.getAlbumsNames()) {
			List<IPhoto> photos = albumsCatalog.getPhotos(albumName);

			sb.append("***** Album " + albumName + ": " + photos.size() + " photos *****\n");
			for (IPhoto photo : photos) {
				sb.append(photo.file().getPath()).append("\n");
			}
		}

		return sb.toString();
	}

}
