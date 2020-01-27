package net.immocrm.db;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="Address")
@Table(name="address")
@NamedQueries(value={
	@NamedQuery(name="Address.fetchAll", query="SELECT a FROM Address a ORDER BY a.city, a.street"),
	@NamedQuery(name="Address.search", query="SELECT a FROM Address a WHERE a.search LIKE :pattern")
})
public class AddressEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="addrSeq")
    @SequenceGenerator(name="addrSeq",sequenceName="ADDRESS_SEQ", allocationSize=1)
	private Integer id;

	@Column(name="street", length=64)
    private String street;

	@Column(name="street2", length=64)
    private String street2;

	@Column(name="postal_code", length=10)
    private String postalCode;

	@Column(name="city", length=40)
	private String city;

	@Column(name="country", length=2)
	private String country;

	@Column(name="search", length=110)
	private String search;

	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="geo_Info_id")
	private GeoInfoEntity geoInfo;


	@Column(name="created_on")
	private Timestamp createdOn;


	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}


	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public GeoInfoEntity getGeoInfo() {
		return geoInfo;
	}

	public void setGeoInfo(GeoInfoEntity geoInfo) {
		this.geoInfo = geoInfo;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	@Override
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@PrePersist
	@PreUpdate
	public void setDefaultValues() {
		search = (street + city).toLowerCase();
		search = search.replaceAll("null", "");
		setDefaultCreatedOn();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((street2 == null) ? 0 : street2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEntity other = (AddressEntity) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (street2 == null) {
			if (other.street2 != null)
				return false;
		} else if (!street2.equals(other.street2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + ": " + street + ", " + postalCode + " " + city;
	}

}