package net.immocrm.gui.create;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Pane;
import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.Immobilie;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.img.ImageViewController;

public class ImagesDialog extends AbstractCreationDialog<Immobilie> {

	private final ImageViewController ctrl;


	public ImagesDialog(AssistentDialogFrame dialogFrame) {
		super(dialogFrame);
		dialogFrame.addButtons(ButtonData.BACK_PREVIOUS, ButtonData.NEXT_FORWARD, ButtonData.CANCEL_CLOSE);
		dialogFrame.setHeader("Bilder hinzufügen");

		try {
			FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("img/Images.fxml");
			dialogFrame.setContentPane((Pane)fxmlLoader.load());
			ctrl = fxmlLoader.getController();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteAll() {
		ctrl.deleteAll();
	}

	public void setTitle(BaseDomain d) {
		dlgFrame.setTitle(d);
	}


	@Override
	protected void valuesToForm(Immobilie immobilie) {
		ctrl.showImages(immobilie.getImageDir());
	}

	public void setHint(NewOrderSteps step) {
		StepHint stepHint = new StepHint(dlgFrame, NewOrderSteps.values());
		stepHint.setHint(step);
	}

	public void setHint(NewImmobilieSteps step) {
		StepHint stepHint = new StepHint(dlgFrame, NewImmobilieSteps.values());
		stepHint.setHint(step);
	}

}
