package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.immocrm.db.PersonEntity;
import net.immocrm.db.TerminEntity;
import net.immocrm.db.tools.EntityUtil;

public class TerminDao extends AbstractDao<TerminEntity> {

	public List<PersonEntity> fetchBirthdays(int dayLimit) {
		List<PersonEntity> resultList = getEntityManager()
				.createQuery("SELECT b FROM Person b", PersonEntity.class)
				.getResultList();
		LocalDate now = LocalDate.now();
		LocalDate pastLimit = now.plusDays(dayLimit);
		Iterator<PersonEntity> it = resultList.iterator();
		while (it.hasNext()) {
			PersonEntity e = it.next();
			getEntityManager().detach(e);
			LocalDate currentBirthDay = getCurrentBirthDay(toLocalDate(e.getBirthday()), now);
			if (!currentBirthDay.isBefore(pastLimit)) {
				it.remove();
			}
		}
		return resultList;
	}

	protected LocalDate toLocalDate(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
	}

	private LocalDate getCurrentBirthDay(LocalDate birthday, LocalDate now) {
		LocalDate currentBirthDay = LocalDate.of(now.getYear(), birthday.getMonth(), birthday.getDayOfMonth());
		if (currentBirthDay.isBefore(now)) {
			currentBirthDay = LocalDate.of(now.getYear()+1, birthday.getMonth(), birthday.getDayOfMonth());
		}
		return currentBirthDay;
	}


	public List<TerminEntity> fetchByOrder(int orderId) {
		List<TerminEntity> resultList = getEntityManager()
				.createNamedQuery("Termin.fetchByOrder", TerminEntity.class)
				.setParameter("orderId", orderId)
				.getResultList();
		return resultList;
	}

	public List<TerminEntity> fetchByDates(LocalDate startDate, LocalDate endDate) {
		List<TerminEntity> resultList = getEntityManager()
				.createNamedQuery("Termin.fetchByDates", TerminEntity.class)
				.setParameter("start", EntityUtil.toDate(startDate))
				.setParameter("end", EntityUtil.toDate(endDate))
				.getResultList();
		return resultList;
	}


	@Override
	protected Class<TerminEntity> getEntityClass() {
		return TerminEntity.class;
	}

}
