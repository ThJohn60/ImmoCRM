package net.immocrm.gui.control;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.domain.vc.ValuesParser;
import net.immocrm.domain.vc.Year;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class YearField extends TextField {

	private String fieldName;


	public YearField() {
		focusListener();
	}

	private void focusListener() {
		focusedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> val, Boolean oldPropertyValue, Boolean newPropertyValue) {
		        if (!newPropertyValue) {
		    		try {
						setYear(ValuesParser.parseYear(getText()));
		    		} catch (ParseException e) {
		    			// ignore
		    		}
		        }
		    }
		});
	}

	public Year getYear() {
		try {
			return ValuesParser.parseYear(getText());
		} catch (ParseException e) {
			return new Year();
		}
	}

	public Year getValidatedYear() {
		try {
			return ValuesParser.parseYear(getText());
		} catch (ParseException e) {
			requestFocus();
			AlertProvider.alertInputError(getText(), fieldName, "Bitte eine plausible Jahreszahl eingeben.");
			throw new NoActionException();
		}
	}

	public void setYear(Year value) {
		if (isNullOrEmpty(value)) {
			setText(null);
		} else {
			setText(value.toString());
		}
	}

	public int getYearNumber() {
		try {
			return NumberValue.parse(getText());
		} catch (ParseException e) {
			AlertProvider.alertInputError(getText(), fieldName, "Bitte eine plausible Jahreszahl eingeben.");
			throw new NoActionException();
		}
	}

	public void setYear(int value) {
		setText(Integer.toString(value));
	}



	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
