package net.immocrm.domain;

import java.util.List;

import net.immocrm.db.RequestEntity;
import net.immocrm.db.dao.ContactDao;
import net.immocrm.db.dao.DatabaseExceptionManager;
import net.immocrm.db.dao.PersonDao;
import net.immocrm.db.dao.RequestDao;
import net.immocrm.domain.valid.DbRemoveException;
import net.immocrm.domain.valid.ValidationIssues;

public class PersonManager {

	private final PersonDao dao;


	public PersonManager() {
		dao = new PersonDao();
	}

	public void save(Person p) {
		try {
			dao.startTransaction();
			persist((PersonDomain)p);
			dao.commit();
		} catch (Exception e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
		}
	}

	void persist(PersonDomain person) {
		person.beforePersistence();
		dao.save(person.getEntity());
	}

	public void delete(Person p) {
		PersonDomain person = (PersonDomain)p;
		try {
			dao.startTransaction();
			RequestDao requestDao = new RequestDao();
			for (RequestEntity requestEntity : requestDao.fetchByPurchaser(person.getId())) {
				requestDao.remove(requestEntity);
			}
			ContactDao contactDao = new ContactDao();
			contactDao.remove(person.getHomeContact().getEntity());
			dao.remove(person.getEntity());
			dao.commit();
		} catch (Exception e) {
			if (DatabaseExceptionManager.isIntegrityConstraintViolation(e)) {
				throw new DbRemoveException(p, "die Person", e.getCause());
			}
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
		}
	}


	public ValidationIssues saveList(List<Person> personList) {
		ValidationIssues result = new ValidationIssues();
		try {
			dao.startTransaction();
			for (Person p : personList) {
				persist((PersonDomain) p, result);
			}
			dao.commit();
		} catch (Exception e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
		}
		return result;
	}

	private void persist(PersonDomain person, ValidationIssues result) {
		ValidationIssues issues = person.validate();
		result.addAllIssues(issues);
		if (!issues.hasErrors()) {
			persist(person);
		}
	}

}
