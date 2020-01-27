package net.immocrm.domain;

import java.util.List;

import net.immocrm.domain.ref.CountryNameEnum;
import net.immocrm.domain.vc.ImmoDate;

public interface Person extends BaseDomain, PersonName {

	String getAnrede();
	void setAnrede(String salutation);

	String getTitle();
	void setTitle(String title);

	String getVornamen();
	String getCompleteName();

	String getFirstName();
	void setFirstName(String firstName);
	String getMiddleName();
	void setMiddleName(String middleName);
	String getLastName();
	void setLastName(String lastName);

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

	Address getHomeAddress();
	Address getOfficeAddress();
	String getHomeAddressText();
	String getOfficeAddressText();
	String getNameAndAddress();

	Contact getHomeContact();
	Contact getOfficeContact();
	String getEmailAddress();

	List<Order> getOrders();
	List<Immobilie> getImmobilien();
	List<ImmoTransaction> getImmoTransaction();

	String getNotice();
	void setNotice(String description);

	ImmoDate getBirthday();
	void setBirthday(ImmoDate birthday);

}