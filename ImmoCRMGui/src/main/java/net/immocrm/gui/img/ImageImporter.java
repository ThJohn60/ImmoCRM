package net.immocrm.gui.img;

import java.io.File;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.immocrm.domain.img.ImageFileName;
import net.immocrm.domain.img.FileManagementException;
import net.immocrm.domain.img.ImageProvider;
import net.immocrm.gui.Main;
import net.immocrm.gui.alert.AlertProvider;

public class ImageImporter {

	private final ImageProvider imageProvider;


	public ImageImporter(ImageProvider imageProvider) {
		this.imageProvider = imageProvider;
	}


	public boolean importImages() {
		List<File> files = getChosenFiles();
        if (files != null) {
        	importFiles(files);
        	return true;
        }
        return false;
	}

	private List<File> getChosenFiles() {
		FileChooser chooser = new FileChooser();
		ExtensionFilter filter = new FileChooser.ExtensionFilter("Bilder", "*." + ImageFileName.ACCEPTED_EXTENSION);
		chooser.getExtensionFilters().add(filter);
		List<File> result = chooser.showOpenMultipleDialog(Main.getApplicationWindow());
		return result;
	}

	void importFiles(List<File> files) {
		new Runnable() {

			@Override
			public void run() {
				try {
					imageProvider.saveImages(files);
				} catch (FileManagementException e) {
					AlertProvider.alertError(e.getMessage());
				}
			}
		}.run();
	}

}
