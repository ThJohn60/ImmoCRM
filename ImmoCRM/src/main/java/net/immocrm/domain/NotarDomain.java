package net.immocrm.domain;

import java.util.List;

import net.immocrm.db.NotarEntity;
import net.immocrm.domain.ref.CountryNameEnum;
import net.immocrm.domain.valid.NotarValidator;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.DomainTool;

class NotarDomain extends AbstractDomain implements Notar {

	private final NotarEntity entity;
	private final AddressFormatter formatter;


	NotarDomain(NotarEntity entity) {
		this.entity = entity;
		formatter = new AddressFormatter();
	}

	@Override
	public Address getAddress() {
		Address result = DomainFactory.newAddress();
		result.setCity(entity.getCity());
		result.setStreet(entity.getStreet());
		result.setPostalCode(entity.getPostalCode());
		return result;
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}



	@Override
	public String getStreet() {
		return entity.getStreet();
	}

	@Override
	public void setStreet(String street) {
		entity.setStreet(street);
	}

	@Override
	public String getStreet2() {
		return null;
	}

	@Override
	public void setStreet2(String street2) {
		// ignore
	}

	@Override
	public String getPostalCode() {
		return entity.getPostalCode();
	}

	@Override
	public void setPostalCode(String postalCode) {
		entity.setPostalCode(postalCode);
	}

	@Override
	public String getCity() {
		return entity.getCity();
	}

	@Override
	public void setCity(String city) {
		entity.setCity(city);
	}

	@Override
	public CountryNameEnum getCountry() {
		if (entity.getCountry() != null) {
			return CountryNameEnum.valueOf(entity.getCountry());
		}
		return null;
	}

	@Override
	public void setCountry(CountryNameEnum country) {
		entity.setCountry(country.name());
	}

	@Override
	public String getAddressText() {
		return formatter.getAddressText(this);
	}

	@Override
	public String getCompleteStreet() {
		return formatter.getCompleteStreet(this);
	}

	@Override
	public String getPostalCodeAndCity() {
		return formatter.getPostalCodeAndCity(this);
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
	public String getCellNumber() {
		return "";
	}

	@Override
	public void setCellNumber(String cellNumber) {
		// ignore
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
	public List<Order> getOrders() {
		return OrderReader.INSTANCE.fetchByNotar(getId());
	}

	@Override
	public NotarEntity getEntity() {
		return entity;
	}


	@Override
	public boolean isEmpty() {
		return DomainTool.isEmpty(entity.getName());
	}

	@Override
	public String getDomainName() {
		return "Notar";
	}

	@Override
	public String getNameAndAddress() {
		return getName() + "\n" + getAddressText();
	}
//
//	@Override
//	public Address getAddress() {
//		return address;
//	}


	@Override
	public ValidationIssues validate() {
		return new NotarValidator().validate(this);
	}


	public void beforePersistence() {
//		entity.setStreet(address.getStreet());
//		entity.setPostalCode(address.getPostalCode());
//		entity.setCity(address.getCity());
	}


	@Override
	public String toString() {
		return String.format("%s in %s", entity.getName(), entity.getCity());
	}

}
