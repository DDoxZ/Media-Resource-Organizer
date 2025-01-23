package leiphotos.domain.core;

import java.io.File;
import java.util.Optional;

import leiphotos.domain.facade.GPSCoordinates;

public class MockFile extends File{

	private long size;

	public MockFile(String pathname, long size) {
		super(pathname);
		this.size = size;
	}

	@Override
	public long length() {
		return size;
	}
}
