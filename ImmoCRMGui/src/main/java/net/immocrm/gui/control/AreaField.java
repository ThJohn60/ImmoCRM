package net.immocrm.gui.control;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import net.immocrm.domain.vc.Area;
import net.immocrm.domain.vc.ValuesParser;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class AreaField extends TextField {

	private String fieldName;


	public AreaField() {
		areaFocusListener();
	}

	private void areaFocusListener() {
		focusedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> val, Boolean oldPropertyValue, Boolean newPropertyValue) {
		        if (!newPropertyValue) {
		    		try {
						setArea(ValuesParser.parseArea(getText()));
		    		} catch (ParseException e) {
		    			// ignore
		    		}
		        }
		    }
		});
	}


	public Area getArea() {
		try {
			return ValuesParser.parseArea(getText());
		} catch (ParseException e) {
			return new Area();
		}
	}

	public Area getValidatedArea() {
		try {
			return ValuesParser.parseArea(getText());
		} catch (ParseException e) {
			requestFocus();
			AlertProvider.alertInputError(getText(), fieldName, "Bitte einen korrekten Flächenwert in " + Area.SQUARE_METER + "eingeben.");
			throw new NoActionException();
		}
	}

	public void setArea(Area value) {
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
