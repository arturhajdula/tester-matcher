package testermatcher.gui.components;

import java.awt.Component;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

class MultiComboBoxRenderer<E extends MultiComboBoxOption> implements ListCellRenderer<E> {
	private final JLabel label = new JLabel(" ");
	private final JCheckBox check = new JCheckBox(" ");

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (index < 0) {
			String txt = getCheckedItemString(list.getModel());
			label.setText(txt.isEmpty() ? " " : txt);
			return label;
		} else {
			check.setText(Objects.toString(value, ""));
			check.setSelected(value.isSelected());
			if (isSelected) {
				check.setBackground(list.getSelectionBackground());
				check.setForeground(list.getSelectionForeground());
			} else {
				check.setBackground(list.getBackground());
				check.setForeground(list.getForeground());
			}
			return check;
		}
	}

	private static <E extends MultiComboBoxOption> String getCheckedItemString(ListModel<E> model) {

		String selectedValues = IntStream.range(1, model.getSize()).mapToObj(model::getElementAt)
				.filter(MultiComboBoxOption::isSelected).map(Objects::toString).sorted().collect(Collectors.joining(", "));

		if (getOptionAll(model).isSelected()) {
			return getOptionAll(model).getText() + " (" + selectedValues + ")";
		} else if (selectedValues.length() == 0) {
			return " (no option selected) ";
		}

		return selectedValues;
	}

	private static <E extends MultiComboBoxOption> E getOptionAll(ListModel<E> model) {
		return model.getElementAt(0);
	}
}