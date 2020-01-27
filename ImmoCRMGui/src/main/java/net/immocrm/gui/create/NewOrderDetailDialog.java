package net.immocrm.gui.create;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Order;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.NoActionException;
import net.immocrm.gui.order.OrderDialogHelper;
import net.immocrm.gui.order.OrderFormController;
import net.immocrm.gui.order.RentOrderDialogHelper;
import net.immocrm.gui.order.SaleOrderDialogHelper;

public class NewOrderDetailDialog extends AbstractCreationDialog<Order> {

	private final OrderFormController ctrl;


	public NewOrderDetailDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Auftragdetails");
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("order/OrderForm.fxml");
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		dialogFrame.setContentPane((Pane)tab.getContent());
		ctrl = fxmlLoader.getController();
	}

	@Override
	protected DialogSaveValidator getValidator() {
		return event -> {
			try {
				ctrl.valuesFromForm(DomainFactory.newOrder());
			} catch (NoActionException e) {
				return false;
			} catch (IncorrectValueException e) {
				AlertProvider.alertError(e.getMessage());
				return false;
			}
			if (ctrl.getOrderState() == null) {
				AlertProvider.alertWarning("Bitte den Auftragszustand auswählen.");
				return false;
			}
			return true;
		};
	}


	@Override
	protected void valuesToForm(Order order) {
		OrderDialogHelper dialogHelper = order.isSaleOrder() ? new SaleOrderDialogHelper() : new RentOrderDialogHelper();
		ctrl.setHelper(dialogHelper);
		ctrl.valuesToForm(order);
	}

	@Override
	protected void valuesFromForm(Order order) {
		ctrl.valuesFromForm(order);
	}

}
