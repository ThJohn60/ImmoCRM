package net.immocrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.immocrm.db.OrderEntity;
import net.immocrm.db.OrderHistoryEntity;
import net.immocrm.db.dao.OrderDao;
import net.immocrm.db.dao.OrderHistoryDao;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.ref.OrderTypeEnum;
import net.immocrm.domain.valid.DbException;

public class OrderReader {

	public static final OrderReader INSTANCE = new OrderReader();

	private final OrderDao dao;
	private final OrderHistoryDao orderHistoryDao;


	private OrderReader() {
		dao = new OrderDao();
		orderHistoryDao = new OrderHistoryDao();
	}


	public Order fetchById(Integer id) {
		OrderEntity entity = dao.fetchById(id);
		if (entity != null) {
			return DomainFactory.createDomain(entity);
		}
		throw new DbException("Auftrag mit der Id = " + id + " ist nicht (mehr) vorhanden.");
	}


	public List<Order> fetchAll() {
		List<Order> resultList = convert(dao.fetchAll());
		return resultList;
	}

	public List<Order> fetchByCustomer(Integer customerId) {
		List<OrderEntity> result1 = dao.fetchByProvider(customerId);
		List<OrderEntity> result2 = dao.fetchByBuyer(customerId);
		List<Order> resultList = convert(result1);
		resultList.addAll(convert(result2));
		return resultList;
	}

	public List<Order> fetchByNotar(Integer notarId) {
		List<Order> resultList = convert(dao.fetchByNotar(notarId));
		return resultList;
	}

	public List<Order> fetchByImmobilie(Integer immoId) {
		List<Order> resultList = convert(dao.fetchByImmobilie(immoId));
		return resultList;
	}

	public List<Order> fetchByDates(LocalDate startDate, LocalDate endDate) {
		List<Order> resultList = convert(dao.fetchByDates(startDate, endDate));
		return resultList;
	}


	public List<Order> readOrders() {
		return convert(dao.fetchAll());
	}

	public List<Order> find(String pattern, EnumSet<OrderTypeEnum> orderTypes, boolean activeOnly, EnumSet<ImmobilieCategoryEnum> immoCategories) {
		List<Integer> catEntities = new ArrayList<>();
		for (ImmobilieCategoryEnum cat : immoCategories) {
			catEntities.add(cat.getId());
		}
		List<Integer> otEntities = new ArrayList<>();
		for (OrderTypeEnum ot : orderTypes) {
			otEntities.add(ot.getId());
		}
		return convert(dao.search(pattern, activeOnly, otEntities, catEntities));
	}

	private List<Order> convert(List<OrderEntity> entities) {
		List<Order> result = new ArrayList<>();
		for (OrderEntity e : entities) {
			result.add(new OrderDomain(e));
		}
		return result;
	}


	public List<OrderHistory> fetchHistoryByOrder(Order order) {
		List<OrderHistory> result = new ArrayList<>();
		for (OrderHistoryEntity e : orderHistoryDao.fetchByOrder(order.getId())) {
			result.add(new OrderHistoryDomain(e));
		}
		return result;
	}

}
