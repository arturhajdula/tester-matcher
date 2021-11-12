package testermatcher.model.transfer;

import java.util.List;

import testermatcher.csv.CSVConfig;
import testermatcher.csv.CSVReader;
import testermatcher.csv.CSVResourcesFilePath;

public class CSVDataContainer {
	private final List<TesterTransfer> testers;
	private final List<TesterDeviceTransfer> testerDevices;
	private final List<DeviceTransfer> devices;
	private final List<BugTransfer> bugs;

	public CSVDataContainer(List<TesterTransfer> testers, List<TesterDeviceTransfer> testerDevices,
			List<DeviceTransfer> devices, List<BugTransfer> bugs) {
		this.testers = testers;
		this.testerDevices = testerDevices;
		this.devices = devices;
		this.bugs = bugs;
	}

	public static CSVDataContainer readData(CSVConfig config) {
		return new CSVDataContainer(
				CSVReader.readSimpleObjectsList(TesterTransfer.class, CSVResourcesFilePath.TESTERS, config),
				CSVReader.readSimpleObjectsList(TesterDeviceTransfer.class, CSVResourcesFilePath.TESTER_DEVICE, config),
				CSVReader.readSimpleObjectsList(DeviceTransfer.class, CSVResourcesFilePath.DEVICES, config),
				CSVReader.readSimpleObjectsList(BugTransfer.class, CSVResourcesFilePath.BUGS, config));
	}

	public List<TesterTransfer> getTesters() {
		return testers;
	}

	public List<TesterDeviceTransfer> getTesterDevices() {
		return testerDevices;
	}

	public List<DeviceTransfer> getDevices() {
		return devices;
	}

	public List<BugTransfer> getBugs() {
		return bugs;
	}
}
