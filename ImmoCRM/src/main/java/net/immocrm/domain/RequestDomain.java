package net.immocrm.domain;

import net.immocrm.db.RequestEntity;
import net.immocrm.domain.valid.RequestValidator;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.domain.vc.Price;

class RequestDomain extends AbstractDomain implements Request {

	private final RequestEntity entity;
	private final OrderDomain order;

	private PersonDomain purchaser;


	RequestDomain(OrderDomain order) {
		this(order, new RequestEntity());
		entity.setOrder(order.getEntity());
	}

	RequestDomain(OrderDomain order, RequestEntity entity) {
		this.order = order;
		this.entity = entity;
		this.purchaser = DomainFactory.createDomain(entity.getPurchaser());
	}


	@Override
	public boolean isEmpty() {
		return purchaser.isEmpty();
	}

	@Override
	public Order getOrder() {
		return order;
	}


	@Override
	public PersonDomain getPurchaser() {
		return purchaser;
	}

	@Override
	public void setPurchaser(Person purchaser) {
		this.purchaser = (PersonDomain)purchaser;
		if (purchaser != null) {
			entity.setPurchaser(this.purchaser.getEntity());
		} else {
			purchaser = DomainFactory.createDomain(entity.getPurchaser());
		}
	}

	@Override
	public Price getRequestPrice() {
		return new Price(entity.getRequestPrice());
	}

	@Override
	public void setRequestPrice(Price requestPrice) {
		entity.setRequestPrice(requestPrice.getValue());
	}

	@Override
	public String getNotice() {
		return entity.getNotice();
	}

	@Override
	public void setNotice(String description) {
		entity.setNotice(description);
	}

	@Override
	public ImmoDateTime getBesichtigungstermin() {
		return new ImmoDateTime(entity.getViewingAppointment());
	}

	@Override
	public void setBesichtigungstermin(ImmoDateTime viewingAppointment) {
		entity.setViewingAppointment(viewingAppointment.toPersistenceTimestamp());
	}

	@Override
	RequestEntity getEntity() {
		return entity;
	}


	@Override
	public String getDomainName() {
		return "Anfrage";
	}

	@Override
	public ValidationIssues validate() {
		return new RequestValidator().validate(this);
	}


	@Override
	public String toString() {
		Price requestPrice = getRequestPrice();
		if (requestPrice.isEmpty()) {
			return String.format("Anfrage von %s", purchaser.getName());
		}
		return String.format("Anfrage von %s (%s)", purchaser.getName(), requestPrice);
	}

}
