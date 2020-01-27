package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.util.List;

import net.immocrm.db.DocumentEntity;

public class DocumentDao extends AbstractDao<DocumentEntity> {

	public List<DocumentEntity> fetchByOrder(int id) {
		List<DocumentEntity> resultList = getEntityManager()
				.createNamedQuery("Document.fetchByOrder", DocumentEntity.class)
				.setParameter("orderId", id)
				.getResultList();
		return resultList;
	}

	public List<DocumentEntity> fetchByImmobilie(int id) {
		List<DocumentEntity> resultList = getEntityManager()
				.createNamedQuery("Document.fetchByImmobilie", DocumentEntity.class)
				.setParameter("immobilieId", id)
				.getResultList();
		return resultList;
	}

	@Override
	protected Class<DocumentEntity> getEntityClass() {
		return DocumentEntity.class;
	}

}
