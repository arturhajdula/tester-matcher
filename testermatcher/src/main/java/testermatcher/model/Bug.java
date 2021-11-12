package testermatcher.model;

import java.io.Serializable;

public final class Bug implements Serializable {

	private static final long serialVersionUID = 1L;

    private final Long bugId;
    private final Device device;
    private final Tester tester;
    
    public Bug(Long bugId, Device device, Tester tester) {
        this.bugId = bugId;
        this.device = device;
        this.tester = tester;
    }

	public Long getBugId() {
		return bugId;
	}

	public Device getDevice() {
		return device;
	}

	public Tester getTester() {
		return tester;
	}

	@Override
	public String toString() {
		return "Bug [bugId=" + bugId + ", device=" + device + ", tester=" + tester + "]";
	}
}
