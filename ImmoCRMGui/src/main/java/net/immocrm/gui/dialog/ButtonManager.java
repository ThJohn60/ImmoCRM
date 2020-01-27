package net.immocrm.gui.dialog;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.immocrm.gui.IconProvider;

public class ButtonManager {

	public static boolean isOkButton(ButtonType buttonType) {
		return buttonType.getButtonData() == ButtonData.OK_DONE
				|| buttonType.getButtonData() == ButtonData.NEXT_FORWARD
				|| buttonType.getButtonData() == ButtonData.FINISH;
	}

	public static boolean isCancelButton(ButtonType buttonType) {
		return buttonType.getButtonData() == ButtonData.CANCEL_CLOSE;
	}


	private final DialogPane pane;
	private Button okButton;
	private Button cancelButton;


	public ButtonManager(DialogPane pane) {
		this.pane = pane;
	}


	public Button getOkButton() {
		return okButton;
	}

	public void addButtons(ButtonData... buttonDatas) {
		pane.getButtonTypes().clear();
		for (ButtonData bt : buttonDatas) {
			if (bt == ButtonData.FINISH) {
				addFinishButton();
			} else if (bt == ButtonData.CANCEL_CLOSE) {
				addCancelButton();
			} else if (bt == ButtonData.NEXT_FORWARD) {
				addForwardButton();
			} else if (bt == ButtonData.BACK_PREVIOUS) {
				addBackwardButton();
			}
		}
	}

	public void addYesButton() {
		Button button = addButton("Ja", ButtonData.YES);
		button.setGraphic(okButtonIcon());
	}

	public void addNoButton() {
		Button button = addButton("Nein", ButtonData.CANCEL_CLOSE);
		button.setGraphic(IconProvider.clearButtonIcon());
	}

	public void addOkButton() {
		okButton = addButton("Ok", ButtonData.OK_DONE);
		okButton.setGraphic(okButtonIcon());
	}

	public void addFinishButton() {
		Button button = addButton("Abschließen", ButtonData.FINISH);
		button.setGraphic(okButtonIcon());
	}

	public void addCancelButton() {
		cancelButton = addButton("Abbrechen", ButtonData.CANCEL_CLOSE);
		cancelButton.setGraphic(IconProvider.clearButtonIcon());
	}

	public void addForwardButton() {
		Button button = addButton("Weiter", ButtonData.NEXT_FORWARD);
		button.setGraphic(forwardButtonIcon());
	}

	public void addBackwardButton() {
		Button button = addButton("Zurück", ButtonData.BACK_PREVIOUS);
		button.setGraphic(backwardButtonIcon());
	}

	public void addCloseButton() {
		Button button = addButton("Schließen", ButtonData.FINISH);
		button.setGraphic(okButtonIcon());
	}

	private Button addButton(String text, ButtonData bd) {
		pane.getButtonTypes().add(new ButtonType(text, bd));
		return getButton(bd);
	}

	public Button getButton(ButtonData buttonData) {
		for (ButtonType bt : pane.getButtonTypes()) {
			if (bt.getButtonData() == buttonData) {
				return (Button)pane.lookupButton(bt);
			}
		}
		return null;
	}

	public Button getDefaultButton() {
		for (ButtonType bt : pane.getButtonTypes()) {
			Button b = (Button)pane.lookupButton(bt);
			if (b.isDefaultButton()) {
				return b;
			}
		}
		return null;
	}

	public ImageView forwardButtonIcon() {
		return loadImage("/rsc/icon/ic_arrow_forward_18pt.png");
	}

	public ImageView backwardButtonIcon() {
		return loadImage("/rsc/icon/ic_arrow_back_18pt.png");
	}

	public ImageView okButtonIcon() {
		return loadImage("/rsc/icon/ic_done_18pt.png");
	}

	private ImageView loadImage(String imgFile) {
		return new ImageView(new Image(IconProvider.class.getResourceAsStream(imgFile)));
	}


}
