package net.immocrm.gui.create;

import javafx.scene.control.ButtonBar.ButtonData;
import net.immocrm.domain.Immobilie;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogSaveValidator;

public class ImmobilieCategoryDialog extends AbstractCreationDialog<Immobilie> {

	private final ImmobilieCategoryController ctrl;


	public ImmobilieCategoryDialog(AssistentDialogFrame dialogFrame, boolean withPreviousButton) {
		super(dialogFrame);
		if (withPreviousButton) {
			dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		} else {
			dialogFrame.addButtons(ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		}
		dialogFrame.setHeader("Objekttyp wählen");
		dialogFrame.setContentPane("create/ImmobilieCategory.fxml");
		this.ctrl = dialogFrame.getController();
	}

	@Override
	protected DialogSaveValidator getValidator() {
		return event -> {
			if (ctrl.getCategory() == null) {
				AlertProvider.alertInfo("Bitte den Objekttyp auswählen.");
				return false;
			}
			return true;
		};
	}


	@Override
	protected void valuesToForm(Immobilie immobilie) {
		ctrl.setCategory(immobilie.getImmobilieCategory());
	}

	@Override
	protected void valuesFromForm(Immobilie immobilie) {
		immobilie.setImmobilieCategory(ctrl.getCategory());
	}

}
