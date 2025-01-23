package leiphotos.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.io.File;

import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ILibrariesController;

public class LoaderPhotos {

	/**
	 * An utility class should not have public constructors
	 */
	private LoaderPhotos() {
	}
	
	/**
	 * Import some photos to a library through its controller
	 * for testing purposes
	 * 
	 * @param slc the photo library controller
	 */
	public static List<IPhoto> load(ILibrariesController slc) {
        List<IPhoto> photos = new ArrayList<>();
        
        Optional<IPhoto> photo;
        photo = slc.importPhoto("Anel", buildPath("photos", "AnelJVasconcelos.jpeg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Best arch", buildPath("photos", "ArchesNationalPark.JPG"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Cloud Gate", buildPath("photos", "Bean.jpeg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("IAS Book borrow by Oppenheimer", buildPath("photos", "Book.jpeg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Cars Oppenheimer movie", buildPath("photos", "Cars.jpg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Ginkaku-ji", buildPath("photos", "Ginkaku-ji.JPG"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Rica Iguana", buildPath("photos", "Iguana.JPG"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("IAS Library Rules", buildPath("photos", "Library.jpeg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("IAS Eistein office", buildPath("photos", "Office.jpg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Papoilas", buildPath("photos", "Papoilas.jpeg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Octopus Vasconcelos", buildPath("photos", "Polvo.jpeg"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("Salto Alto", buildPath("photos", "SapatoJVasconcelos.JPG"));
        if (photo.isPresent()) photos.add(photo.get());
        photo = slc.importPhoto("", buildPath("FicheiroQueNaoExiste"));
        if (photo.isPresent()) photos.add(photo.get());
        return Collections.unmodifiableList(photos);
    }
	
	private static String buildPath(String... parts) {
        return String.join(File.separator, parts);
    }
}
