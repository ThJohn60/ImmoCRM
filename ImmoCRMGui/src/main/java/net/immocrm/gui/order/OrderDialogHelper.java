package net.immocrm.gui.order;

import net.immocrm.domain.Order;

public interface OrderDialogHelper {

	String getFinanceOrderFormFxmlFilePath();

	String getCustomerLabel();
	String getBuyerLabel();

	boolean isNotarBoxVisible();

	String calcProvision(Order order);

	String getDesiredPriceLabel();
	String getRealPriceLabel();
	String getPriceLabel();

	String getHausgeldName();

}
