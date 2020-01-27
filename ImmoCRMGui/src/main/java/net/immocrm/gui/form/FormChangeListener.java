package net.immocrm.gui.form;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

class FormChangeListener {

	private final ChangeListener<Object> changeListener;
	private boolean fieldChanged;


	FormChangeListener() {
		this.changeListener = changeListener();
	}

	private <T> ChangeListener<T> changeListener() {
		return (observable, oldValue, newValue) -> {
				if (newValue != null && !newValue.equals(oldValue)) {
					fieldChanged = true;
				}
		};
	}

	boolean isAnyFieldChanged() {
		return fieldChanged;
	}


	void addChangeListenerToProperties(Node node) {
		if (node instanceof Pane) {
			addChangeListenerToProperties(((Pane)node).getChildren());
		} else if  (node instanceof Group) {
			addChangeListenerToProperties(((Group)node).getChildren());
		} else if  (node instanceof TextInputControl) {
			addChangeListener(((TextInputControl)node).textProperty());
		} else if  (node instanceof ComboBox<?>) {
			addChangeListener(((ComboBox<?>)node).selectionModelProperty());
		} else if  (node instanceof CheckBox) {
			addChangeListener(((CheckBox)node).onActionProperty());
		} else if  (node instanceof RadioButton) {
			addChangeListener(((RadioButton)node).onActionProperty());
		} else if  (node instanceof Pane) {
			addChangeListenerToProperties(node);
		} else if  (node instanceof TabPane) {
			ObservableList<Tab> tabs = ((TabPane)node).getTabs();
			for (Tab tab : tabs) {
				addChangeListenerToProperties(tab.getContent());
			}
		} else if  (node instanceof BorderPane) {
			addBorderPaneChildren((BorderPane)node);
		}
	}

	private void addBorderPaneChildren(BorderPane borderPane) {
		addChangeListenerToProperties(borderPane.getCenter());
		addChangeListenerToProperties(borderPane.getTop());
		addChangeListenerToProperties(borderPane.getLeft());
		addChangeListenerToProperties(borderPane.getBottom());
		addChangeListenerToProperties(borderPane.getRight());
	}

	private void addChangeListenerToProperties(ObservableList<Node> l) {
		for (Node node : l) {
			addChangeListenerToProperties(node);
		}
	}

	private void addChangeListener(StringProperty prop) {
		prop.addListener(changeListener);
	}

	private void addChangeListener(ObjectProperty<?> prop) {
		prop.addListener(changeListener);
	}

}
