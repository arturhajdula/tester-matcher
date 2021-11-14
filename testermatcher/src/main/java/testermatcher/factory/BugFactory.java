package testermatcher.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import testermatcher.model.Bug;
import testermatcher.model.Device;
import testermatcher.model.Tester;
import testermatcher.transfer.model.BugTransfer;

public class BugFactory {

	public static List<Bug> createBugs(List<BugTransfer> bugTransfers, Map<Long, Tester> testers,
			Map<Long, Device> devices) {

		List<Bug> bugs = new ArrayList<>();
		bugTransfers.stream()
				.forEach(d -> bugs.add(createSingleBug(d, devices.get(d.getDeviceId()), testers.get(d.getTesterId()))));
		return bugs;
	}

	private static Bug createSingleBug(BugTransfer b, Device d, Tester t) {
		return new Bug(b.getBugId(), d, t);
	}
}
