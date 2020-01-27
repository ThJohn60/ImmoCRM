package net.immocrm.gui.create;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.immocrm.domain.BaseDomain;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.dialog.ButtonManager;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;

public class AssistentDialogFrame extends FormDialog {

	private final ButtonManager buttonManager;
	private FXMLLoader fxmlLoader;


	public AssistentDialogFrame(String fxmlFileName) {
		super(createDialog(fxmlFileName));
		this.buttonManager = new ButtonManager(dialog.getDialogPane());
		dialog.getDialogPane().setUserData(this);
	}

	private static Dialog<ButtonType> createDialog(String fxmlFileName) {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(fxmlFileName);
		DialogPane pane = (DialogPane)BaseDialogBuilder.loadPane(fxmlLoader);
		Dialog<ButtonType> result = new Dialog<ButtonType>();
		result.setDialogPane(pane);
		return result;
	}


	public <T> T getController() {
		return fxmlLoader.getController();
	}

	public void setTitle(BaseDomain d) {
		super.setTitle(TitleProvider.getTitle(d));
	}

	public void setContentPane(String fxmlFileName) {
		fxmlLoader = BaseDialogBuilder.getFxmlLoader(fxmlFileName);
		setContentPane(BaseDialogBuilder.loadPane(fxmlLoader));
	}

	public void setContentPane(Pane p) {
		BorderPane pane = (BorderPane)lookup("#contentPane");
		pane.setCenter(p);
	}

	public void addButtons(ButtonData... buttonDatas) {
		buttonManager.addButtons(buttonDatas);
	}

	public void setOnCloseRequestHandler(DialogSaveValidator validator) {
		@SuppressWarnings("unchecked")
		EventHandler<DialogEvent> closingHandler = event -> {
			ButtonType buttonType = ((Dialog<ButtonType>)event.getSource()).getResult();
			if (ButtonManager.isOkButton(buttonType) && !validator.isClosingOk(event)) {
				event.consume();
			}
		};
		setOnCloseRequest(closingHandler);
	}

}
