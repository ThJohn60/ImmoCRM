package net.immocrm.domain;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.PersonEntity;
import net.immocrm.db.dao.PersonDao;
import net.immocrm.domain.valid.DbException;

public class PersonReader {

	public static final PersonReader INSTANCE = new PersonReader();

	private final PersonDao dao;


	private PersonReader() {
		dao = new PersonDao();
	}


	public Person fetchById(Integer id) {
		PersonEntity entity = dao.fetchById(id);
		if (entity != null) {
			return DomainFactory.createDomain(entity);
		}
		throw new DbException("Person mit der Id = " + id + " ist nicht (mehr) vorhanden.");
	}


	public List<Person> fetchAll() {
		return convert(dao.fetchAll());
	}

	public List<Person> find(String filterPattern) {
		if (filterPattern != null && !filterPattern.isEmpty()) {
			return convert(dao.search(filterPattern));
		}
		return convert(dao.fetchAll());
	}

	private List<Person> convert(List<PersonEntity> entities) {
		List<Person> result = new ArrayList<>();
		for (PersonEntity e : entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}

}
