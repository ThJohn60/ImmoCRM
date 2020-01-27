package net.immocrm.domain;

import java.io.File;
import java.sql.Timestamp;

import net.immocrm.db.DocumentEntity;
import net.immocrm.domain.img.DocFile;
import net.immocrm.domain.img.DocumentDirectory;
import net.immocrm.domain.valid.ValidationIssues;

public class DocumentDomain extends AbstractDomain implements BaseDomain, Document {

	private final DocumentEntity entity;

	private ImmobilieDomain immobilie;


	DocumentDomain(DocumentEntity entity) {
		this.entity = entity;
		immobilie = DomainFactory.createDomain(entity.getImmobilie());
		entity.setImmobilie(immobilie.getEntity());
	}


	@Override
	public Immobilie getImmobilie() {
		return immobilie;
	}

	@Override
	public void setImmobilie(Immobilie im) {
		immobilie = (ImmobilieDomain)im;
		entity.setImmobilie(immobilie.getEntity());
	}


	@Override
	public DocumentDirectory getDocDir() {
		return immobilie.getDocDir();
	}

	@Override
	public DocFile getDocFile() {
		return new DocFile(null, getFile());
	}

	@Override
	public File getFile() {
		File target = new File(immobilie.getDocDir().getDirectory(), getFileName());
		return target;
	}

	@Override
	public String getFileName() {
		return entity.getFileName();
	}

	@Override
	public void setFileName(String filenName) {
		entity.setFileName(filenName);
	}

	@Override
	public String getDocName() {
		return entity.getDocName();
	}

	@Override
	public void setDocName(String docName) {
		entity.setDocName(docName);
	}

	@Override
	public String getDescription() {
		return entity.getDescription();
	}

	@Override
	public void setDescription(String description) {
		entity.setDescription(description);
	}

	public Timestamp getCreatedOn() {
		return entity.getCreatedOn();
	}

	public void setCreatedOn(Timestamp createdOn) {
		entity.setCreatedOn(createdOn);
	}

	public void setDefaultValues() {
		entity.setDefaultValues();
	}

	@Override
	public boolean isEmpty() {
		return getFileName() == null || getFileName().isEmpty();
	}

	@Override
	public String getDomainName() {
		return "Dokumente";
	}

	@Override
	public ValidationIssues validate() {
		return null;
	}

	@Override
	DocumentEntity getEntity() {
		return entity;
	}

	@Override
	public String toString() {
		return getDocName();
	}

}
