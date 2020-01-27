package net.immocrm.gui.alert;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.dialog.BaseDialog;
import net.immocrm.gui.dialog.ButtonManager;

public class AlertDialogBuilder {

	private final Dialog<ButtonType> dialog;
	private final ButtonManager buttonManager;
	private final boolean withLabel;


	public AlertDialogBuilder(boolean withLabel) {
		this.withLabel = withLabel;
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader(withLabel ? "alert/AlertDialogWithLabel.fxml" : "alert/AlertDialogWithTextFlow.fxml");
		DialogPane pane = (DialogPane)BaseDialogBuilder.loadPane(fxmlLoader);
		dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(pane);
		buttonManager = new ButtonManager(pane);
	}

	public Dialog<ButtonType> build() {
		return dialog;
	}

	public AlertDialogBuilder withYesNoButton() {
		buttonManager.addYesButton();
		buttonManager.addNoButton();
		return this;
	}

	public AlertDialogBuilder withOkButton() {
		buttonManager.addOkButton();
		return this;
	}

	public AlertDialogBuilder withTitle(String title) {
		dialog.setTitle(title);
		return this;
	}

	public AlertDialogBuilder withHeader(String header) {
		((Label)dialog.getDialogPane().lookup(BaseDialog.HEADER_LABEL)).setText(header);
		return this;
	}

	public AlertDialogBuilder withMessage(String msg) {
		if (withLabel) {
			((Label)dialog.getDialogPane().lookup("#msg")).setText(msg);
		} else {
			TextFlow textFlow = (TextFlow)dialog.getDialogPane().lookup("#msg");
			textFlow.getChildren().add(new Text(msg));
		}
		return this;
	}

	public AlertDialogBuilder withMessage(Text... textList) {
		if (withLabel) {
			throw new IllegalArgumentException("Text not allowed");
		}
		TextFlow textFlow = (TextFlow)dialog.getDialogPane().lookup("#msg");
		for (Text t : textList) {
			textFlow.getChildren().add(t);
		}
		return this;
	}

	public AlertDialogBuilder withDetailMessage(String msg) {
		Label detailLabel = new Label(msg);
		detailLabel.setText(msg);
		dialog.getDialogPane().setExpandableContent(detailLabel);
		return this;
	}

	public AlertDialogBuilder withIcon(ImageView icon) {
		VBox pane = (VBox)dialog.getDialogPane().lookup("#graphicPane");
		pane.getChildren().add(icon);
		return this;
	}

}
