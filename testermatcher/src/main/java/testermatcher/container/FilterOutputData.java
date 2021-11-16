package testermatcher.container;

import java.util.HashSet;
import java.util.Set;

public class FilterOutputData {

	private Set<String> selectedCountries;
	private Set<String> selectedDeviceNames;

	public FilterOutputData() {
		this.selectedCountries = new HashSet<String>();
		this.selectedDeviceNames = new HashSet<String>();
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
	public void setSelectedDeviceNames(Set<String> selectedDevices) {
		this.selectedDeviceNames = selectedDevices;
	}

	
}
