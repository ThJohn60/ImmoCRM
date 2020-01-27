package net.immocrm.gui.property;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import net.immocrm.domain.Person;
import net.immocrm.gui.create.NewImmobilieManager;
import net.immocrm.gui.overview.MenuItemInfo;

public class NewImmobilieController {

	@FXML
	public void newImmobilie(Event event) {
		MenuItemInfo mi = getUserData(event);
		if (mi != null && mi.isPersonNode()) {
			showImmobilie(mi.getPerson());
		} else {
			showImmobilie(null);
		}
	}

	public void newImmobilie(Person person) {
		showImmobilie(person);
	}

	private void showImmobilie(Person person) {
		NewImmobilieManager dlgMan = new NewImmobilieManager();
		dlgMan.setCustomer(person);
		dlgMan.newImmobilieAssistent();
	}

	private MenuItemInfo getUserData(Event event) {
		if (event.getSource() instanceof MenuItem) {
			MenuItem mi = (MenuItem)event.getSource();
			return (MenuItemInfo)mi.getUserData();
		}
		return null;
	}

}
