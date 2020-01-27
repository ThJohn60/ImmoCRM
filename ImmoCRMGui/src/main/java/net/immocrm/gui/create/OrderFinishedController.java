package net.immocrm.gui.create;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.ref.OrderTypeEnum;

public class OrderFinishedController extends ImmobilieFinishedController {

	@FXML Label lbOrderType;
	@FXML Label lbOrder;
	@FXML Label lbCustomer;


	public void valuesToForm(Order order) {
		lbOrderType.setText(order.getOrderType().name());
		lbCustomer.setText(ownerText(order.getCustomer()));
		lbOrder.setText(orderText(order));
		immoValuesToForm(order.getImmobilie());
	}

	private void immoValuesToForm(Immobilie immobilie) {
		lbPropertyType.setText(immobilie.getImmobilieTypeName());
		lbImmobilie.setText(immobilieText(immobilie));
		lbAusstattung.setText(ausstattungText(immobilie));
		lbImages.setText(imageText(immobilie));
	}

	private String orderText(Order order) {
		StringBuilder result = new StringBuilder();
		if (order.getOrderType() == OrderTypeEnum.Verkauf) {
			result.append("Gewünschter Preis: ").append(order.getCustomerPrice()).append("\n");
			result.append("Realistischer Preis: ").append(order.getEstimatedPrice()).append("\n");
			result.append("Verkäuferprovision: ").append(order.getCustomerProvision()).append("\n");
			result.append("Käuferprovision: ").append(order.getBuyerProvision()).append("\n");
		} else if (order.getOrderType() == OrderTypeEnum.Vermietung) {
			result.append("Gewünschte Miete: ").append(order.getCustomerPrice()).append("\n");
			result.append("Realistische Miete: ").append(order.getEstimatedPrice()).append("\n");
			result.append("Provision: ").append(order.getCustomerProvision()).append("\n");
		}
		return result.toString();
	}

}
