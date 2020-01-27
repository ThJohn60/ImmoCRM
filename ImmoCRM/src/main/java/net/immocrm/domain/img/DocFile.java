package net.immocrm.domain.img;

import java.io.File;

public class DocFile {

	private final File newFile;
	private final File immoFile;


	public DocFile(File newFile, File immoFile) {
		this.newFile = newFile;
		this.immoFile = immoFile;
	}


	public String getNewFileName() {
		return newFile.getName();
	}

	public String getImmoFileName() {
		return immoFile.getName();
	}


	public File getNewFile() {
		return newFile;
	}

	public File getImmoFile() {
		return immoFile;
	}

	public File getFile() {
		return newFile != null ? newFile : immoFile;
	}

	public String getName() {
		return getFile().getName();
	}


	public boolean isNew() {
		return immoFile == null;
	}

	public boolean isFileChanged() {
		return immoFile != null && newFile != null && !immoFile.getName().equals(newFile.getName());
	}

}
