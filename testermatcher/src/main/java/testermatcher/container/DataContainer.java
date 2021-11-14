package testermatcher.container;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import testermatcher.factory.BugFactory;
import testermatcher.factory.DeviceFactory;
import testermatcher.factory.TesterFactory;
import testermatcher.model.Bug;
import testermatcher.model.Device;
import testermatcher.model.Tester;
import testermatcher.transfer.DataTransfer;
import testermatcher.transfer.model.TesterDeviceTransfer;

public class DataContainer {

	private final Map<Long, Tester> testers;
	private final Map<Long, Device> devices;
	private final Map<Long, Bug> bugs;

	public DataContainer(Map<Long, Tester> testers, Map<Long, Device> devices, Map<Long, Bug> bugs) {
		this.testers = testers;
		this.devices = devices;
		this.bugs = bugs;
	}

	public static DataContainer generateDataContainer(DataTransfer dataTransfer) {
		List<TesterDeviceTransfer> testerDeviceTransfer = dataTransfer.getTesterDevices();

		final Map<Long, Set<Long>> testersDevices = testerDeviceTransfer.stream()
				.collect(Collectors.groupingBy(TesterDeviceTransfer::getTesterId,
						Collectors.mapping(TesterDeviceTransfer::getDeviceId, Collectors.toSet())));
		final Map<Long, Set<Long>> devicesTesters = testerDeviceTransfer.stream()
				.collect(Collectors.groupingBy(TesterDeviceTransfer::getDeviceId,
						Collectors.mapping(TesterDeviceTransfer::getTesterId, Collectors.toSet())));

		Map<Long, Tester> testers = TesterFactory.createTesters(dataTransfer.getTesters()).stream()
				.collect(Collectors.toMap(Tester::getTesterId, Function.identity()));
		Map<Long, Device> devices = DeviceFactory.createDevices(dataTransfer.getDevices()).stream()
				.collect(Collectors.toMap(Device::getDeviceId, Function.identity()));

		testers.values().stream().forEach(t -> t.setDevices(testersDevices.get(t.getTesterId()).stream()
				.map(deviceId -> devices.get(deviceId)).collect(Collectors.toSet())));
		devices.values().stream().forEach(d -> d.setTesters(devicesTesters.get(d.getDeviceId()).stream()
				.map(testerId -> testers.get(testerId)).collect(Collectors.toSet())));

		Map<Long, Bug> bugs = BugFactory.createBugs(dataTransfer.getBugs(), testers, devices).stream()
				.collect(Collectors.toMap(Bug::getBugId, Function.identity()));

		return new DataContainer(testers, devices, bugs);
	}

	public String getTesterUserName(Long testerId) {
		return this.getTesters().get(testerId).getUserName();
	}

	public Map<Long, Tester> getTesters() {
		return testers;
	}

	public Map<Long, Device> getDevices() {
		return devices;
	}

	public Map<Long, Bug> getBugs() {
		return bugs;
	}
}
