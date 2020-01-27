package net.immocrm.gui.img;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import net.immocrm.domain.img.ImageDirectory;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.Main;

public class ImageTabController implements Initializable {

	@FXML Tab imageTab;

	private ImageViewController imageCtrl;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("img/Images.fxml"));
		Pane pane = BaseDialogBuilder.loadPane(loader);
		imageTab.setContent(pane);
		imageCtrl = loader.getController();
	}

	public void showImages(ImageDirectory imageDir) {
		imageCtrl.showImages(imageDir);
	}

}
