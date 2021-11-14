package testermatcher;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import testermatcher.container.DataContainer;
import testermatcher.csv.CSVConfig;
import testermatcher.gui.TesterMatcherFrame;
import testermatcher.transfer.DataTransfer;

public class MainApp {

	public static void main(String[] args) {
		CSVConfig config = CSVConfig.createDefaultCSVConfig();
		DataTransfer dataCSV = DataTransfer.readData(config);
		DataContainer data = DataContainer.generateDataContainer(dataCSV);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(data);
			}
		});
	}

	private static void createAndShowGUI(DataContainer data) {
		JFrame frame = new TesterMatcherFrame("Tester Matcher", data);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500, 365);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
