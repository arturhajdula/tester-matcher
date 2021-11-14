package testermatcher.algorithm;

public final class UserWithExperience {

	private final String userName;
	private final Long experience;

	public UserWithExperience(String userName, Long experience) {
		this.userName = userName;
		this.experience = experience;
	}

	public String getUserName() {
		return userName;
	}

	public Long getExperience() {
		return experience;
	}
}
