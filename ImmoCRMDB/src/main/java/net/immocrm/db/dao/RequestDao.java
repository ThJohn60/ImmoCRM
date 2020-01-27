package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.time.LocalDate;
import java.util.List;

import net.immocrm.db.RequestEntity;
import net.immocrm.db.tools.EntityUtil;

public class RequestDao extends AbstractDao<RequestEntity> {

	public List<RequestEntity> fetchByPurchaser(int purchaserId) {
		List<RequestEntity> resultList = getEntityManager()
				.createNamedQuery("Request.fetchByPurchaser", RequestEntity.class)
				.setParameter("purchaserId", purchaserId)
				.getResultList();
		return resultList;
	}

	public List<RequestEntity> fetchByOrder(int orderId) {
		List<RequestEntity> resultList = getEntityManager()
				.createNamedQuery("Request.fetchByOrder", RequestEntity.class)
				.setParameter("orderId", orderId)
				.getResultList();
		return resultList;
	}

	public List<RequestEntity> searchInOrder(Integer orderId, String pattern) {
		List<RequestEntity> resultList = getEntityManager()
				.createNamedQuery("Request.fetchByOrderAndPattern", RequestEntity.class)
				.setParameter("pattern", "%" + pattern.toLowerCase() + "%")
				.setParameter("orderId", orderId)
				.getResultList();
		return resultList;
	}

	public List<RequestEntity> fetchByDates(LocalDate startDate, LocalDate endDate) {
		List<RequestEntity> resultList = getEntityManager()
				.createNamedQuery("Request.fetchByDates", RequestEntity.class)
				.setParameter("start", EntityUtil.toDate(startDate))
				.setParameter("end", EntityUtil.toDate(endDate))
				.getResultList();
		return resultList;
	}


	@Override
	protected Class<RequestEntity> getEntityClass() {
		return RequestEntity.class;
	}

}
