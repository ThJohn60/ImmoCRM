package net.immocrm.gui.order;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import net.immocrm.domain.ref.OrderTypeEnum;
import net.immocrm.gui.create.NewOrderManager;
import net.immocrm.gui.overview.MenuItemInfo;

public class NewOrderController {

	@FXML
	public void newOrder(Event event) {
		newOrder(event, null);
	}

	@FXML
	public void newSaleOrder(Event event) {
		newOrder(event, OrderTypeEnum.Verkauf);
	}

	@FXML
	public void newRentOrder(Event event) {
		newOrder(event, OrderTypeEnum.Vermietung);

	}

	private void newOrder(Event event, OrderTypeEnum type) {
		MenuItemInfo mi = getUserData(event);

		NewOrderManager dlgMan = new NewOrderManager();
		if (mi != null) {
			if (mi.isPersonNode()) {
				dlgMan.setOwner(mi.getPerson());
			} else if (mi.isImmobilieNode()) {
				dlgMan.setImmobilie(mi.getImmobilie());
			}
		}

		dlgMan.newOrderAssistent(type);
	}

	private MenuItemInfo getUserData(Event event) {
		if (event.getSource() instanceof MenuItem) {
			MenuItem mi = (MenuItem)event.getSource();
			return (MenuItemInfo)mi.getUserData();
		}
		return null;
	}

}
