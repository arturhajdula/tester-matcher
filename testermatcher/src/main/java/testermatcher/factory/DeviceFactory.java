package testermatcher.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import testermatcher.model.Device;
import testermatcher.model.Tester;
import testermatcher.model.transfer.DeviceTransfer;
import testermatcher.model.transfer.TesterDeviceTransfer;
import testermatcher.model.transfer.TesterTransfer;

public class DeviceFactory {

	public static List<Device> createDevices(List<DeviceTransfer> deviceTransfers) {

		List<Device> devices = new ArrayList<>();
		deviceTransfers.stream().forEach(d -> devices.add(createSingleDevice(d)));
		return devices;
	}

	private static Device createSingleDevice(DeviceTransfer d) {
		return new Device(d.getDeviceId(), d.getDescription());
	}
}
