package net.immocrm.gui.order;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Order;
import net.immocrm.domain.OrderReader;
import net.immocrm.domain.TextMaker;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.MainController;
import net.immocrm.gui.refresh.RefreshedImmobilien;
import net.immocrm.gui.refresh.RefreshedOrders;
import net.immocrm.gui.refresh.RefreshedPersons;

public class OrderDetailTab extends BaseTab {

	private OrderDetailController myCtrl;
	private Tab myTab;
	private Order order;
	private OrderDetailTab instance;


	public OrderDetailTab(TabPane tabPane) {
		super(tabPane);
		instance = this;
	}


	public void showTab(Order o) {
		buildtab(o);
		select(myTab);
//		myCtrl.requestFocus();
	}


	private void buildtab(Order o) {
		this.order = o;
		FXMLLoader loader = getLoader("OrderDetailView.fxml");
		myTab = loadTab(loader);
		myTab.setText(TextMaker.INSTANCE.getTabHeader(o));
		myTab.setGraphic(IconProvider.orderIcon());
		myTab.setUserData(this);

		try {
			myCtrl = loader.getController();
			myCtrl.showOrder(o);
			setScrollPane(myCtrl.getScrollPane());
			tabPane.getTabs().add(myTab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Order getSelectedOrder() {
		if (!myTab.isSelected()) {
			return null;
		}
		return order;
	}

	@Override
	public void refresh() {
		order = OrderReader.INSTANCE.fetchById(order.getId());
		myCtrl.showOrder(order);
	}


	@Override
	public void refreshPersons(RefreshedPersons persons) {
		if (order.getCustomer().equals(persons.getDomain())) {
			order.setCustomer(persons.getDomain());
			myCtrl.showOrder(order);
		}
	}


	@Override
	public void refreshImmobilie(RefreshedImmobilien immobilien) {
		if (order.getImmobilie().equals(immobilien.getDomain())) {
			order.setImmobilie(immobilien.getDomain());
			myCtrl.showOrder(order);
		}
	}


	@Override
	public void refreshOrders(RefreshedOrders orders) {
		if (order.equals(orders.getDomain())) {
			if (!orders.isDelete()) {
				order = orders.getDomain();
				myCtrl.showOrder(order);
			} else {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						MainController.instance().closeTab(instance);
					}

				});
			}
		}
	}

}
