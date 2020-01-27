package net.immocrm.gui.create;

import net.immocrm.domain.Constants;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.OrderManager;
import net.immocrm.domain.Person;
import net.immocrm.domain.ref.OrderTypeEnum;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.order.NewFinanceDialog;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.select.ImmobilienSelectionDialog;
import net.immocrm.gui.select.PersonSelectionDialog;

public class NewOrderManager {

	private final AssistentDialogFrame dlgFrame;
	private final ImagesDialog imageDlg;
	private final StepHint stepHint;

	private OrderInputMode inputMode;
	private Person customer;
	private Immobilie immobilie;


	public NewOrderManager() {
		dlgFrame = new AssistentDialogFrame(("create/DialogFrameOrder.fxml"));
		imageDlg = new ImagesDialog(new AssistentDialogFrame("create/DialogFrameOrder.fxml"));		// workaround
		stepHint = new StepHint(dlgFrame, NewOrderSteps.values());
	}

	public void setOwner(Person p) {
		this.customer = p;
	}

	public void setImmobilie(Immobilie immobilie) {
		this.immobilie = immobilie;
	}


	public Order newOrderAssistent(OrderTypeEnum type) {
		Order order = DomainFactory.newOrder();
		order.setOrderType(type);
		if (customer != null) {
			order.setCustomer(customer);
		}
		if (immobilie != null) {
			order.setImmobilie(immobilie);
			customer = immobilie.getOwner();
			order.setCustomer(customer);
		}
		NewOrderSteps step = NewOrderSteps.firstStep(type);

		while (true) {
			dlgFrame.setTitle(order);
			stepHint.setHint(step);
			DialogResponse response = showDialog(order, step);
			if (response.isForeward()) {
				if (step == NewOrderSteps.OrderType && customer != null) {
					step = NewOrderSteps.Owner;
				} else {
					step = step.nextStep(inputMode);
				}
			} else if (response.isBackward()) {
				if (step == NewOrderSteps.Owner && customer != null) {
					step = NewOrderSteps.OrderType;
				} else {
					step = step.previousStep(inputMode);
				}
			} else {
				if (response.isOkDone()) {
					try {
						saveOrder(order);
					} catch (Exception e) {
						imageDlg.deleteAll();
						AlertProvider.alertException(e);
						if (Constants.DEBUG_MODE) {
							e.printStackTrace();
						}
						continue;
					}
				} else if (response.isCanceled()) {
					imageDlg.deleteAll();
				}
				return order;
			}
		}
	}

	private DialogResponse showDialog(Order order, NewOrderSteps step) {
		switch (step) {
		case OrderType:
			DialogResponse mode = new OrderTypeDialog(dlgFrame).showAndWait(order);
			return mode;
		case OrderFork:
			OrderForkDialog forkDialog = new OrderForkDialog(dlgFrame, inputMode);
			DialogResponse reponse = forkDialog.showAndWait(order);
			inputMode = forkDialog.getInputMode();
			showSelectionDialog(order);
			return reponse;
		case ImmobilieType:
			return new ImmobilieCategoryDialog(dlgFrame, true).showAndWait(order.getImmobilie());
		case Lage:
			return new ImmobilieFormDialog(dlgFrame).showAndWait(order.getImmobilie());
		case Eigenschaften:
			return new EigenschaftenDialog(dlgFrame).showAndWait(order.getImmobilie());
		case Ausstattung:
			return new AusstattungDialog(dlgFrame).showAndWait(order.getImmobilie());
		case Images:
			imageDlg.setTitle(order);
			imageDlg.setHint(step);
			return imageDlg.showAndWait(order.getImmobilie());
		case Owner:
			OwnerDialog ownerDialog = new OwnerDialog(dlgFrame);
			DialogResponse response = ownerDialog.showAndWait(order.getCustomer());
			order.getImmobilie().setOwner(order.getCustomer());
			return response;
		case OrderDetails:
			NewOrderDetailDialog dlg = new NewOrderDetailDialog(dlgFrame);
			return dlg.showAndWait(order);
		case Finance:
			NewFinanceDialog financeDlg = new NewFinanceDialog(dlgFrame, order.isSaleOrder());
			return financeDlg.showAndWait(order);
		case Finish:
			return new OrderFinishedDialog(dlgFrame).showAndWait(order);
		default:
			return null;		// can't happen
		}
	}

	private void showSelectionDialog(Order order) {
		if (inputMode == OrderInputMode.WithoutCustomer) {
			PersonSelectionDialog dialog = new PersonSelectionDialog("Auftraggeber");
			Person c = dialog.showDialog();
			order.setCustomer(c);
		} else if (inputMode == OrderInputMode.WithoutCustomerAndImmobile) {
			ImmobilienSelectionDialog dialog = new ImmobilienSelectionDialog();
			Immobilie i = dialog.showDialog();
			order.setCustomer(i.getOwner());
			order.setImmobilie(i);
		}
	}

	private void saveOrder(Order order) {
		new OrderManager().save(order);
		MainController.getRefreshDispatcher().refreshOrderViews(order, RefreshType.insertItem);
		MainController.instance().showOrderDetails(order);
	}

}
