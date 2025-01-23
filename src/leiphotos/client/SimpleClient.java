package leiphotos.client;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import leiphotos.domain.facade.IAlbumsController;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ILibrariesController;
import leiphotos.domain.facade.IViewsController;
import leiphotos.domain.facade.LEIPhotos;
import leiphotos.domain.facade.ViewsType;

/**
 * A simple application client that uses the different services provided by the 
 * three application controllers.
 *	
 * @author malopes
 */
public class SimpleClient {

	private static final String OPEN_MSG = "\n----------------------------------------------- ";
	private static final String CLOSE_MSG = "-----------------------------------------------\n";

	/**
	 * An utility class should not have public constructors
	 */
	private SimpleClient() {
	}

	public static void main (String [] args) throws InterruptedException {

		LEIPhotos leiphotos = new  LEIPhotos();
		ILibrariesController plc = leiphotos.libsController();
		IViewsController vlc = leiphotos.viewsController();
		IAlbumsController alc = leiphotos.albumsController();

		//setUp
		List<IPhoto> photos = loadSomePhotos(plc); 
		showState(plc,vlc);

		//core operations with libs and views
		markSomePhotosAsFavourites(plc, photos.get(0), photos.get(1), photos.get(2));
		showState(plc,vlc);

		deleteSomePhotos(plc, photos.get(0), photos.get(6));
		showState(plc,vlc);

		emptyTrash(plc);
		showState(plc,vlc);	

		changeSortingCriteria(vlc,ViewsType.ALL_MAIN);
		showState(plc,vlc);	

		markSomePhotosAsFavourites(plc, photos.get(1), photos.get(9), photos.get(10));
		showState(plc,vlc);

		// match all photos that have Vasconcelos anywhere in the toString
		List<IPhoto> selectedPhotos = searchPhotos(vlc,ViewsType.ALL_MAIN, ".*Vasconcelos.*");

		//operations with albums	
		String[] albums = {"Oppenheimer","Monuments","JoanaVasconcelos","Monuments"};

		createSomeAlbums(alc, albums);
		showState(vlc,alc);

		addSomePhotosToAlbum(alc, albums[0], List.of(photos.get(3),photos.get(4),photos.get(7),photos.get(8)));
		addSomePhotosToAlbum(alc, albums[1], List.of(photos.get(2)));
		addSomePhotosToAlbum(alc, albums[2], selectedPhotos);
		showState(vlc,alc);

		removeAlbum(alc,albums[1]);
		showState(vlc,alc);

		//operations over the library that interfere with albums
		deleteSomePhotos(plc, photos.get(7));
		showState(plc,vlc,alc);

		int seconds = 20;
		System.out.println("\nWait " + seconds + " seconds (enough time for photo in trash to be deleted).\n"
					+ "Note that, in the designed solution, the cleaning is only performed \n"
					+ "when the photos in trash have to be presented in the trash view. \n"); 
		Thread.sleep(seconds * 1000);
		showState(plc,vlc,alc);
	}


	private static void showState(Object... objects) {
		for(Object o: objects)
			System.out.println(o);
	}

	private static List<IPhoto> loadSomePhotos(ILibrariesController plc) {
		System.out.println(OPEN_MSG);		
		System.out.println("    Load photos and add them to main library    ");
		System.out.println(CLOSE_MSG);
		List<IPhoto> list = LoaderPhotos.load(plc);
		System.out.println(idsToString(list));
		return  list;
	}

	private static void markSomePhotosAsFavourites(ILibrariesController plc, IPhoto... photos) {
		System.out.println(OPEN_MSG);		
		System.out.println("    Marking \n" +  idsToString(Arrays.asList(photos)) + " as favourites          ");
		System.out.println(CLOSE_MSG);
		plc.toggleFavourite(new HashSet<>(Arrays.asList(photos)));
	}

	private static void deleteSomePhotos(ILibrariesController plc, IPhoto... photos) {
		System.out.println(OPEN_MSG);		
		System.out.print("    Delete \n" + idsToString(Arrays.asList(photos)));
		System.out.println(CLOSE_MSG);
		plc.deletePhotos(new HashSet<>(Arrays.asList(photos)));
	}

	private static void emptyTrash(ILibrariesController plc) {
		System.out.println(OPEN_MSG);		
		System.out.println("    Empty Trash                                ");
		System.out.println(CLOSE_MSG);
		plc.emptyTrash();
	}

	private static void changeSortingCriteria(IViewsController vlc, ViewsType v) {
		System.out.println(OPEN_MSG);		
		System.out.println("    Change sorting criteria of "+ v + ": use title");
		System.out.println(CLOSE_MSG);
		Comparator<IPhoto> criteria = (p1,p2) -> p1.title().compareTo(p2.title());
		vlc.setSortingCriteria(v,criteria);
	}

	private static List<IPhoto> searchPhotos(IViewsController vlc, ViewsType v, String regex) {
		System.out.println(OPEN_MSG);		
		System.out.println("    Search photos in "+ v + " matching " + regex);
		System.out.println(CLOSE_MSG);
		List<IPhoto> list = vlc.getMatches(v, regex);
		System.out.println(idsToString(list));
		return list;
	}

	private static void createSomeAlbums(IAlbumsController alc, String... albumsNames) {
		System.out.println(OPEN_MSG);		
		System.out.println("    Create " + Arrays.toString(albumsNames) + "                   ");
		System.out.println(CLOSE_MSG);
		for(String name: albumsNames)
			alc.createAlbum(name);
	}

	private static void addSomePhotosToAlbum(IAlbumsController alc, String albumName, List<IPhoto> photos) {
		System.out.println(OPEN_MSG);		
		System.out.println("    Add \n" + idsToString(photos) + " to Album " + albumName);
		System.out.println(CLOSE_MSG);
		alc.selectAlbum(albumName);
		Set<IPhoto> set = photos.stream().collect(Collectors.toSet());
		alc.addPhotos(set);
	}


	private static void removeAlbum(IAlbumsController alc, String albumName) {
		System.out.println(OPEN_MSG);		
		System.out.println("   Remove  Album " + albumName);
		System.out.println(CLOSE_MSG);
		alc.selectAlbum(albumName);
		alc.removeAlbum();
	}

	private static String idsToString(Iterable<IPhoto> photos) {
		StringBuilder sb = new StringBuilder();
		for (IPhoto p: photos) {
			sb.append(p.file()+"\n");
		}
		return sb.toString();
	}
}
