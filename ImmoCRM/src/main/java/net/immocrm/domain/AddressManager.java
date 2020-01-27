package net.immocrm.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.immocrm.db.AddressEntity;
import net.immocrm.db.dao.AddressDao;

public class AddressManager {

	private final AddressDao dao;


	public AddressManager() {
		dao = new AddressDao();
	}


	public List<Address> fetchAll() {
		List<AddressEntity> entities = dao.fetchAll();
		return new ArrayList<>(convertAndFilter(entities));
	}

	public List<Address> search(String pattern) {
		List<AddressEntity> found = dao.search(pattern);
		return new ArrayList<>(convertAndFilter(found));
	}

	private Set<Address> convertAndFilter(List<AddressEntity> entities) {
		Set<Address> result = new HashSet<>();
		for (AddressEntity e: entities) {
			AddressDomain address = DomainFactory.createDomain(e);
			if (!address.isEmpty()) {
				result.add(DomainFactory.createDomain(e));
			}
		}
		return result;
	}


	public void save(Address address) {
		persist((AddressDomain)address);
	}

	void persist(AddressDomain address) {
		try {
			dao.startTransaction();
			address.beforePersistence();
			dao.copy(address.getEntity());
			dao.commit();
		} catch (RuntimeException e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
			throw e;
		}
	}

	public void delete(AddressDomain domain) {
		dao.remove(domain.getEntity());
	}

}
