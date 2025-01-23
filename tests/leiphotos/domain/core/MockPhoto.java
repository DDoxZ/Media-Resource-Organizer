package leiphotos.domain.core;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.domain.facade.IPhoto;

/**
 * This class is a Mock for type IPhoto. 
 * It allow us to write unit tests for any class C that depend on the type IPhoto,
 * that are independent of any implementation of this type. 
 * This is a way of testing C isolated from the bugs that might exist in class Photo. 
 * For instance, we can create use this class to create a mock photo like this:
 * 
 * 		MockPhoto photo = new MockPhoto("Test Photo",null,null,false,null,0,true);

 * There are frameworks that support the creation of mocks, for instance, Mockito.
 * Using this framework, we would write instead:
 *  		
 * 		IPhoto mockPhoto = mock(IPhoto.class);
 *		when(mockPhoto.title()).thenReturn("Test Photo");
 *      when(mockPhoto.matches(anyString())).thenReturn(true);
 * 
 * @author malopes DCO2324
 *
 */
public record MockPhoto(
		String title,
		LocalDateTime capturedDate,
		LocalDateTime addedDate,
		boolean isFavourite,
		File file,
		long size,
		boolean matches
		) implements IPhoto{

	public MockPhoto(File file) {
		this(null,null,null,false,file,0,true);
	}
	
	public MockPhoto(File file, boolean matches) {
		this(null,null,null,false,file,0,matches);
	}

	
	@Override
	public void toggleFavourite() {
		//mock is immutable
	}

	@Override
	public Optional<? extends GPSCoordinates> getPlace() {
		return Optional.empty();
	}

	@Override
	public boolean matches(String regexp) {
		return matches;
	}
}
