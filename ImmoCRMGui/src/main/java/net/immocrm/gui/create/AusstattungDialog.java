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
import net.immocrm.gui.property.AusstattungController;

public class AusstattungDialog extends AbstractCreationDialog<Immobilie> {

	private final AusstattungController ctrl;

	public AusstattungDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Ausstattung");
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("property/Ausstattung.fxml");
		Tab tab = BaseDialogBuilder.load(fxmlLoader);
		dialogFrame.setContentPane((Pane)tab.getContent());
		ctrl = fxmlLoader.getController();
	}

	@Override
	protected DialogSaveValidator getValidator() {
		return event -> {
			try {
				valuesFromForm(DomainFactory.newImmobilie());
			} catch (IncorrectValueException e) {
				AlertProvider.alertError(e.getMessage());
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
