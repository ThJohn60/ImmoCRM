package net.immocrm.gui.order;

import net.immocrm.domain.Order;
import net.immocrm.domain.tool.FinancialCalculator;

public class SaleOrderDialogHelper implements OrderDialogHelper {

	@Override
	public String getFinanceOrderFormFxmlFilePath() {
		return "order/SaleFinanceOrderForm.fxml";
	}

	@Override
	public String getCustomerLabel() {
		return "Verkäufer";
	}

	@Override
	public String getBuyerLabel() {
		return "Käufer";
	}

	@Override
	public String getDesiredPriceLabel() {
		return "Wunschpreis:";
	}

	@Override
	public String getRealPriceLabel() {
		return "Realer Preis:";
	}

	@Override
	public String getPriceLabel() {
		return "Verkaufspreis:";
	}

	@Override
	public boolean isNotarBoxVisible() {
		return true;
	}

	@Override
	public String calcProvision(Order order) {
		return FinancialCalculator.calcSaleProvision(order.getSettlementPrice(), order).toString();
	}

	@Override
	public String getHausgeldName() {
		return "Nebenkosten";
	}

}


