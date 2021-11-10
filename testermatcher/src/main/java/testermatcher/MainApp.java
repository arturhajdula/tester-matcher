package testermatcher;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import testermatcher.csv.CSVConfig;
import testermatcher.csv.CSVReader;
import testermatcher.csv.CSVResourcesFilePath;
import testermatcher.factory.TesterFactory;
import testermatcher.model.Bug;
import testermatcher.model.Device;
import testermatcher.model.Tester;
import testermatcher.model.transfer.TesterDeviceTransfer;
import testermatcher.model.transfer.TesterTransfer;

public class MainApp {

	public static void main(String[] args) {

			CSVConfig config = CSVConfig.createDefaultCSVConfig();
			List<TesterTransfer> testersCSV = CSVReader.readSimpleObjectsList(TesterTransfer.class, CSVResourcesFilePath.TESTERS, config);
			List<TesterDeviceTransfer> testerDevices = CSVReader.readSimpleObjectsList(TesterDeviceTransfer.class, CSVResourcesFilePath.TESTER_DEVICE, config);
			List<Device> devices = CSVReader.readSimpleObjectsList(Device.class, CSVResourcesFilePath.DEVICES, config);
			List<Bug> bugs = CSVReader.readSimpleObjectsList(Bug.class, CSVResourcesFilePath.BUGS, config);

			List<Tester> testers = TesterFactory.createTesters(testersCSV, devices, testerDevices);

			// Country = "US", Device = "iPhone 4" (id = 1)
			final Map<Long, Device> devicesMap = devices.stream()
					.collect(Collectors.toMap(Device::getDeviceId, Function.identity()));
			final Map<Long, Tester> testersMap = testers.stream()
					.collect(Collectors.toMap(Tester::getTesterId, Function.identity()));
			
			String country = "US";
			Device device = devicesMap.get(1L);
			List<Tester> r = testers.stream().filter(m -> m.getCountry().equals(country) && m.getDevices().contains(device)).collect(Collectors.toList());

			List<Bug> bugs2 = bugs.stream().filter(b -> ( testersMap.get(b.getTesterId()).getCountry().equals(country) && devicesMap.get(b.getDeviceId()).equals(device))).collect(Collectors.toList());
			bugs2.forEach(System.out::println);
			r.forEach(System.out::println);

//			Map<>
			
			Map<Long, Map<Long, Long>> map = bugs.stream()
				    .collect(Collectors.groupingBy(Bug::getTesterId,
				        Collectors.groupingBy(Bug::getDeviceId, Collectors.counting())));

//			System.out.println(map);
			
//			devices.forEach(System.out::println);
//			bugs.forEach(System.out::println);
//			testerDevices.forEach(System.out::println);

	}

}
