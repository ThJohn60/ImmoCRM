package net.immocrm.domain.img;

import java.io.File;

public abstract class FileManager {

	private final String pathName;
	private final boolean temporalPath;


	public FileManager(String pathName, boolean isNew) {
		this.pathName = pathName;
		temporalPath = isNew;
	}


	public boolean isTemporalPath() {
		return temporalPath;
	}

	public File getDirectory() {
		File dir = new File(pathName);
		if (!dir.exists()) {
			dir.mkdir();
		}
		return dir;
	}

	public void deleteImagesAndDirectory() {
		File dir = new File(pathName);
		if (dir.exists()) {
			for (File file : dir.listFiles()) {
				file.delete();
			}
			dir.delete();
		}
	}

	public boolean isDirectoryEmpty() {
		File dir = new File(pathName);
		if (dir.exists()) {
			String[] list = dir.list();
			if (list.length > 0) {
				return false;
			}
		}
		return true;
	}

	public void deleteDirectory() {
		File dir = new File(pathName);
		if (dir.exists()) {
			dir.delete();
		}
	}

	public void renameTo(FileManager directory) {
		File oldDir = new File(pathName);
		if (oldDir.exists()) {
			File newDir = directory.getDirectory();
			for (File file : oldDir.listFiles()) {
				file.renameTo(new File(newDir, file.getName()));
			}
			oldDir.delete();
		}
	}

	@Override
	public String toString() {
		return pathName;
	}

}
