package net.immocrm.db;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="Preferences")
@Table(
	name="preferences",
	uniqueConstraints =	@UniqueConstraint(columnNames={"name" })
)
@NamedQueries(value={
		@NamedQuery(name="Preferences.fetchAll", query="SELECT b FROM Preferences b"),
		@NamedQuery(name="Preferences.fetchByName", query="SELECT b FROM Preferences b WHERE b.name = :name"),
	})
public class PreferencesEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="preferencesSeq")
    @SequenceGenerator(name="preferencesSeq",sequenceName="PREFERENCES_SEQ", allocationSize=1)
	private Integer id;

	@Column(length=24)
	private String name;

	@Column(length=100)
	private String value;

	private boolean internal;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
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
		return "Pref[" + name + ": " + value + "]";
	}

}
