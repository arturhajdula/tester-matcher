package testermatcher.algorithm;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import testermatcher.container.DataContainer;
import testermatcher.container.FilterOutputData;
import testermatcher.model.Bug;
import testermatcher.model.Device;
import testermatcher.model.Tester;

public class TesterMatcherAlgorithm {

	public static List<UserWithExperience> execute(DataContainer dataContainer, FilterOutputData filter) {
		Set<String> selectedCountries = filter.getSelectedCountries();
		Set<Long> selectedDeviceIds = getSelectedDeviceIdsFromDeviceNames(filter, dataContainer);

		List<Tester> testersMatched = dataContainer.getTesters().values().stream()
				.filter(filterTestersCondition(selectedCountries, selectedDeviceIds)).collect(Collectors.toList());

		List<Bug> bugsMatched = dataContainer.getBugs().values().stream()
				.filter(filterBugsCondition(selectedCountries, selectedDeviceIds)).collect(Collectors.toList());

		Map<Long, Long> bugsCountPerTesterId = bugsMatched.stream()
				.collect(Collectors.groupingBy(b -> b.getTester().getTesterId(), Collectors.counting()));

		if (bugsCountPerTesterId.size() < testersMatched.size()) {
			insertTestersWithNoBugsFiled(testersMatched, bugsCountPerTesterId);
		}

		return bugsCountPerTesterId.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.map(e -> new UserWithExperience(dataContainer.getTesterUserName(e.getKey()), e.getValue()))
				.collect(Collectors.toList());
	}

	private static void insertTestersWithNoBugsFiled(List<Tester> testersMatched,
			Map<Long, Long> bugsCountPerTesterId) {
		Set<Long> testerIds = new HashSet<Long>(
				testersMatched.stream().map(t -> t.getTesterId()).collect(Collectors.toSet()));
		testerIds.removeAll(bugsCountPerTesterId.keySet());
		testerIds.stream().forEach(missingTesterId -> bugsCountPerTesterId.put(missingTesterId, 0L));
	}

	private static Set<Long> getSelectedDeviceIdsFromDeviceNames(FilterOutputData filter, DataContainer dataContainer) {
		return dataContainer.getDevices().values().stream()
				.filter(dev -> filter.getSelectedDeviceNames().contains(dev.getDescription())).map(d -> d.getDeviceId())
				.collect(Collectors.toSet());
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
