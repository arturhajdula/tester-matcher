package testermatcher.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public final class Tester implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private final Long testerId;
    private final String firstName;
    private final String lastName;
    private final String country;
    private final Date lastLogin;
	private final Set<Device> devices;
	
	public Tester(Long testerId, String firstName, String lastName, String country, Date lastLogin,
			Set<Device> devices) {
		this.testerId = testerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.lastLogin = lastLogin;
		this.devices = Collections.unmodifiableSet(devices);
	}

	public Long getTesterId() {
		return testerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCountry() {
		return country;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	@Override
	public String toString() {
		return "Tester [testerId=" + testerId + ", firstName=" + firstName + ", lastName=" + lastName + ", country="
				+ country + ", lastLogin=" + lastLogin + ", devices=" + devices + "]";
	}
}
