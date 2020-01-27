package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.util.List;

import net.immocrm.db.ImmobilieTransactionEntity;


public class ImmobilieTransactionDao extends AbstractDao<ImmobilieTransactionEntity> {

	@Override
	protected Class<ImmobilieTransactionEntity> getEntityClass() {
		return ImmobilieTransactionEntity.class;
	}

	public List<ImmobilieTransactionEntity> fetchByImmobilie(Integer id) {
		List<ImmobilieTransactionEntity> resultList = getEntityManager()
				.createNamedQuery("ImmobilieTransaction.fetchByImmobilie", ImmobilieTransactionEntity.class)
				.setParameter("immobilieId", id)
				.getResultList();
		return resultList;
	}

	public List<ImmobilieTransactionEntity> fetchByPerson(Integer id) {
		List<ImmobilieTransactionEntity> resultList = getEntityManager()
				.createNamedQuery("ImmobilieTransaction.fetchByPerson", ImmobilieTransactionEntity.class)
				.setParameter("personId", id)
				.getResultList();
		return resultList;
	}

}
