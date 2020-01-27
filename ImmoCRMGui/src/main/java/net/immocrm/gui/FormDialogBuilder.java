package net.immocrm.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.immocrm.domain.BaseDomain;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.dialog.BaseDialog;
import net.immocrm.gui.dialog.ButtonManager;
import net.immocrm.gui.dialog.DialogCancelChecker;
import net.immocrm.gui.dialog.DialogSaveValidator;
import net.immocrm.gui.form.FormDialog;

public class FormDialogBuilder {

	private final DialogPane pane;
	private final Dialog<ButtonType> dialog;
	private final ButtonManager buttonManager;
	private final FormDialog formDialog;
	private final FXMLLoader fxmlLoader;


	public FormDialogBuilder(String fxmlFileName) {
		fxmlLoader = BaseDialogBuilder.getFxmlLoader(fxmlFileName);
		pane = (DialogPane)BaseDialogBuilder.loadPane(fxmlLoader);
		dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(pane);
		formDialog = new FormDialog(dialog);
		pane.setUserData(formDialog);
		buttonManager = new ButtonManager(pane);
	}


	public <T> T getController() {
		return fxmlLoader.getController();
	}

	public DialogPane getPane() {
		return pane;
	}

	public Button getOkButton() {
		return buttonManager.getOkButton();
	}


	public FormDialog build() {
		return formDialog;
	}

	public FormDialogBuilder withOkCancelButtons() {
		return withOkButton()
			  .withCancelButton();
	}

	public FormDialogBuilder withOkButton() {
		buttonManager.addOkButton();
		return this;
	}

	public FormDialogBuilder withCancelButton() {
		buttonManager.addCancelButton();
		return this;
	}

	public FormDialogBuilder withCloseButton() {
		buttonManager.addCloseButton();
		return this;
	}

	public FormDialogBuilder withHeader(String header) {
		((Label)pane.lookup(BaseDialog.HEADER_LABEL)).setText(header);
		return this;
	}

	public FormDialogBuilder withTitle(BaseDomain dom) {
		dialog.setTitle(dom.getDomainName());
		return this;
	}

	public FormDialogBuilder withGraphic(Node node) {
		dialog.setGraphic(node);
		return this;
	}

	public FormDialogBuilder withTitle(String title) {
		dialog.setTitle(title);
		return this;
	}

	public FormDialogBuilder withDialogIcon(Image icon) {
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(icon);
		return this;
	}

	public FormDialogBuilder withFormPane(Pane formPane) {
		pane.setContent(formPane);
		return this;
	}

	public FormDialogBuilder openTab(int tabNr) {
		TabPane tabPane = getTabPane();
		if (tabPane != null) {
			SingleSelectionModel<Tab> selModel = tabPane.getSelectionModel();
			selModel.select(tabNr);
		}
		return this;
	}


	public TabPane getTabPane() {
		TabPane tabPane = null;
		for (Node node : pane.getChildren()) {
			if (node instanceof TabPane) {
				tabPane = (TabPane)node;
			}
		}
		return tabPane;
	}

	public FormDialogBuilder withOnCloseRequestHandler(DialogSaveValidator validator) {
		@SuppressWarnings("unchecked")
		EventHandler<DialogEvent> closingHandler = event -> {
			ButtonType buttonType = ((Dialog<ButtonType>)event.getSource()).getResult();
			if (isOkButton(buttonType) && !validator.isClosingOk(event)) {
				event.consume();
			}
		};
		dialog.setOnCloseRequest(closingHandler);
		return this;
	}

	private boolean isOkButton(ButtonType buttonType) {
		return buttonType.getButtonData() == ButtonData.OK_DONE
				|| buttonType.getButtonData() == ButtonData.NEXT_FORWARD
				|| buttonType.getButtonData() == ButtonData.FINISH;
	}


	public FormDialogBuilder addButtonActionHandler(String selector, EventHandler<ActionEvent> handler) {
		Button button = (Button)pane.lookup(selector);
		if (button != null) {
			setIcon(button);
			button.setOnAction(handler);
		}
		return this;
	}

	public FormDialogBuilder addButtonActionHandler(String selector, EventHandler<ActionEvent> handler, ImageView icon) {
		Button button = (Button)pane.lookup(selector);
		if (button != null) {
			button.setGraphic(icon);
			button.setOnAction(handler);
		}
		return this;
	}

	private void setIcon(Button button) {
		if (button.getId().contains("select")) {
			button.setGraphic(IconProvider.selectButtonIcon());
		} else if (button.getId().contains("new") || button.getId().contains("edit")) {
			button.setGraphic(IconProvider.editButtonIcon());
		}
	}

	public FormDialogBuilder setOnCloseRequestOkHandler(DialogSaveValidator validator) {
		DialogCancelChecker checker = getDialogCancelHandler();
		@SuppressWarnings("unchecked")
		EventHandler<DialogEvent> closingHandler = event -> {
			ButtonType buttonType = ((Dialog<ButtonType>)event.getSource()).getResult();
			if (ButtonManager.isOkButton(buttonType)) {
				if (!validator.isClosingOk(event)) {
					event.consume();
				}
			} else if (ButtonManager.isCancelButton(buttonType)) {
				if (!checker.isCancelingOk(event)) {
					event.consume();
				} else {
					((Button)pane.lookupButton(buttonType)).requestFocus();
				}
			}
		};
		dialog.setOnCloseRequest(closingHandler);
		return this;
	}

	private DialogCancelChecker getDialogCancelHandler() {
		return evt -> {
			if (formDialog.isAnyFieldChanged()) {
				return AlertProvider.confirmCancel();
			}
			return true;
		};
	}

}
