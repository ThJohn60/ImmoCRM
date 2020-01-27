package net.immocrm.domain;


import java.util.Collections;
import java.util.List;

import net.immocrm.db.AddressEntity;
import net.immocrm.db.TerminEntity;
import net.immocrm.domain.termin.BaseTermin;
import net.immocrm.domain.termin.TerminCategoryEnum;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.ImmoDateTime;

public class IndependentTermin extends BaseTermin implements BaseDomain {

	private final TerminEntity entity;

	private AddressDomain address;


	public IndependentTermin() {
		this(TerminCategoryEnum.other, new TerminEntity());
	}


	IndependentTermin(TerminCategoryEnum typeEnum, TerminEntity entity) {
		super(typeEnum);
		this.entity = entity;
		address = DomainFactory.createDomain((AddressEntity)null);
	}


	@Override
	public String getTitle() {
		return entity.getTitle();
	}

	public void setTitle(String title) {
		entity.setTitle(title);
	}

	@Override
	public String getCalendarLabel() {
		if (getLocation() == null || getLocation().isEmpty()) {
			return entity.getTitle();
		}
		return entity.getTitle() + "\n" + entity.getLocation();
	}

	@Override
	public TerminCategoryEnum getCategory() {
		if (entity.getCategoryId() == null) {
			return null;
		}
		return TerminCategoryEnum.byId(entity.getCategoryId());
	}

	public void setCategory(TerminCategoryEnum ce) {
		entity.setCategoryId(ce.getId());
	}

	@Override
	public ImmoDateTime getDate() {
		return new ImmoDateTime(entity.getStartDate());
	}

	@Override
	public void setDate(ImmoDateTime d) {
		entity.setStartDate(d.toPersistenceTimestamp());
	}

	public ImmoDateTime getEndDate() {
		return new ImmoDateTime(entity.getEndDate());
	}


	public void setEndDate(ImmoDateTime d) {
		if (d.isEmpty()) {
			entity.setEndDate(null);
		} else {
			entity.setEndDate(d.toPersistenceTimestamp());
		}
	}


	public String getParticipant() {
		return entity.getParticipant();
	}


	public void setParticipant(String participant) {
		entity.setParticipant(participant);
	}


	public String getLocation() {
		return entity.getLocation();
	}


	public void setLocation(String location) {
		entity.setLocation(location);
	}


	@Override
	public String getDetails() {
		return entity.getDescription();
	}

	public void setDetails(String d) {
		entity.setDescription(d);
	}


	@Override
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address a) {
		this.address = (AddressDomain)a;
		if (!address.isEmpty() &&  getLocation() == null) {
			setLocation(address.getStreet() + ", " + address.getPostalCodeAndCity());
		}
	}


	@Override
	public List<PersonName> getParticipants() {
		return Collections.emptyList();
	}

	@Override
	public boolean isIndependentTermin() {
		return true;
	}


	public TerminEntity getEntity() {
		return entity;
	}

	@Override
	public Integer getId() {
		return entity.getId();
	}

	@Override
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public boolean isSameId(BaseDomain d) {
		return getId() != null && d.getId() != null && getId().equals(d.getId());
	}

	@Override
	public ImmoDateTime getCreateTimestamp() {
		return new ImmoDateTime(entity.getCreatedOn());
	}

	@Override
	public boolean isEmpty() {
		return getTitle() == null  ||  getTitle().isEmpty();
	}

	@Override
	public String getDomainName() {
		return "Termin";
	}

	@Override
	public ValidationIssues validate() {
		return null;
	}

}
