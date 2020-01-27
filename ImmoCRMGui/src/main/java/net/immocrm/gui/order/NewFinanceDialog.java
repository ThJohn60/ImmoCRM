package net.immocrm.gui.order;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Order;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.create.AbstractCreationDialog;
import net.immocrm.gui.create.AssistentDialogFrame;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.NoActionException;

public class NewFinanceDialog extends AbstractCreationDialog<Order> {

	private final FinanceFormController ctrl;


	public NewFinanceDialog(AssistentDialogFrame dialogFrame, boolean isSale) {
		super(dialogFrame);
		OrderDialogHelper dialogHelper = isSale ? new SaleOrderDialogHelper() : new RentOrderDialogHelper();
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Finanzen");
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(dialogHelper.getFinanceOrderFormFxmlFilePath());
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
			return true;
		};
	}


	@Override
	protected void valuesToForm(Order order) {
		ctrl.valuesToForm(order);
	}

	@Override
	protected void valuesFromForm(Order order) {
		ctrl.valuesFromForm(order);
	}

}
