package testermatcher.gui.components;

public class MultiComboBoxOption {
	private final String text;
	private final boolean optionSelectAll;
	private boolean selected;

	public MultiComboBoxOption(String text, boolean selected, boolean optionSelectAll) {
		this.text = text;
		this.selected = selected;
		this.optionSelectAll = optionSelectAll;
	}

	public MultiComboBoxOption(String text) {
		this(text, false, false);
	}

	public String getText() {
		return text;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isOptionSelectAll() {
		return optionSelectAll;
	}

	@Override
	public String toString() {
		return text;
	}
}