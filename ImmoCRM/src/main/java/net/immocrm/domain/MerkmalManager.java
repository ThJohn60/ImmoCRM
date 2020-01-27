package net.immocrm.domain;

import static net.immocrm.domain.valid.ValidationTool.isNullOrEmpty;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.immocrm.db.MerkmalEntity;
import net.immocrm.domain.quality.CurrencyMerkmalDomain;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.quality.NumberMerkmalDomain;
import net.immocrm.domain.quality.SquareMerkmalDomain;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.vc.Area;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.domain.vc.Price;
import net.immocrm.domain.vc.ValuesParser;
import net.immocrm.domain.vc.Year;

public class MerkmalManager {

	static final String LAST_RENOVATION_YEAR = "Letztes Renovierungsjahr";
	static final String ZUSTAND = "Zustand";
	static final String HAUSGELD = "Hausgeld";
	static final String RENT_PRICE = "Mietpreis";
	static final String PURCHASE_PRICE = "Kaufpreis";
	static final String PURCHASE_DATE = "Kaufdatum";
	static final String TERRACE_CNT = "Anz. Terrassen";
	static final String BALCONY_CNT = "Anz. Balkone";
	static final String ROOM_CNT = "Anz. Zimmer";
	static final String CONSTRUCTION_YEAR = "Baujahr";
	static final String AREA = "Wohnfläche";
	static final String NUTZFLAECHE = "Nutzfläche";
	static final String GRUNDSTUECK = "Grundstück";
	static final String BAUJAHR = "Baujahr";
	static final String FLOOR = "Etage";


	private final Map<String, MerkmalDomain> merkmalByType;


	public MerkmalManager(List<MerkmalEntity> merkmalList) {
		merkmalByType = new HashMap<>();
		for (MerkmalEntity e : merkmalList) {
//			System.out.println("Fetch " + e + ", Immo-Id=" + e.getImmobilieId());
			merkmalByType.put(e.getType().getName(), DomainFactory.createDomain(e));
		}
	}


	public Merkmal getMerkmal(MerkmalType type) {
		return merkmalByType.get(type.getName());
	}

	public Merkmal getMerkmal(String typeName) {
		return merkmalByType.get(typeName);
	}


	public Area getArea(String areaName) {
		Merkmal m = getMerkmal(areaName);
		return  m != null ? ((SquareMerkmalDomain)m).getArea() : Area.EMTPTY_AREA;
	}

	public void setArea(MerkmalType type, Area a) {
		if (isNullOrEmpty(a)) {
			setMerkmalDomain(type, null);
		} else {
			SquareMerkmalDomain m = new SquareMerkmalDomain(type, a);
			setMerkmalDomain(type, m);
		}
	}

	public Year getYear(String typeName) {
		Merkmal m = getMerkmal(typeName);
		if (m != null) {
			try {
				return ValuesParser.parseYear(m.getValue());
			} catch (ParseException e) {
				// can't happen
			}
		}
		return new Year();
	}

	public void setYear(MerkmalType type, Year y) {
		if (y == null  ||  y.isEmpty()) {
			setMerkmalDomain(type, null);
		} else {
			MerkmalDomain m = new NumberMerkmalDomain(type, y);
			setMerkmalDomain(type, m);
		}
	}

	public void setImmoDate(MerkmalType type, ImmoDate d) {
		if (d == null  ||  d.isEmpty()) {
			setMerkmalDomain(type, null);
		} else {
			MerkmalDomain m = new MerkmalDomain(type, d.toString());
			setMerkmalDomain(type, m);
		}
	}


	public Price getPrice(String priceName) {
		Merkmal m = getMerkmal(priceName);
		return m != null ? ((CurrencyMerkmalDomain)m).getPrice() : Price.EMTPTY_PRICE;
	}

	public void setPrice(MerkmalType type, Price p) {
		if (isNullOrEmpty(p)) {
			setMerkmalDomain(type, null);
		} else {
			MerkmalDomain m = new CurrencyMerkmalDomain(type, p);
			setMerkmalDomain(type, m);
		}
	}

	public NumberValue getNumber(String numberName) {
		Merkmal m = getMerkmal(numberName);
		return m != null ? ((NumberMerkmalDomain)m).getNumber() : new NumberValue();
	}

	public void setNumber(MerkmalType type, NumberValue n) {
		if (isNullOrEmpty(n)) {
			setMerkmalDomain(type, null);
		} else {
			MerkmalDomain m = new NumberMerkmalDomain(type, n);
			setMerkmalDomain(type, m);
		}
	}


	public List<MerkmalEntity> getMerkmalList() {
		List<MerkmalEntity> result = new ArrayList<>();
		for (MerkmalDomain m : merkmalByType.values()) {
			result.add(m.getEntity());
		}
		return result;
	}

	public List<Merkmal> getMerkmalList(Category category) {
		List<Merkmal> result = new ArrayList<>();
		for (MerkmalDomain m: merkmalByType.values()) {
			if (m.getCategory() == category) {
				if (m.isBooleanType() && m.isSet() || !m.isBooleanType() && !m.isEmpty()) {
					result.add(m);
				}
			}
		}
		return result;
	}

	public List<Merkmal> getMerkmalList(EnumSet<Category> categories) {
		List<Merkmal> result = new ArrayList<>();
		for (MerkmalDomain m: merkmalByType.values()) {
			if (categories.contains(m.getCategory())) {
				if (m.isBooleanType() && m.isSet() || !m.isBooleanType() && !m.isEmpty()) {
					result.add(m);
				}
			}
		}
		return result;
	}


	public void setMerkmal(MerkmalType t, String value) {
		MerkmalDomain m = DomainFactory.createMerkmalDomain(t, value);
		setMerkmalDomain(t, m);
	}

	public void setMerkmalDomain(MerkmalType t, MerkmalDomain m) {
		setMerkmal(t.getName(), m);
	}

	private void setMerkmal(String typeName, MerkmalDomain m) {
		if (m != null  &&  !m.isEmpty()) {
			MerkmalDomain mPersistent = merkmalByType.get(typeName);
			if (mPersistent != null) {
//				System.out.println("Update " + mPersistent + " with " + m + ", Immo-Id=" + mPersistent.getImmobilieId());
				mPersistent.getEntity().setValue(m.getEntity().getValue());
			} else {
//				System.out.println("Insert " + m);
				merkmalByType.put(typeName, m);
			}
		} else {
			merkmalByType.remove(typeName);
		}
	}


}
