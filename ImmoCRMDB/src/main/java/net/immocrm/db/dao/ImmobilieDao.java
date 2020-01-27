package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.util.List;

import net.immocrm.db.ImmobilieEntity;


public class ImmobilieDao extends AbstractDao<ImmobilieEntity> {

	public List<ImmobilieEntity> fetchByOwner(int ownerId) {
		List<ImmobilieEntity> resultList = getEntityManager()
				.createNamedQuery("Immobilie.fetchByOwner", ImmobilieEntity.class)
				.setParameter("ownerId", ownerId)
				.getResultList();
		return resultList;
	}

	public List<ImmobilieEntity> fetchByAddress(int addressId) {
		List<ImmobilieEntity> resultList = getEntityManager()
				.createNamedQuery("Immobilie.fetchByAddress", ImmobilieEntity.class)
				.setParameter("addressId", addressId)
				.getResultList();
		return resultList;
	}

	@Override
	protected Class<ImmobilieEntity> getEntityClass() {
		return ImmobilieEntity.class;
	}

	public List<ImmobilieEntity> fetch(String searchPattern, List<Integer> catIds) {
		if (catIds.isEmpty()) {
			if (searchPattern.isEmpty()) {
				return fetchAll();
			}
			return search(searchPattern);
		}
		List<ImmobilieEntity> resultList = getEntityManager()
				.createNamedQuery("Immobilie.searchByCategory", ImmobilieEntity.class)
				.setParameter("pattern", "%" + searchPattern.toLowerCase() + "%")
				.setParameter("catIds", catIds)
				.getResultList();
		return resultList;
	}

}
