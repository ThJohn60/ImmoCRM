package net.immocrm.gui.property;

import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import net.immocrm.domain.quality.MerkmalType;

public class ImmoMerkmalFormBuilder {

	private static final int MAX_TEXT_COLUMNS = 3;
	private static final int MAX_BOOL_COLUMNS = 4;

	private final Map<MerkmalType, CheckBox> checkBoxes;
	private final Map<MerkmalType, TextField> textFields;


	public ImmoMerkmalFormBuilder(Map<MerkmalType, CheckBox> checkBoxes, Map<MerkmalType, TextField> textFields) {
		this.checkBoxes = checkBoxes;
		this.textFields = textFields;
	}


	public void addTextFields(GridPane pane, List<MerkmalType> fields) {
		int row = 0;
		for (int i = 0; i < fields.size(); i += MAX_TEXT_COLUMNS) {
			int columnIndex = 0;
			for (int j = 0; j < MAX_TEXT_COLUMNS && i + j < fields.size(); j++) {
				addTextField(pane, fields.get(i+j), row, columnIndex);
				columnIndex += 3;
			}
			row++;
		}
	}

	private void addTextField(GridPane generalFields, MerkmalType t, int rowIndex, int columnIndex) {
		generalFields.add(getLabel(t), columnIndex, rowIndex);
		generalFields.add(getTextField(t), columnIndex+1, rowIndex);
	}

	private Label getLabel(MerkmalType t) {
		Label label = new Label(t.getName() + ":");
		label.getStyleClass().add("lbl");
		return label;
	}

	private TextField getTextField(MerkmalType t) {
		TextField tField = new TextField();
		if (t.isNumeric()  ||  t.isArea()  ||  t.isCurrency()) {
			tField.setAlignment(Pos.BASELINE_RIGHT);
		}
		textFields.put(t, tField);
		return tField;
	}

	////////////////////////////////////

	public void addCheckBoxFields(GridPane pane, List<MerkmalType> fields) {
		int row = 0;
		for (int i = 0; i < fields.size(); i += MAX_BOOL_COLUMNS) {
			for (int col = 0; col < MAX_BOOL_COLUMNS && i + col < fields.size(); col++) {
				addBooleanField(pane, fields.get(i+col), row, col);
			}
			row++;
		}
	}

	private void addBooleanField(GridPane generalFields, MerkmalType t, int rowIndex, int columnIndex) {
		CheckBox chBox = new CheckBox(t.getName());
		chBox.getStyleClass().add("chbox");
		checkBoxes.put(t, chBox);
		generalFields.add(chBox, columnIndex, rowIndex);
	}

}
