package net.immocrm.domain;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.MerkmalTypeEntity;
import net.immocrm.db.dao.MerkmalTypeDao;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.quality.MerkmalTypeChangeable;
import net.immocrm.domain.quality.MerkmalTypeProvider;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.valid.DbSaveException;

public class MerkmalTypeManager {

	private static final MerkmalTypeProvider typeProvider = new MerkmalTypeProvider();

	public static MerkmalTypeChangeable emptyDomain() {
		return new MerkmalTypeDomain();
	}


	private final MerkmalTypeDao dao;


	public MerkmalTypeManager() {
		this.dao = new MerkmalTypeDao();
	}

	public List<MerkmalType> getTypeOfCategory(Category cat, ImmobilieCategoryEnum immoType) {
		return new ArrayList<>(typeProvider.filterCategory(cat, immoType));
	}

	public MerkmalType getTypeByName(String name) {
		return typeProvider.getTypeByName(name);
	}

	public List<MerkmalType> getAllTypes() {
		return typeProvider.getAllTypes();
	}


	public MerkmalTypeChangeable fetchById(Integer id) {
		return DomainFactory.createDomain(dao.fetchById(id));
	}

	public List<MerkmalTypeChangeable> fetchAll() {
		return convert(dao.fetchAll());
	}

	private List<MerkmalTypeChangeable> convert(List<MerkmalTypeEntity> entities) {
		List<MerkmalTypeChangeable> result = new ArrayList<>();
		for (MerkmalTypeEntity e : entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}

	public void save(MerkmalTypeChangeable mt) {
		try {
			dao.saveCommitted(((MerkmalTypeDomain)mt).getEntity());
			typeProvider.refresh();
		} catch (Exception e) {
			throw new DbSaveException(e);
		}
	}

}
