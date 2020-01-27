package net.immocrm.gui.create;

import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieManager;
import net.immocrm.domain.Person;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogResponse;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.select.PersonSelectionDialog;

public class NewImmobilieManager {

	private final AssistentDialogFrame dlgFrame;
	private final ImagesDialog imageDlg;
	private final StepHint stepHint;
	private ImmoInputMode inputMode;
	private Person owner;


	public NewImmobilieManager() {
		dlgFrame = new AssistentDialogFrame("create/DialogFrameImmobilie.fxml");
		imageDlg = new ImagesDialog(new AssistentDialogFrame("create/DialogFrameImmobilie.fxml"));		// workaround
		stepHint = new StepHint(dlgFrame, NewImmobilieSteps.values());
	}

	public void setCustomer(Person owner) {
		this.owner = owner;
	}


	public Immobilie newImmobilieAssistent() {
		Immobilie immobilie = DomainFactory.newImmobilie();
		if (owner != null) {
			immobilie.setOwner(owner);
		}
		NewImmobilieSteps step = NewImmobilieSteps.Type;
		while (true) {
			dlgFrame.setTitle(immobilie);
			stepHint.setHint(step);
			try {
				DialogResponse response = showDialog(immobilie, step);
				if (response.isForeward()) {
					if (step == NewImmobilieSteps.Type  &&  owner != null) {
						step = NewImmobilieSteps.Details;
					} else {
						step  = step.nextStep(inputMode);
					}
				} else if (response.isBackward()) {
					if (step == NewImmobilieSteps.Details  &&  owner != null) {
						step = NewImmobilieSteps.Type;
					} else {
						step = step.previousStep(inputMode);
					}
				} else {
					if (response.isOkDone()) {
						boolean newOwner = immobilie.getOwner().isNew();
						new ImmobilieManager().save(immobilie);
						if (newOwner) {
							MainController.getRefreshDispatcher().refreshPersonViews(immobilie.getOwner(), RefreshType.insertItem);
						}
						MainController.getRefreshDispatcher().refreshImmobilieViews(immobilie, RefreshType.insertItem);
						MainController.instance().showImmobileDetails(immobilie);
					} else if (response.isCanceled()) {
						imageDlg.deleteAll();
					}
					return immobilie;
				}
			} catch (Exception e) {
				e.printStackTrace();
				AlertProvider.alertException(e);
				imageDlg.deleteAll();
				return null;
			}
		}
	}

	private DialogResponse showDialog(Immobilie immobilie, NewImmobilieSteps step) {
		switch (step) {
		case Type:
			return new ImmobilieCategoryDialog(dlgFrame, false).showAndWait(immobilie);
		case Fork:
			ImmoForkDialog forkDialog = new ImmoForkDialog(dlgFrame, inputMode);
			DialogResponse reponse = forkDialog.showAndWait(immobilie);
			inputMode = forkDialog.getInputMode();
			showSelectionDialog(immobilie);
			return reponse;
		case Details:
			return new DetailDialog(dlgFrame).showAndWait(immobilie);
		case Eigenschaften:
			return new EigenschaftenDialog(dlgFrame).showAndWait(immobilie);
		case Ausstattung:
			return new AusstattungDialog(dlgFrame).showAndWait(immobilie);
		case Images:
			imageDlg.setTitle(immobilie);
			imageDlg.setHint(step);
			return imageDlg.showAndWait(immobilie);
		case Owner:
			OwnerDialog ownerDialog = new OwnerDialog(dlgFrame);
			return ownerDialog.showAndWait(immobilie.getOwner());
		case Finish:
			return new ImmobilieFinishedDialog(dlgFrame).showAndWait(immobilie);
		default:
			return null;		// can't happen
		}
	}

	private void showSelectionDialog(Immobilie immobilie) {
		if (inputMode == ImmoInputMode.WithoutCustomer) {
			PersonSelectionDialog dialog = new PersonSelectionDialog("Auftraggeber");
			Person customer = dialog.showDialog();
			immobilie.setOwner(customer);
		}
	}

}
