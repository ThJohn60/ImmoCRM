package net.immocrm.domain.img;

import net.immocrm.domain.Immobilie;

public class DocumentDirectory extends FileManager {

	private static final String PATH = INIT();

	private static final String INIT() {
		String userDir = System.getProperty("user.dir");
		if (userDir.contains("workspace")) {
			return "../ImmoCRMDerbyDB/doc/";
		}
		return "ImmoCRMDerbyDB/doc/";
	}


	public DocumentDirectory(Immobilie immobilie) {
		super(getPathName(immobilie), immobilie.isNew());
	}

	private static String getPathName(Immobilie immobilie) {
		return PATH + (immobilie.isNew() ? immobilie.toString() : immobilie.getId().toString());
	}

}
