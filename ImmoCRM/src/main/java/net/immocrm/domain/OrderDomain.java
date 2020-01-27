package net.immocrm.domain;

import java.util.Collections;
import java.util.List;

import net.immocrm.db.OrderEntity;
import net.immocrm.domain.img.DocumentDirectory;
import net.immocrm.domain.ref.OrderStateEnum;
import net.immocrm.domain.ref.OrderTypeEnum;
import net.immocrm.domain.valid.OrderValidator;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.DomainTool;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.domain.vc.Percent;
import net.immocrm.domain.vc.Price;

class OrderDomain extends AbstractDomain implements Order {

	private final OrderEntity entity;
	private final OrderHistoryProvider orderForHistory;

	private PersonDomain buyer;
	private PersonDomain customer;
	private NotarDomain notar;
	private ImmobilieDomain immobilie;

	OrderDomain() {
		this(null);
	}

	OrderDomain(OrderEntity e) {
		this.entity = e;
		customer = DomainFactory.createDomain(entity.getCustomer());
		buyer = DomainFactory.createDomain(entity.getBuyer());
		immobilie = DomainFactory.createDomain(entity.getImmobilie());
		notar = DomainFactory.createDomain(entity.getNotariat());
		entity.setProvider(customer.getEntity());
		entity.setImmobilie(immobilie.getEntity());
		if (entity.isNew()) {
			entity.setOrderStateId(OrderStateEnum.OPEN.getId());
		}
		orderForHistory = new OrderHistoryProvider(this);
	}


	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isFinished() {
		return getOrderState() == OrderStateEnum.FINISHED ||  getOrderState() == OrderStateEnum.CANCELED;
	}

	@Override
	public DocumentDirectory getDocumentManager() {
		return new DocumentDirectory(null);
	}

	@Override
	public PersonDomain getCustomer() {
		return customer;
	}

	@Override
	public void setCustomer(Person customer) {
		this.customer = (PersonDomain)customer;
		entity.setProvider(this.customer.getEntity());
	}


	@Override
	public PersonDomain getBuyer() {
		return buyer;
	}

	@Override
	public void setBuyer(Person b) {
		buyer = (PersonDomain)b;
		if (b != null) {
			entity.setBuyer(buyer.getEntity());
		}
	}


	@Override
	public String getAblage() {
		return entity.getAblage();
	}

	@Override
	public void setAblage(String ablage) {
		entity.setAblage(ablage);
	}


	@Override
	public ImmobilieDomain getImmobilie() {
		return immobilie;
	}

	@Override
	public void setImmobilie(Immobilie immobilie) {
		if (immobilie != null) {
			this.immobilie = (ImmobilieDomain)immobilie;
			this.entity.setImmobilie(this.immobilie.getEntity());
		}
	}


	@Override
	public AddressDomain getImmobilieAddress() {
		return immobilie.getAddress();
	}

	@Override
	public String getImmobilieAddressText() {
		return immobilie.getAddress().toString();
	}

	@Override
	public OrderTypeEnum getOrderType() {
		if (entity.getOrderTypeId() == null) {
			return null;
		}
		return OrderTypeEnum.byId(entity.getOrderTypeId());
	}

	@Override
	public void setOrderType(OrderTypeEnum type) {
		if (type != null) {
			entity.setOrderType(type.getId());
		}
	}

	@Override
	public boolean isSaleOrder() {
		return getOrderType() == OrderTypeEnum.Verkauf;
	}

	@Override
	public boolean isRentOrder() {
		return getOrderType() == OrderTypeEnum.Vermietung;
	}


	@Override
	public void setOrderState(OrderStateEnum stateEnum) {
		if (stateEnum == null) {
			entity.setOrderStateId(null);
		} else {
			entity.setOrderStateId(stateEnum.getId());
		}
	}

	@Override
	public OrderStateEnum getOrderState() {
		if (entity.getOrderStateId() == null) {
			return OrderStateEnum.OPEN;
		}
		return OrderStateEnum.getById(entity.getOrderStateId());
	}

	@Override
	public List<Request> getRequests() {
		return RequestReader.INSTANCE.fetchByOrderId(entity.getId());
	}

	@Override
	public List<Document> getDocuments() {
		return DocumentReader.INSTANCE.fetchByOrderId(entity.getId());
	}


