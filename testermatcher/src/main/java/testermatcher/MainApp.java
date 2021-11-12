package testermatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import testermatcher.csv.CSVConfig;
import testermatcher.model.Bug;
import testermatcher.model.Device;
import testermatcher.model.Tester;
import testermatcher.model.transfer.CSVDataContainer;

public class MainApp {

	public static void main(String[] args) {

		CSVConfig config = CSVConfig.createDefaultCSVConfig();
		CSVDataContainer dataCSV = CSVDataContainer.readData(config);
		DataContainer data = DataContainer.generateDataContainer(dataCSV);

		Set<String> countriesAll = data.getTesters().values().stream().map(t -> t.getCountry())
				.collect(Collectors.toSet());
		Set<Long> deviceIdsAll = data.getDevices().values().stream().map(d -> d.getDeviceId())
				.collect(Collectors.toSet());
		System.out.println("countriesAll = " + countriesAll);
		System.out.println("deviceIdsAll = " + deviceIdsAll);

		Set<String> selectedCountries = Stream.of("US", "JP", "GB").collect(Collectors.toSet());
		Set<Long> selectedDeviceIds = Stream.of(5L, 6L, 7L, 8L, 9L).collect(Collectors.toSet());

		System.out.println("Countries = " + selectedCountries + "\tDevices = "
				+ selectedDeviceIds.stream().map(s -> data.getDevices().get(s)).collect(Collectors.toSet()));

		List<Tester> testersMatched = data.getTesters().values().stream()
				.filter(filterTestersCondition(selectedCountries, selectedDeviceIds)).collect(Collectors.toList());

		List<Bug> bugsMatched = data.getBugs().values().stream()
				.filter(filterBugsCondition(selectedCountries, selectedDeviceIds)).collect(Collectors.toList());

		Map<Long, Long> bugsCountPerTesterId = bugsMatched.stream()
				.collect(Collectors.groupingBy(b -> b.getTester().getTesterId(), Collectors.counting()));

		if (bugsCountPerTesterId.size() < testersMatched.size()) {
			Set<Long> testerIds = new HashSet<Long>(
					testersMatched.stream().map(t -> t.getTesterId()).collect(Collectors.toSet()));
			testerIds.removeAll(bugsCountPerTesterId.keySet());
			testerIds.stream().forEach(missingTesterId -> bugsCountPerTesterId.put(missingTesterId, 0L));
		}
		System.out.println("All matching testers (" + testersMatched.size() + "):" + testersMatched);

		bugsCountPerTesterId.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEach(e -> System.out.println(data.getTesterUserName(e.getKey()) + " => " + e.getValue()));

	}

	private static Predicate<? super Bug> filterBugsCondition(Set<String> selectedCountries,
			Set<Long> selectedDeviceIds) {
		return b -> isBugMeetFilterRequirements(b, selectedCountries, selectedDeviceIds);
	}

	private static Predicate<? super Tester> filterTestersCondition(Set<String> selectedCountries,
			Set<Long> selectedDeviceIds) {
		return m -> isTesterMeetFilterRequirements(m, selectedCountries, selectedDeviceIds);
	}

	private static boolean isTesterMeetFilterRequirements(Tester tester, Set<String> selectedCountries,
			Set<Long> selectedDeviceIds) {
		Set<Long> testerDeviceIds = tester.getDevices().stream().map(d -> d.getDeviceId()).collect(Collectors.toSet());

		return isMatchTesterCountry(tester, selectedCountries)
				&& isMatchTesterDevices(selectedDeviceIds, testerDeviceIds);
	}

	private static boolean isBugMeetFilterRequirements(Bug b, Set<String> selectedCountries,
			Set<Long> selectedDeviceIds) {
		return isMatchTesterCountry(b.getTester(), selectedCountries)
				&& isMatchBugDevice(b.getDevice(), selectedDeviceIds);
	}

	private static boolean isMatchTesterCountry(Tester m, Set<String> selectedCountries) {
		return selectedCountries.contains(m.getCountry());
	}

	private static boolean isMatchBugDevice(Device device, Set<Long> selectedDeviceIds) {
		return selectedDeviceIds.contains(device.getDeviceId());
	}

	private static boolean isMatchTesterDevices(Set<Long> selectedDeviceIds, Set<Long> testerDeviceIds) {
		return !Collections.disjoint(testerDeviceIds, selectedDeviceIds);
	}

}
