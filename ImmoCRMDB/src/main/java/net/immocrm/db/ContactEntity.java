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

@Entity(name="Contact")
@Table(name="contact")

@NamedQueries(value={
		@NamedQuery(name="Contact.fetchAll", query="SELECT b FROM Contact b"),
		@NamedQuery(name="Contact.search", query="SELECT b FROM Contact b WHERE b.search LIKE :pattern")
	})
public class ContactEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="contSeq")
    @SequenceGenerator(name="contSeq",sequenceName="CONTACT_SEQ", allocationSize=1)
	private Integer id;

	@Column(name="cell", length=64)
    private String cellNumber;

	@Column(name="tel", length=32)
    private String telNumber;

	@Column(name="fax", length=32)
    private String faxNumber;

	@Column(name="mail", length=255)
	private String emailAddress;

	@Column(length=400)
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

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
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

	public boolean isEmpty() {
		return telNumber == null && faxNumber == null && cellNumber == null && emailAddress == null;
	}

	@PrePersist
	@PreUpdate
	public void setDefaultValues() {
		search = (telNumber + faxNumber + cellNumber + emailAddress).toLowerCase();
		search = search.replaceAll("null", "");
		setDefaultCreatedOn();
	}

	@Override
	public String toString() {
		return "Contct[" + telNumber + ", " + emailAddress + "]";
	}

}