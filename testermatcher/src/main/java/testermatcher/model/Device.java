package testermatcher.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public final class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long deviceId;
	private final String description;
	private Set<Tester> testers;

	public Device(Long deviceId, String description, Set<Tester> testers) {
		this.deviceId = deviceId;
		this.description = description;
		this.testers = Collections.unmodifiableSet(testers);
	}

	public Device(Long deviceId, String description) {
		this.deviceId = deviceId;
		this.description = description;
		this.testers = Collections.emptySet();
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public String getDescription() {
		return description;
	}

	public void setTesters(Set<Tester> testers) {
		this.testers = testers;
	}

	public Set<Tester> getTesters() {
		return testers;
	}

	@Override
	public String toString() {
		return " [(" + deviceId + ".) " + description + " (" + testers.size() + " testers)] ";
	}
}
