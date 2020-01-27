package net.immocrm.db;

import java.sql.Timestamp;
import java.util.ArrayList;
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

@Entity(name="Immobilie")
@Table(name="immobilie")
@NamedQueries(value={
	@NamedQuery(name="Immobilie.fetchAll", query="SELECT i FROM Immobilie i ORDER BY i.address.city, i.address.street"),
	@NamedQuery(name="Immobilie.search", query="SELECT i FROM Immobilie i WHERE i.owner.search LIKE :pattern OR i.address.search LIKE :pattern ORDER BY i.address.city, i.address.street"),
	@NamedQuery(name="Immobilie.searchByCategory", query="SELECT i FROM Immobilie i WHERE (i.owner.search LIKE :pattern OR i.address.search LIKE :pattern) AND i.immobilieCategory IN :catIds ORDER BY i.address.city, i.address.street"),
	@NamedQuery(name="Immobilie.fetchByAddress", query="SELECT i FROM Immobilie i WHERE i.address.id = :addressId"),
	@NamedQuery(name="Immobilie.fetchByOwner", query="SELECT i FROM Immobilie i WHERE i.owner.id = :ownerId"),
})
public class ImmobilieEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="propSeq")
    @SequenceGenerator(name="propSeq", sequenceName="PROPERTY_SEQ", allocationSize=1)
	private Integer id;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH,CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinColumn(name="owner_id")
	private PersonEntity owner;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="adr_id")
	private AddressEntity address;

	@Column(name="category_id")
	private Integer immobilieCategory;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="immo_type_id")
	private MerkmalTypeEntity immobilieType;

	private String wohneinheit;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name="immobilie_id")
	private List<MerkmalEntity> qualities;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="zustand_id")
	private MerkmalTypeEntity zustand;

	@Column(name="energieausweistyp_id")
	private Integer energieausweisType;

	private Double energieverbrauchskennwert;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="notice_id")
	private NoticeEntity notice;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="ausstattungbeschreibung_id")
	private NoticeEntity ausstattung;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="objektbeschreibung_id")
	private NoticeEntity objektbeschreibung;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="lagebeschreibung_id")
	private NoticeEntity lagebeschreibung;

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

	public PersonEntity getOwner() {
		return owner;
	}

	public void setOwner(PersonEntity owner) {
		this.owner = owner;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public MerkmalTypeEntity getImmobilieType() {
		return immobilieType;
	}

	public void setImmobilieType(MerkmalTypeEntity type) {
		this.immobilieType = type;
	}

	public String getWohneinheit() {
		return wohneinheit;
	}

	public void setWohneinheit(String wohneinheit) {
		this.wohneinheit = wohneinheit;
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

	public String getObjektbeschreibung() {
		if (objektbeschreibung != null) {
			return objektbeschreibung.getText();
		}
		return "";
	}

	public void setObjektbeschreibung(String text) {
		if (objektbeschreibung == null) {
			if (text.isEmpty()) {
				return;
			}
			objektbeschreibung = new NoticeEntity();
		}
		this.objektbeschreibung.setText(text);
	}

	public String getLagebeschreibung() {
		if (lagebeschreibung != null) {
			return lagebeschreibung.getText();
		}
		return "";
	}

	public void setLagebeschreibung(String text) {
		if (lagebeschreibung == null) {
			if (text.isEmpty()) {
				return;
			}
			lagebeschreibung = new NoticeEntity();
		}
		this.lagebeschreibung.setText(text);
	}

	public String getAusstattung() {
		if (ausstattung != null) {
			return ausstattung.getText();
		}
		return "";
	}

	public void setAusstattung(String text) {
		if (ausstattung == null) {
			if (text.isEmpty()) {
				return;
			}
			ausstattung = new NoticeEntity();
		}
		this.ausstattung.setText(text);
	}

	public List<MerkmalEntity> getMerkmalList() {
		if (qualities == null) {
			qualities = new ArrayList<>();
		}
		return qualities;
	}

	public void setMerkmalList(List<MerkmalEntity> qualities) {
		this.qualities = qualities;
	}

	public Integer getImmobilieCategory() {
		return immobilieCategory;
	}

	public void setImmobilieCategory(Integer cat) {
		this.immobilieCategory = cat;
	}

	public Integer getEnergieausweisType() {
		return energieausweisType;
	}

	public void setEnergieausweisType(Integer eat) {
		this.energieausweisType = eat;
	}

	public Double getEnergieverbrauchskennwert() {
		return energieverbrauchskennwert;
	}

	public void setEnergieverbrauchskennwert(Double evk) {
		this.energieverbrauchskennwert = evk;
	}

	public MerkmalTypeEntity getZustand() {
		return zustand;
	}

	public void setZustand(MerkmalTypeEntity zustand) {
		this.zustand = zustand;
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
		return "Immobilie[" + immobilieType + ", " + address + "]";
	}

}
