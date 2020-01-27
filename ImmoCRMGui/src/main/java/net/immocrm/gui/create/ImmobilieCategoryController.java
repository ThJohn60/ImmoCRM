package net.immocrm.gui.create;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;

public class ImmobilieCategoryController {

	@FXML ToggleGroup typeToggle;

	@FXML RadioButton flat;
	@FXML RadioButton house;
	@FXML RadioButton business;
	@FXML RadioButton ground;
	@FXML RadioButton garage;
	@FXML RadioButton other;


	public ImmobilieCategoryEnum getCategory() {
		if (flat.isSelected()) {
			return ImmobilieCategoryEnum.Wohnung;
		}
		if (house.isSelected()) {
			return ImmobilieCategoryEnum.Haus;
		}
		if (business.isSelected()) {
			return ImmobilieCategoryEnum.Gewerbegebäude;
		}
		if (ground.isSelected()) {
			return ImmobilieCategoryEnum.Grundstück;
		}
		if (garage.isSelected()) {
			return ImmobilieCategoryEnum.Stellplatz;
		}
		if (other.isSelected()) {
			return ImmobilieCategoryEnum.Sonstiges;
		}
		return null;
	}

	public void setCategory(ImmobilieCategoryEnum category) {
		if (category != null) {
			switch (category) {
			case Gewerbegebäude:
				business.setSelected(true);
				break;
			case Grundstück:
				ground.setSelected(true);
				break;
			case Haus:
				house.setSelected(true);
				break;
			case Sonstiges:
				other.setSelected(true);
				break;
			case Stellplatz:
				garage.setSelected(true);
				break;
			case Wohnung:
				flat.setSelected(true);
				break;
			default:
				break;
			}
		}
	}

}
