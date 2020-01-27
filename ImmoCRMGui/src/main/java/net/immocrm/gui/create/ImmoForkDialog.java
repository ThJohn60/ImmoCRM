package net.immocrm.gui.create;

import javafx.scene.control.ButtonBar.ButtonData;
import net.immocrm.domain.Immobilie;

public class ImmoForkDialog extends AbstractCreationDialog<Immobilie> {

	private final ImmoForkController ctrl;


	public ImmoForkDialog(AssistentDialogFrame dialogFrame, ImmoInputMode inputMode) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Auswahl");
		dialogFrame.setContentPane("create/ImmobilieFork.fxml");
		ctrl = dialogFrame.getController();
		ctrl.setInputMode(inputMode);
	}

	public ImmoInputMode getInputMode() {
		return ctrl.getInputMode();
	}

}
