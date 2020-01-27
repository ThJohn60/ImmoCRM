package net.immocrm.gui.property;

import javafx.fxml.FXML;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.img.ImageProvider;
import net.immocrm.gui.MainController;
import net.immocrm.gui.img.ImageImporter;

public class AddImagesController {

	@FXML
	public void addImages() {
		addImages(MainController.instance().getImmobilie());
	}

	public void addImages(Immobilie immobilie) {
		if (immobilie != null) {
			ImageProvider imageProvider = new ImageProvider(immobilie.getImageDir());
			ImageImporter imageImporter = new ImageImporter(imageProvider);
			imageImporter.importImages();
		}
	}

}
