package net.immocrm.domain.img;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DocProvider {

	private DocumentDirectory docDir;

	public DocProvider(DocumentDirectory docDir) {
		this.docDir = docDir;
	}


	public void copyDoc(File source) throws FileManagementException {
		try {
			File target = new File(docDir.getDirectory(), source.getName());
			Files.copy(Paths.get(source.getAbsolutePath()), Paths.get(target.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileManagementException("Problem beim Kopieren der Datei " + source.getAbsolutePath() + ": " + e.getMessage(), e);
		}
	}

	public void deleteDoc(String fileName) {
		File[] files = fetchAllFiles();
		if (files.length > 0) {
			for (File file : files) {
				if (file.getAbsolutePath().contains(fileName)) {
					file.delete();
				}
			}
			if (files.length == 1) {
				docDir.deleteDirectory();
			}
		}
	}

	public void deleteAll() {
		File[] files = fetchAllFiles();
		if (files.length > 0) {
			for (File file : files) {
				file.delete();
			}
			docDir.deleteDirectory();
		}
	}

	public File[] fetchAllFiles() {
		if (docDir == null) {
			return new File[0];
		}
		File[] files = docDir.getDirectory().listFiles();
		return files;
	}

}
