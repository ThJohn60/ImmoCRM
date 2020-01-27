package net.immocrm.gui.order;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import net.immocrm.domain.Constants;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Order;
import net.immocrm.domain.OrderSettlementmanager;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.ref.OrderStateEnum;
import net.immocrm.domain.valid.DbException;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.FormDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;
import net.immocrm.gui.form.NoActionException;

public class OrderSettlementDialog {

	private OrderSettlementController ctrl;


	public Order showDialog(Order order) {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("order/OrderSettlementForm.fxml");
		Pane formPane = BaseDialogBuilder.loadPane(fxmlLoader);
		FormDialog dlg = createFormDialog(order, formPane);
		ctrl = fxmlLoader.getController();
		ctrl.orderToForm(order);
		DialogResponse response = dlg.showAndWait();
		if (response.isOkDone()) {
			return order;
		}
		return null;
	}


	private FormDialog createFormDialog(Order order, Pane formPane) {
		FormDialogBuilder builder = new FormDialogBuilder("order/OrderSettlementDialog.fxml")
				.withTitle("Eigentümerwechsel")
				.withHeader(TextMaker.INSTANCE.getDialogHeader(order.getImmobilie()))
				.withOkCancelButtons()
				.withFormPane(formPane)
				.setOnCloseRequestOkHandler(getDialogSaveHandler(order));
		return builder.build();
	}

	private DialogSaveValidator getDialogSaveHandler(Order order) {
		return event -> {
			OrderStateEnum oldState = order.getOrderState();
			try {
				if (validate()) {
					ctrl.orderFromForm(order);
					new OrderSettlementmanager().changeOwner(order);
					return true;
				}
			} catch (NoActionException e) {
			} catch (IncorrectValueException e) {
				AlertProvider.alertException(e);
			} catch (DbException e) {
				AlertProvider.alertException(e);
			} catch (Exception e) {
				if (Constants.DEBUG_MODE) {
					e.printStackTrace();
				}
				AlertProvider.alertError(e.getMessage());
			}
			order.setOrderState(oldState);
			return false;
		};
	}

	private boolean validate() {
		Order order = DomainFactory.newOrder();
		ctrl.orderFromForm(order);
		ValidationIssues issues = new ValidationIssues();
		if (order.getBuyer() == null || order.getBuyer().isEmpty()) {
			issues.addMissingField("Käufer");
		}
		if (order.getSettlementDateTime().isEmpty()) {
			issues.addMissingField("Datum");
		}
		if (issues.hasErrors()) {
			AlertProvider.alertIssues(issues);
			return false;
		}
		return true;
	}

}
