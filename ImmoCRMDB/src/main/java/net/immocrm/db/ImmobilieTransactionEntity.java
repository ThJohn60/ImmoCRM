package net.immocrm.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="ImmobilieTransaction")
@Table(name="immobilie_transaction")
@NamedQueries(value={
	@NamedQuery(name="ImmobilieTransaction.fetchAll", query="SELECT b FROM ImmobilieTransaction b ORDER BY b.startDate"),
	@NamedQuery(name="ImmobilieTransaction.fetchByImmobilie", query="SELECT b FROM ImmobilieTransaction b WHERE b.immobilie.id = :immobilieId"),
	@NamedQuery(name="ImmobilieTransaction.fetchByPerson", query="SELECT b FROM ImmobilieTransaction b WHERE b.person.id = :personId"),
})

public class ImmobilieTransactionEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="immoTransSeq")
    @SequenceGenerator(name="immoTransSeq", sequenceName="IMMO_TRANS_SEQ", allocationSize=1)
	private Integer id;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="immobilie_id")
	private ImmobilieEntity immobilie;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	private PersonEntity person;

	@Column(name="is_sale_event")
	private Integer isSaleEvent;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderEntity order;

	@Column(name="price")
	private Long price;

	@Column(name="start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name="end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="notice_id")
	private NoticeEntity notice;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="zustand_id")
	private MerkmalTypeEntity zustand;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="immo_trans_id")
	private List<MerkmalEntity> qualities;

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

	public ImmobilieEntity getImmobilie() {
		return immobilie;
	}

	public void setImmobilie(ImmobilieEntity immobilie) {
		this.immobilie = immobilie;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public PersonEntity getPerson() {
		return person;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
	}

	public Integer getIsSaleEvent() {
		return isSaleEvent;
	}

	public void setIsSaleEvent(Integer b) {
		this.isSaleEvent = b;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNotice() {
		if (notice != null) {
			return notice.getText();
		}
		return "";
	}

	public void setNotice(String text) {
		if (notice == null) {
			if (text.isEmpty()) {
				return;
			}
			notice = new NoticeEntity();
		}
		this.notice.setText(text);
	}

	public List<MerkmalEntity> getMerkmalList() {
		if (qualities == null) {
			qualities = new ArrayList<>();
		}
		return qualities;
	}

	public void setMerkmalList(List<MerkmalEntity> qualities) {
		this.qualities = qualities;
	}

	public MerkmalTypeEntity getZustand() {
		return zustand;
	}

	public void setZustand(MerkmalTypeEntity zustand) {
		this.zustand = zustand;
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
		return "ImmoTrans[" + immobilie + ", Datum: " + startDate + "]";
	}

}
