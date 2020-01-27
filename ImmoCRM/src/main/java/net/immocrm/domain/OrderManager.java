package net.immocrm.domain;

import net.immocrm.db.OrderHistoryEntity;
import net.immocrm.db.dao.IntegrityConstraintViolationException;
import net.immocrm.db.dao.OrderDao;
import net.immocrm.db.dao.OrderHistoryDao;
import net.immocrm.domain.valid.DbRemoveException;

public class OrderManager {

	private final OrderDao dao;
	private final OrderHistoryDao historyDao;

	private final ImmobilieManager immobilieMan;


	public OrderManager() {
		dao = new OrderDao();
		historyDao = new OrderHistoryDao();
		immobilieMan = new ImmobilieManager();
	}


	public void save(Order o) {
		OrderDomain order = (OrderDomain)o;
		try {
			dao.startTransaction();
			persist(order);
			dao.commit();
			immobilieMan.renameImageDirectory(order.getImmobilie());
		} catch (RuntimeException e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
			throw e;
		}
	}

	public void persist(OrderDomain order) {
		order.beforePersistence();

		saveHistory(order);
		immobilieMan.persist(order.getImmobilie());
//		if (order.getImmobilie().isNew()) {
//		}
		dao.save(order.getEntity());
	}

	private void saveHistory(OrderDomain order) {
		OrderHistoryProvider orderForHistory = order.getOrderForHistory();
		for (OrderHistoryEntity entity : orderForHistory.historyEntries(order)) {
			historyDao.save(entity);
		}
	}


	public void delete(Order o) {
		try {
			OrderDomain order = (OrderDomain)o;
			dao.removeCommited(order.getEntity());
		} catch (IntegrityConstraintViolationException e) {
			throw new DbRemoveException(o, "der Auftrag", e.getCause());
		}
	}

}
