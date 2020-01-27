package net.immocrm.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import net.immocrm.gui.dialog.BaseDialog;
import net.immocrm.gui.dialog.ButtonManager;

public class BaseDialogBuilder {

	private final DialogPane pane;
	private final Dialog<ButtonType> dialog;
	private final ButtonManager buttonManager;


	public BaseDialogBuilder(String fxmlFileName) {
		FXMLLoader fxmlLoader = getFxmlLoader(fxmlFileName);
		pane = (DialogPane)loadPane(fxmlLoader);
		dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(pane);
		buttonManager = new ButtonManager(pane);
	}

	public static FXMLLoader getFxmlLoader(String fxmlFileName) {
		return new FXMLLoader(Main.class.getResource(fxmlFileName));
	}

	public static <T> T load(FXMLLoader fxmlLoader) {
		try {
			return fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Pane loadPane(FXMLLoader fxmlLoader) {
		try {
			return fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public DialogPane getPane() {
		return pane;
	}

	public BaseDialog build() {
		return new BaseDialog(dialog);
	}


	public BaseDialogBuilder withOkCancelButtons() {
		return withOkButton()
			  .withCancelButton();
	}

	public BaseDialogBuilder withOkButton() {
		buttonManager.addOkButton();
		return this;
	}

	public BaseDialogBuilder withCancelButton() {
		buttonManager.addCancelButton();
		return this;
	}

	public BaseDialogBuilder withCloseButton() {
		buttonManager.addCloseButton();
		return this;
	}


	public BaseDialogBuilder withHeader(String header) {
		((Label)pane.lookup(BaseDialog.HEADER_LABEL)).setText(header);
		return this;
	}

	public BaseDialogBuilder withTitle(String title) {
		dialog.setTitle(title);
		return this;
	}

}
