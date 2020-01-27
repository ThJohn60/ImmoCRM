package net.immocrm.gui.control;

import javafx.scene.control.ComboBox;
import net.immocrm.domain.termin.TerminCategoryEnum;

public class TerminCategoryComboBox extends ComboBox<String> {

	public TerminCategoryComboBox() {
		getItems().addAll(TerminCategoryEnum.meeting.getName(),
				TerminCategoryEnum.personal.getName(),
				TerminCategoryEnum.other.getName());
	}


	public TerminCategoryEnum getCategory() {
		if (getValue() != null) {
			return TerminCategoryEnum.byName(getValue());
		}
		return null;
	}

	public void setCategory(TerminCategoryEnum c) {
		if (c != null) {
			setValue(c.getName());
		} else {
			setValue(null);
		}
	}

}
