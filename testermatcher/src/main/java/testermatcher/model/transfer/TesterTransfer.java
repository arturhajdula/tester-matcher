package testermatcher.model.transfer;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TesterTransfer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Long testerId;
	private final String firstName;
	private final String lastName;
	private final String country;
	private final Date lastLogin;

	@JsonCreator
	public TesterTransfer(@JsonProperty("testerId") Long testerId, @JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName, @JsonProperty("country") String country,
			@JsonProperty("lastLogin") Date lastLogin) {
		this.testerId = testerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.lastLogin = lastLogin;
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

	@Override
	public String toString() {
		return "TesterTransfer [testerId=" + testerId + ", firstName=" + firstName + ", lastName=" + lastName + ", country="
				+ country + ", lastLogin=" + lastLogin + "]";
	}

}
