package net.immocrm.domain;

import net.immocrm.db.AddressEntity;
import net.immocrm.db.GeoInfoEntity;
import net.immocrm.domain.geo.Coordinates;
import net.immocrm.domain.ref.CountryNameEnum;
import net.immocrm.domain.valid.AddressValidator;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.DomainTool;

class AddressDomain extends AbstractDomain implements Address {

	private final AddressEntity entity;
	private GeoInfoEntity geoInfo;
	private AddressFormatter formatter;


	public AddressDomain(AddressEntity e) {
		this.entity = e;
		formatter = new AddressFormatter();
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
		return entity.getStreet2();
	}

	@Override
	public void setStreet2(String street2) {
		entity.setStreet2(street2);
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
		if (country != null) {
			entity.setCountry(country.name());
		} else {
			entity.setCountry(null);
		}
	}


	/**
	 * see https://api.immobilienscout24.de/our-apis/gis/geoautocompletion-v2.html#entity
	 */
	public Coordinates getCoordinates() {
		if (entity.getGeoInfo() == null || entity.getGeoInfo().getLon() == null) {
			return new Coordinates();
		}
		return new Coordinates(getGeoInfoEntity().getLon(), getGeoInfoEntity().getLat());
	}

	public void setCoordinates(Coordinates coordinates) {
		getGeoInfoEntity().setLon(coordinates.getLongitude());
		getGeoInfoEntity().setLat(coordinates.getLatitude());
	}

	public String getQuarter() {
		return getGeoInfoEntity().getQuarter();
	}

	public void setQuarter(String quarter) {
		getGeoInfoEntity().setQuarter(quarter);
	}

	public Long getImmo24QuarterId() {
		return getGeoInfoEntity().getImmo24QuarterId();
	}

	public void setImmo24QuarterId(Long immo24QuarterId) {
		getGeoInfoEntity().setImmo24QuarterId(immo24QuarterId);
	}

	private GeoInfoEntity getGeoInfoEntity() {
		if (geoInfo == null) {
			if (entity.getGeoInfo() == null) {
				geoInfo = new GeoInfoEntity();
			} else {
				geoInfo = entity.getGeoInfo();
			}
		}
		return geoInfo;
	}

	public void removeGeoInfo() {
		entity.setGeoInfo(null);
	}


	@Override
	AddressEntity getEntity() {
		return entity;
	}

	@Override
	public boolean isEmpty() {
		return DomainTool.isEmpty(getCity(), getStreet(), getStreet2(), getPostalCode());
	}

	@Override
	public String getDomainName() {
		return "";
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
	public ValidationIssues validate() {
		return new AddressValidator().validate(this);
	}

	public void beforePersistence() {
		if (geoInfo != null && geoInfo.isNew() && !geoInfo.isEmpty()) {
			entity.setGeoInfo(geoInfo);
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressDomain other = (AddressDomain) obj;
		return toString().equals(other.toString());
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "Adresse nicht erfasst";
		}
		if (getStreet2() == null) {
			return getStreet() + ", " + getPostalCodeAndCity();
		}
		return getStreet() + " " + getStreet2() + ", " + getPostalCodeAndCity();
	}

}
