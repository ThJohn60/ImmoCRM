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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Termin")
@Table(name="termin")
@NamedQueries(value={
	@NamedQuery(name="Termin.fetchAll", query="SELECT t FROM Termin t"),
	@NamedQuery(name="Termin.search", query="SELECT t FROM Termin t WHERE t.participant LIKE :pattern"),
	@NamedQuery(name="Termin.fetchByOrder", query="SELECT t FROM Termin t WHERE t.order.id = :orderId"),
	@NamedQuery(name="Termin.fetchByDates", query="SELECT t FROM Termin t WHERE t.startDate >= :start AND t.startDate < :end"),
})
public class TerminEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="terminSeq")
    @SequenceGenerator(name="terminSeq",sequenceName="TERMIN_SEQ", allocationSize=1)
	private Integer id;

	private String title;

	@Column(name="category_id")
	private Integer categoryId;

	@Column(name="start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name="end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private String participant;
	private String location;
	private String description;


	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="participant_id")
	private PersonEntity person;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderEntity order;

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

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date terminDate) {
		this.startDate = terminDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PersonEntity getPerson() {
		return person;
	}

	public void setPerson(PersonEntity p) {
		this.person = p;
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
		setDefaultCreatedOn();
	}

	@Override
	public String toString() {
		return "Termin[" + title + "]";
	}

}