	@Override
	public List<OrderHistory> getHistory() {
		if (isNew()) {
			return Collections.emptyList();
		}
		List<OrderHistory> result = OrderReader.INSTANCE.fetchHistoryByOrder(this);
		return result;
	}

	@Override
	public Price getCustomerPrice() {
		return new Price(entity.getCustomerPrice());
	}

	@Override
	public void setCustomerPrice(Price price) {
		entity.setCustomerPrice(price.getValue());
	}

	@Override
	public Price getEstimatedPrice() {
		return new Price(entity.getEstimatedPrice());
	}

	@Override
	public void setEstimatedPrice(Price price) {
		entity.setEstimatedPrice(price.getValue());
	}

	@Override
	public Price getSettlementPrice() {
		return new Price(entity.getSettlementPrice());
	}

	@Override
	public void setSettlementPrice(Price price) {
		entity.setSettlementPrice(price.getValue());
	}


	@Override
	public Percent getBuyerProvision() {
		return new Percent(entity.getBuyerProvision());
	}

	@Override
	public void setBuyerProvision(Percent provision) {
		entity.setBuyerProvision(provision.getValue());
	}

	@Override
	public Percent getCustomerProvision() {
		return new Percent(entity.getCustomerProvision());
	}

	@Override
	public void setCustomerProvision(Percent provision) {
		entity.setCustomerProvision(provision.getValue());
	}


	@Override
	public String getField1() {
		return entity.getField1Id();
	}

	@Override
	public void setField1Id(String field1Id) {
		entity.setField1Id(field1Id);
	}

	@Override
	public String getField2() {
		return entity.getField2Id();
	}

	@Override
	public void setField2Id(String field2Id) {
		entity.setField2Id(field2Id);
	}

	@Override
	public NotarDomain getNotar() {
		return notar;
	}

	@Override
	public void setNotar(Notar notar) {
		if (notar != null) {
			this.notar = (NotarDomain)notar;
			entity.setNotariat(this.notar.getEntity());
		} else {
			entity.setNotariat(null);
			this.notar = DomainFactory.createDomain(entity.getNotariat());
		}
	}

	@Override
	public ImmoDate getStartDate() {
		return new ImmoDate(entity.getCreatedOn());
	}

	@Override
	public ImmoDateTime getSettlementDateTime() {
		return new ImmoDateTime(entity.getSettlementDateTime());
	}

	@Override
	public void setSettlementDateTime(ImmoDateTime dt) {
		entity.setSettlementDateTime(dt.toPersistenceTimestamp());
	}

	@Override
	public ImmoDateTime getHandoverDateTime() {
		return new ImmoDateTime(entity.getHandoverDateTime());
	}

	@Override
	public void setHandoverDateTime(ImmoDateTime dt) {
		entity.setHandoverDateTime(dt.toPersistenceTimestamp());
	}

	@Override
	public ImmoDate getBillDate() {
		return new ImmoDate(entity.getBillDate());
	}

	@Override
	public 	void setBillDate(ImmoDate dt) {
		entity.setBillDate(dt.toPersistenceDate());
	}

	@Override
	public ImmoDate getPayedDate() {
		return new ImmoDate(entity.getPayedDate());
	}

	@Override
	public 	void setPayedDate(ImmoDate dt) {
		entity.setPayedDate(dt.toPersistenceDate());
	}

	@Override
	public String getNotice() {
		return entity.getNotice();
	}

	@Override
	public void setNotice(String text) {
		entity.setNotice(text);
	}

	public OrderHistoryProvider getOrderForHistory() {
		return orderForHistory;
	}


	@Override
	OrderEntity getEntity() {
		return entity;
	}

	@Override
	public String getDomainName() {
		return "Auftrag";
	}

	@Override
	public ValidationIssues validate() {
		return new OrderValidator().validate(this);
	}


	public void beforePersistence() {
		if (DomainTool.isEmpty(immobilie.getOwner())) {
			immobilie.setOwner(customer);
		}
		if (buyer.isEmpty()) {
			entity.setBuyer(null);
		}
		if (notar.isEmpty()) {
			entity.setNotariat(null);
		}
		setOrderState(OrderStateCalulator.stateOf(this));
	}


	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return entity.equals(((OrderDomain)obj).getEntity());
	}

	@Override
	public String toString() {
		return getOrderType().name() + ": " + getImmobilie().getAddress().getAddressText();
	}

}
