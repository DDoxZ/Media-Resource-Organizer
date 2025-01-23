package leiphotos.domain.core;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import leiphotos.domain.facade.GPSCoordinates;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {

	@Test
	void testCreatePhotoWithoutGPS() {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		File expectedFile = new File("test.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		String expectedCamera = "Canon PowerShot G5";
		String expectedFabricante = "Canon";

		PhotoMetadata meta = new PhotoMetadata(null, expectedCapturedDate, expectedCamera, expectedFabricante);
		Photo testPhoto = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);

		String expectedString = "File:" + expectedFile.toString() + "\n" +
														"Title:" + expectedTitle + " Added:" + expectedAddedDate.toString() + " Size:" + expectedFile.length() + "\n" +
														"[No Location, " + expectedCapturedDate.toString() + ", " + expectedCamera + ", " + expectedFabricante + "]";
		
		assertEquals(expectedString, testPhoto.toString());
	}

	@Test
	void testCreatePhotoWithGPS() {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		File expectedFile = new File("test.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		String expectedCamera = "Canon PowerShot G5";
		String expectedFabricante = "Canon";
		double expectedLat = 41.88;
		double expectedLong = -87.62;
		String expectedDesc = "";
		GPSLocation expectedGpsLocation = new GPSLocation(expectedLat, expectedLong, expectedDesc); 

		PhotoMetadata meta = new PhotoMetadata(expectedGpsLocation, expectedCapturedDate, expectedCamera, expectedFabricante);
		Photo testPhoto = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);

		String expectedString = "File:" + expectedFile.toString() + "\n" +
														"Title:" + expectedTitle + " Added:" + expectedAddedDate.toString() + " Size:" + expectedFile.length() + "\n" +
														"[{Lat:" + expectedLat + " Long:" + expectedLong + " Desc:" + expectedDesc + "}, "+ expectedCapturedDate.toString() + ", " + expectedCamera + ", " + expectedFabricante + "]";
		
		assertEquals(expectedString, testPhoto.toString());
	}

	@Test
	void testToggleFavourite() {
		Photo testPhoto = new Photo(null, null, null, null);

		assertEquals(false, testPhoto.isFavourite());

		testPhoto.toggleFavourite();
		assertEquals(true, testPhoto.isFavourite());

		testPhoto.toggleFavourite();
		assertEquals(false, testPhoto.isFavourite());
	}

	@Test
	void testSize() { //requires the use of a mock file class
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("test.jpg",expectedSize);
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		
		Photo testPhoto = new Photo(expectedTitle, expectedAddedDate, null, expectedFile);

		assertEquals(expectedSize, testPhoto.size());
	}

	@Test
	void testNoMatches() {
		String regexp = "Exp.*";
		MockFile expectedFile = new MockFile("test.jpg", 1024);
		String expectedTitle = "Test Photo";

		LocalDateTime expectedAddedDate = LocalDateTime.now();
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		String expectedCamera = "Canon PowerShot G5";
		String expectedFabricante = "Canon";
		double expectedLat = 41.88;
		double expectedLong = -87.62;
		String expectedDesc = "";
		GPSLocation expectedGpsLocation = new GPSLocation(expectedLat, expectedLong, expectedDesc);

		PhotoMetadata meta = new PhotoMetadata(expectedGpsLocation, expectedCapturedDate, expectedCamera, expectedFabricante);

		Photo testPhoto = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);

		assertFalse(testPhoto.matches(regexp));
	}


	@Test
	void testMatchesTitle() {
		String regexp = "Test.*";
		MockFile expectedFile = new MockFile("abc.jpg", 1024);
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		String expectedCamera = "Canon PowerShot G5";
		String expectedFabricante = "Canon";
		double expectedLat = 41.88;
		double expectedLong = -87.62;
		String expectedDesc = "";
		GPSLocation expectedGpsLocation = new GPSLocation(expectedLat, expectedLong, expectedDesc);

		PhotoMetadata meta = new PhotoMetadata(expectedGpsLocation, expectedCapturedDate, expectedCamera, expectedFabricante);

		Photo testPhoto = new Photo(expectedTitle, expectedAddedDate, meta, expectedFile);

		assertTrue(testPhoto.matches(regexp));
	}


	@Test
	void testMatchesFile() {
		String regexp = "Test.*";

		LocalDateTime expectedAddedDate = LocalDateTime.now();
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		String expectedCamera = "Canon PowerShot G5";
		String expectedFabricante = "Canon";
		double expectedLat = 41.88;
		double expectedLong = -87.62;
		String expectedDesc = "";
		GPSLocation expectedGpsLocation = new GPSLocation(expectedLat, expectedLong, expectedDesc);

		PhotoMetadata meta = new PhotoMetadata(expectedGpsLocation, expectedCapturedDate, expectedCamera, expectedFabricante);

		File file = new File("Test.jpg");
		Photo testPhoto = new Photo("Title Photo", expectedAddedDate, meta, file);

		assertTrue(testPhoto.matches(regexp));
	}

	@Test
	void testEquals() {
		File file1 = new File("test1.jpg");
		File file2 = new File("test2.jpg");
		File file3 = new File("test1.jpg");

		LocalDateTime expectedAddedDate = LocalDateTime.now();
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		String expectedCamera = "Canon PowerShot G5";
		String expectedFabricante = "Canon";
		double expectedLat = 41.88;
		double expectedLong = -87.62;
		String expectedDesc = "";
		GPSLocation expectedGpsLocation = new GPSLocation(expectedLat, expectedLong, expectedDesc);
		PhotoMetadata meta = new PhotoMetadata(expectedGpsLocation, expectedCapturedDate, expectedCamera, expectedFabricante);

		Photo testPhoto1 = new Photo("Test Photo", expectedAddedDate, meta, file1);
		Photo testPhoto2 = new Photo("Test Photo", expectedAddedDate, meta, file2);
		Photo testPhoto3 = new Photo("Test Photo", expectedAddedDate, meta, file3);

		assertFalse(testPhoto1.equals(testPhoto2));
		assertTrue(testPhoto1.equals(testPhoto3));
		assertFalse(testPhoto2.equals(testPhoto3));
	}

	@Test
	void testHashCode() {
		File file1 = new File("test1.jpg");
		File file2 = new File("test1.jpg");

		LocalDateTime expectedAddedDate = LocalDateTime.now();
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		String expectedCamera = "Canon PowerShot G5";
		String expectedFabricante = "Canon";
		double expectedLat = 41.88;
		double expectedLong = -87.62;
		String expectedDesc = "";
		GPSLocation expectedGpsLocation = new GPSLocation(expectedLat, expectedLong, expectedDesc);

		PhotoMetadata meta = new PhotoMetadata(expectedGpsLocation, expectedCapturedDate, expectedCamera, expectedFabricante);

		Photo testPhoto1 = new Photo("Test Photo", expectedAddedDate, meta, file1);
		Photo testPhoto2 = new Photo("Test Photo", expectedAddedDate, meta, file2);

		assertNotEquals(testPhoto1.hashCode(), testPhoto2.hashCode());
	}

}