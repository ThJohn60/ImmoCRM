package net.immocrm.domain;

import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.List;

import net.immocrm.db.ImmobilieTransactionEntity;
import net.immocrm.db.MerkmalTypeEntity;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.Price;

public class ImmoTransactionDomain extends AbstractDomain implements ImmoTransaction {

	private final ImmobilieTransactionEntity entity;
	private final MerkmalManager merkmalProvider;
	private OrderDomain order;
	private ImmobilieDomain immobilie;
	private PersonDomain person;


	ImmoTransactionDomain() {
		this(null);
	}

	ImmoTransactionDomain(ImmobilieTransactionEntity e) {
		this.entity = e != null ? e : new ImmobilieTransactionEntity();
		order = DomainFactory.createDomain(entity.getOrder());
		merkmalProvider = new MerkmalManager(entity.getMerkmalList());
		immobilie = DomainFactory.createDomain(entity.getImmobilie());
		person = DomainFactory.createDomain(entity.getPerson());
//		entity.setPerson(person.getEntity());
	}


	@Override
	public OrderDomain getOrder() {
		return order;
	}

	public void setOrder(Order p) {
		this.order = (OrderDomain)p;
		if (order != null) {
			this.entity.setOrder(order.getEntity());
		}
	}



	@Override
	public ImmobilieDomain getImmobilie() {
		return immobilie;
	}

	@Override
	public void setImmobilie(Immobilie im) {
		if (im != null) {
			this.immobilie = (ImmobilieDomain)im;
			this.entity.setImmobilie(this.immobilie.getEntity());
		}
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(Person p) {
		this.person = (PersonDomain)p;
		if (person != null) {
			this.entity.setPerson(person.getEntity());
		}
	}

	@Override
	public boolean isSaleEvent() {
		return entity.getIsSaleEvent() != null && entity.getIsSaleEvent().intValue() == 1;
	}

	@Override
	public void setIsSaleEvent(boolean b) {
		entity.setIsSaleEvent(b ? 1 : 0 );
	}

	@Override
	public Price getPrice() {
		return new Price(entity.getPrice());
	}

	@Override
	public void setPrice(Price price) {
		entity.setPrice(price.getValue());
	}

	@Override
	public ImmoDate getStartDate() {
		return new ImmoDate(entity.getStartDate());
	}

	@Override
	public void setStartDate(ImmoDate d) {
		entity.setStartDate(d.toPersistenceDate());
	}

	@Override
	public ImmoDate getEndDate() {
		return new ImmoDate(entity.getEndDate());
	}

	@Override
	public void setEndDate(ImmoDate d) {
		entity.setEndDate(d.toPersistenceDate());
	}

	@Override
	public MerkmalType getZustand() {
		if (entity.getZustand() != null) {
			return DomainFactory.createDomain(entity.getZustand());
		}
		return null;
	}

	public String getZustandName() {
		if (entity.getZustand() != null) {
			return entity.getZustand().getName();
		}
		return "";
	}

	public void setZustand(MerkmalType type) {
		if (type == null) {
			entity.setZustand(null);
		} else {
			MerkmalTypeEntity typeEntity = ((MerkmalTypeDomain)type).getEntity();
			entity.setZustand(typeEntity);
		}
	}

	@Override
	public String getNotice() {
		return entity.getNotice();
	}

	@Override
	public void setNotice(String text) {
		entity.setNotice(text);
	}

	@Override
	public Merkmal getMerkmal(MerkmalType type) {
		return merkmalProvider.getMerkmal(type);
	}

	@Override
	public List<Merkmal> getMerkmalList(EnumSet<Category> categories) {
		return merkmalProvider.getMerkmalList(categories);
	}

	@Override
	public List<Merkmal> getMerkmalList(Category category) {
		return merkmalProvider.getMerkmalList(category);
	}


	@Override
	public Price getHausgeld() {
		return merkmalProvider.getPrice(MerkmalManager.HAUSGELD);
	}

	@Override
	public Price getMiete() {
		return merkmalProvider.getPrice(MerkmalManager.RENT_PRICE);
	}

	@Override
	public boolean isEmpty() {
		return entity.getImmobilie() == null || order.isEmpty();
	}

	@Override
	ImmobilieTransactionEntity getEntity() {
		return entity;
	}

	@Override
	public String getDomainName() {
		return "Immobilie";
	}

	public Timestamp getCreatedOn() {
		return entity.getCreatedOn();
	}

	public void setDefaultValues() {
		entity.setDefaultValues();
	}

	public void beforePersistence() {
		entity.setMerkmalList(merkmalProvider.getMerkmalList());
	}



	@Override
	public ValidationIssues validate() {
		return null;			// TODO
//		return new ImmobilieValidator().validate(this);
	}


	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return entity.equals(((ImmoTransactionDomain)obj).getEntity());
	}

	@Override
	public String toString() {
		return DomainFactory.createDomain(entity.getImmobilie()).toString();
	}

}
