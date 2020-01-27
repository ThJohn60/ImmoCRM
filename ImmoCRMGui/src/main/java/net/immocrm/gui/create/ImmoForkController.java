package net.immocrm.gui.create;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ImmoForkController {

	@FXML ToggleGroup typeToggle;

	@FXML RadioButton customerExists;
	@FXML RadioButton nothingExists;


	public ImmoInputMode getInputMode() {
		if (customerExists.isSelected()) {
			return ImmoInputMode.WithoutCustomer;
		}
		return ImmoInputMode.All;
	}

	public void setInputMode(ImmoInputMode inputMode) {
		if (inputMode == null) {
			inputMode = ImmoInputMode.All;
		}
		switch (inputMode) {
		case All:
			nothingExists.setSelected(true);
			break;
		case WithoutCustomer:
			customerExists.setSelected(true);
			break;
		default:
			break;
		}
	}

}
