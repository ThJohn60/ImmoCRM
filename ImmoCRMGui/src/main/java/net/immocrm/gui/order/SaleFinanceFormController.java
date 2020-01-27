package net.immocrm.gui.order;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.immocrm.domain.Order;
import net.immocrm.domain.tool.FinancialCalculator;
import net.immocrm.domain.vc.Price;
import net.immocrm.gui.control.PriceField;

public class SaleFinanceFormController extends FinanceFormController implements Initializable {


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setProvisionCalcListener(customerPrice, customerPrice, customerPriceIncome);
		setProvisionCalcListener(estimatedPrice, estimatedPrice, estimatedPriceIncome);
		setProvisionCalcListener(settlementPrice, settlementPrice, settlementPriceIncome);
		setProvisionCalcListener(customerProvision, customerPrice, customerPriceIncome);
		setProvisionCalcListener(customerProvision, estimatedPrice, estimatedPriceIncome);
		setProvisionCalcListener(customerProvision, settlementPrice, settlementPriceIncome);
		setProvisionCalcListener(buyerProvision, customerPrice, customerPriceIncome);
		setProvisionCalcListener(buyerProvision, estimatedPrice, estimatedPriceIncome);
		setProvisionCalcListener(buyerProvision, settlementPrice, settlementPriceIncome);

		setSquareMeterPriceListener(customerPrice, customerPricePerSquareMeter);
		setSquareMeterPriceListener(estimatedPrice, estimatedPricePerSquareMeter);
		setSquareMeterPriceListener(settlementPrice, settlementPricePerSquareMeter);
	}

	@Override
	protected void additionalFinanceToForm(Order o) {
		if (o.getCustomerProvision().isEmpty() && o.getBuyerProvision().isEmpty()) {
			customerProvision.clear();
			buyerProvision.clear();
			customerPriceIncome.setText("");
			estimatedPriceIncome.setText("");
			settlementPriceIncome.setText("");
		} else {
			customerProvision.setPercent(o.getCustomerProvision());
			buyerProvision.setPercent(o.getBuyerProvision());
			customerPriceIncome.setText(FinancialCalculator.calcSaleProvision(o.getCustomerPrice(), o).toString());
			estimatedPriceIncome.setText(FinancialCalculator.calcSaleProvision(o.getEstimatedPrice(), o).toString());
			settlementPriceIncome.setText(FinancialCalculator.calcSaleProvision(o.getSettlementPrice(), o).toString());
		}
	}


	@Override
	public void valuesFromForm(Order o) {
		o.setEstimatedPrice(estimatedPrice.getValidatedPrice());
		o.setCustomerPrice(customerPrice.getValidatedPrice());
		o.setSettlementPrice(settlementPrice.getValidatedPrice());
		o.setBuyerProvision(buyerProvision.getValidatedPercent());
		o.setCustomerProvision(customerProvision.getValidatedPercent());
	}


	@Override
	protected void calcProvision(PriceField priceField, Label incomeField) {
		Price price = priceField.getPrice();
		if (!price.isEmpty()) {
			Price provision = FinancialCalculator.calcSaleProvision(price, customerProvision.getPercent(), buyerProvision.getPercent());
			incomeField.setText(provision.toString());
		}
	}

}
