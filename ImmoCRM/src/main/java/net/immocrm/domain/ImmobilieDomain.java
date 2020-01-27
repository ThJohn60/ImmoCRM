package net.immocrm.domain;

import java.util.EnumSet;
import java.util.List;

import net.immocrm.db.ImmobilieEntity;
import net.immocrm.db.MerkmalTypeEntity;
import net.immocrm.domain.img.DocumentDirectory;
import net.immocrm.domain.img.ImageDirectory;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.EnergieausweisTypeEnum;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.valid.ImmobilieValidator;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.Area;
import net.immocrm.domain.vc.Energieverbrauchskennwert;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.domain.vc.Price;
import net.immocrm.domain.vc.Year;

class ImmobilieDomain extends AbstractDomain implements Immobilie {

	private final ImmobilieEntity entity;

	private final AddressDomain address;
	private final MerkmalManager merkmalMan;
	private final MerkmalTypeManager merkmaltypeMan;

	private MerkmalTypeDomain immobilieType;
	private PersonDomain owner;
	private ImageDirectory imageDir;
	private DocumentDirectory docDir;


	public ImmobilieDomain(ImmobilieEntity e) {
		this.entity = e;
		owner = DomainFactory.createDomain(entity.getOwner());
		address = DomainFactory.createDomain(entity.getAddress());
		immobilieType = entity.getImmobilieType() != null ? DomainFactory.createDomain(entity.getImmobilieType()) : null;
		merkmaltypeMan = new MerkmalTypeManager();

		entity.setOwner(owner.getEntity());
		merkmalMan = new MerkmalManager(entity.getMerkmalList());
	}

	@Override
	public String getImmobilieTypeName() {
		if (immobilieType != null) {
			return immobilieType.getName();
		}
		ImmobilieCategoryEnum cat = getImmobilieCategory();
		if (cat != null) {
			return cat.name();
		}
		return "Immobilie";
	}

	@Override
	public MerkmalType getImmobilieType() {
		return immobilieType;
	}

	@Override
	public void setImmobilieType(MerkmalType type) {
		if (type == null) {
			entity.setImmobilieType(null);
			immobilieType = null;
		} else {
			MerkmalTypeEntity typeEntity = ((MerkmalTypeDomain)type).getEntity();
			entity.setImmobilieType(typeEntity);
			immobilieType = DomainFactory.createDomain(typeEntity);
		}
	}

	@Override
	public String getWohneinheit() {
		return entity.getWohneinheit();
	}

	@Override
	public void setWohneinheit(String wohneinheit) {
		entity.setWohneinheit(wohneinheit);
	}

	@Override
	public MerkmalType getZustand() {
		MerkmalTypeEntity z = entity.getZustand();
		if (z != null) {
			return DomainFactory.createDomain(z);
		}
		return null;
	}

	@Override
	public String getZustandName() {
		MerkmalType zustand = getZustand();
		if (zustand != null) {
			return zustand.getName();
		}
		return "";
	}

	@Override
	public void setZustand(MerkmalType type) {
		if (type == null) {
			entity.setZustand(null);
		} else {
			MerkmalTypeEntity typeEntity = ((MerkmalTypeDomain)type).getEntity();
			entity.setZustand(typeEntity);
		}
	}

	@Override
	public ImmobilieCategoryEnum getImmobilieCategory() {
		if (entity.getImmobilieCategory() != null) {
			return ImmobilieCategoryEnum.getById(entity.getImmobilieCategory());
		}
		return null;
	}

	@Override
	public void setImmobilieCategory(ImmobilieCategoryEnum category) {
		entity.setImmobilieCategory(category.getId());
	}


	@Override
	public Merkmal getMerkmal(MerkmalType type) {
		return merkmalMan.getMerkmal(type);
	}

	@Override
	public List<Merkmal> getMerkmalList(EnumSet<Category> categories) {
		return merkmalMan.getMerkmalList(categories);
	}

	@Override
	public List<Merkmal> getMerkmalList(Category category) {
		return merkmalMan.getMerkmalList(category);
	}

	@Override
	public Area getArea() {
		return merkmalMan.getArea(MerkmalManager.AREA);
	}

	@Override
	public void setMerkmal(MerkmalType t, String value) {
		merkmalMan.setMerkmal(t, value);
	}

