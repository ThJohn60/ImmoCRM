package net.immocrm.gui.order;

import net.immocrm.domain.Order;
import net.immocrm.domain.OrderManager;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class OrderDialog {

	private OrderDialogController dialogCtrl;


	public Order showDialog(Order order) {
		FormDialogBuilder builder = dialogBuilder(order);
		FormDialog dlg = builder.build();
		dialogCtrl = builder.getController();
		dialogCtrl.valuesToForm(order);

		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return order;
		}
		return null;
	}

	private FormDialogBuilder dialogBuilder(Order order) {
		FormDialogBuilder builder = new FormDialogBuilder("order/OrderDialog.fxml")
				.withTitle(order)
				.withHeader(TextMaker.INSTANCE.getDialogHeader(order))
				.withOkCancelButtons()
				.setOnCloseRequestOkHandler(getDialogSaveHandler(order));
		return builder;
	}

	private DialogSaveValidator getDialogSaveHandler(Order order) {
		return evt -> {
			try {
				dialogCtrl.valuesFromForm(order);
				ValidationIssues issues = order.validate();
				if (issues.hasErrors()) {
					AlertProvider.alertIssues(issues);
					return false;
				}
				OrderManager orderMan = new OrderManager();
				orderMan.save(order);
				return true;
			} catch (NoActionException e) {
				return false;
			} catch (IncorrectValueException e) {
				AlertProvider.alertException(e);
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				AlertProvider.alertException(e);
			}
			return false;
		};
	}

}
