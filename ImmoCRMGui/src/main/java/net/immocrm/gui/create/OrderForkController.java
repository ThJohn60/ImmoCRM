package net.immocrm.gui.create;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class OrderForkController {

	@FXML ToggleGroup typeToggle;

	@FXML RadioButton immoExists;
	@FXML RadioButton customerExists;
	@FXML RadioButton nothingExists;


	public OrderInputMode getInputMode() {
		if (customerExists.isSelected()) {
			return OrderInputMode.WithoutCustomer;
		}
		if (immoExists.isSelected()) {
			return OrderInputMode.WithoutCustomerAndImmobile;
		}
		return OrderInputMode.All;
	}


	public void setInputMode(OrderInputMode inputMode) {
		if (inputMode == null) {
			inputMode = OrderInputMode.All;
		}
		switch (inputMode) {
		case All:
			nothingExists.setSelected(true);
			break;
		case WithoutCustomer:
			customerExists.setSelected(true);
			break;
		case WithoutCustomerAndImmobile:
			immoExists.setSelected(true);
			break;
		default:
			break;
		}
	}

}
