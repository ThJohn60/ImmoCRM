package net.immocrm.gui.order;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Order;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.refresh.RefreshDispatcher;
import net.immocrm.gui.refresh.RefreshedOrders;

public class OrderTableTab extends BaseTab {

	private final RefreshDispatcher refreshDispatcher;

	private OrderController myCtrl;
	private Tab myTab;


	public OrderTableTab(TabPane tabPane, RefreshDispatcher refreshDispatcher) {
		super(tabPane);
		this.refreshDispatcher = refreshDispatcher;
	}


	@Override
	public Order getSelectedOrder() {
		if (myTab != null && myCtrl != null && myTab.isSelected()) {
			return myCtrl.getSelectedOrder();
		}
		return null;
	}

	public void showTab() {
		if (myCtrl == null) {
			buildtab();
			refresh();
		}
		select(myTab);
		myCtrl.requestFocus();
	}


	private void buildtab() {
		FXMLLoader loader = getLoader("OrderTableView.fxml");
		myTab = loadTab(loader);
		myCtrl = loader.getController();
		tabPane.getTabs().add(myTab);
//		myTab.setOnClosed(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event event) {
//				myCtrl = null;
//				myTab = null;
//			}
//		});
	}

	@Override
	public void refresh() {
		refreshOrders(refreshDispatcher.getRefreshedOrders());
	}

	@Override
	public void refreshOrders(RefreshedOrders orders) {
		myCtrl.refresh(orders);
	}

}
