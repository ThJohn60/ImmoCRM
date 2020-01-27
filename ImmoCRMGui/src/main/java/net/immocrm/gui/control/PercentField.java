package net.immocrm.gui.control;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.domain.vc.Percent;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class PercentField extends TextField {

	private String fieldName;


	public PercentField() {
		percentFocusListener();
	}

	private void percentFocusListener() {
		focusedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> val, Boolean oldPropertyValue, Boolean newPropertyValue) {
		        if (!newPropertyValue) {
		    		try {
						setPercent(parsePercentValue());
		    		} catch (ParseException e) {
		    			// ignore
		    		}
		        }
		    }
		});
	}


	public Percent getPercent() {
		try {
			return parsePercentValue();
		} catch (ParseException e) {
			return new Percent();
		}
	}

	public Percent getValidatedPercent() {
		try {
			return parsePercentValue();
		} catch (ParseException e) {
			requestFocus();
			AlertProvider.alertInputError(getText(), fieldName, "Bitte einen korrekten Prozentwert eingeben.");
			throw new NoActionException();
		}
	}

	private Percent parsePercentValue() throws ParseException {
		if (isNullOrEmpty(getText())) {
			return new Percent();
		}
		String textValue = getText().replaceAll(Percent.PERCENT_SYMBOL, "").trim();
		double parsedValue = NumberValue.NUMBER_FORMATTER.parse(textValue).doubleValue();
		return new Percent(parsedValue);
	}

	public void setPercent(Percent value) {
		if (isNullOrEmpty(value)) {
			setText("");
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
