package testermatcher.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import testermatcher.model.Device;
import testermatcher.model.Tester;
import testermatcher.model.transfer.TesterDeviceTransfer;
import testermatcher.model.transfer.TesterTransfer;

public class TesterFactory {

	public static List<Tester> createTesters(List<TesterTransfer> testerTransfer, List<Device> devices,
			List<TesterDeviceTransfer> testerDeviceTransfer) {

		final Map<Long, Device> devicesMap = devices.stream()
				.collect(Collectors.toMap(Device::getDeviceId, Function.identity()));
		final Map<Long, Set<Device>> testersDevices = testerDeviceTransfer.stream().collect(Collectors.groupingBy(
				TesterDeviceTransfer::getTesterId,
				Collectors.mapping(testerDevice -> devicesMap.get(testerDevice.getDeviceId()), Collectors.toSet())));

		List<Tester> testers = new ArrayList<>();
		testerTransfer.stream().forEach(t -> testers.add(createSingleTester(testersDevices, t)));

		return testers;
	}

	private static Tester createSingleTester(final Map<Long, Set<Device>> testersDevices, TesterTransfer t) {
		return new Tester(t.getTesterId(), t.getFirstName(), t.getLastName(), t.getCountry(), t.getLastLogin(),
				testersDevices.get(t.getTesterId()));
	}
}
