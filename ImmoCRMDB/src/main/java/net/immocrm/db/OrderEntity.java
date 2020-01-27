package net.immocrm.db;

import java.sql.Timestamp;
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

@Entity(name="Order")
@Table(name = "immobilie_order")
@NamedQueries(value={
	@NamedQuery(name="Order.fetchAll", query="SELECT o FROM Order o ORDER BY o.createdOn DESC"),
	@NamedQuery(name="Order.search", query="SELECT o FROM Order o WHERE (o.customer.search LIKE :pattern OR o.immobilie.address.search LIKE :pattern)"),
	@NamedQuery(name="Order.fetchByBuyer", query="SELECT o FROM Order o WHERE o.buyer.id = :buyerId"),
	@NamedQuery(name="Order.fetchByProvider", query="SELECT o FROM Order o WHERE o.customer.id = :providerId"),
	@NamedQuery(name="Order.fetchByNotar", query="SELECT o FROM Order o WHERE o.notariat.id = :notarId"),
	@NamedQuery(name="Order.fetchByProperty", query="SELECT o FROM Order o WHERE o.immobilie.id = :propertyId"),
	@NamedQuery(name="Order.fetchByDates", query="SELECT o FROM Order o WHERE (o.settlementDateTime >= :start AND o.settlementDateTime < :end) "
			+ "OR (o.handoverDateTime >= :start AND o.handoverDateTime < :end) OR (o.billDate >= :start AND o.billDate < :end)"),
})
public class OrderEntity extends AbstractEntity implements Cloneable {

	@Id
    @GeneratedValue(generator="orderSeq")
    @SequenceGenerator(name="orderSeq",sequenceName="ORDER_SEQ", allocationSize=1)
	private Integer id;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private PersonEntity customer;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="immobilie_id")
	private ImmobilieEntity immobilie;

	@Column(name="type_id")
	private Integer orderTypeId;

	@Column(name="state_id")
	private Integer orderStateId;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="purchaser_id")
	private PersonEntity buyer;

	@Column(name="customer_provision")
	private Double customerProvision;

	@Column(name="purchaser_provision")
	private Double buyerProvision;

	@Column(name="customer_price")
	private Long customerPrice;

	@Column(name="estimated_price")
	private Long estimatedPrice;

	@Column(name="settlement_price")
	private Long settlementPrice;

	@Column(name="field1_id", length=64)
	private String field1Id;

	@Column(name="field2_id", length=64)
	private String field2Id;

	@Column(name="order_id", length=64)
	private String ablage;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private List<OrderHistoryEntity> history;

	@Column(name="settlement_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date settlementDateTime;

	@Column(name="handover_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date handoverDateTime;

	@Column(name="bill_date")
	@Temporal(TemporalType.DATE)
	private Date billDate;

	@Column(name="payed_date")
	@Temporal(TemporalType.DATE)
	private Date payedDate;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="notariat_id")
	private NotarEntity notariat;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="notice_id")
	private NoticeEntity notice;

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

	public PersonEntity getCustomer() {
		return customer;
	}

	public void setProvider(PersonEntity p) {
		this.customer = p;
	}

	public ImmobilieEntity getImmobilie() {
		return immobilie;
	}

	public void setImmobilie(ImmobilieEntity immo) {
		this.immobilie = immo;
	}

	public Integer getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderType(Integer type) {
		this.orderTypeId = type;
	}

	public Integer getOrderStateId() {
		return orderStateId;
	}

	public void setOrderStateId(Integer state) {
		this.orderStateId = state;
	}

	public Long getCustomerPrice() {
		return customerPrice;
	}

	public void setCustomerPrice(Long p) {
		this.customerPrice = p;
	}

	public Long getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(Long p) {
		this.estimatedPrice = p;
	}

	public PersonEntity getBuyer() {
		return buyer;
	}

	public void setBuyer(PersonEntity buyer) {
		this.buyer = buyer;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long p) {
		this.settlementPrice = p;
	}

	public NotarEntity getNotariat() {
		return notariat;
	}

	public void setNotariat(NotarEntity n) {
		this.notariat = n;
	}


	public String getField1Id() {
		return field1Id;
	}

	public void setField1Id(String field1Id) {
		this.field1Id = field1Id;
	}

	public String getField2Id() {
		return field2Id;
	}

	public void setField2Id(String field2Id) {
		this.field2Id = field2Id;
	}


	public Date getSettlementDateTime() {
		return settlementDateTime;
	}

	public void setSettlementDateTime(Date settlementDateTime) {
		this.settlementDateTime = settlementDateTime;
	}

	public Date getHandoverDateTime() {
		return handoverDateTime;
	}

	public void setHandoverDateTime(Date handoverDateTime) {
		this.handoverDateTime = handoverDateTime;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDateTime) {
		this.billDate = billDateTime;
	}

	public Date getPayedDate() {
		return payedDate;
	}

	public void setPayedDate(Date payedDate) {
		this.payedDate = payedDate;
	}


	public Double getBuyerProvision() {
		return buyerProvision;
	}

	public void setBuyerProvision(Double p) {
		this.buyerProvision = p;
	}

	public Double getCustomerProvision() {
		return customerProvision;
	}

	public void setCustomerProvision(Double p) {
		this.customerProvision = p;
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

	public String getAblage() {
		return ablage;
	}

	public void setAblage(String id) {
		this.ablage = id;
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
		return "Order[" + immobilie + "]";
	}

}
