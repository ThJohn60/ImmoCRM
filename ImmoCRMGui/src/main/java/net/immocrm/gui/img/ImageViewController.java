package net.immocrm.gui.img;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import net.immocrm.domain.img.ImageDirectory;
import net.immocrm.domain.img.ImageProvider;

public class ImageViewController implements Initializable {

	@FXML Pane imageArea;

	private ImageImporter imageImporter;
	private ImageViewer imageViewer;
	private ImageProvider imageProvider;

	private boolean	visible;


	public ImageViewController(Pane imageArea) {
		this.imageArea = imageArea;
		init();
	}


	public ImageViewController() {
		super();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	private void init() {
		imageProvider = new ImageProvider();
		imageViewer = new ImageViewer(imageArea, imageProvider);
		imageImporter = new ImageImporter(imageProvider);
		DragAndDropEventHandler dadEventHandler = new DragAndDropEventHandler(imageImporter, imageViewer);
		dadEventHandler.setDropTarget(imageArea);
	}


	public void showImages(ImageDirectory iDir) {
		imageProvider.setImageDir(iDir);
		if (!visible) {
			imageViewer.showImages();
			visible = true;
		}
	}

	public void deleteAll() {
		imageProvider.deleteAll();
	}


	@FXML
	public void importImage() {
		if (imageImporter.importImages()) {
			imageViewer.showImages();
		}
	}

	@FXML
	public void deleteImage() {
		ImageDeleter imageDeleter = new ImageDeleter(imageViewer, imageProvider);
		imageDeleter.deleteImage();
	}

}
