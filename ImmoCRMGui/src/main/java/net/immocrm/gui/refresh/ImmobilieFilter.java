package net.immocrm.gui.refresh;

import java.util.EnumSet;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;

public class ImmobilieFilter {

	private final TextField filterField;
	private final CheckBox flats;
	private final CheckBox houses;
	private final CheckBox office;
	private final CheckBox site;


	public ImmobilieFilter(TextField filterField, CheckBox flats, CheckBox houses,
			CheckBox office, CheckBox site) {
		this.filterField = filterField;
		this.flats = flats;
		this.houses = houses;
		this.office = office;
		this.site = site;
	}

	public String getFilterPattern() {
		return filterField.getText();
	}

	public EnumSet<ImmobilieCategoryEnum> categorySet() {
		EnumSet<ImmobilieCategoryEnum> result = EnumSet.noneOf(ImmobilieCategoryEnum.class);
		if (flats.isSelected()) {
			result.add(ImmobilieCategoryEnum.Wohnung);
		}
		if (houses.isSelected()) {
			result.add(ImmobilieCategoryEnum.Haus);
		}
		if (office.isSelected()) {
			result.add(ImmobilieCategoryEnum.Gewerbegebäude);
		}
		if (site.isSelected()) {
			result.add(ImmobilieCategoryEnum.Grundstück);
		}
		return result;
	}

	public boolean isFilterActive() {
		return !getFilterPattern().isEmpty()
			|| flats.isSelected()
			|| houses.isSelected()
			|| office.isSelected()
			|| site.isSelected();
	}

}
