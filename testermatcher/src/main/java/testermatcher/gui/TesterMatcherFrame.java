package testermatcher.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashSet;
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

import testermatcher.algorithm.TesterMatcherAlgorithm;
import testermatcher.algorithm.UserWithExperience;
import testermatcher.container.DataContainer;
import testermatcher.container.FilterInputData;
import testermatcher.container.FilterOutputData;
import testermatcher.gui.components.MultiComboBox;
import testermatcher.gui.components.MultiComboBoxOption;
import testermatcher.gui.components.TestersTableModel;

public class TesterMatcherFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private DataContainer dataContainer;
	private FilterInputData filterInput;
	private FilterOutputData filterOutput;
	private JTable table;

	public TesterMatcherFrame(String title, DataContainer data) {
		super(title);
		this.dataContainer = data;
		this.filterInput = new FilterInputData(data);
		this.filterOutput = new FilterOutputData();

		setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		addComponentsToPanel(panel);
		add(panel, BorderLayout.NORTH);
	}

	private void addComponentsToPanel(JPanel panel) {
		int gridy = 0;

		MultiComboBoxOption[] countriesAll = createComboBoxData(filterInput.getCountriesAll());
		MultiComboBoxOption[] deviceNamesAll = createComboBoxData(filterInput.getDeviceNamesAll());

		addComponent(panel, new JLabel("Countries:"), gridy++);

		MultiComboBox<MultiComboBoxOption> countriesSelect = new MultiComboBox<>(
				new DefaultComboBoxModel<>(countriesAll));
		countriesSelect.addPopupMenuListener(new CheckedComboBoxPopupMenuListener(filterOutput.getSelectedCountries()));
		addComponent(panel, countriesSelect, gridy++);

		addComponent(panel, new JLabel("Devices:"), gridy++, new Insets(5, 0, 0, 0));

		MultiComboBox<MultiComboBoxOption> devicesSelect = new MultiComboBox<>(
				new DefaultComboBoxModel<>(deviceNamesAll));
		devicesSelect.addPopupMenuListener(new CheckedComboBoxPopupMenuListener(filterOutput.getSelectedDeviceNames()));
		addComponent(panel, devicesSelect, gridy++);

		addComponent(panel, new JLabel("Matched testers with experience:"), gridy++, new Insets(30, 0, 0, 0));

		this.table = new JTable(new TestersTableModel());
		this.table.setPreferredScrollableViewportSize(new Dimension(1, 1));
		this.table.setFillsViewportHeight(true);
		addComponent(panel, new JScrollPane(table), gridy++, new Insets(0, 0, 0, 0), 150);
	}

	private MultiComboBoxOption[] createComboBoxData(List<String> options) {
		MultiComboBoxOption[] m = new MultiComboBoxOption[options.size()];
		int i = 0;
		for (String el : options) {
			m[i++] = new MultiComboBoxOption(el);
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
			MultiComboBox<MultiComboBoxOption> comboBox = (MultiComboBox<MultiComboBoxOption>) e.getSource();
			ComboBoxModel<MultiComboBoxOption> model = comboBox.getModel();
			Set<String> valuesPrev = new HashSet<>(selectedValues);
			selectedValues.clear();
			fillSelectedValues(model);

			if (isChangeInSelection(valuesPrev)) {
				updateTableModel(executeAlgorithm());
			}
		}

		private void fillSelectedValues(ComboBoxModel<MultiComboBoxOption> model) {
			for (int i = 0; i < model.getSize(); i++) {
				MultiComboBoxOption item = model.getElementAt(i);
				if (item.isSelected() && !item.isOptionSelectAll()) {
					selectedValues.add(item.toString());
				}
			}
		}

		private boolean isChangeInSelection(Set<String> valuesPrev) {
			return !selectedValues.equals(valuesPrev);
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
		}
	}

	public List<UserWithExperience> executeAlgorithm() {
		return TesterMatcherAlgorithm.execute(dataContainer, filterOutput);
	}

	private void updateTableModel(List<UserWithExperience> result) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		result.forEach(r -> model.addRow(new Object[] { r.getUserName(), r.getExperience() }));
		model.fireTableDataChanged();
	}
}
