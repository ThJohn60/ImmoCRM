package net.immocrm.gui.refresh;

import java.util.EnumSet;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.ref.OrderTypeEnum;

public class DomainViewFilter {

	private final TextField filterField;
	private final CheckBox activeOnlyFilter;
	private final CheckBox saleFilter;
	private final CheckBox rentFilter;

	private final ImmobilieFilter immobilieFilter;


	public DomainViewFilter(TextField filterField, CheckBox activeOnlyFilter, CheckBox saleFilter,
			CheckBox rentFilter, ImmobilieFilter immobilieFilter) {
		this.filterField = filterField;
		this.activeOnlyFilter = activeOnlyFilter;
		this.saleFilter = saleFilter;
		this.rentFilter = rentFilter;
		this.immobilieFilter = immobilieFilter;
	}



	public final boolean isActiveFilterSelected() {
		return activeOnlyFilter.isSelected();
	}

	public EnumSet<ImmobilieCategoryEnum> categorySet() {
		return immobilieFilter.categorySet();
	}

	public ImmobilieFilter getImmobilieFilter() {
		return immobilieFilter;
	}


	public String getFilterPattern() {
		return filterField.getText();
	}

	public EnumSet<OrderTypeEnum> orderTypeSet() {
		EnumSet<OrderTypeEnum> result = EnumSet.noneOf(OrderTypeEnum.class);
		if (saleFilter.isSelected()) {
			result.add(OrderTypeEnum.Verkauf);
		}
		if (rentFilter.isSelected()) {
			result.add(OrderTypeEnum.Vermietung);
		}
		return result;
	}

	public boolean isFilterActive() {
		return !getFilterPattern().isEmpty()
				|| activeOnlyFilter.isSelected()
				|| saleFilter.isSelected()
				|| rentFilter.isSelected()
				|| immobilieFilter.isFilterActive();
	}

}
