package net.immocrm.gui.request;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.immocrm.domain.Order;
import net.immocrm.domain.Request;

public class RequestTabController {

	@FXML TableView<RequestRow> requestTable;

	private final RequestController requestCtrl;

	private Order orderInDialog;


	public RequestTabController() {
		requestCtrl = new RequestController();
	}

	public void requestsToForm(Order order) {
		this.orderInDialog = order;
		ObservableList<RequestRow> items = requestTable.getItems();
		items.clear();
		for (Request request : order.getRequests()) {
			items.add(new RequestRow(request));
		}
	}


	@FXML
	public void newRequest() {
		newItem(orderInDialog);
	}

	private void newItem(Order order) {
		Request r = requestCtrl.newRequest(order, true);
		if (r != null) {
			order.getRequests().add(r);
			ObservableList<RequestRow> tableContent = requestTable.getItems();
			tableContent.add(new RequestRow(r));
			requestTable.setItems(tableContent);
		}
	}

	@FXML
	public void editRequest() {
		RequestRow row = requestTable.getSelectionModel().getSelectedItem();
		if (row != null) {
			Request r = requestCtrl.showDialog(row.getDomain());
			if (r != null) {
				ObservableList<RequestRow> items = requestTable.getItems();
				items.remove(row);
				items.add(new RequestRow(r));
			}
		}
	}

	@FXML
	public void deleteRequest() {
		RequestRow row = requestTable.getSelectionModel().getSelectedItem();
		if (row != null) {
			Request request = row.getDomain();
			if (requestCtrl.delete(request)) {
				requestTable.getItems().remove(row);
			}
		}
	}

	@FXML
	public void mouseClickOnRequestItem(MouseEvent e) {
		if (e.getClickCount() == 2) {
			editRequest();
		}
	}

	@FXML
	public void keyPressedOnRequestItem(KeyEvent e) {
		if (e.isConsumed()) {
			return;
		}
		if (e.getCode() == KeyCode.ENTER) {
			editRequest();
			e.consume();
		}
	}

}
