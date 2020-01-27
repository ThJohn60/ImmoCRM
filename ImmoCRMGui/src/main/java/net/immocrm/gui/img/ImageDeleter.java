package net.immocrm.gui.img;

import java.io.File;
import java.util.List;

import net.immocrm.domain.img.ImageProvider;

public class ImageDeleter {

	private final ImageViewer imageViewer;
	private final ImageProvider imageProvider;


	public ImageDeleter(ImageViewer imageViewer, ImageProvider imageProvider) {
		this.imageViewer = imageViewer;
		this.imageProvider = imageProvider;
	}


	public void deleteImage() {
		if (imageViewer.getPageCount() > 0) {
			int currentPageIndex = imageViewer.getCurrentPageIndex();
			imageProvider.deleteImage(currentPageIndex);
			List<File> imageFiles = imageProvider.getFiles();
			imageViewer.showImages(imageFiles);
			if (!imageFiles.isEmpty()) {
				if (currentPageIndex >= imageFiles.size()) {
					currentPageIndex--;
				}
				imageViewer.setCurrentPageIndex(currentPageIndex);
			}
		}
	}

}
