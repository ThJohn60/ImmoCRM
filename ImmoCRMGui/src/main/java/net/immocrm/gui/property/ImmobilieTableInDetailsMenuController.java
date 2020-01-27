package net.immocrm.gui.property;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import net.immocrm.domain.Person;
import net.immocrm.gui.MainController;
import net.immocrm.gui.tools.DeleteItemController;
import net.immocrm.gui.tools.EditItemController;

public class ImmobilieTableInDetailsMenuController {

	@FXML Hyperlink newImmobilieLink;

	private TableView<ImmoActionRow> immobilieTable;
	private Person person;


	public void setImmobilieTable(TableView<ImmoActionRow> immobilieTable) {
		this.immobilieTable = immobilieTable;
	}

	public void setPerson(Person person) {
		this.person = person;
		if (newImmobilieLink != null) {
			newImmobilieLink.setTooltip(new Tooltip("Eine neue Immobilie für" + person.getName() + " eingeben"));
		}
	}


	@FXML
	public void gotoImmobilie() {
		ImmoActionRow selectedItem = immobilieTable.getSelectionModel().getSelectedItem();
		if  (selectedItem != null) {
			MainController.instance().showImmobileDetails(selectedItem.getImmobilie());
		}
	}

	@FXML
	public void newImmobilie() {
		new NewImmobilieController().newImmobilie(person);
	}

	@FXML
	public void editImmobilie() {
		ImmoActionRow row = immobilieTable.getSelectionModel().getSelectedItem();
		if  (row != null) {
			new EditItemController().editImmobilie(row.getImmobilie());
		}
	}

	@FXML
	public void deleteImmobilie() {
		ImmoActionRow selectedItem = immobilieTable.getSelectionModel().getSelectedItem();
		if  (selectedItem != null) {
			DeleteItemController deleteItemCtrl = new DeleteItemController();
			deleteItemCtrl.deleteImmobilie(selectedItem.getImmobilie());
		}
	}

}
