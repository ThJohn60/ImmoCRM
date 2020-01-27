package net.immocrm.gui.create;

import javafx.scene.control.ButtonBar.ButtonData;
import net.immocrm.domain.Order;
import net.immocrm.domain.ref.OrderStateEnum;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogSaveValidator;

public class OrderTypeDialog extends AbstractCreationDialog<Order> {

	private final OrderTypeController ctrl;


	public OrderTypeDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Auftragsart wählen");
		dialogFrame.setContentPane("create/OrderType.fxml");
		this.ctrl = dialogFrame.getController();
	}

	@Override
	protected DialogSaveValidator getValidator() {
		return event -> {
			if (ctrl.getSelectedOrderType() == null) {
				AlertProvider.alertInfo("Eingabe ist unvollständig", "Bitte die Auftragsart auswählen.");
				return false;
			}
			return true;
		};
	}


	@Override
	protected void valuesToForm(Order order) {
		ctrl.selectByOrderType(order);
	}

	@Override
	protected void valuesFromForm(Order order) {
		order.setOrderType(ctrl.getSelectedOrderType());
		order.setOrderState(OrderStateEnum.OPEN);
	}

}
