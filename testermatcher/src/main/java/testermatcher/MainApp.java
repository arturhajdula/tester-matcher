package testermatcher;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import testermatcher.csv.CSVConfig;
import testermatcher.gui.MainFrame;
import testermatcher.model.transfer.CSVDataContainer;

public class MainApp {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		CSVConfig config = CSVConfig.createDefaultCSVConfig();
		CSVDataContainer dataCSV = CSVDataContainer.readData(config);
		DataContainer data = DataContainer.generateDataContainer(dataCSV);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(data);
			}
		});

//		Set<String> selectedCountries = Stream.of("US", "JP", "GB").collect(Collectors.toSet());
//		Set<Long> selectedDeviceIds = Stream.of(5L, 6L, 7L, 8L, 9L).collect(Collectors.toSet());
//
//		System.out.println("Countries = " + selectedCountries + "\tDevices = "
//				+ selectedDeviceIds.stream().map(s -> data.getDevices().get(s)).collect(Collectors.toSet()));
//
//		List<Tester> testersMatched = data.getTesters().values().stream()
//				.filter(filterTestersCondition(selectedCountries, selectedDeviceIds)).collect(Collectors.toList());
//
//		List<Bug> bugsMatched = data.getBugs().values().stream()
//				.filter(filterBugsCondition(selectedCountries, selectedDeviceIds)).collect(Collectors.toList());
//
//		Map<Long, Long> bugsCountPerTesterId = bugsMatched.stream()
//				.collect(Collectors.groupingBy(b -> b.getTester().getTesterId(), Collectors.counting()));
//
//		if (bugsCountPerTesterId.size() < testersMatched.size()) {
//			Set<Long> testerIds = new HashSet<Long>(
//					testersMatched.stream().map(t -> t.getTesterId()).collect(Collectors.toSet()));
//			testerIds.removeAll(bugsCountPerTesterId.keySet());
//			testerIds.stream().forEach(missingTesterId -> bugsCountPerTesterId.put(missingTesterId, 0L));
//		}
//		System.out.println("All matching testers (" + testersMatched.size() + "):" + testersMatched);
//
//		bugsCountPerTesterId.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//				.forEach(e -> System.out.println(data.getTesterUserName(e.getKey()) + " => " + e.getValue()));

	}

	private static void createAndShowGUI(DataContainer data) {
		JFrame frame = new MainFrame("Tester Matcher", data);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500, 365);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
