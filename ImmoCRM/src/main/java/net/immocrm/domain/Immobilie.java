package net.immocrm.domain;

import java.util.EnumSet;
import java.util.List;

import net.immocrm.domain.img.DocumentDirectory;
import net.immocrm.domain.img.ImageDirectory;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.EnergieausweisTypeEnum;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.vc.Area;
import net.immocrm.domain.vc.Energieverbrauchskennwert;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.domain.vc.Price;
import net.immocrm.domain.vc.Year;

public interface Immobilie extends BaseDomain {

	Person getOwner();
	void setOwner(Person owner);

	Address getAddress();
	void setAddress(Address addresss);

	List<Order> getOrders();

	Merkmal getMerkmal(MerkmalType type);
	List<Merkmal> getMerkmalList(Category category);
	List<Merkmal> getMerkmalList(EnumSet<Category> categories);
	void setMerkmal(MerkmalType type, String value);
	void setMerkmal(MerkmalType t, boolean value);

	Area getArea();

	Price getHausgeld();
	void setHausgeld(Price p);
	Price getMiete();
	void setMiete(Price p);
	Year getBaujahr();
	void setBaujahr(Year y);
	Year getLastRenovationYear();
	void setLastRenovationYear(Year y);
	String getWohneinheit();
	void setWohneinheit(String wohneinheit);

	Area getWohnflaeche();
	void setWohnflaeche(Area a);
	Area getNutzflaeche();
	void setNutzflaeche(Area a);
	Area getGrundstueck();
	void setGrundstueck(Area a);
	ImmoDate getPurchaseDate();
	void setPurchaseDate(ImmoDate d);

	Price getPurchasePrice();
	void setPurchasePrice(Price p);
	NumberValue getRoomCnt();
	void setRoomCnt(NumberValue c);
	NumberValue getBalconyCnt();
	NumberValue getTerraceCnt();
	NumberValue getFloor();
	void setFloor(NumberValue v);
	void setTerraceCnt(NumberValue v);
	void setBalconyCnt(NumberValue v);

	String getZustandName();
	MerkmalType getZustand();
	void setZustand(MerkmalType type);

	String getInternalNotice();
	void setInternalNotice(String description);

	String getImmobilieTypeName();
	MerkmalType getImmobilieType();
	void setImmobilieType(MerkmalType type);

	ImmobilieCategoryEnum getImmobilieCategory();
	void setImmobilieCategory(ImmobilieCategoryEnum category);

	String getAusstattung();
	void setAusstattung(String ausstattung);

	ImageDirectory getImageDir();
	DocumentDirectory getDocDir();

	EnergieausweisTypeEnum getEnergieausweisType();
	void setEnergieausweisType(EnergieausweisTypeEnum type);
	Energieverbrauchskennwert getEnergieverbrauchskennwert();
	void setEnergieverbrauchskennwert(Energieverbrauchskennwert value);

	String getObjektbeschreibung();
	void setObjektbeschreibung(String text);
	String getLagebeschreibung();
	void setLagebeschreibung(String text);

}