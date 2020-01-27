package net.immocrm.domain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.immocrm.db.ImmobilieEntity;
import net.immocrm.db.dao.ImmobilieDao;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.valid.DbException;

public class ImmobilieReader  {

	public static final ImmobilieReader INSTANCE = new ImmobilieReader();

	private final ImmobilieDao dao;
	private final ImmobilieTransactionManager immoTransMan;


	private ImmobilieReader() {
		immoTransMan = new ImmobilieTransactionManager();
		dao = new ImmobilieDao();
	}


	public Immobilie fetchById(Integer id) {
		ImmobilieEntity entity = dao.fetchById(id);
		if (entity != null) {
			return DomainFactory.createDomain(entity);
		}
		throw new DbException("Immobilie mit der Id = " + id + " ist nicht (mehr) vorhanden.");
	}

	public List<Immobilie> fetchByOwner(Integer ownerId) {
		return convert(dao.fetchByOwner(ownerId));
	}

	public List<Immobilie> find(String filterPattern, EnumSet<ImmobilieCategoryEnum> categories) {
		List<Integer> result = new ArrayList<>();
		for (ImmobilieCategoryEnum cat : categories) {
			result.add(cat.getId());
		}
		return convert(dao.fetch(filterPattern, result));
	}


	public List<Immobilie> find(String filterPattern) {
		if (filterPattern != null && !filterPattern.isEmpty()) {
			return convert(dao.search(filterPattern));
		}
		return convert(dao.fetchAll());
	}

	private List<Immobilie> convert(List<ImmobilieEntity> entities) {
		List<Immobilie> result = new ArrayList<>();
		for (ImmobilieEntity e : entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}


	public List<ImmoTransaction> fetchTransactionsByPerson(Integer id) {
		return immoTransMan.fetchTransactionsByPerson(id);
	}


	public List<ImmoTransaction> fetchTransactions(Immobilie immobilie) {
		return immoTransMan.fetchTransactions(immobilie);
	}

}
