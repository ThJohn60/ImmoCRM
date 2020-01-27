package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.util.List;

import net.immocrm.db.PersonEntity;


public class PersonDao extends AbstractDao<PersonEntity> {
	
	public List<PersonEntity> fetchByAddress(int addressId) {
		List<PersonEntity> resultList = getEntityManager()
				.createNamedQuery("Person.fetchByAddress", PersonEntity.class)
				.setParameter("addressId", addressId)
				.getResultList();
		return resultList;
	}

	@Override
	protected Class<PersonEntity> getEntityClass() {
		return PersonEntity.class;
	}

}
