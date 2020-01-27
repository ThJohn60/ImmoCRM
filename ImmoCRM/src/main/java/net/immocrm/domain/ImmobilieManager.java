package net.immocrm.domain;

import net.immocrm.db.dao.DatabaseExceptionManager;
import net.immocrm.db.dao.ImmobilieDao;
import net.immocrm.domain.img.DocumentDirectory;
import net.immocrm.domain.img.ImageDirectory;
import net.immocrm.domain.valid.DbRemoveException;

public class ImmobilieManager {

	private final ImmobilieDao dao;


	public ImmobilieManager() {
		dao = new ImmobilieDao();
	}


	public void save(Immobilie i) {
		ImmobilieDomain immobilie = (ImmobilieDomain)i;
		try {
			dao.startTransaction();
			persist(immobilie);
			dao.commit();
			renameImageDirectory(immobilie);
			renameDocDirectory(immobilie);
		} catch (RuntimeException e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
			throw e;
		}
	}

	void persist(ImmobilieDomain immobilie) {
		immobilie.beforePersistence();
		dao.save(immobilie.getEntity());
	}

	public void renameImageDirectory(Immobilie immobilie) {
		ImageDirectory imageDir = immobilie.getImageDir();
		if (imageDir.isDirectoryEmpty()) {
			imageDir.deleteDirectory();
		} else {
			if (imageDir.isTemporalPath()) {
				if (immobilie.isNew()) {
					throw new IllegalArgumentException("immobilie must not be new");
				}
				ImageDirectory newImageDir = new ImageDirectory(immobilie);
				imageDir.renameTo(newImageDir);
			}
		}
	}

	public void renameDocDirectory(Immobilie immobilie) {
		DocumentDirectory docDir = immobilie.getDocDir();
		if (docDir.isDirectoryEmpty()) {
			docDir.deleteDirectory();
		} else {
			if (docDir.isTemporalPath()) {
				if (immobilie.isNew()) {
					throw new IllegalArgumentException("immobilie must not be new");
				}
				DocumentDirectory newDir = new DocumentDirectory(immobilie);
				docDir.renameTo(newDir);
			}
		}
	}

	public void delete(Immobilie i) {
		try {
			ImmobilieDomain immobilie = (ImmobilieDomain)i;
			dao.startTransaction();
			dao.remove(immobilie.getEntity());
			ImageDirectory imageDir = immobilie.getImageDir();
			imageDir.deleteImagesAndDirectory();
			dao.commit();
		} catch (Exception e) {
			dao.rollback();
			if (DatabaseExceptionManager.isIntegrityConstraintViolation(e)) {
				throw new DbRemoveException(i, "die Immobilie", e.getCause());
			}
		}
	}

}
