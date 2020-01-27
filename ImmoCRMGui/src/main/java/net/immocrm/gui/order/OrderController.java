package net.immocrm.gui.order;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import net.immocrm.domain.Order;
import net.immocrm.gui.MainController;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.tools.EditItemController;
import net.immocrm.gui.tools.GotoItemController;

public class OrderController implements Initializable {

	@FXML BorderPane tablePane;
	@FXML TableView<OrderRow> tableView;

	private OrderTableController tableCtrl;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableCtrl = new OrderTableController(tableView);

	}

	public Order getSelectedOrder() {
		return tableCtrl.getDomainObject();
	}


	public void refresh(RefreshedOrders orders) {
		tableCtrl.fillTable(orders);
	}

	@FXML
	public void mouseClickOnTableItem(MouseEvent event) {
		if (event.getClickCount() == 2) {
			GotoItemController ctrl = new GotoItemController();
			ctrl.gotoItem();
			event.consume();
		}
	}

	@FXML
	public void keyPressedOnTableItem(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			EditItemController editItemCtrl = new EditItemController();
			editItemCtrl.editItem();
			event.consume();
		}
	}

	public void requestFocus() {
		tableView.requestFocus();
	}

	@FXML
	public void gotoOrder() {
		MainController.instance().showOrderDetails(tableCtrl.getDomainObject());
	}

	@FXML
	public void gotoImmobilie() {
		MainController.instance().showImmobileDetails(tableCtrl.getDomainObject().getImmobilie());
	}

}
