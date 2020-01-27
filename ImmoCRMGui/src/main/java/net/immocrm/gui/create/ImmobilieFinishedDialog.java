package net.immocrm.gui.create;

import javafx.scene.control.ButtonBar.ButtonData;
import net.immocrm.domain.Immobilie;

public class ImmobilieFinishedDialog extends AbstractCreationDialog<Immobilie> {

	public ImmobilieFinishedDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.setHeader("Abschluß");
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.FINISH, ButtonData.CANCEL_CLOSE);
		dialogFrame.setContentPane("create/ImmobilieFinished.fxml");
	}


	@Override
	protected void valuesToForm(Immobilie immobilie) {
		ImmobilieFinishedController dlgCtrl = dlgFrame.getController();
		dlgCtrl.valuesToForm(immobilie);
	}

}
