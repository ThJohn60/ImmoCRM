package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.TypedQuery;

import net.immocrm.db.EntityTool;
import net.immocrm.db.OrderEntity;
import net.immocrm.db.tools.EntityUtil;


public class OrderDao extends AbstractDao<OrderEntity> {

	public List<OrderEntity> fetchByBuyer(int buyerId) {
		List<OrderEntity> resultList = getEntityManager()
				.createNamedQuery("Order.fetchByBuyer", OrderEntity.class)
				.setParameter("buyerId", buyerId)
				.getResultList();
		return resultList;
	}

	public List<OrderEntity> fetchByNotar(int notarId) {
		List<OrderEntity> resultList = getEntityManager()
				.createNamedQuery("Order.fetchByNotar", OrderEntity.class)
				.setParameter("notarId", notarId)
				.getResultList();
		return resultList;
	}

	public List<OrderEntity> fetchByProvider(int providerId) {
		List<OrderEntity> resultList = getEntityManager()
				.createNamedQuery("Order.fetchByProvider", OrderEntity.class)
				.setParameter("providerId", providerId)
				.getResultList();
		return resultList;
	}

	public List<OrderEntity> fetchByImmobilie(int propertyId) {
		List<OrderEntity> resultList = getEntityManager()
				.createNamedQuery("Order.fetchByProperty", OrderEntity.class)
				.setParameter("propertyId", propertyId)
				.getResultList();
		return resultList;
	}

	public List<OrderEntity> fetchByDates(LocalDate startDate, LocalDate endDate) {
		List<OrderEntity> resultList = getEntityManager()
				.createNamedQuery("Order.fetchByDates", OrderEntity.class)
				.setParameter("start", EntityUtil.toDate(startDate))
				.setParameter("end", EntityUtil.toDate(endDate))
				.getResultList();
		return resultList;
	}


	public List<OrderEntity> search(String pattern, boolean activeOnly, List<Integer> otEntities, List<Integer> catIds) {
		String jpqlString = searchJpqlString(pattern, activeOnly, otEntities, catIds);
		TypedQuery<OrderEntity> query = getEntityManager().createQuery(jpqlString, OrderEntity.class);
		setQueryParameter(query, pattern, otEntities, catIds);
		List<OrderEntity> result = query.getResultList();
		return result;
	}

	private String searchJpqlString(String pattern, boolean activeOnly, List<Integer> otEntities, List<Integer> catIds) {
		StringBuilder jpql = new StringBuilder("SELECT b FROM Order b WHERE");
		if (!EntityTool.isEmpty(pattern)) {
			jpql.append(" (b.customer.search LIKE :pattern OR b.immobilie.address.search LIKE :pattern OR b IN (SELECT r.order FROM Request r WHERE r.purchaser.search LIKE :pattern)) AND");
		}
		if (activeOnly) {
			jpql.append(" b.orderStateId <= 3 AND");
		}
		if (!otEntities.isEmpty()) {
			jpql.append(" b.orderTypeId IN :types AND");
		}
		if (!catIds.isEmpty()) {
			jpql.append(" b.immobilie.immobilieCategory IN :catIds AND");
		}
		jpql.delete(jpql.length() - 3, jpql.length());
		jpql.append("ORDER BY b.createdOn DESC");
		return jpql.toString();
	}

	private void setQueryParameter(TypedQuery<?> query, String pattern, List<Integer> otEntities, List<Integer> catIds) {
		if (!EntityTool.isEmpty(pattern)) {
			query.setParameter("pattern", '%' + pattern.toLowerCase() + '%');
		}
		if (!otEntities.isEmpty()) {
			query.setParameter("types", otEntities);
		}
		if (!catIds.isEmpty()) {
			query.setParameter("catIds", catIds);
		}
	}

	@Override
	protected Class<OrderEntity> getEntityClass() {
		return OrderEntity.class;
	}

}
