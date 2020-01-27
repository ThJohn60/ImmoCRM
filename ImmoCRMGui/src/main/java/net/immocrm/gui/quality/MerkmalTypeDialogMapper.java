package net.immocrm.gui.quality;

import javafx.scene.layout.Pane;
import net.immocrm.domain.quality.MerkmalTypeChangeable;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.DataTypeEnum;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.gui.form.MappingTool;

public class MerkmalTypeDialogMapper {

	private MappingTool mt;


	public MerkmalTypeDialogMapper(Pane pane) {
		mt = new MappingTool(pane);
	}


	void qualityTypeFromForm(MerkmalTypeChangeable merkmalType) {
		merkmalType.setName(mt.getText("#name"));
		merkmalType.setCategory(Category.byName(mt.getText("#category")));
		merkmalType.setActive(mt.isCheckBoxSelected("#active"));
		merkmalType.setDataType(getDataType());
		merkmalType.setImmobilieTypes(getCompatibleImmobilieTypes());
	}

	void merkmalTypeToForm(MerkmalTypeChangeable merkmalType) {
		mt.setText("#name", merkmalType.getName());
		mt.setText("#category", merkmalType.getCategory().getName());
		compatibleImmobilieTypesToForm(merkmalType);
		dataTypeToForm(merkmalType);
	}

	private DataTypeEnum getDataType() {
		if (mt.isRadoButtonSelected("#bool")) {
			return DataTypeEnum.bool;
		}
		if (mt.isRadoButtonSelected("#num")) {
			return DataTypeEnum.num;
		}
		if (mt.isRadoButtonSelected("#area")) {
			return DataTypeEnum.area;
		}
		if (mt.isRadoButtonSelected("#currency")) {
			return DataTypeEnum.curr;
		}
		return DataTypeEnum.text;
	}

	private void dataTypeToForm(MerkmalTypeChangeable merkmalType) {
		if (merkmalType.isBoolean()) {
			mt.selectRadioButton("#bool");
		} else if (merkmalType.isNumeric()) {
			mt.selectRadioButton("#numeric");
		} else if (merkmalType.isArea()) {
			mt.selectRadioButton("#area");
		} else if (merkmalType.isCurrency()) {
			mt.selectRadioButton("#curr");
		} else {
			mt.selectRadioButton("#text");
		}
	}

	private String getCompatibleImmobilieTypes() {
		StringBuilder propType = new StringBuilder();
		if (mt.isCheckBoxSelected("#house")) {
			propType.append(ImmobilieCategoryEnum.Haus.getShortcut());
		}
		if (mt.isCheckBoxSelected("#flat")) {
			propType.append(ImmobilieCategoryEnum.Wohnung.getShortcut());
		}
		if (mt.isCheckBoxSelected("#business")) {
			propType.append(ImmobilieCategoryEnum.Gewerbegebäude.getShortcut());
		}
		if (mt.isCheckBoxSelected("#ground")) {
			propType.append(ImmobilieCategoryEnum.Grundstück.getShortcut());
		}
		if (mt.isCheckBoxSelected("#garage")) {
			propType.append(ImmobilieCategoryEnum.Stellplatz.getShortcut());
		}
		if (mt.isCheckBoxSelected("#else")) {
			propType.append(ImmobilieCategoryEnum.Sonstiges.getShortcut());
		}
		return propType.toString();
	}

	private void compatibleImmobilieTypesToForm(MerkmalTypeChangeable merkmalType) {
		mt.selectCheckBox("#active", merkmalType.isActive());
		mt.selectCheckBox("#house", merkmalType.isHaus());
		mt.selectCheckBox("#flat", merkmalType.isWohnung());
		mt.selectCheckBox("#business", merkmalType.isGeschaeft());
		mt.selectCheckBox("#ground", merkmalType.isGrundstueck());
		mt.selectCheckBox("#garage", merkmalType.isKfzStellplatz());
		mt.selectCheckBox("#else", merkmalType.isSonstigesElse());
	}

}