	@Override
	public void setMerkmal(MerkmalType t, boolean value) {
		merkmalMan.setMerkmal(t, value ? Boolean.TRUE.toString() : null);
	}

	@Override
	public AddressDomain getAddress() {
		return address;
	}

	@Override
	public void setAddress(Address addresss) {
		address.setStreet(addresss.getStreet());
		address.setStreet2(addresss.getStreet2());
		address.setPostalCode(addresss.getPostalCode());
		address.setCity(addresss.getCity());
	}


	public String getCity() {
		return address.getCity();
	}


	@Override
	public PersonDomain getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Person owner) {
		this.owner = (PersonDomain)owner;
		if (owner != null) {
			this.entity.setOwner(this.owner.getEntity());
		}
	}

	@Override
	public List<Order> getOrders() {
		return OrderReader.INSTANCE.fetchByImmobilie(getId());
	}


	@Override
	public String getAusstattung() {
		return entity.getAusstattung();
	}

	@Override
	public void setAusstattung(String ausstattung) {
		entity.setAusstattung(ausstattung);
	}


	@Override
	public String getInternalNotice() {
		return entity.getNotice();
	}

	@Override
	public void setInternalNotice(String text) {
		entity.setNotice(text);
	}


	@Override
	public String getObjektbeschreibung() {
		return entity.getObjektbeschreibung();
	}

	@Override
	public void setObjektbeschreibung(String text) {
		entity.setObjektbeschreibung(text);
	}

	@Override
	public String getLagebeschreibung() {
		return entity.getLagebeschreibung();
	}

	@Override
	public void setLagebeschreibung(String text) {
		entity.setLagebeschreibung(text);
	}

	@Override
	public EnergieausweisTypeEnum getEnergieausweisType() {
		if (entity.getEnergieausweisType() != null) {
			return EnergieausweisTypeEnum.getById(entity.getEnergieausweisType());
		}
		return EnergieausweisTypeEnum.Unbekannt;
	}

	@Override
	public void setEnergieausweisType(EnergieausweisTypeEnum t) {
//		EnergieausweisTypeEntity e = (t != null) ? ReferenceTableProvider.INSTANCE.getEnergieausweisType(t) : null;
		entity.setEnergieausweisType(t == null ? EnergieausweisTypeEnum.Unbekannt.getId() : t.getId());
	}

	@Override
	public Energieverbrauchskennwert getEnergieverbrauchskennwert() {
		return new Energieverbrauchskennwert(entity.getEnergieverbrauchskennwert());
	}

	@Override
	public void setEnergieverbrauchskennwert(Energieverbrauchskennwert value) {
		entity.setEnergieverbrauchskennwert(value.getValue());
	}


	@Override
	public boolean isEmpty() {
		return address.isEmpty() || owner.isEmpty();
	}

	@Override
	ImmobilieEntity getEntity() {
		return entity;
	}

	@Override
	public String getDomainName() {
		return "Immobilie";
	}

	@Override
	public ImageDirectory getImageDir() {
		if (imageDir == null) {
			imageDir = new ImageDirectory(this);
		}
		return imageDir;
	}

	@Override
	public DocumentDirectory getDocDir() {
		if (docDir == null) {
			docDir = new DocumentDirectory(this);
		}
		return docDir;
	}



	@Override
	public Price getHausgeld() {
		return merkmalMan.getPrice(MerkmalManager.HAUSGELD);
	}

	@Override
	public void setHausgeld(Price p) {
		merkmalMan.setPrice(merkmaltypeMan.getTypeByName(MerkmalManager.HAUSGELD), p);
	}

	@Override
	public Price getPurchasePrice() {
		return merkmalMan.getPrice(MerkmalManager.PURCHASE_PRICE);
	}

	@Override
	public void setPurchasePrice(Price p) {
		merkmalMan.setPrice(merkmaltypeMan.getTypeByName(MerkmalManager.PURCHASE_PRICE), p);
	}


	@Override
	public Price getMiete() {
		return merkmalMan.getPrice(MerkmalManager.RENT_PRICE);
	}

	@Override
	public void setMiete(Price p) {
		merkmalMan.setPrice(merkmaltypeMan.getTypeByName(MerkmalManager.RENT_PRICE), p);
	}

	@Override
	public Year getBaujahr() {
		return merkmalMan.getYear(MerkmalManager.BAUJAHR);
	}

	@Override
	public void setBaujahr(Year y) {
		MerkmalType type = merkmaltypeMan.getTypeByName(MerkmalManager.BAUJAHR);
		merkmalMan.setYear(type, y);
	}

	@Override
	public Year getLastRenovationYear() {
		return merkmalMan.getYear(MerkmalManager.LAST_RENOVATION_YEAR);
	}

	@Override
	public void setLastRenovationYear(Year y) {
		MerkmalType type = merkmaltypeMan.getTypeByName(MerkmalManager.LAST_RENOVATION_YEAR);
		merkmalMan.setYear(type, y);
	}


	@Override
	public Area getWohnflaeche() {
		return merkmalMan.getArea(MerkmalManager.AREA);
	}

	@Override
	public void setWohnflaeche(Area a) {
		merkmalMan.setArea(merkmaltypeMan.getTypeByName(MerkmalManager.AREA), a);
	}

	@Override
	public Area getNutzflaeche() {
		return merkmalMan.getArea(MerkmalManager.NUTZFLAECHE);
	}

	@Override
	public void setNutzflaeche(Area a) {
		merkmalMan.setArea(merkmaltypeMan.getTypeByName(MerkmalManager.NUTZFLAECHE), a);
	}

	@Override
	public Area getGrundstueck() {
		return merkmalMan.getArea(MerkmalManager.GRUNDSTUECK);
	}

	@Override
	public void setGrundstueck(Area a) {
		merkmalMan.setArea(merkmaltypeMan.getTypeByName(MerkmalManager.GRUNDSTUECK), a);
	}


	@Override
	public NumberValue getBalconyCnt() {
		return merkmalMan.getNumber(MerkmalManager.BALCONY_CNT);
	}

	@Override
	public void setBalconyCnt(NumberValue v) {
		merkmalMan.setNumber(merkmaltypeMan.getTypeByName(MerkmalManager.BALCONY_CNT), v);
	}

	@Override
	public NumberValue getTerraceCnt() {
		return merkmalMan.getNumber(MerkmalManager.TERRACE_CNT);
	}

	@Override
	public void setTerraceCnt(NumberValue v) {
		merkmalMan.setNumber(merkmaltypeMan.getTypeByName(MerkmalManager.TERRACE_CNT), v);
	}

	@Override
	public NumberValue getFloor() {
		return merkmalMan.getNumber(MerkmalManager.FLOOR);
	}

	@Override
	public void setFloor(NumberValue v) {
		merkmalMan.setNumber(merkmaltypeMan.getTypeByName(MerkmalManager.FLOOR), v);
	}


	@Override
	public ImmoDate getPurchaseDate() {
		Merkmal merkmal = merkmalMan.getMerkmal(MerkmalManager.PURCHASE_DATE);
		if (merkmal != null) {
//			return new ImmoDate(merkmal.getValue());				// TODO
		}
		return new ImmoDate();				// TODO
	}

	@Override
	public void setPurchaseDate(ImmoDate d) {
		merkmalMan.setImmoDate(merkmaltypeMan.getTypeByName(MerkmalManager.PURCHASE_DATE), d);
	}


	@Override
	public NumberValue getRoomCnt() {
		return merkmalMan.getNumber(MerkmalManager.ROOM_CNT);
	}

	@Override
	public void setRoomCnt(NumberValue c) {
		MerkmalType type = merkmaltypeMan.getTypeByName(MerkmalManager.ROOM_CNT);
		merkmalMan.setNumber(type, c);
	}

	@Override
	public ValidationIssues validate() {
		return new ImmobilieValidator().validate(this);
	}

	public void beforePersistence() {
		if (address.isEmpty()) {
			entity.setAddress(null);
		} else {
			entity.setAddress(address.getEntity());
		}
		entity.setMerkmalList(merkmalMan.getMerkmalList());
	}


	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return entity.equals(((ImmobilieDomain)obj).getEntity());
	}

	@Override
	public String toString() {
		return getImmobilieTypeName() + " in " + getCity();
	}

}
