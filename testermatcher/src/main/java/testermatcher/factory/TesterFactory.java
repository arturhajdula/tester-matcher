package testermatcher.factory;

import java.util.ArrayList;
import java.util.List;

import testermatcher.model.Tester;
import testermatcher.model.transfer.TesterTransfer;

public class TesterFactory {

	public static List<Tester> createTesters(List<TesterTransfer> testerTransfers) {

		List<Tester> testers = new ArrayList<>();
		testerTransfers.stream().forEach(t -> testers.add(createSingleTester(t)));
		return testers;
	}

	private static Tester createSingleTester(TesterTransfer t) {
		return new Tester(t.getTesterId(), t.getFirstName(), t.getLastName(), t.getCountry(), t.getLastLogin());
	}
}
