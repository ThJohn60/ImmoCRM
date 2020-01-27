package net.immocrm.domain;

import net.immocrm.db.EntityTool;
import net.immocrm.db.PreferencesEntity;
import net.immocrm.domain.valid.ValidationIssues;

public class PreferencesDomain extends AbstractDomain implements Preference {

	private final PreferencesEntity entity;
	private final String oldValue;


	public PreferencesDomain() {
		this(null);
	}

	public PreferencesDomain(PreferencesEntity e) {
		this.entity = e != null ? e : new PreferencesEntity();
		oldValue = e.getValue() != null ? e.getValue() : "";
	}


	public String getOldValue() {
		return oldValue;
	}

	public boolean isValueChanged() {
		return !oldValue.equals(getValue());
	}


	@Override
	public Integer getId() {
		return entity.getId();
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}

	@Override
	public String getValue() {
		return entity.getValue();
	}

	@Override
	public void setValue(String value) {
		entity.setValue(value);
	}

	@Override
	public boolean isInternal() {
		return entity.isInternal();
	}

	@Override
	public void setIntern(boolean intern) {
		entity.setInternal(intern);
	}


	@Override
	public boolean isEmpty() {
		return EntityTool.isNull(getValue());
	}

	@Override
	public String getDomainName() {
		return "Einstellungen";
	}

	@Override
	PreferencesEntity getEntity() {
		return entity;
	}


	@Override
	public ValidationIssues validate() {
		return ValidationIssues.emptyIssues;
	}

	@Override
	public String toString() {
		return getName() + ": " + getValue();
	}

}
