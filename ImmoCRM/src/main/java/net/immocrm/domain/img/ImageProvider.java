package net.immocrm.domain.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageProvider {

	private ImageDirectory imageDir;

	public ImageProvider() {
		super();
	}

	public ImageProvider(ImageDirectory imageDir) {
		this.imageDir = imageDir;
	}

	public ImageDirectory getImageDir() {
		return imageDir;
	}

	public void setImageDir(ImageDirectory imageDir) {
		this.imageDir = imageDir;
	}


	public List<File> getFiles() {
		File[] files = fetchJpgFiles();
		if (files.length > 0) {
			return new ArrayList<>(Arrays.asList(files));
		}
		return new ArrayList<>();
	}


	public File[] fetchJpgFiles() {
		if (imageDir == null) {
			return new File[0];
		}
		File[] files = imageDir.getDirectory().listFiles((dir, name) -> {
			return name.toLowerCase().endsWith(ImageFileName.ACCEPTED_EXTENSION);
		});
		return files;
	}


	public void saveImages(List<File> files) throws FileManagementException {
		int fileIndex = ImageFileName.getHighestImageNumber(fetchJpgFiles());
		for (File file : files) {
			BufferedImage image = readImage(file);
			writeImage(image, ImageFileName.getFileName(++fileIndex));
		}
	}

	private BufferedImage readImage(File source) throws FileManagementException {
		try {
			return ImageIO.read(source);
		} catch (IOException e) {
			throw new FileManagementException("Problem beim Lesen der Datei " + source.getAbsolutePath() + ": " + e.getMessage(), e);
		}
	}

	private void writeImage(BufferedImage image, String fileName) throws FileManagementException {
		File target = new File(imageDir.getDirectory(), fileName);
		try {
			ImageIO.write(image, ImageFileName.ACCEPTED_EXTENSION, target);
		} catch (IOException e) {
			throw new FileManagementException("Problem beim Schreiben der Datei " + target.getAbsolutePath() + ": " + e.getMessage(), e);
		}
	}


	public void deleteImage(int index) {
		File[] files = fetchJpgFiles();
		if (files.length > 0) {
			files[index].delete();
			if (files.length == 1) {
				imageDir.deleteDirectory();
			}
		}
	}

	public void deleteAll() {
		File[] files = fetchJpgFiles();
		if (files.length > 0) {
			for (File file : files) {
				file.delete();
			}
			imageDir.deleteDirectory();
		}
	}

}
