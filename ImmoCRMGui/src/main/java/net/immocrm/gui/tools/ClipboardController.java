package net.immocrm.gui.tools;

import javafx.fxml.FXML;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import net.immocrm.domain.Address;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.gui.MainController;
import net.immocrm.gui.property.ImmobileTexter;

public class ClipboardController {

	public static final ClipboardController INSTANCE = new ClipboardController();

	@FXML
	public void copyAddressToClipboard() {
		if (MainController.instance().getOrder() != null) {
			copyAddressToClipboard(MainController.instance().getOrder().getCustomer());
		} else if (MainController.instance().getImmobilie() != null) {
			copyAddressToClipboard(MainController.instance().getImmobilie().getOwner());
		} else if (MainController.instance().getPerson() != null) {
			copyAddressToClipboard(MainController.instance().getPerson());
		} else if (MainController.instance().getNotar() != null) {
			Notar n = MainController.instance().getNotar();
			copyAddressToClipboard(n.getName(), n.getAddress());
		}
	}

	public void copyAddressToClipboard(Person person) {
		if (person != null) {
			copyAddressToClipboard(person.getName(), person.getHomeAddress());
		}
	}

	public void copyAddressToClipboard(String name, Address address) {
		copyToClipboard(name + "\n" + address.toString());
	}


	@FXML
	public void copyToClipboard() {
		Order o = MainController.instance().getOrder();
		if (!copyToClipboard(o)) {
			Immobilie i = MainController.instance().getImmobilie();
			if (!copyToClipboard(i)) {
				Person p = MainController.instance().getPerson();
				if (p != null) {
					copyAddressToClipboard(p);
				}
			}
		}
	}

	public boolean copyToClipboard(Order o) {
		if (o != null) {
			// TODO
		}
		return false;
	}

	public boolean copyToClipboard(Immobilie i) {
		if (i != null) {
			ImmobileTexter immobileTexter = new ImmobileTexter(i);
			copyToClipboard(immobileTexter.getText());
		}
		return (i != null);
	}

	private void copyToClipboard(String text) {
	     final Clipboard clipboard = Clipboard.getSystemClipboard();
	     final ClipboardContent content = new ClipboardContent();
	     content.putString(text);
	     clipboard.setContent(content);
	}

}
