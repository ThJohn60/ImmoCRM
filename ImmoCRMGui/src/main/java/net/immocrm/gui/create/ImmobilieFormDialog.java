package net.immocrm.gui.create;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.vc.IncorrectValueException;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.NoActionException;
import net.immocrm.gui.property.ImmobilieFormController;

public class ImmobilieFormDialog extends AbstractCreationDialog<Immobilie> {

	private final ImmobilieFormController ctrl;


	public ImmobilieFormDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Lage");
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("property/ImmobilieForm.fxml");
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		dialogFrame.setContentPane((Pane)tab.getContent());
		ctrl = fxmlLoader.getController();
	}

	@Override
	protected DialogSaveValidator getValidator() {
		return event -> {
			try {
				Immobilie testImmobilie = DomainFactory.newImmobilie();
				valuesFromForm(testImmobilie);
				if (testImmobilie.getAddress().isEmpty()) {
					AlertProvider.alertInfo("Eingabe ist unvollständig", "Bitte eine Adresse eingeben oder auswählen.");
					return false;
				}
			} catch (NoActionException e) {
				return false;
			} catch (IncorrectValueException e) {
				AlertProvider.alertException(e);
				return false;
			}
			return true;
		};
	}


	@Override
	protected void valuesToForm(Immobilie immobilie) {
		ctrl.valuesToForm(immobilie);
	}

	@Override
	protected void valuesFromForm(Immobilie immobilie) {
		ctrl.valuesFromForm(immobilie);
	}

}
