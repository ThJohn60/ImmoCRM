package net.immocrm.gui.tools;

import javafx.fxml.FXML;
import net.immocrm.domain.Constants;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieManager;
import net.immocrm.domain.Notar;
import net.immocrm.domain.NotarManager;
import net.immocrm.domain.Order;
import net.immocrm.domain.OrderManager;
import net.immocrm.domain.Person;
import net.immocrm.domain.PersonManager;
import net.immocrm.domain.Request;
import net.immocrm.domain.RequestManager;
import net.immocrm.domain.valid.DbRemoveException;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.refresh.RefreshType;

public class DeleteItemController {

	@FXML
	public boolean deleteItem() {
		if (MainController.instance().getOrder() != null) {
			return deleteOrder(MainController.instance().getOrder());
		}
		if (MainController.instance().getImmobilie() != null) {
			return deleteImmobilie(MainController.instance().getImmobilie());
		}
		if (MainController.instance().getPerson() != null) {
			return deletePerson(MainController.instance().getPerson());
		}
		if (MainController.instance().getNotar() != null) {
			return deleteNotar(MainController.instance().getNotar());
		}
		AlertProvider.alertMissingRemoveItem();
		return false;
	}

	public boolean deletePerson(Person person) {
		if (!person.getOrders().isEmpty() || !person.getImmobilien().isEmpty()) {
			AlertProvider.alertCannotRemoveItem(person);
			return false;
		}
		if (AlertProvider.confirmRemove(person)) {
			try {
				new PersonManager().delete(person);
				MainController.getRefreshDispatcher().refreshPersonViews(person, RefreshType.deleteItem);
				return true;
			} catch (DbRemoveException e) {
				alertDbRemoveException(e);
			} catch (Exception e) {
				alertException(e);
			}
		}
		return false;
	}

	public boolean deleteImmobilie(Immobilie immobilie) {
		if (!immobilie.getOrders().isEmpty()) {
			AlertProvider.alertCannotRemoveItem(immobilie);
			return false;
		}
		if (AlertProvider.confirmRemove(immobilie)) {
			try {
				new ImmobilieManager().delete(immobilie);
				MainController.getRefreshDispatcher().refreshImmobilieViews(immobilie, RefreshType.deleteItem);
				return true;
			} catch (DbRemoveException e) {
				alertDbRemoveException(e);
			} catch (Exception e) {
				alertException(e);
			}
		}
		return false;
	}

	public boolean deleteOrder(Order order) {
		if (AlertProvider.confirmRemove(order)) {
			try {
				new OrderManager().delete(order);
				MainController.getRefreshDispatcher().refreshOrderViews(order, RefreshType.deleteItem);
				return true;
			} catch (DbRemoveException e) {
				alertDbRemoveException(e);
			} catch (Exception e) {
				alertException(e);
			}
		}
		return false;
	}

	public boolean deleteNotar(Notar notar) {
		if (!notar.getOrders().isEmpty()) {
			AlertProvider.alertCannotRemoveItem(notar);
			return false;
		}
		if (AlertProvider.confirmRemove(notar)) {
			try {
				new NotarManager().delete(notar);
				MainController.getRefreshDispatcher().refreshNotarViews(notar, RefreshType.deleteItem);
				return true;
			} catch (DbRemoveException e) {
				alertDbRemoveException(e);
			} catch (Exception e) {
				alertException(e);
			}
		}
		return false;
	}

	public boolean deleteRequest(Request request) {
		try {
			new RequestManager().delete(request);
			MainController.getRefreshDispatcher().refreshOrderViews(request.getOrder(), RefreshType.updateItem);
			return true;
		} catch (DbRemoveException e) {
			alertDbRemoveException(e);
		} catch (Exception e) {
			alertException(e);
		}
		return false;
	}

	private void alertException(Exception e) {
		if (Constants.DEBUG_MODE) {
			e.printStackTrace();
		}
		AlertProvider.alertException("Problem beim Löschen", e);
	}

	private void alertDbRemoveException(DbRemoveException e) {
		if (Constants.DEBUG_MODE) {
			e.printStackTrace();
		}
		AlertProvider.alertError(e.getProblemType(), e.getMessage(), e.getCause());
	}

}
