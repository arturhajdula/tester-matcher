package testermatcher.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Device implements Serializable {

	private static final long serialVersionUID = 1L;

    private final Long deviceId;
    private final String description;

	@JsonCreator
    public Device(@JsonProperty("deviceId") Long deviceId, @JsonProperty("description") String description) {
        this.deviceId = deviceId;
        this.description = description;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public String getDescription() {
        return description;
    }

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", description=" + description + "]";
	}
}
