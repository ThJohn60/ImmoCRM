package net.immocrm.domain.quality;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.MerkmalTypeEntity;
import net.immocrm.db.dao.MerkmalTypeDao;
import net.immocrm.domain.DomainFactory;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;

public class MerkmalTypeProvider {

	private final MerkmalTypeDao dao;

	private List<MerkmalTypeChangeable> merkmalTypes;


	public MerkmalTypeProvider() {
		this.dao = new MerkmalTypeDao();
		refresh();
	}


	public void refresh() {
		merkmalTypes = convert(dao.fetchAll());
	}

	private static List<MerkmalTypeChangeable> convert(List<MerkmalTypeEntity> entities) {
		List<MerkmalTypeChangeable> result = new ArrayList<>();
		for (MerkmalTypeEntity e : entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}


	public List<MerkmalType> getAllTypes() {
		return new ArrayList<MerkmalType>(merkmalTypes);
	}

	public MerkmalType getTypeByName(String name) {
		for (MerkmalTypeChangeable t : merkmalTypes) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}


	public List<MerkmalType> filterCategory(Category filterCategory, ImmobilieCategoryEnum merkmalType) {
		List<MerkmalType> result = new ArrayList<>();
		for (MerkmalTypeChangeable t : merkmalTypes) {
			if (t.getCategory() == filterCategory
					&& isTypeCompatible(t.getImmobileTypes(), merkmalType)
					&& t.isActive()) {
				result.add(t);
			}
		}
		return result;
	}

	private boolean isTypeCompatible(String immobilieTypes, ImmobilieCategoryEnum immobilieType) {
		return immobilieType.isCompatible(immobilieTypes);
	}

}
