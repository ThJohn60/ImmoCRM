package net.immocrm.domain;

import java.util.Collections;
import java.util.List;

import net.immocrm.db.EntityTool;
import net.immocrm.db.PersonEntity;
import net.immocrm.domain.ref.CountryNameEnum;
import net.immocrm.domain.valid.PersonValidator;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.ImmoDate;

class PersonDomain extends AbstractDomain implements Person {

	private final PersonEntity entity;

	private final AddressDomain homeAddress;
	private final ContactDomain homeContact;


	PersonDomain(PersonEntity e) {
		this.entity = e;
		homeAddress = DomainFactory.createDomain(entity.getHomeAddress());
		homeContact = new ContactDomain(entity.getHomeContact());
	}


	@Override
	public boolean isEmpty() {
		return EntityTool.isNull(entity.getFirstName(), entity.getLastName());
	}

	@Override
	public String getName() {
		return getName(false);
	}

	@Override
	public String getCompleteName() {
		return getName(true);
	}

	@Override
	public String getVornamen() {
		if (isEmpty()) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		result.append(getFirstName());
		if (getMiddleName() != null && !getMiddleName().isEmpty()) {
			result.append(" ").append(getMiddleName());
		}
		return result.toString();
	}

	private String getName(boolean withMiddleName) {
		if (isEmpty()) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		if (getTitle() != null && !getTitle().trim().isEmpty()) {
			result.append(getTitle()).append(" ");
		}
		result.append(getFirstName()).append(" ");
		if (withMiddleName && getMiddleName() != null && !getMiddleName().isEmpty()) {
			result.append(getMiddleName()).append(" ");
		}
		result.append(getLastName());
		return result.toString();
	}

	@Override
	public List<Order> getOrders() {
		if (isNew()) {
			return Collections.emptyList();
		}
		return OrderReader.INSTANCE.fetchByCustomer(getId());
	}

	@Override
	public List<Immobilie> getImmobilien() {
		if (isNew()) {
			return Collections.emptyList();
		}
		return ImmobilieReader.INSTANCE.fetchByOwner(getId());
	}

	@Override
	public List<ImmoTransaction> getImmoTransaction() {
		if (isNew()) {
			return Collections.emptyList();
		}
		return ImmobilieReader.INSTANCE.fetchTransactionsByPerson(getId());
	}


	@Override
	public String getAnrede() {
		return entity.getAnrede();
	}

	@Override
	public void setAnrede(String salutation) {
		entity.setAnrede(salutation);
	}

	@Override
	public String getTitle() {
		return entity.getTitle();
	}

	@Override
	public void setTitle(String title) {
		entity.setTitle(title);
	}

	@Override
	public String getFirstName() {
		return entity.getFirstName();
	}

	@Override
	public void setFirstName(String name) {
		entity.setFirstName(name);
	}

	@Override
	public String getMiddleName() {
		return entity.getMiddleName();
	}

	@Override
	public void setMiddleName(String name) {
		entity.setMiddleName(name);
	}

	@Override
	public String getLastName() {
		return entity.getLastName();
	}

	@Override
	public void setLastName(String name) {
		entity.setLastName(name);
	}

	@Override
	public String getStreet() {
		return homeAddress.getStreet();
	}

	@Override
	public void setStreet(String st) {
		homeAddress.setStreet(st);
	}

	@Override
	public String getStreet2() {
		return homeAddress.getStreet2();
	}

	@Override
	public void setStreet2(String st) {
		homeAddress.setStreet2(st);
	}

	@Override
	public String getPostalCode() {
		return homeAddress.getPostalCode();
	}

	@Override
	public void setPostalCode(String pc) {
		homeAddress.setPostalCode(pc);
	}

	@Override
	public String getCity() {
		return homeAddress.getCity();
	}

	@Override
	public void setCity(String city) {
		if (city == null) {
			city = "";
		}
		homeAddress.setCity(city);
	}

	@Override
	public CountryNameEnum getCountry() {
		return homeAddress.getCountry();
	}

	@Override
	public void setCountry(CountryNameEnum country) {
		homeAddress.setCountry(country);
	}

	@Override
	public AddressDomain getHomeAddress() {
		return homeAddress;
	}

	@Override
	public AddressDomain getOfficeAddress() {
		return null;
	}

	@Override
	public String getHomeAddressText() {
		return homeAddress.toString();
	}

	@Override
	public String getOfficeAddressText() {
		return "";
	}

	@Override
	public ContactDomain getHomeContact() {
		return homeContact;
	}

	@Override
	public ContactDomain getOfficeContact() {
		return null;
	}

	@Override
	public String getNotice() {
		return entity.getNotice();
	}

	@Override
	public void setNotice(String description) {
		entity.setNotice(description);
	}

	@Override
	public ImmoDate getBirthday() {
		return new ImmoDate(entity.getBirthday());
	}

	@Override
	public void setBirthday(ImmoDate birthday) {
		entity.setBirthday(birthday.toPersistenceDate());
	}

	@Override
	public String getEmailAddress() {
		return homeContact.getEmailAddress();
	}


	@Override
	PersonEntity getEntity() {
		return entity;
	}

	@Override
	public String getDomainName() {
		return "Person";
	}

	@Override
	public String getNameAndAddress() {
		if (!getHomeAddress().isEmpty()) {
			return getName() + "\n" + getHomeAddress().getAddressText();
		}
		return "";
	}


	@Override
	public ValidationIssues validate() {
		return new PersonValidator().validate(this);
	}

	public void beforePersistence() {
		if (homeContact.isEmpty()) {
			entity.setHomeContact(null);
		} else {
			entity.setHomeContact(homeContact.getEntity());
		}
		if (homeAddress.isEmpty()) {
			entity.setHomeAddress(null);
		} else {
			entity.setHomeAddress(homeAddress.getEntity());
		}
	}


	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PersonDomain) {
			return entity.equals(((PersonDomain)obj).getEntity());
		}
		return false;
	}

	@Override
	public String toString() {
		if (homeAddress.isEmpty()) {
			return getName();
		}
		return getName() + ", " + getHomeAddressText();
	}

}
