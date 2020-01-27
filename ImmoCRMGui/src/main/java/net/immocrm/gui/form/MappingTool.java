package net.immocrm.gui.form;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextInputControl;

@Deprecated
@SuppressWarnings("unchecked")
public class MappingTool {

	private final Node root;


	public MappingTool(Node view) {
		this.root = view;
	}


	public String getText(String selector) {
		Node node = getNode(selector);
		if (node instanceof TextInputControl) {
			return ((TextInputControl)node).getText();
		}
		if (node instanceof Label) {
			return ((Label)node).getText();
		}
		if (node instanceof ComboBox) {
			SingleSelectionModel<String> selectionModel = getComboBoxSelectionModel(selector);
			return selectionModel.getSelectedItem();
		}
		return "";
	}

	public void setText(String selector, String text) {
		Node node = getNode(selector);
		if (node instanceof TextInputControl) {
			TextInputControl textInputControl = (TextInputControl)node;
			textInputControl.setText(text);
		} else if (node instanceof Label) {
			Label label = (Label)node;
			label.setText(text);
		} else if (node instanceof ComboBox) {
			SingleSelectionModel<String> selectionModel = getComboBoxSelectionModel(selector);
			selectionModel.select(text);
		} else if (node == null) {
			System.out.println("Unknown node selector: " + selector);
		} else {
			System.out.println("Unknown node type: " + node.getClass());
		}
	}



	private <T> SingleSelectionModel<T> getComboBoxSelectionModel(String selector) {
		ComboBox<T> comboBox = getComboBox(selector);
		return comboBox.getSelectionModel();
	}

	private <T> ComboBox<T> getComboBox(String selector) {
		return (ComboBox<T>)getNode(selector);
	}



	public boolean isRadoButtonSelected(String selector) {
		return getRadioButton(selector).isSelected();
	}

	public void selectRadioButton(String selector) {
		RadioButton radioButton = getRadioButton(selector);
		radioButton.setSelected(true);
	}

	private RadioButton getRadioButton(String selector) {
		return (RadioButton)getNode(selector);
	}



	public boolean isCheckBoxSelected(String selector) {
		return getCheckBox(selector).isSelected();
	}

	public void selectCheckBox(String selector, boolean select) {
		CheckBox checkBox = getCheckBox(selector);
		checkBox.setSelected(select);
	}

	public CheckBox getCheckBox(String selector) {
		return (CheckBox)getNode(selector);
	}

	private Node getNode(String selector) {
		return root.lookup(selector);
	}

}
