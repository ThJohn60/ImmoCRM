package net.immocrm.gui.control;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import net.immocrm.domain.vc.Energieverbrauchskennwert;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class VerbrauchskennwertField extends TextField {

	private String fieldName;


	public VerbrauchskennwertField() {
		focusListener();
	}

	private void focusListener() {
		focusedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> val, Boolean oldPropertyValue, Boolean newPropertyValue) {
		        if (!newPropertyValue) {
		    		try {
						setVerbrauchskennwert(parseVerbrauchskennwertValue());
		    		} catch (ParseException e) {
		    			// ignore
		    		}
		        }
		    }
		});
	}


	public Energieverbrauchskennwert getVerbrauchskennwert() {
		try {
			return parseVerbrauchskennwertValue();
		} catch (ParseException e) {
			return new Energieverbrauchskennwert();
		}
	}

	public Energieverbrauchskennwert getValidatedVerbrauchskennwert() {
		try {
			return parseVerbrauchskennwertValue();
		} catch (ParseException e) {
			requestFocus();
			AlertProvider.alertInputError(getText(), fieldName, "Bitte einen korrekten Kwennwert eingeben.");
			throw new NoActionException();
		}
	}

	private Energieverbrauchskennwert parseVerbrauchskennwertValue() throws ParseException {
		if (isNullOrEmpty(getText())) {
			return new Energieverbrauchskennwert();
		}
		String trimmedVerbrauchskennwert = getText().replaceAll(Energieverbrauchskennwert.UNIT, "").trim();
		double parsedValue = NumberValue.NUMBER_FORMATTER.parse(trimmedVerbrauchskennwert).doubleValue();
		return new Energieverbrauchskennwert(parsedValue);
	}

	public void setVerbrauchskennwert(Energieverbrauchskennwert value) {
		if (value.isEmpty()) {
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
