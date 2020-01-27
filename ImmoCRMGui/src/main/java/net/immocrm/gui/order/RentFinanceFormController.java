package net.immocrm.gui.order;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.immocrm.domain.Order;
import net.immocrm.domain.tool.FinancialCalculator;
import net.immocrm.domain.vc.Price;
import net.immocrm.gui.control.PriceField;

public class RentFinanceFormController extends FinanceFormController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setProvisionCalcListener(customerPrice, customerPrice, customerPriceIncome);
		setProvisionCalcListener(estimatedPrice, estimatedPrice, estimatedPriceIncome);
		setProvisionCalcListener(settlementPrice, settlementPrice, settlementPriceIncome);
		setProvisionCalcListener(customerProvision, customerPrice, customerPriceIncome);
		setProvisionCalcListener(customerProvision, estimatedPrice, estimatedPriceIncome);
		setProvisionCalcListener(customerProvision, settlementPrice, settlementPriceIncome);

		setSquareMeterPriceListener(customerPrice, customerPricePerSquareMeter);
		setSquareMeterPriceListener(estimatedPrice, estimatedPricePerSquareMeter);
		setSquareMeterPriceListener(settlementPrice, settlementPricePerSquareMeter);
	}


	@Override
	protected void additionalFinanceToForm(Order o) {
		if (o.getCustomerProvision().isEmpty()) {
			customerProvision.clear();
			customerPriceIncome.setText("");
			estimatedPriceIncome.setText("");
			settlementPriceIncome.setText("");
		} else {
			customerProvision.setPercent(o.getCustomerProvision());
			double monthCount = o.getCustomerProvision().toNumber();
			customerPriceIncome.setText(FinancialCalculator.calcRentProvision(o.getCustomerPrice(), monthCount).toString());
			estimatedPriceIncome.setText(FinancialCalculator.calcRentProvision(o.getEstimatedPrice(), monthCount).toString());
			settlementPriceIncome.setText(FinancialCalculator.calcRentProvision(o.getSettlementPrice(), monthCount).toString());
		}
	}

	@Override
	protected void valuesFromForm(Order o) {
		o.setEstimatedPrice(estimatedPrice.getValidatedPrice());
		o.setCustomerPrice(customerPrice.getValidatedPrice());
		o.setSettlementPrice(settlementPrice.getValidatedPrice());
		o.setCustomerProvision(customerProvision.getValidatedPercent());
	}


	@Override
	protected void calcProvision(PriceField priceField, Label incomeField) {
		Price price = priceField.getPrice();
		if (!price.isEmpty() && customerProvision.getLength() > 0) {
			double monthCount = customerProvision.getPercent().toNumber();
			Price prov = FinancialCalculator.calcRentProvision(price, monthCount);
			incomeField.setText(prov.toString());
		}
	}

}
