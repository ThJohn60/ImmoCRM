package net.immocrm.gui.control;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;

import javafx.scene.control.TextField;
import net.immocrm.domain.vc.Price;
import net.immocrm.domain.vc.ValuesParser;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class PriceField extends TextField {

	private String fieldName;


	public PriceField() {
		priceFocusListener();
	}

	private void priceFocusListener() {
		focusedProperty().addListener( (val, oldPropertyValue, newPropertyValue) -> {
		        if (!newPropertyValue) {
		    		try {
						setPrice(ValuesParser.parsePrice(getText()));
		    		} catch (ParseException e) {
		    			// ignore
		    		}
		        }
			});
	}


	public Price getPrice() {
		try {
			return ValuesParser.parsePrice(getText());
		} catch (ParseException e) {
			return new Price();
		}
	}

	public Price getValidatedPrice() {
		try {
			return ValuesParser.parsePrice(getText());
		} catch (ParseException e) {
			requestFocus();
			AlertProvider.alertInputError(getText(), fieldName, "Bitte einen korrekten Preis (auch ohne Währung) eingeben.");
			throw new NoActionException();
		}
	}

	public void setPrice(Price value) {
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
