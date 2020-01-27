package net.immocrm.gui.control;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import net.immocrm.domain.vc.ImmoDate;

public class ImmoDatePicker extends DatePicker {

	public void setImmoDate(ImmoDate d) {
		if (d == null) {
			setValue(null);
		}
		setValue(d.toLocalDate());
	}

	public ImmoDate getImmoDate() {
		LocalDate ld = getValue();
		if (ld == null) {
			return new ImmoDate();
		}
		return new ImmoDate(ld);
	}

}
