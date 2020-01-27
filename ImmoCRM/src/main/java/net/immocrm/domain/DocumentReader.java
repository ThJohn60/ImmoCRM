package net.immocrm.domain;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.DocumentEntity;
import net.immocrm.db.dao.DocumentDao;
import net.immocrm.domain.valid.DbException;

public class DocumentReader {

	public static final DocumentReader INSTANCE = new DocumentReader();

	private final DocumentDao dao;


	private DocumentReader() {
		dao = new DocumentDao();
	}


	public Document fetchById(Integer id) {
		DocumentEntity entity = dao.fetchById(id);
		if (entity != null) {
			return DomainFactory.createDomain(entity);
		}
		throw new DbException("Dokument mit der Id = " + id + " ist nicht (mehr) vorhanden.");
	}

	public List<Document> fetchByOrderId(Integer orderId) {
		return convert(dao.fetchByOrder(orderId));
	}

	public List<Document> fetchByImmobilie(int immoId) {
		return convert(dao.fetchByImmobilie(immoId));
	}

	private List<Document> convert(List<DocumentEntity> entities) {
		List<Document> result = new ArrayList<>();
		for (DocumentEntity e : entities) {
			result.add(DomainFactory.createDomain(e));
		}
		return result;
	}

}
