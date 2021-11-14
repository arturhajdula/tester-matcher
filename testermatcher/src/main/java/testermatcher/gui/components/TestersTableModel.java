package testermatcher.gui.components;

import javax.swing.table.DefaultTableModel;

public final class TestersTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private final static String[] columns = new String[] { "User", "Experience" };

	public TestersTableModel() {
		super(createEmptyTable(), columns);
	}

	private static Object[][] createEmptyTable() {
		return new Object[][] {};
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public String getColumnName(int index) {
		return columns[index];
	}

	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 1:
			return Long.class;
		default:
			return String.class;
		}
	}
}