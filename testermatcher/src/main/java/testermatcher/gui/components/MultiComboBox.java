package testermatcher.gui.components;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.stream.IntStream;

import javax.accessibility.Accessible;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.ComboPopup;

public class MultiComboBox<E extends MultiComboBoxOption> extends JComboBox<E> {

	private static final long serialVersionUID = 1L;

	private static final String OPTION_ALL = "ALL";

	private boolean keepOpen;
	private transient ActionListener listener;

	public MultiComboBox() {
		super();
	}

	public MultiComboBox(ComboBoxModel<E> model) {
		super(createModelWithOptionAll(model));
	}

	private static <E extends MultiComboBoxOption> ComboBoxModel<E> createModelWithOptionAll(ComboBoxModel<E> model) {
		MultiComboBoxOption[] m = new MultiComboBoxOption[model.getSize() + 1];
		m[0] = new MultiComboBoxOption(OPTION_ALL, false, true);
		for (int i = 1; i < model.getSize() + 1; i++) {
			m[i] = new MultiComboBoxOption(model.getElementAt(i - 1).toString());
		}
		DefaultComboBoxModel<MultiComboBoxOption> test = new DefaultComboBoxModel<>(m);
		return (ComboBoxModel<E>) test;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 20);
	}

	@Override
	public void updateUI() {
		setRenderer(null);
		removeActionListener(listener);
		super.updateUI();
		listener = e -> {
			if ((e.getModifiers() & AWTEvent.MOUSE_EVENT_MASK) != 0) {
				updateItem(getSelectedIndex());
				keepOpen = true;
			}
		};
		setRenderer(new MultiComboBoxRenderer<>());
		addActionListener(listener);
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "checkbox-select");
		getActionMap().put("checkbox-select", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Accessible a = getAccessibleContext().getAccessibleChild(0);
				if (a instanceof ComboPopup) {
					updateItem(((ComboPopup) a).getList().getSelectedIndex());
				}
			}
		});
	}

	protected void updateItem(int index) {
		if (isPopupVisible()) {
			E item = getItemAt(index);
			item.setSelected(!item.isSelected());
			setSelectedIndex(-1);
			setSelectedItem(item);

			if (item.isOptionSelectAll()) {
				boolean selectAllChecked = item.isSelected();
				for (int i = 1; i < this.getModel().getSize(); i++) {
					E nextItem = getItemAt(i);
					nextItem.setSelected(selectAllChecked);
				}
			} else {
				E itemOptionSelectAll = getItemAt(0);
				if (IntStream.range(1, getModel().getSize()).mapToObj(getModel()::getElementAt)
						.allMatch(MultiComboBoxOption::isSelected)) {
					itemOptionSelectAll.setSelected(true);
				} else {
					itemOptionSelectAll.setSelected(false);
				}
			}
		}
	}

	@Override
	public void setPopupVisible(boolean v) {
		if (keepOpen) {
			keepOpen = false;
		} else {
			super.setPopupVisible(v);
		}
	}
}