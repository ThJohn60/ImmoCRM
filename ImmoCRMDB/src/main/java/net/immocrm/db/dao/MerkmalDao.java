package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.util.List;

import javax.persistence.Query;

import net.immocrm.db.ImmobilieEntity;
import net.immocrm.db.MerkmalEntity;

public class MerkmalDao extends AbstractDao<MerkmalEntity> {

	public List<MerkmalEntity> fetchByImmobilieId(int immoId) {
		List<MerkmalEntity> resultList = getEntityManager()
				.createNamedQuery("Quality.fetchByImmobilieId", MerkmalEntity.class)
				.setParameter("immobilieId", immoId)
				.getResultList();
		return resultList;
	}

	@Override
	protected Class<MerkmalEntity> getEntityClass() {
		return MerkmalEntity.class;
	}

	public void deleteAllOfImmobilie(ImmobilieEntity immoEntity) {
		String stmt = "DELETE FROM Quality b WHERE b.immobilieId = :immoId";
		Query query = getEntityManager().createQuery(stmt);
		query.setParameter("immoId", immoEntity.getId());
		query.executeUpdate();
		getEntityManager().flush();
	}

}
