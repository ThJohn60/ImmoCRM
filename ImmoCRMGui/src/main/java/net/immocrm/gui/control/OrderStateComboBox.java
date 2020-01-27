package net.immocrm.gui.control;

import javafx.scene.control.ComboBox;
import net.immocrm.domain.ref.OrderStateEnum;

public class OrderStateComboBox extends ComboBox<OrderStateEnum> {

	public OrderStateComboBox() {
		getItems().addAll(OrderStateEnum.values());
	}

}
