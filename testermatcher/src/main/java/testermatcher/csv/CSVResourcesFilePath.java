package testermatcher.csv;

public final class CSVResourcesFilePath {

	public static final String FILES_PATH = "src/main/resources/";

	public static final String TESTERS = getFilePath("testers.csv");
	public static final String DEVICES = getFilePath("devices.csv");
	public static final String TESTER_DEVICE = getFilePath("tester_device.csv");
	public static final String BUGS = getFilePath("bugs.csv");

	private static String getFilePath(final String fileName) {
		return FILES_PATH + fileName;
	}

}
