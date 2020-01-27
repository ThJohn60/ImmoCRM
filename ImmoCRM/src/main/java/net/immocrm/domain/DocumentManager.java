package net.immocrm.domain;

import net.immocrm.db.dao.DocumentDao;
import net.immocrm.db.dao.IntegrityConstraintViolationException;
import net.immocrm.domain.img.DocProvider;
import net.immocrm.domain.valid.DbRemoveException;

public class DocumentManager {

	private final DocumentDao dao;


	public DocumentManager() {
		dao = new DocumentDao();
	}


	public void save(Document d) {
		DocumentDomain doc = (DocumentDomain)d;
		try {
			dao.startTransaction();
			persist(doc);
			dao.commit();
		} catch (Exception e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
		}
	}

	void persist(DocumentDomain doc) {
		dao.save(doc.getEntity());
	}

	public void delete(Document d) {
		try {
			DocumentDomain doc = (DocumentDomain)d;
			dao.removeCommited(doc.getEntity());
			DocProvider docProvider = new DocProvider(doc.getDocDir());
			docProvider.deleteDoc(d.getFileName());
		} catch (IntegrityConstraintViolationException e) {
			throw new DbRemoveException(d, "die Anfrage", e.getCause());
		}
	}

}
