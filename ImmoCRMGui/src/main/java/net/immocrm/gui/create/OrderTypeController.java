package net.immocrm.gui.create;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import net.immocrm.domain.Order;
import net.immocrm.domain.ref.OrderTypeEnum;

public class OrderTypeController {

	@FXML ToggleGroup typeToggle;

	@FXML RadioButton sale;
	@FXML RadioButton rent;
	@FXML RadioButton buy;
	@FXML RadioButton rentWanted;


	public OrderTypeEnum getSelectedOrderType() {
		if (sale.isSelected()) {
			return OrderTypeEnum.Verkauf;
		}
		if (rent.isSelected()) {
			return OrderTypeEnum.Vermietung;
		}
		if (buy.isSelected()) {
			return OrderTypeEnum.Kauf;
		}
		if (rentWanted.isSelected()) {
			return OrderTypeEnum.Mietgesuch;
		}
		return null;
	}

	public void selectByOrderType(Order order) {
		if (order.getOrderType() != null) {
			switch (order.getOrderType()) {
			case Kauf:
				buy.setSelected(true);
				break;
			case Mietgesuch:
				rentWanted.setSelected(true);
				break;
			case Verkauf:
				sale.setSelected(true);
				break;
			case Vermietung:
				rent.setSelected(true);
				break;
			}
		}
	}

}
