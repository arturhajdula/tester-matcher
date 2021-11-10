package testermatcher.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Bug implements Serializable {

	private static final long serialVersionUID = 1L;

    private final Long bugId;
    private final Long deviceId;
    private final Long testerId;
    
	@JsonCreator
    public Bug(@JsonProperty("bugId") Long bugId, @JsonProperty("deviceId") Long deviceId, @JsonProperty("testerId") Long testerId) {
        this.bugId = bugId;
        this.deviceId = deviceId;
        this.testerId = testerId;
    }

	public Long getBugId() {
		return bugId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public Long getTesterId() {
		return testerId;
	}

	@Override
	public String toString() {
		return "Bug [bugId=" + bugId + ", deviceId=" + deviceId + ", testerId=" + testerId + "]";
	}
}
