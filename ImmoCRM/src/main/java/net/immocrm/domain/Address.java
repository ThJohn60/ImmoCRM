package net.immocrm.domain;

import net.immocrm.domain.ref.CountryNameEnum;

public interface Address extends BaseDomain {

	String getStreet();
	void setStreet(String street);

	String getStreet2();
	void setStreet2(String street2);

	String getPostalCode();
	void setPostalCode(String postalCode);

	String getCity();
	void setCity(String city);

	CountryNameEnum getCountry();
	void setCountry(CountryNameEnum country);

	String getAddressText();
	String getCompleteStreet();
	String getPostalCodeAndCity();

}