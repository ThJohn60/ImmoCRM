package net.immocrm.gui.control;


import javafx.scene.control.ComboBox;

public class MonthComboBox extends ComboBox<String> {

	public MonthComboBox() {
		String[] monthNames = new String[] {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
		getItems().addAll(monthNames);
	}

	public int getMonthNumber() {
		return getSelectionModel().getSelectedIndex() + 1;
	}

	public void setMonthNumber(int monthValue) {
		getSelectionModel().select(monthValue - 1);
	}

}
