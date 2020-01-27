package net.immocrm.gui.tools;

import javafx.fxml.FXML;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieReader;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.OrderReader;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonReader;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.notar.NotarDialog;
import net.immocrm.gui.order.OrderDialog;
import net.immocrm.gui.person.PersonDialog;
import net.immocrm.gui.property.ImmobilieDialog;
import net.immocrm.gui.refresh.RefreshType;

public class EditItemController {

	@FXML
	public boolean editItem() {
		if (MainController.instance().getOrder() != null) {
			return editOrder(MainController.instance().getOrder());
		}
		if (MainController.instance().getImmobilie() != null) {
			return editImmobilie(MainController.instance().getImmobilie());
		}
		if (MainController.instance().getPerson() != null) {
			return (editPerson(MainController.instance().getPerson(), false) != null);
		}
		if (MainController.instance().getNotar() != null) {
			return editNotar(MainController.instance().getNotar());
		}
		AlertProvider.alertMissingRemoveItem();
		return false;
	}

	private boolean editNotar(Notar n) {
		Notar notar = new NotarDialog().showDialog(n);
		return (notar != null);
	}


	public Person editPerson(Person person, boolean disableNameChange) {
		if (!person.isNew()) {
			person = PersonReader.INSTANCE.fetchById(person.getId());
		}
		person = new PersonDialog().showDialog(person, 0, disableNameChange);
		MainController.getRefreshDispatcher().refreshPersonViews(person, RefreshType.updateItem);
		return person;
	}

	public boolean editOrder(Order order) {
		if (!order.isNew()) {
			order = OrderReader.INSTANCE.fetchById(order.getId());
		}
		Order changedOrder = new OrderDialog().showDialog(order);
		if (changedOrder != null) {
			MainController.getRefreshDispatcher().refreshOrderViews(changedOrder, RefreshType.updateItem);
			return true;
		}
		return false;
	}

	public boolean editImmobilie(Immobilie immobilie) {
		if (!immobilie.isNew()) {
			immobilie = ImmobilieReader.INSTANCE.fetchById(immobilie.getId());
		}
		immobilie = new ImmobilieDialog().showDialog(immobilie);
		if (immobilie != null) {
			MainController.getRefreshDispatcher().refreshImmobilieViews(immobilie, RefreshType.updateItem);
			return true;
		}
		return false;
	}

}
