package net.immocrm.gui.help;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.immocrm.domain.Constants;

public class AboutController implements Initializable {

	@FXML TextFlow aboutPane;
	@FXML TextFlow headerPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headerPane.getChildren().add(header());

		aboutPane.getChildren().add(description());
		aboutPane.getChildren().add(version());
		aboutPane.getChildren().add(author());
		aboutPane.getChildren().add(iconCredit());
	}

	//
	private Text description() {
		Text text = new Text("Die intelligente Kundenverwaltung für Immobilienmakler\n\n");
		text.setFill(Color.DARKBLUE);
		text.setFont(Font.font("Tahoma", FontPosture.ITALIC, 16));
		return text;
	}

	private Text author() {
		return createText("\n\nAutor: Thilo John");
	}

	private Text version() {
		return createText("Version " + Constants.VERSION);
	}

	private Text iconCredit() {
		return createText("\n\nIcons made by itim2101 from www.flaticon.com");
	}

	private Text createText(String str) {
		Text text = new Text(str);
		text.setFill(Color.DARKBLUE);
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
		return text;
	}

	private Text header() {
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-135.0);

		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(5.0);

		Text text = new Text();
		text.setText(Constants.APPLICATION_NAME);
		text.setFill(Color.DARKBLUE);
		text.setFont(Font.font(null, FontWeight.BOLD, 36));
		text.setX(10.0);
		text.setY(10.0);
		text.setTextOrigin(VPos.TOP);

		text.setEffect(lighting);
		return text;
	}

}
