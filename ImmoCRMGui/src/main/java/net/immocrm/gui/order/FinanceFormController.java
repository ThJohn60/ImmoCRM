package net.immocrm.gui.order;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.immocrm.domain.Order;
import net.immocrm.domain.vc.Area;
import net.immocrm.domain.vc.Price;
import net.immocrm.gui.control.PercentField;
import net.immocrm.gui.control.PriceField;

public abstract class FinanceFormController {

	@FXML PercentField customerProvision;
	@FXML PercentField buyerProvision;

	@FXML Label customerPricePerSquareMeter;
	@FXML Label customerPriceIncome;
	@FXML Label estimatedPricePerSquareMeter;
	@FXML Label estimatedPriceIncome;
	@FXML Label settlementPricePerSquareMeter;
	@FXML Label settlementPriceIncome;

	@FXML PriceField customerPrice;
	@FXML PriceField estimatedPrice;
	@FXML PriceField settlementPrice;


	protected Order order;


	public void valuesToForm(Order o) {
		this.order = o;
		customerPrice.setPrice(o.getCustomerPrice());
		estimatedPrice.setPrice(o.getEstimatedPrice());
		settlementPrice.setPrice(o.getSettlementPrice());
		Area area = o.getImmobilie().getArea();
		if (area != null) {
			customerPricePerSquareMeter.setText(o.getCustomerPrice().divideBy(area).toString());
			estimatedPricePerSquareMeter.setText(o.getEstimatedPrice().divideBy(area).toString());
			settlementPricePerSquareMeter.setText(o.getSettlementPrice().divideBy(area).toString());
		}
		additionalFinanceToForm(o);
	}



	public void setSettlementPrice(Price settlementPrice) {
		this.settlementPrice.setPrice(settlementPrice);
	}



	protected abstract void additionalFinanceToForm(Order o);


	abstract void valuesFromForm(Order o);


	@FXML
	public void refreshCalculation() {
		calcProvision(customerPrice, customerPriceIncome);
		calcProvision(estimatedPrice, estimatedPriceIncome);
		calcProvision(settlementPrice, settlementPriceIncome);

		calcSquareMeterPrice(customerPrice, customerPricePerSquareMeter);
		calcSquareMeterPrice(estimatedPrice, estimatedPricePerSquareMeter);
		calcSquareMeterPrice(settlementPrice, settlementPricePerSquareMeter);
	}

	protected abstract void calcProvision(PriceField priceField, Label incomeField);

	void calcSquareMeterPrice(PriceField priceField, Label sqmField) {
		Price price = priceField.getPrice();
		if (!price.isEmpty() && order.getImmobilie().getArea() != null) {
			Price result = price.divideBy(order.getImmobilie().getArea());
			sqmField.setText(result.toString());
		}
	}


	protected void setProvisionCalcListener(TextField focusField, PriceField priceField, Label incomeField) {
		focusField.focusedProperty().addListener(
			provisionCalcListener(priceField, incomeField)
		);
	}

	private ChangeListener<? super Boolean> provisionCalcListener(PriceField priceField, Label incomeField) {
		return (val, oldPropertyValue, newPropertyValue) -> {
		    if (!newPropertyValue) {
				calcProvision(priceField, incomeField);
		    }
		};
	}


	protected void setSquareMeterPriceListener(PriceField priceField, Label sqmField) {
		priceField.focusedProperty().addListener(
			squareMeterPriceListener(priceField, sqmField)
		);
	}

	private ChangeListener<? super Boolean> squareMeterPriceListener(PriceField priceField, Label sqmField) {
		return (val, oldPropertyValue, newPropertyValue) -> {
		    if (!newPropertyValue) {
				try {
					calcSquareMeterPrice(priceField, sqmField);
				} catch (Exception e) {
					// ignore
				}
		    }
		};
	}

}
