package net.immocrm.gui.property;

import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.form.NoActionException;

public class MerkmalFormMapper {

	private final Map<MerkmalType, CheckBox> checkBoxes;
	private final Map<MerkmalType, TextField> textFields;


	public MerkmalFormMapper(Map<MerkmalType, CheckBox> checkBoxes, Map<MerkmalType, TextField> textFields) {
		this.checkBoxes = checkBoxes;
		this.textFields = textFields;
	}


	public void setTextQualities(Immobilie immobilie) {
		for (Entry<MerkmalType, TextField> entry : textFields.entrySet()) {
			try {
				immobilie.setMerkmal(entry.getKey(), entry.getValue().getText());
			} catch (IncorrectValueException e) {
				entry.getValue().requestFocus();
				AlertProvider.alertException(e);
				throw new NoActionException();
			}
		}
	}

	public void getTextQualities(Immobilie immobilie) {
		for (Entry<MerkmalType, TextField> entry : textFields.entrySet()) {
			Merkmal quality = immobilie.getMerkmal(entry.getKey());
			if (quality != null) {
				entry.getValue().setText(quality.getValue());
			}
		}
	}


	public void setBooleanQualities(Immobilie immobilie) {
		for (Entry<MerkmalType, CheckBox> entry : checkBoxes.entrySet()) {
			try {
				immobilie.setMerkmal(entry.getKey(), entry.getValue().isSelected());
			} catch (IncorrectValueException e) {
				entry.getValue().requestFocus();
				AlertProvider.alertException(e);
				throw new NoActionException();
			}
		}
	}

	public void getBooleanQualities(Immobilie immobilie) {
		for (Entry<MerkmalType, CheckBox> entry : checkBoxes.entrySet()) {
			Merkmal quality = immobilie.getMerkmal(entry.getKey());
			if (quality != null && quality.getValue() != null) {
				entry.getValue().setSelected(quality.isSet());
			}
		}
	}

}
