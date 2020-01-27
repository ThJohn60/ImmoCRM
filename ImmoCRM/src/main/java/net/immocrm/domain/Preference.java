package net.immocrm.domain;

public interface Preference extends BaseDomain {

	String getName();
	void setName(String name);

	String getValue();
	void setValue(String value);
	
	boolean isInternal();
	void setIntern(boolean intern);

}