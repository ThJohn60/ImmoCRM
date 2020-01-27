package net.immocrm.gui.dialog;

import javafx.scene.control.DialogEvent;

@FunctionalInterface
public interface DialogCancelChecker {

	boolean isCancelingOk(DialogEvent event);
	
}
