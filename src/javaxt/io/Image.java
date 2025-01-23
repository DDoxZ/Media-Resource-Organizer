package javaxt.io;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Simplified version of Image class from JXT
 *	
 * @author JXT 
 * 
 * 
 * For documentation see:
 * 		https://www.javaxt.com/documentation?jar=javaxt-core&package=javaxt.io&class=Image
 */

//******************************************************************************
//**  Image Class
//******************************************************************************
/**
 *   Used to open, resize, rotate, crop and save images.
 *
 ******************************************************************************/

public class Image {
	private BufferedImage bufferedImage = null;
	private IIOMetadata metadata;
	private HashMap<Integer, Object> exif;
	private HashMap<Integer, Object> gps;


	//**************************************************************************
	//** Constructor
	//**************************************************************************
	/**  Creates a new instance of this class using an existing image */

	public Image(String PathToImageFile){
		this(new java.io.File(PathToImageFile));
	}

	public Image(java.io.File file){
		if (!file.exists()) throw new IllegalArgumentException("Input file not found.");
		try{ createBufferedImage(new FileInputStream(file)); }
		catch(Exception e){}
	}

	//**************************************************************************
	//** createBufferedImage
	//**************************************************************************
	/** Used to create a BufferedImage from a InputStream
	 */
	private void createBufferedImage(java.io.InputStream input) {
		try{
			//bufferedImage = ImageIO.read(input);

			javax.imageio.stream.ImageInputStream stream = ImageIO.createImageInputStream(input);

			Iterator<?> iter = ImageIO.getImageReaders(stream);
			if (!iter.hasNext()) {
				return;
			}

			ImageReader reader = (ImageReader)iter.next();
			ImageReadParam param = reader.getDefaultReadParam();
			reader.setInput(stream, true, true);

			try {
				bufferedImage = reader.read(0, param);
				metadata = reader.getImageMetadata(0);
			}
			finally {
				reader.dispose();
				stream.close();
			}


			input.close();
		}
		catch(Exception e){
			//e.printStackTrace();
		}
	}


	//**************************************************************************
	//** getBufferedImage
	//**************************************************************************
	/**  Returns the java.awt.image.BufferedImage represented by the current
	 *   image.
	 */
	public BufferedImage getBufferedImage(){
		return bufferedImage;
	}

	//**************************************************************************
	//** getWidth
	//**************************************************************************
	/**  Returns the width of the image, in pixels. */

	public int getWidth(){
		return bufferedImage.getWidth();
	}


	//**************************************************************************
	//** getHeight
	//**************************************************************************
	/** Returns the height of the image, in pixels.
	 */
	public int getHeight(){
		return bufferedImage.getHeight();
	}


	//**************************************************************************
	//** getIIOMetadata
	//**************************************************************************
	/** Returns the raw, javax.imageio.metadata.IIOMetadata associated with this
	 * image. You can iterate through the metadata using an xml parser like this:
   <pre>
    IIOMetadata metadata = image.getIIOMetadata();
    for (String name : metadata.getMetadataFormatNames()) {
        System.out.println( "Format name: " + name );
        org.w3c.dom.Node metadataNode = metadata.getAsTree(name);
        System.out.println(javaxt.xml.DOM.getNodeValue(metadataNode));
    }
   </pre>
	 */
	public IIOMetadata getIIOMetadata(){
		return metadata;
	}


	//  //**************************************************************************
	//  //** getExifData
	//  //**************************************************************************
	//  /** Returns the raw EXIF byte array (marker 0xE1).
	//   */
	//    public byte[] getExifData(){
	//        IIOMetadataNode[] tags = getUnknownTags(0xE1);
	//        if (tags.length==0) return null;
	//        return (byte[]) tags[0].getUserObject();
	//    }
	//

	//**************************************************************************
	//** getExifTags
	//**************************************************************************
	/** Used to parse EXIF metadata and return a list of key/value pairs found
	 *  in the metadata. Values can be Strings, Integers, or raw Byte Arrays.
	 *  You can retrieve specific EXIF metadata values like this:
   <pre>
    javaxt.io.Image image = new javaxt.io.Image("/temp/image.jpg");
    java.util.HashMap<Integer, Object> exif = image.getExifTags();
    System.out.println("Date: " + exif.get(0x0132));
    System.out.println("Camera: " + exif.get(0x0110));
    System.out.println("Focal Length: " + exif.get(0x920A));
    System.out.println("F-Stop: " + exif.get(0x829D));
    System.out.println("Shutter Speed: " + exif.get(0x829A));
   </pre>
	 * Note that the EXIF MakerNote is not parsed.
	 */
	public HashMap<Integer, Object> getExifTags(){
		if (exif==null) parseExif();
		return exif;
	}


