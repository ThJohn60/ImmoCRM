package net.immocrm.gui.refresh;

public interface OrderViewRefreshable {

	boolean isVisible();

	void refresh(RefreshedOrders orders);

}
