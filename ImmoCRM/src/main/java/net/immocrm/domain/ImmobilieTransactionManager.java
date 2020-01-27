package net.immocrm.domain;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.ImmobilieTransactionEntity;
import net.immocrm.db.dao.ImmobilieTransactionDao;

public class ImmobilieTransactionManager {

	private final ImmobilieTransactionDao dao;


	public ImmobilieTransactionManager() {
		dao = new ImmobilieTransactionDao();
	}


	public ImmobilieTransactionDao getDao() {
		return dao;
	}

	void persist(ImmoTransactionDomain immoTrans) {
		immoTrans.beforePersistence();
		dao.save(immoTrans.getEntity());
	}


	public void startTransaction() {
		dao.startTransaction();
	}

	public void commit() {
		dao.commit();
	}

	public List<ImmoTransaction> fetchTransactionsByPerson(Integer id) {
		List<ImmobilieTransactionEntity> entities = dao.fetchByPerson(id);
		List<ImmoTransaction> transactions = convertTransactions(entities);
		return transactions;
	}

	public List<ImmoTransaction> fetchTransactions(Immobilie immobilie) {
		List<ImmobilieTransactionEntity> entities = dao.fetchByImmobilie(immobilie.getId());
		List<ImmoTransaction> transactions = convertTransactions(entities);
		return transactions;
	}

	private  List<ImmoTransaction> convertTransactions(List<ImmobilieTransactionEntity> entities) {
		List<ImmoTransaction> result = new ArrayList<>();
		for (ImmobilieTransactionEntity e : entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}


	public void rollbackSave(RuntimeException e) {
		RollbackHandler handler = new RollbackHandler(dao);
		handler.rollbackSave(e);
	}

}
