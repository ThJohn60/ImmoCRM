package net.immocrm.gui.create;

import javafx.scene.control.ButtonBar.ButtonData;
import net.immocrm.domain.Order;

public class OrderForkDialog extends AbstractCreationDialog<Order> {

	private final OrderForkController ctrl;


	public OrderForkDialog(AssistentDialogFrame dialogFrame, OrderInputMode inputMode) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Auswahl");
		dialogFrame.setContentPane("create/OrderFork.fxml");
		ctrl = dialogFrame.getController();
		ctrl.setInputMode(inputMode);
	}


	public OrderInputMode getInputMode() {
		return ctrl.getInputMode();
	}

}
