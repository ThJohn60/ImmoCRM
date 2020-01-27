package net.immocrm.gui.order;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.gui.MainController;
import net.immocrm.gui.create.NewOrderManager;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.request.RequestController;
import net.immocrm.gui.tools.DeleteItemController;

public class OrderTableInDetailsMenuController {

	@FXML Hyperlink newOrderLink;

	private TableView<OrderRow> orderTable;
	private Immobilie immobilie;
	private Person person;


	public void setOrderTable(TableView<OrderRow> orderTable) {
		this.orderTable = orderTable;
	}

	public void setImmobilie(Immobilie immobilie) {
		this.immobilie = immobilie;
		if (newOrderLink != null) {
			newOrderLink.setTooltip(new Tooltip("Einen neuen Auftrag von " + immobilie.getOwner().getName() + " eingeben"));
		}
	}

	public void setPerson(Person person) {
		this.person = person;
		if (newOrderLink != null) {
			newOrderLink.setTooltip(new Tooltip("Einen neuen Auftrag von " + person.getName() + " eingeben"));
		}
	}




	@FXML
	public void gotoOrder() {
		OrderRow selectedItem = orderTable.getSelectionModel().getSelectedItem();
		if  (selectedItem != null) {
			MainController.instance().showOrderDetails(selectedItem.getDomain());
		}
	}


	@FXML
	public void newOrder() {
		NewOrderManager dlgMan = new NewOrderManager();
		if (immobilie != null) {
			dlgMan.setImmobilie(immobilie);
			dlgMan.setOwner(immobilie.getOwner());
		}
		if (person != null) {
			dlgMan.setOwner(person);
		}
		dlgMan.newOrderAssistent(null);
	}

	@FXML
	public void newRequest() {
		OrderRow selectedItem = orderTable.getSelectionModel().getSelectedItem();
		if  (selectedItem != null) {
			RequestController ctrl = new RequestController();
			ctrl.newRequest(selectedItem.getDomain(), true);
			MainController.getRefreshDispatcher().refreshOrderViews(selectedItem.getDomain(), RefreshType.updateItem);
		}
	}

	@FXML
	public void editOrder() {
		OrderRow selectedItem = orderTable.getSelectionModel().getSelectedItem();
		if  (selectedItem != null) {
			OrderDialog dialog = new OrderDialog();
			Order order = dialog.showDialog(selectedItem.getDomain());
			if (order != null) {
				MainController.getRefreshDispatcher().refreshOrderViews(order, RefreshType.updateItem);
			}
		}
	}

	@FXML
	public void deleteOrder() {
		OrderRow selectedItem = orderTable.getSelectionModel().getSelectedItem();
		if  (selectedItem != null) {
			DeleteItemController deleteItemCtrl = new DeleteItemController();
			deleteItemCtrl.deleteOrder(selectedItem.getDomain());
		}
	}

}
