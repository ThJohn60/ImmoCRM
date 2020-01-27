package net.immocrm.domain;

public interface Contact extends BaseDomain {

	String getCellNumber();
	void setCellNumber(String cellNumber);

	String getTelNumber();
	void setTelNumber(String telNumber);

	String getFaxNumber();
	void setFaxNumber(String faxNumber);

	String getEmailAddress();
	void setEmailAddress(String emailAddress);

}