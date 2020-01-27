package net.immocrm.gui.control;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;

import javafx.scene.control.TextField;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.domain.vc.ValuesParser;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class NumberField extends TextField {

	private String fieldName;


	public NumberValue getNumber() {
		try {
			return ValuesParser.parseNumber(getText());
		} catch (ParseException e) {
			return new NumberValue();
		}
	}

	public NumberValue getValidatedNumber() {
		try {
			return ValuesParser.parseNumber(getText());
		} catch (ParseException e) {
			requestFocus();
			AlertProvider.alertInputError(getText(), fieldName, "Bitte eine korrekte Zahl eingeben.");
			throw new NoActionException();
		}
	}

	public void setNumber(NumberValue value) {
		if (isNullOrEmpty(value)) {
			setText(null);
		} else {
			setText(value.toString());
		}
	}


	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
