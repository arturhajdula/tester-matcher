package testermatcher.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import testermatcher.DataContainer;
import testermatcher.FilterContainer;
import testermatcher.algorithm.TesterMatcherAlgorithm;
import testermatcher.algorithm.UserWithExperience;
import testermatcher.gui.components.CheckableItem;
import testermatcher.gui.components.CheckedComboBox;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private DataContainer dataContainer;
	private FilterContainer filterContainer;
	private JTable table;

	public MainFrame(String title, DataContainer data) {
		super(title);
		this.dataContainer = data;
		this.filterContainer = new FilterContainer(data);

		setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		addComponentsToPanel(panel);
		add(panel, BorderLayout.NORTH);
	}

	private void addComponentsToPanel(JPanel panel) {
		int gridy = 0;

		CheckableItem[] countriesAll = createComboBoxData(filterContainer.getCountriesAll());
		CheckableItem[] deviceNamesAll = createComboBoxData(filterContainer.getDeviceNamesAll());

		addComponent(panel, new JLabel("Countries:"), gridy++);

		CheckedComboBox<CheckableItem> countriesSelect = new CheckedComboBox<>(
				new DefaultComboBoxModel<>(countriesAll));
		countriesSelect
				.addPopupMenuListener(new CheckedComboBoxPopupMenuListener(filterContainer.getSelectedCountries()));
		addComponent(panel, countriesSelect, gridy++);

		addComponent(panel, new JLabel("Devices:"), gridy++, new Insets(5, 0, 0, 0));

		CheckedComboBox<CheckableItem> devicesSelect = new CheckedComboBox<>(
				new DefaultComboBoxModel<>(deviceNamesAll));
		devicesSelect
				.addPopupMenuListener(new CheckedComboBoxPopupMenuListener(filterContainer.getSelectedDeviceNames()));
		addComponent(panel, devicesSelect, gridy++);

		addComponent(panel, new JLabel("Matched testers with experience:"), gridy++, new Insets(30, 0, 0, 0));

		this.table = new JTable(new DefaultTableModel(new Object[][] {}, new String[] { "No.", "User", "Experience" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		this.table.setPreferredScrollableViewportSize(new Dimension(1, 1));
		this.table.setFillsViewportHeight(true);
		addComponent(panel, new JScrollPane(table), gridy++, new Insets(0, 0, 0, 0), 150);
	}

	private CheckableItem[] createComboBoxData(List<String> options) {
		CheckableItem[] m = new CheckableItem[options.size()];
		int i = 0;
		for (String el : options) {
			m[i++] = new CheckableItem(el);
		}
		return m;
	}

	private static void addComponent(Container panel, Component component, int gridy) {
		addComponent(panel, component, gridy, new Insets(0, 0, 0, 0));
	}

	private static void addComponent(Container panel, Component component, int gridy, Insets insets) {
		addComponent(panel, component, gridy, insets, 0);
	}

	private static void addComponent(Container panel, Component component, int gridy, Insets insets, int ipady) {
		GridBagConstraints gbc = new GridBagConstraints(0, gridy, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, insets, 0, ipady);
		panel.add(component, gbc);
	}

	private final class CheckedComboBoxPopupMenuListener implements PopupMenuListener {

		private Set<String> selectedValues;

		public CheckedComboBoxPopupMenuListener(Set<String> selectedValues) {
			this.selectedValues = selectedValues;
		}

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			CheckedComboBox<CheckableItem> comboBox = (CheckedComboBox<CheckableItem>) e.getSource();
			ComboBoxModel<CheckableItem> model = comboBox.getModel();
			selectedValues.clear();

			for (int i = 0; i < model.getSize(); i++) {
				CheckableItem item = model.getElementAt(i);
				if (item.isSelected()) {
					selectedValues.add(item.toString());
				}
			}

			updateTableModel(executeAlgorithm());
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
		}
	}

	public List<UserWithExperience> executeAlgorithm() {
		return TesterMatcherAlgorithm.execute(dataContainer, filterContainer);
	}

	private void updateTableModel(List<UserWithExperience> result) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		result.forEach(r -> model.addRow(new Object[] { r.getUserName(), r.getExperience() }));
		model.fireTableDataChanged();
	}
}
