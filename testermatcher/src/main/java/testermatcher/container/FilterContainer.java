package testermatcher.container;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterContainer {

	private final List<String> countriesAll;
	private final List<String> deviceNamesAll;

	private Set<String> selectedCountries;
	private Set<String> selectedDeviceNames;

	public FilterContainer(DataContainer data) {
		Set<String> countriesAll = data.getTesters().values().stream().map(t -> t.getCountry()).sorted()
				.collect(Collectors.toSet());
		this.countriesAll = countriesAll.stream().sorted().collect(Collectors.toList());

		Set<String> deviceNamesAll = data.getDevices().values().stream().map(d -> d.getDescription()).sorted()
				.collect(Collectors.toSet());
		this.deviceNamesAll = deviceNamesAll.stream().sorted().collect(Collectors.toList());
		this.selectedCountries = new HashSet<>();
		this.selectedDeviceNames = new HashSet<>();
	}

	public Set<String> getSelectedCountries() {
		return selectedCountries;
	}

	public void setSelectedCountries(Set<String> selectedCountries) {
		this.selectedCountries = selectedCountries;
	}

	public Set<String> getSelectedDeviceNames() {
		return selectedDeviceNames;
	}

	public void setSelectedDeviceNames(Set<String> selectedDeviceNames) {
		this.selectedDeviceNames = selectedDeviceNames;
	}

	public List<String> getCountriesAll() {
		return countriesAll;
	}

	public List<String> getDeviceNamesAll() {
		return deviceNamesAll;
	}
}
