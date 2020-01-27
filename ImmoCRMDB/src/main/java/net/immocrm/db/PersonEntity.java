package net.immocrm.db;

import java.sql.Timestamp;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Person")
@Table(name="person")
@NamedQueries(value={
	@NamedQuery(name="Person.fetchAll", query="SELECT p FROM Person p ORDER BY p.lastName, p.firstName"),
	@NamedQuery(name="Person.search", query="SELECT DISTINCT p FROM Person p WHERE p.search LIKE :pattern OR p.homeAddress.search LIKE :pattern ORDER BY p.lastName, p.firstName"),
	@NamedQuery(name="Person.fetchByAddress", query="SELECT p FROM Person p WHERE p.homeAddress.id = :addressId"),
})
public class PersonEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="personSeq")
    @SequenceGenerator(name="personSeq",sequenceName="PERSON_SEQ", allocationSize=1)
	private Integer id;


	@Column(name="anrede", length=24)
	private String anrede;

	@Column(name="title", length=24)
	private String title;

	@Column(name="firstName", length=32)
	private String firstName;

	@Column(name="middleName", length=32)
	private String middleName;

	@Column(name="lastName", length=32)
	private String lastName;

	@Column(name="search", length=100)
	private String search;

	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="home_adr_id")
	private AddressEntity homeAddress;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="office_adr_id")
	private AddressEntity officeAddress;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="home_contact_id")
	private ContactEntity homeContact;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="office_contact_id")
	private ContactEntity officeContact;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="notice_id")
	private NoticeEntity noticeEntity;

	@Temporal(TemporalType.DATE)
	private Date birthday;

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

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String a) {
		this.anrede = a;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String name) {
		this.middleName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public AddressEntity getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(AddressEntity a) {
		this.homeAddress = a;
	}

	public AddressEntity getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(AddressEntity a) {
		this.officeAddress = a;
	}

	public ContactEntity getHomeContact() {
		return homeContact;
	}

	public void setHomeContact(ContactEntity c) {
		this.homeContact = c;
	}

	public ContactEntity getOfficeContact() {
		return officeContact;
	}

	public void setOfficeContact(ContactEntity c) {
		this.officeContact = c;
	}

	public String getNotice() {
		if (noticeEntity != null) {
			return noticeEntity.getText();
		}
		return "";
	}

	public void setNotice(String text) {
		if (noticeEntity == null) {
			if (text.isEmpty()) {
				return;
			}
			noticeEntity = new NoticeEntity();
		}
		this.noticeEntity.setText(text);
	}

	public NoticeEntity getNoticeEntity() {
		return noticeEntity;
	}

	public void setNoticeEntity(NoticeEntity notice) {
		this.noticeEntity = notice;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
		search = (firstName + lastName).toLowerCase().replaceAll("null", "");
		setDefaultCreatedOn();
	}

	@Override
	public String toString() {
		return String.format("Person[%s %s]", firstName, lastName);
	}

}