	//**************************************************************************
	//** getGpsTags
	//**************************************************************************
	/** Used to parse EXIF metadata and return a list of key/value pairs
	 *  associated with GPS metadata. Values can be Strings, Integers, or raw
	 *  Byte Arrays.
	 */
	public HashMap<Integer, Object> getGpsTags(){
		if (gps==null) parseExif();
		return gps;
	}


	/** Private method used to initialize the exif and gps hashmaps */
	private void parseExif(){

		exif = new HashMap<Integer, Object>();
		gps = new HashMap<Integer, Object>();
		for (IIOMetadataNode marker : getUnknownTags(0xE1)){
			byte[] exifData = (byte[]) marker.getUserObject();

			MetadataParser metadataParser = new MetadataParser(exifData, 0xE1);
			HashMap<Integer, Object> exif = metadataParser.getTags("EXIF");
			HashMap<Integer, Object> gps = metadataParser.getTags("GPS");

			if (exif!=null) this.exif.putAll(exif);
			if (gps!=null) this.gps.putAll(gps);

			metadataParser = null;
		}
	}


	//**************************************************************************
	//** getGPSCoordinate
	//**************************************************************************
	/** Returns the x/y (lon/lat) coordinate tuple for the image. Value is
	 *  derived from EXIF GPS metadata (tags 0x0001, 0x0002, 0x0003, 0x0004).
	 */
	public double[] getGPSCoordinate(){
		getExifTags();
		try{
			Double lat = getCoordinate((String) gps.get(0x0002));
			Double lon = getCoordinate((String) gps.get(0x0004));
			String latRef = (String) gps.get(0x0001); //N
			String lonRef = (String) gps.get(0x0003); //W

			if (!latRef.equalsIgnoreCase("N")) lat = -lat;
			if (!lonRef.equalsIgnoreCase("E")) lon = -lon;

			return new double[]{lon, lat};
		}
		catch(Exception e){
			return null;
		}
	}


	private double getCoordinate(String RationalArray) {

		//num + "/" + den
		String[] arr = RationalArray.substring(1, RationalArray.length()-1).split(",");
		String[] deg = arr[0].trim().split("/");
		String[] min = arr[1].trim().split("/");
		String[] sec = arr[2].trim().split("/");

		double degNumerator = Double.parseDouble(deg[0]);
		double degDenominator = 1D; try{degDenominator = Double.parseDouble(deg[1]);} catch(Exception e){}
		double minNumerator = Double.parseDouble(min[0]);
		double minDenominator = 1D; try{minDenominator = Double.parseDouble(min[1]);} catch(Exception e){}
		double secNumerator = Double.parseDouble(sec[0]);
		double secDenominator = 1D; try{secDenominator = Double.parseDouble(sec[1]);} catch(Exception e){}

		double m = 0;
		if (degDenominator != 0 || degNumerator != 0){
			m = (degNumerator / degDenominator);
		}

		if (minDenominator != 0 || minNumerator != 0){
			m += (minNumerator / minDenominator) / 60D;
		}

		if (secDenominator != 0 || secNumerator != 0){
			m += (secNumerator / secDenominator / 3600D);
		}

		return m;
	}


	//**************************************************************************
	//** getGPSDatum
	//**************************************************************************
	/** Returns the datum associated with the GPS coordinate. Value is
	 *  derived from EXIF GPS metadata (tag 0x0012).
	 */
	public String getGPSDatum(){
		getExifTags();
		return (String) gps.get(0x0012);
	}


