package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.util.List;

import net.immocrm.db.OrderHistoryEntity;


public class OrderHistoryDao extends AbstractDao<OrderHistoryEntity> {
	
	public List<OrderHistoryEntity> fetchByOrder(int orderId) {
		List<OrderHistoryEntity> resultList = getEntityManager()
				.createNamedQuery("OrderHistory.fetchByOrder", OrderHistoryEntity.class)
				.setParameter("orderId", orderId)
				.getResultList();
		return resultList;
	}

	@Override
	protected Class<OrderHistoryEntity> getEntityClass() {
		return OrderHistoryEntity.class;
	}
	
}
