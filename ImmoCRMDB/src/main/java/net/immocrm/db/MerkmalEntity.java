package net.immocrm.db;

import java.sql.Timestamp;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="Quality")
@Table(
	name="quality",
	uniqueConstraints =	@UniqueConstraint(columnNames={"type_id","immobilie_id" })
)
@NamedQueries(value={
		@NamedQuery(name="Quality.fetchAll", query="SELECT b FROM Quality b"),
		@NamedQuery(name="Quality.fetchByImmobilieId", query="SELECT b FROM Quality b WHERE b.immobilieId = :immobilieId"),
	})
public class MerkmalEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="qualitySeq")
    @SequenceGenerator(name="qualitySeq",sequenceName="QUALITY_SEQ", allocationSize=1)
	private Integer id;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	private MerkmalTypeEntity type;

	@Column(length=40)
	private String value;

	@Column(name="immobilie_id")
	private Integer immobilieId;

	@Column(name="immo_trans_id")
	private Integer immoTransId;


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

	public MerkmalTypeEntity getType() {
		return type;
	}

	public void setType(MerkmalTypeEntity type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getImmobilieId() {
		return immobilieId;
	}

	public void setImmobilieId(Integer id) {
		this.immobilieId = id;
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
		return "Quality[" + type + ": " + value + "]";
	}

}
