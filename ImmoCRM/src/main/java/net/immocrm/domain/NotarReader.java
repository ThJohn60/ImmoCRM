package net.immocrm.domain;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.NotarEntity;
import net.immocrm.db.dao.NotarDao;
import net.immocrm.domain.valid.DbException;

public class NotarReader {

	public static final NotarReader INSTANCE = new NotarReader();

	private final NotarDao dao;


	private NotarReader() {
		dao = new NotarDao();
	}


	public Notar fetchById(Integer id) {
		NotarEntity entity = dao.fetchById(id);
		if (entity != null) {
			return DomainFactory.createDomain(entity);
		}
		throw new DbException("Notar mit der Id = " + id + " ist nicht (mehr) vorhanden.");
	}

	public List<Notar> find(String filterPattern) {
		if (filterPattern != null && !filterPattern.isEmpty()) {
			return convert(dao.search(filterPattern));
		}
		return convert(dao.fetchAll());
	}

	public List<Notar> fetchAll() {
		return convert(dao.fetchAll());
	}

	private List<Notar> convert(List<NotarEntity> entities) {
		List<Notar> result = new ArrayList<>();
		for (NotarEntity e: entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}

}
