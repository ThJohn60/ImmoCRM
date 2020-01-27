package net.immocrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.RequestEntity;
import net.immocrm.db.dao.RequestDao;
import net.immocrm.domain.valid.DbException;

public class RequestReader {

	public static final RequestReader INSTANCE = new RequestReader();

	private final RequestDao dao;


	private RequestReader() {
		dao = new RequestDao();
	}


	public Request fetchById(Integer id) {
		RequestEntity entity = dao.fetchById(id);
		if (entity != null) {
			return DomainFactory.createDomain(entity);
		}
		throw new DbException("Anfrage mit der Id = " + id + " ist nicht (mehr) vorhanden.");
	}

	public List<Request> fetchByOrderId(Integer orderId) {
		return convert(dao.fetchByOrder(orderId));
	}

	public List<Request> fetchByPurchaserId(Integer id) {
		return convert(dao.fetchByPurchaser(id));
	}

	public List<Request> fetchByDates(LocalDate startDate, LocalDate endDate) {
		return convert(dao.fetchByDates(startDate, endDate));
	}

	public List<Request> searchInOrder(Integer orderId, String pattern) {
		return convert(dao.searchInOrder(orderId, pattern));
	}

	private List<Request> convert(List<RequestEntity> entities) {
		List<Request> result = new ArrayList<>();
		for (RequestEntity e : entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}

}
