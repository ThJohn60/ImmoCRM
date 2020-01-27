package net.immocrm.gui.property;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.MerkmalTypeManager;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.gui.control.AreaField;
import net.immocrm.gui.control.EnergieausweisComboBox;
import net.immocrm.gui.control.MerkmalTypeComboBox;
import net.immocrm.gui.control.NumberField;
import net.immocrm.gui.control.PriceField;
import net.immocrm.gui.control.VerbrauchskennwertField;
import net.immocrm.gui.control.YearField;

public class EigenschaftenController implements Initializable {

	@FXML MerkmalTypeComboBox zustand;
	@FXML PriceField hausgeld;
	@FXML YearField baujahr;
	@FXML PriceField mietpreis;
	@FXML AreaField nutzflaeche;
	@FXML AreaField grundstueck;
	@FXML NumberField balkone;
	@FXML NumberField terrassen;
	@FXML NumberField etage;

	@FXML GridPane heizungFields;
	@FXML VerbrauchskennwertField verbrauchskennwert;
	@FXML EnergieausweisComboBox energieausweistyp;
	@FXML TextField energieausweis;

	@FXML FlowPane ownerHyperlinkPane;
	@FXML Hyperlink lnkNewCustomer;
	@FXML Hyperlink lnkEditCustomer;
	@FXML Hyperlink lnkSelectCustomer;

	@FXML TextArea objektbeschreibung;

	private MerkmalFormMapper mapper;
	private ImmoMerkmalFormBuilder builder;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Map<MerkmalType, CheckBox> checkBoxes = new HashMap<>();
		Map<MerkmalType, TextField> textFields = new HashMap<>();
		mapper = new MerkmalFormMapper(checkBoxes, textFields);
		builder = new ImmoMerkmalFormBuilder(checkBoxes, textFields);
	}


	public void valuesToForm(Immobilie immobilie) {
		addAusstattungFields(immobilie.getImmobilieCategory());

		MerkmalTypeManager mtm = new MerkmalTypeManager();
		zustand.fill(mtm.getTypeOfCategory(Category.Zustand, immobilie.getImmobilieCategory()));
		zustand.setValue(immobilie.getZustand());

		mapper.getTextQualities(immobilie);
		mapper.getBooleanQualities(immobilie);

		hausgeld.setText(immobilie.getHausgeld().toString());
		baujahr.setText(immobilie.getBaujahr().toString());
		mietpreis.setText(immobilie.getMiete().toString());
		nutzflaeche.setText(immobilie.getNutzflaeche().toString());
		grundstueck.setText(immobilie.getGrundstueck().toString());
		balkone.setText(immobilie.getBalconyCnt().toString());
		terrassen.setText(immobilie.getTerraceCnt().toString());
		etage.setText(immobilie.getFloor().toString());
		objektbeschreibung.setText(immobilie.getObjektbeschreibung());
		energieausweistyp.setValue(immobilie.getEnergieausweisType());
		verbrauchskennwert.setVerbrauchskennwert(immobilie.getEnergieverbrauchskennwert());
	}


	public void valuesFromForm(Immobilie immobilie) {
		mapper.setBooleanQualities(immobilie);
		mapper.setTextQualities(immobilie);

		immobilie.setZustand(zustand.getValue());
		immobilie.setHausgeld(hausgeld.getValidatedPrice());
		immobilie.setBaujahr(baujahr.getValidatedYear());
		immobilie.setMiete(mietpreis.getValidatedPrice());
		immobilie.setNutzflaeche(nutzflaeche.getValidatedArea());
		immobilie.setGrundstueck(grundstueck.getValidatedArea());
		immobilie.setTerraceCnt(terrassen.getValidatedNumber());
		immobilie.setBalconyCnt(balkone.getValidatedNumber());
		immobilie.setFloor(etage.getValidatedNumber());
		immobilie.setObjektbeschreibung(objektbeschreibung.getText());
		immobilie.setEnergieausweisType(energieausweistyp.getValue());
		immobilie.setEnergieverbrauchskennwert(verbrauchskennwert.getValidatedVerbrauchskennwert());
	}

	private void addAusstattungFields(ImmobilieCategoryEnum immoCategory) {
		MerkmalTypeManager mtm = new MerkmalTypeManager();
		builder.addCheckBoxFields(heizungFields, mtm.getTypeOfCategory(Category.Heizung, immoCategory));
		// TODO changeListener
	}

}
