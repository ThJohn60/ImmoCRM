package net.immocrm.gui.create;

import javafx.scene.control.ButtonBar.ButtonData;
import net.immocrm.domain.Order;

public class OrderFinishedDialog extends AbstractCreationDialog<Order> {

	public OrderFinishedDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.FINISH, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Abschluß");
		dialogFrame.setContentPane("create/OrderFinished.fxml");
	}

	@Override
	protected void valuesToForm(Order order) {
		OrderFinishedController dlgCtrl = dlgFrame.getController();
		dlgCtrl.valuesToForm(order);
	}

}
