package net.immocrm.gui.doc;

import net.immocrm.domain.Document;

public class DokuRow  {

	private final Document document;

	public DokuRow(Document document) {
		this.document = document;
	}


	public Document getDocument() {
		return document;
	}


	public String getTitle() {
		return document.getDocName();
	}

	public String getFileName() {
		return document.getFileName();
	}

	public String getDetails() {
		return document.getDescription();
	}

}
