package net.immocrm.gui.property;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.MerkmalTypeManager;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;

public class AusstattungController implements Initializable {

	@FXML TextArea ausstattungText;
	@FXML GridPane ausstattungFields;
	@FXML Label lbBath;
	@FXML GridPane bathFields;
	@FXML Label idCar;
	@FXML GridPane carFields;

	private MerkmalFormMapper mapper;
	private ImmoMerkmalFormBuilder builder;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Map<MerkmalType, CheckBox> checkBoxes = new HashMap<>();
		Map<MerkmalType, TextField> textFields = new HashMap<>();
		mapper = new MerkmalFormMapper(checkBoxes, textFields);
		builder = new ImmoMerkmalFormBuilder(checkBoxes, textFields);
	}


	public void valuesFromForm(Immobilie immobilie) {
		mapper.setTextQualities(immobilie);
		mapper.setBooleanQualities(immobilie);
		immobilie.setAusstattung(ausstattungText.getText());
	}

	public void valuesToForm(Immobilie immobilie) {
		addAusstattungFields(immobilie.getImmobilieCategory());
		mapper.getTextQualities(immobilie);
		mapper.getBooleanQualities(immobilie);
		ausstattungText.setText(immobilie.getAusstattung());
	}

	private void addAusstattungFields(ImmobilieCategoryEnum immoCategory) {
		MerkmalTypeManager mtm = new MerkmalTypeManager();
		builder.addCheckBoxFields(ausstattungFields, mtm.getTypeOfCategory(Category.Sonstiges, immoCategory));
		builder.addCheckBoxFields(carFields, mtm.getTypeOfCategory(Category.KFZ_Stellplatz, immoCategory));
		builder.addCheckBoxFields(bathFields, mtm.getTypeOfCategory(Category.Bad, immoCategory));
		// TODO changeListener
	}

}
