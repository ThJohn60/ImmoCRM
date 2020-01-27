package net.immocrm.domain;

import java.io.File;

import net.immocrm.domain.img.DocFile;
import net.immocrm.domain.img.DocumentDirectory;

public interface Document extends BaseDomain {

	Immobilie getImmobilie();
	void setImmobilie(Immobilie immobilie);

	DocumentDirectory getDocDir();
	DocFile getDocFile();
	File getFile();

	String getFileName();
	void setFileName(String filenName);

	String getDocName();
	void setDocName(String docName);

	String getDescription();
	void setDescription(String description);

}