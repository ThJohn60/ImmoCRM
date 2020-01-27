package net.immocrm.domain.img;

import java.io.File;

public class ImageFileName {

	public static final String ACCEPTED_EXTENSION = "jpg";

	private static final String PREFIX = "img_";
	private static final String FILE_NAME_PATTERN = PREFIX + "%d." + ACCEPTED_EXTENSION;


	public static int getHighestImageNumber(File[] files) {
		int result = 0;
		for (File file : files) {
			int number = ImageFileName.getImageNumberOf(file);
			if (number > result) {
				result = number;
			}
		}
		return result;
	}

	private static int getImageNumberOf(File file) {
		String nameWithoutPrefix = file.getName().substring(PREFIX.length());
		String number = nameWithoutPrefix.substring(0, nameWithoutPrefix.length() - 4);
		return Integer.parseInt(number);
	}

	public static String getFileName(int index) {
		String fileName = String.format(FILE_NAME_PATTERN, index);
		return fileName;
	}

}
