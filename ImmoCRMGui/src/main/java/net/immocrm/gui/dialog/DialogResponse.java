package net.immocrm.gui.dialog;

import javafx.scene.control.ButtonBar.ButtonData;

public class DialogResponse {

	public static final DialogResponse CANCEL_RESPONSE = new DialogResponse(ButtonData.CANCEL_CLOSE);

	private final ButtonData buttonData;


	public DialogResponse(ButtonData buttonData) {
		this.buttonData = buttonData;
	}


	public boolean isOkDone() {
		return buttonData == ButtonData.OK_DONE || buttonData == ButtonData.FINISH;
	}

	public boolean isCanceled() {
		return buttonData == ButtonData.CANCEL_CLOSE;
	}

	public boolean isForeward() {
		return buttonData == ButtonData.NEXT_FORWARD;
	}

	public boolean isBackward() {
		return buttonData == ButtonData.BACK_PREVIOUS;
	}

	@Override
	public String toString() {
		return "Resp[" + buttonData + "]";
	}

}
