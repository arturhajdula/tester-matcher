package testermatcher.container;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterInputData {

	private final List<String> countriesAll;
	private final List<String> deviceNamesAll;

	public FilterInputData(DataContainer data) {
		Set<String> countriesAll = data.getTesters().values().stream().map(t -> t.getCountry()).sorted()
				.collect(Collectors.toSet());
		this.countriesAll = countriesAll.stream().sorted().collect(Collectors.toList());

		Set<String> deviceNamesAll = data.getDevices().values().stream().map(d -> d.getDescription()).sorted()
				.collect(Collectors.toSet());
		this.deviceNamesAll = deviceNamesAll.stream().sorted().collect(Collectors.toList());
	}

	public List<String> getCountriesAll() {
		return countriesAll;
	}

	public List<String> getDeviceNamesAll() {
		return deviceNamesAll;
	}
}
