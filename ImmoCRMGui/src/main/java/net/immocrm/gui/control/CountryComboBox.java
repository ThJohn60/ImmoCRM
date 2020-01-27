package net.immocrm.gui.control;

import javafx.scene.control.ComboBox;
import net.immocrm.domain.ref.CountryNameEnum;

public class CountryComboBox extends ComboBox<CountryNameEnum> {

	public CountryComboBox() {
		getItems().addAll(CountryNameEnum.values());
		setValue(CountryNameEnum.DE);
	}

}
