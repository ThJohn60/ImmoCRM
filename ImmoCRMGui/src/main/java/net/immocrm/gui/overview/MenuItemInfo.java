package net.immocrm.gui.overview;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.gui.tree.BaseNodeValue;

public class MenuItemInfo {

	private BaseNodeValue selectedItem;


	public MenuItemInfo(BaseNodeValue selectedItem) {
		this.selectedItem = selectedItem;
	}


	public BaseNodeValue getSelectedItem() {
		return selectedItem;
	}

	public boolean isPersonNode() {
		return selectedItem.isPersonNode();
	}

	public boolean isImmobilieNode() {
		return selectedItem.isImmobilieNode();
	}

	public boolean isOrderNode() {
		return selectedItem.isOrderNode();
	}

	public Person getPerson() {
		return selectedItem.getPerson();
	}

	public Immobilie getImmobilie() {
		return selectedItem.getImmobilie();
	}

	public Order getOrder() {
		return selectedItem.getOrder();
	}

}
