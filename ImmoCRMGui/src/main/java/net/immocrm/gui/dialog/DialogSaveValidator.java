package net.immocrm.gui.dialog;

import javafx.scene.control.DialogEvent;

@FunctionalInterface
public interface DialogSaveValidator {

	boolean isClosingOk(DialogEvent event);
	
}
