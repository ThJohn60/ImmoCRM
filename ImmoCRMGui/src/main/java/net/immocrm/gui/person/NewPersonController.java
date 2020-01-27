package net.immocrm.gui.person;

import javafx.fxml.FXML;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Person;
import net.immocrm.gui.MainController;
import net.immocrm.gui.refresh.RefreshType;

public class NewPersonController {

	@FXML
	public void newPerson() {
		PersonDialog dialog = new PersonDialog();
		Person person = dialog.showDialog(DomainFactory.newPerson());
		if (person != null) {
			MainController.getRefreshDispatcher().refreshPersonViews(person, RefreshType.insertItem);
		}
	}

}
