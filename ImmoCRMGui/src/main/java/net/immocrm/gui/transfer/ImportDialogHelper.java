package net.immocrm.gui.transfer;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ImportDialogHelper {

	public void setImportantNote(ObservableList<Node> textFlow) {
		textFlow.add(buildText("Wichtig:", FontWeight.BOLD));
		textFlow.add(buildText(" Die CSV-Datei muss in Excel (oder LibreOffice etc.) im ", FontWeight.NORMAL));
		textFlow.add(buildText("UTF-8", FontWeight.BOLD));
		textFlow.add(buildText("-Zeichensatz und mit ", FontWeight.NORMAL));
		textFlow.add(buildText("Semikolon", FontWeight.BOLD));
		textFlow.add(buildText(" als Trennzeichen abgespeichert werden.", FontWeight.NORMAL));
	}

	private static Text buildText(String t, FontWeight fontWeight) {
		Text text = new Text(t);
		text.setFont(Font.font("System", fontWeight, 12));
		return text;
	}

}