	//**************************************************************************
	//** getUnknownTags
	//**************************************************************************
	/** Returns a list of "unknown" IIOMetadataNodes for a given MarkerTag. You
	 *  can use this method to retrieve EXIF, IPTC, XPM, and other format
	 *  specific metadata. Example:
     <pre>
      byte[] IptcData = (byte[]) metadata.getUnknownTags(0xED)[0].getUserObject();
      byte[] ExifData = (byte[]) metadata.getUnknownTags(0xE1)[0].getUserObject();
     </pre>
	 */
	public IIOMetadataNode[] getUnknownTags(int MarkerTag){
		java.util.ArrayList<IIOMetadataNode> markers = new java.util.ArrayList<IIOMetadataNode>();
		if (metadata!=null)
			for (String name : metadata.getMetadataFormatNames()) {
				IIOMetadataNode node=(IIOMetadataNode) metadata.getAsTree(name);
				Node[] unknownNodes = getElementsByTagName("unknown", node);
				for (Node unknownNode : unknownNodes){
					try{
						int marker = Integer.parseInt(getAttributeValue(unknownNode.getAttributes(), "MarkerTag"));
						if (marker==MarkerTag) markers.add((IIOMetadataNode) unknownNode);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		return markers.toArray(new IIOMetadataNode[markers.size()]);
	}

	//**************************************************************************
	//** getElementsByTagName (Copied from javaxt.xml.DOM)
	//**************************************************************************
	/** Returns an array of nodes that match a given tagName (node name). The
	 *  results will include all nodes that match, regardless of namespace. To
	 *  narrow the results to a specific namespace, simply include the namespace
	 *  prefix in the tag name (e.g. "t:Contact"). Returns an empty array if
	 *  no nodes are found.
	 */
	private static Node[] getElementsByTagName(String tagName, Node node){
		java.util.ArrayList<Node> nodes = new java.util.ArrayList<Node>();
		getElementsByTagName(tagName, node, nodes);
		return nodes.toArray(new Node[nodes.size()]);
	}

	private static void getElementsByTagName(String tagName, Node node, java.util.ArrayList<Node> nodes){
		if (node!=null && node.getNodeType()==1){

			String nodeName = node.getNodeName().trim();
			if (nodeName.contains(":") && !tagName.contains(":")){
				nodeName = nodeName.substring(nodeName.indexOf(":")+1);
			}

			if (nodeName.equalsIgnoreCase(tagName)){
				nodes.add(node);
			}

			NodeList childNodes = node.getChildNodes();
			for (int i=0; i<childNodes.getLength(); i++){
				getElementsByTagName(tagName, childNodes.item(i), nodes);
			}
		}
	}


	//**************************************************************************
	//** getAttributeValue (Copied from javaxt.xml.DOM)
	//**************************************************************************
	/**  Used to return the value of a given node attribute. The search is case
	 *   insensitive. If no match is found, returns an empty string.
	 */
	public static String getAttributeValue(NamedNodeMap attrCollection, String attrName){

		if (attrCollection!=null){
			for (int i=0; i < attrCollection.getLength(); i++ ) {
				Node node = attrCollection.item(i);
				if (node.getNodeName().equalsIgnoreCase(attrName)) {
					return node.getNodeValue();
				}
			}
		}
		return "";
	}
	//******************************************************************************
	//**  MetadataParser Class
	//******************************************************************************
	/**
	 *   Used to decode EXIF and IPTC metadata. Adapted from 2 classes developed by
	 *   Norman Walsh and released under the W3C open source license. The original
	 *   exif classes can be found in the W3C Jigsaw project in the
	 *   org.w3c.tools.jpeg package.
	 *
	 *  @author  Norman Walsh
	 *  @copyright Copyright (c) 2003 Norman Walsh
	 ******************************************************************************/

	private class MetadataParser {

		// Implementation notes:
		// (1) Merged Version 1.1 of the "Exif.java" and "ExifData.java" classes.
		// (2) Added new IPTC metadata parser.
		// (3) All unsigned integers are treated as signed ints (should be longs).
		// (4) Added logic to parse GPS Info using the GPS IFD offset value (tag 34853,
		//     hex 0x8825).
		// (5) Added logic to parse an array of rational numbers (e.g. GPS metadata).
		// (6) Improved performance in the parseExif() method by serializing only the
		//     first 8 characters into a string (vs the entire EXIF byte array).
		// (7) TODO: Need to come up with a clever scheme to parse MakerNotes.

		private final int bytesPerFormat[] = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
		private final int NUM_FORMATS = 12;
		private final int FMT_BYTE = 1;
		private final int FMT_STRING = 2;
		private final int FMT_USHORT = 3;
		private final int FMT_ULONG = 4;
		private final int FMT_URATIONAL = 5;
		private final int FMT_SBYTE = 6;
		//private final int FMT_UNDEFINED = 7;
		private final int FMT_SSHORT = 8;
		private final int FMT_SLONG = 9;
		private final int FMT_SRATIONAL = 10;
		//private final int FMT_SINGLE = 11;
		//private final int FMT_DOUBLE = 12;

		private byte[] data = null;
		private boolean intelOrder = false;

		private final int TAG_EXIF_OFFSET = 0x8769;
		private final int TAG_INTEROP_OFFSET = 0xa005;
		private final int TAG_GPS_OFFSET = 0x8825;
		

		private HashMap<String, HashMap<Integer, Object>> tags =
				new HashMap<String, HashMap<Integer, Object>>();


		public MetadataParser(byte[] data, int marker) {
			switch (marker) {
			case 0xED: parseIptc(data); break;
			case 0xE1: parseExif(data); break;
			}

			data = null;
		}


		//**************************************************************************
		//** parseIptc
		//**************************************************************************
		/** Used to parse IPTC metadata
		 */
		private void parseIptc(byte[] iptcData) {

			HashMap<Integer, Object> tags = new HashMap<Integer, Object>();
			this.tags.put("IPTC", tags);

			data = iptcData;

			int offset = 0;
			while (offset < data.length) {
				if (data[offset] == 0x1c) {

					offset++;

					int directoryType;
					int tagType;
					int tagByteCount;
					try {
						directoryType = data[offset++];
						tagType = data[offset++];
						tagByteCount = get16u(offset);
						offset += 2;
					}
					catch (Exception e) {
						return;
					}


					int tagIdentifier = tagType | (directoryType << 8);

					String str = "";
					if (tagByteCount < 1 || tagByteCount>(data.length-offset)) {
					}
					else {
						try {
							str = new String(data, offset, tagByteCount, "UTF-8");
							offset += tagByteCount;
						}
						catch (Exception e) {
						}
					}
					tags.put(tagIdentifier, str);
				}
				else{
					offset++;
				}
			}
		}


		//**************************************************************************
		//** parseExif
		//**************************************************************************
		/** Used to parse EXIF metadata
		 */
		public void parseExif(byte[] exifData) {

			HashMap<Integer, Object> tags = new HashMap<Integer, Object>();
			this.tags.put("EXIF", tags);


			try{
				String dataStr = new String(exifData, 0, 8, "UTF-8"); //new String(exifData);
				if (exifData.length <= 4 || !"Exif".equals(dataStr.substring(0, 4))) {
					//System.err.println("Not really EXIF data");
					return;
				}

				String byteOrderMarker = dataStr.substring(6, 8);
				if ("II".equals(byteOrderMarker)) {
					intelOrder = true;
				} else if ("MM".equals(byteOrderMarker)) {
					intelOrder = false;
				} else {
					//System.err.println("Incorrect byte order in EXIF data.");
					return;
				}
			}
			catch(Exception e){
				return;
			}


			data = exifData;

			int checkValue = get16u(8);
			if (checkValue != 0x2a) {
				data = null;
				//System.err.println("Check value fails: 0x"+ Integer.toHexString(checkValue));
				return;
			}


			if (data==null) return;


			int firstOffset = get32u(10);
			processExifDir(6 + firstOffset, 6, tags);
		}



		//**************************************************************************
		//** getTags
		//**************************************************************************
		/** Returns key/value pairs representing the EXIF or IPTC data.
		 */
		public HashMap<Integer, Object> getTags(String dir) {
			return tags.get(dir);
		}


		private void processExifDir(int dirStart, int offsetBase, HashMap<Integer, Object> tags) {
			if (dirStart>=data.length) return;


			int numEntries = get16u(dirStart);
			for (int de = 0; de < numEntries; de++) {
				int dirOffset = dirStart + 2 + (12 * de);

				int tag = get16u(dirOffset);
				int format = get16u(dirOffset + 2);
				int components = get32u(dirOffset + 4);

				//System.err.println("EXIF: entry: 0x" + Integer.toHexString(tag)
				//		 + " " + format
				//		 + " " + components);

				if (format < 0 || format > NUM_FORMATS) {
					//System.err.println("Bad number of formats in EXIF dir: " + format);
					return;
				}

				int byteCount = components * bytesPerFormat[format];
				int valueOffset = dirOffset + 8;

				if (byteCount > 4) {
					int offsetVal = get32u(dirOffset + 8);
					valueOffset = offsetBase + offsetVal;
				}

				if (tag == TAG_EXIF_OFFSET || tag == TAG_INTEROP_OFFSET || tag == TAG_GPS_OFFSET) {

					String dirName = "";
					switch (tag) {
					case TAG_EXIF_OFFSET:
						dirName = "EXIF";
						break;
					case TAG_INTEROP_OFFSET:
						dirName = "EXIF";
						break;
					case TAG_GPS_OFFSET:
						dirName = "GPS";
						break;
					}

					tags = this.tags.get(dirName);
					if (tags==null){
						tags = new HashMap<Integer, Object>();
						this.tags.put(dirName, tags);
					}

					int subdirOffset = get32u(valueOffset);
					processExifDir(offsetBase + subdirOffset, offsetBase, tags);

				}

				//else if (tag==0x927c){ //Maker Note

				//TODO: Come up with a clever way to process the Maker Note
				//data = java.util.Arrays.copyOfRange(data, valueOffset, byteCount);
				//tags = new HashMap<Integer, String>();
				//processExifDir(0, 6);

				//}

				else {

					switch (format) {
					case FMT_STRING:
						String value = getString(valueOffset, byteCount);
						if (value!=null) tags.put(tag, value);
						break;
					case FMT_SBYTE:
					case FMT_BYTE:
					case FMT_USHORT:
					case FMT_SSHORT:
					case FMT_ULONG:
					case FMT_SLONG:
						tags.put(tag, (int) getDouble(format, valueOffset));
						break;
					case FMT_URATIONAL:
					case FMT_SRATIONAL:

						if (components>1) {

							//Create a string representing an array of rational numbers
							StringBuffer str = new StringBuffer();
							str.append("[");
							for (int i=0; i<components; i++){
								str.append( getRational(valueOffset + (8 * i)) );
								if (i<components-1) str.append(",");
							}
							str.append("]");
							tags.put(tag, str.toString());
						}
						else{
							tags.put(tag, getRational(valueOffset));
						}
						break;


					default: //including FMT_UNDEFINED
						byte[] result = getUndefined(valueOffset, byteCount);
						if (result!=null) tags.put(tag, result);
						break;
					}

				}
			}
		}

		//**************************************************************************
		//** getRational
		//**************************************************************************
		/** Returns a string representation of a rational number (numerator and
		 *  denominator separated with a "/" character).
		 */
		private String getRational(int offset) {
			int num = get32s(offset);
			int den = get32s(offset + 4);
			String result = "";

			// This is a bit silly, I really ought to find a real GCD algorithm
			if (num % 10 == 0 && den % 10 == 0) {
				num = num / 10;
				den = den / 10;
			}

			if (num % 5 == 0 && den % 5 == 0) {
				num = num / 5;
				den = den / 5;
			}

			if (num % 3 == 0 && den % 3 == 0) {
				num = num / 3;
				den = den / 3;
			}

			if (num % 2 == 0 && den % 2 == 0) {
				num = num / 2;
				den = den / 2;
			}

			if (den == 0) {
				result = "0";
			} else if (den == 1) {
				result = "" + num; // "" + int sure looks ugly...
			} else {
				result = "" + num + "/" + den;
			}
			return result;
		}

		private int get16s(int offset) {
			int hi, lo;

			if (intelOrder) {
				hi = data[offset + 1];
				lo = data[offset];
			} else {
				hi = data[offset];
				lo = data[offset + 1];
			}

			lo = lo & 0xFF;
			hi = hi & 0xFF;

			return (hi << 8) + lo;
		}

		private int get16u(int offset) {
			int value = get16s(offset);
			return value & 0xFFFF;
		}

		private int get32s(int offset) {
			int n1, n2, n3, n4;

			if (intelOrder) {
				n1 = data[offset + 3] & 0xFF;
				n2 = data[offset + 2] & 0xFF;
				n3 = data[offset + 1] & 0xFF;
				n4 = data[offset] & 0xFF;
			} else {
				n1 = data[offset] & 0xFF;
				n2 = data[offset + 1] & 0xFF;
				n3 = data[offset + 2] & 0xFF;
				n4 = data[offset + 3] & 0xFF;
			}

			return (n1 << 24) + (n2 << 16) + (n3 << 8) + n4;
		}

		private int get32u(int offset) {
			return get32s(offset); //Should probably return a long instead...
		}

		private byte[] getUndefined(int offset, int length) {
			return java.util.Arrays.copyOfRange(data, offset, offset+length);
		}

		private String getString(int offset, int length) {
			try{
				return new String(data, offset, length, "UTF-8").trim();
			}
			catch(Exception e){
				return null;
			}
		}

		//**************************************************************************
		//** getDouble
		//**************************************************************************
		/** Used convert a byte into a double. Note that this method used to be
		 *  called convertAnyValue().
		 */
		private double getDouble(int format, int offset) {
			switch (format) {
			case FMT_SBYTE:
				return data[offset];
			case FMT_BYTE:
				int iValue = data[offset];
				return iValue & 0xFF;
			case FMT_USHORT:
				return get16u(offset);
			case FMT_ULONG:
				return get32u(offset);
			case FMT_URATIONAL:
			case FMT_SRATIONAL:
				int num = get32s(offset);
				int den = get32s(offset + 4);
				if (den == 0) return 0;
				else return (double) num / (double) den;
			case FMT_SSHORT:
				return get16s(offset);
			case FMT_SLONG:
				return get32s(offset);
			default:
				return 0.0;
			}
		}
	}
} //end image class