package net.immocrm.gui.control;

import javafx.scene.control.ComboBox;
import net.immocrm.domain.ref.EnergieausweisTypeEnum;

public class EnergieausweisComboBox extends ComboBox<EnergieausweisTypeEnum> {

	public EnergieausweisComboBox() {
		getItems().addAll(EnergieausweisTypeEnum.values());
	}

}
