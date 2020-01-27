package net.immocrm.gui.order;

import net.immocrm.domain.Order;
import net.immocrm.domain.tool.FinancialCalculator;

public class RentOrderDialogHelper implements OrderDialogHelper {

	@Override
	public String getFinanceOrderFormFxmlFilePath() {
		return "order/RentFinanceOrderForm.fxml";
	}

	@Override
	public String getCustomerLabel() {
		return "Vermieter";
	}

	@Override
	public String getBuyerLabel() {
		return "Mieter";
	}

	@Override
	public String getDesiredPriceLabel() {
		return "Wunschmiete:";
	}

	@Override
	public String getRealPriceLabel() {
		return "Reale Miete:";
	}

	@Override
	public String getPriceLabel() {
		return "Miete:";
	}

	@Override
	public boolean isNotarBoxVisible() {
		return false;
	}

	@Override
	public String calcProvision(Order order) {
		return FinancialCalculator.calcRentProvision(order.getSettlementPrice(), order.getCustomerProvision().toNumber()).toString();
	}

	@Override
	public String getHausgeldName() {
		return "Hausgeld";
	}

}
