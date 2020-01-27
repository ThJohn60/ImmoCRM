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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Request")
@Table(name="request")
@NamedQueries(value={
	@NamedQuery(name="Request.fetchAll", query="SELECT r FROM Request r"),
	@NamedQuery(name="Request.search", query="SELECT r FROM Request r WHERE r.purchaser.search LIKE :pattern"),
	@NamedQuery(name="Request.fetchByPurchaser", query="SELECT r FROM Request r WHERE r.purchaser.id = :purchaserId"),
	@NamedQuery(name="Request.fetchByOrder", query="SELECT r FROM Request r WHERE r.order.id = :orderId"),
	@NamedQuery(name="Request.fetchByOrderAndPattern", query="SELECT r FROM Request r WHERE r.order.id = :orderId AND r.purchaser.search LIKE :pattern"),
	@NamedQuery(name="Request.fetchByDates", query="SELECT r FROM Request r WHERE r.viewingAppointment >= :start AND r.viewingAppointment < :end"),
})
public class RequestEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="eventSeq")
    @SequenceGenerator(name="eventSeq",sequenceName="EVENT_SEQ", allocationSize=1)
	private Integer id;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderEntity order;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="purchaser_id")
	private PersonEntity purchaser;

	@Column(name="request_price")
	private Long requestPrice;

	@Column(name="request_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;

	@Column(name="viewing_appointment")
	private Timestamp viewingAppointment;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="notice_id")
	private NoticeEntity noticeEntity;

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

	public PersonEntity getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(PersonEntity purchaser) {
		this.purchaser = purchaser;
	}

	public Long getRequestPrice() {
		return requestPrice;
	}

	public void setRequestPrice(Long requestPrice) {
		this.requestPrice = requestPrice;
	}


	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
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

	public Date getViewingAppointment() {
		return viewingAppointment;
	}

	public void setViewingAppointment(Date viewingAppointment) {
		if (viewingAppointment != null) {
			this.viewingAppointment = new Timestamp(viewingAppointment.getTime());
		} else {
			this.viewingAppointment = null;
		}
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
		return "Anfrage[" + purchaser + ", " + requestPrice + "]";
	}

}
