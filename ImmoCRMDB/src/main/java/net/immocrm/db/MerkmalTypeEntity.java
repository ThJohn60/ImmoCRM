package net.immocrm.db;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

@Entity(name="QualityType")
@Table(name="quality_Type")
@Cache(type=CacheType.FULL)
@NamedQueries(value={
		@NamedQuery(name="QualityType.fetchAll", query="SELECT b FROM QualityType b"),
	})
public class MerkmalTypeEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="qualityTypeSeq")
    @SequenceGenerator(name="qualityTypeSeq",sequenceName="QUALITY_TYPE_SEQ", allocationSize=1)
	private Integer id;

	@Column(length=64)
	private String name;

	@Column(nullable=false)
	private boolean active;

	@Column(nullable=false)
	private boolean internal;

	@Column(name="property_types", length=4)
	private String propertyTypes;

	@Enumerated(EnumType.STRING)
	@Column(length=8)
	private MerkmalTypeEnum category;

	@Column(name="data_type", length=8)
	private String dataType;

	@Column(length=100)
	private String description;

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

	public String getImmobilieTypes() {
		return propertyTypes;
	}

	public void setImmobilieTypes(String propertyTypes) {
		this.propertyTypes = propertyTypes;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public MerkmalTypeEnum getMerkmalTypeCategory() {
		return category;
	}

	public void setCategory(MerkmalTypeEnum category) {
		this.category = category;
	}

	@Override
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	@Override
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "MerkmalTypeEntity[" + name + "]";
	}

}
