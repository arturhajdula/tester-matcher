package testermatcher.transfer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TesterDeviceTransfer {
	
	private final Long testerId;
	private final Long deviceId;

	@JsonCreator
	public TesterDeviceTransfer(@JsonProperty("testerId") Long testerId, @JsonProperty("deviceId") Long deviceId) {
		this.testerId = testerId;
		this.deviceId = deviceId;
	}

	public Long getTesterId() {
		return testerId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	@Override
	public String toString() {
		return "TesterDevice [testerId=" + testerId + ", deviceId=" + deviceId + "]";
	}
}
