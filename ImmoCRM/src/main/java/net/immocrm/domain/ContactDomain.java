package net.immocrm.domain;

import net.immocrm.db.ContactEntity;
import net.immocrm.db.EntityTool;
import net.immocrm.domain.valid.ContactValidator;
import net.immocrm.domain.valid.ValidationIssues;

class ContactDomain extends AbstractDomain implements BaseDomain, Contact {

	private ContactEntity entity;


	public ContactDomain(ContactEntity entity) {
		this.entity = entity != null ? entity : new ContactEntity();
	}


	@Override
	public String getCellNumber() {
		return entity.getCellNumber();
	}

	@Override
	public void setCellNumber(String cellNumber) {
		entity.setCellNumber(cellNumber);
	}

	@Override
	public String getTelNumber() {
		return entity.getTelNumber();
	}

	@Override
	public void setTelNumber(String telNumber) {
		entity.setTelNumber(telNumber);
	}

	@Override
	public String getFaxNumber() {
		return entity.getFaxNumber();
	}

	@Override
	public void setFaxNumber(String faxNumber) {
		entity.setFaxNumber(faxNumber);
	}

	@Override
	public String getEmailAddress() {
		return entity.getEmailAddress();
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		entity.setEmailAddress(emailAddress);
	}

	@Override
	public ContactEntity getEntity() {
		return entity;
	}

	@Override
	public String getDomainName() {
		return "";
	}

	@Override
	public boolean isEmpty() {
		return EntityTool.isNull(getCellNumber(), getEmailAddress(), getFaxNumber(), getTelNumber());
	}


	@Override
	public ValidationIssues validate() {
		return new ContactValidator().validate(this);
	}


	@Override
	public String toString() {
		if (isEmpty()) {
			return "";
		}
		return getEmailAddress() + ", " + getTelNumber() + " " + getCellNumber();
	}

}
