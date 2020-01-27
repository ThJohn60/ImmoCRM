package net.immocrm.db;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="Notar")
@Table(name="notar")
@NamedQueries(value={
	@NamedQuery(name="Notar.fetchAll", query="SELECT b FROM Notar b"),
	@NamedQuery(name="Notar.search", query="SELECT b FROM Notar b WHERE b.search LIKE :pattern"),
})
public class NotarEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="notarSeq")
    @SequenceGenerator(name="notarSeq",sequenceName="NOTAR_SEQ", allocationSize=1)
	private Integer id;


	@Column(name="name", length=255)
	private String name;

	@Column(name="street", length=64)
    private String street;

	@Column(name="postal_code", length=10)
    private String postalCode;

	@Column(name="city", length=40)
	private String city;

	@Column(name="country", length=2)
	private String country;

	@Column(name="tel", length=32)
    private String telNumber;

	@Column(name="fax", length=32)
    private String faxNumber;

	@Column(name="mail", length=255)
	private String emailAddress;

	@Column(name="search", length=400)
	private String search;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
		search = (name + street + city).toLowerCase();
		search = search.replaceAll("null", "");
		setDefaultCreatedOn();
	}

	@Override
	public String toString() {
		return name + " in " +  city;
	}

}
