package net.immocrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.immocrm.db.TerminEntity;
import net.immocrm.db.dao.IntegrityConstraintViolationException;
import net.immocrm.db.dao.TerminDao;
import net.immocrm.domain.termin.BillTermin;
import net.immocrm.domain.termin.BirthdayTermin;
import net.immocrm.domain.termin.HandoverTermin;
import net.immocrm.domain.termin.RequestTermin;
import net.immocrm.domain.termin.SettlementTermin;
import net.immocrm.domain.termin.Termin;
import net.immocrm.domain.termin.TerminCategoryEnum;
import net.immocrm.domain.valid.DbRemoveException;

public class TerminManager {

	private final TerminDao dao;


	public TerminManager() {
		this.dao = new TerminDao();
	}


	public List<Termin> fetchByDates(LocalDate startDate, LocalDate endDate) {
		List<Termin> result = addOrderDates(startDate, endDate);
		for (Request r : RequestReader.INSTANCE.fetchByDates(startDate, endDate)) {
			result.add(new RequestTermin(r));
		}
		result.addAll(fetchBirthdays(startDate, endDate));
		result.addAll(fetchIndependent(startDate, endDate));
		Collections.sort(result);
		return result;
	}


	private List<Termin> addOrderDates(LocalDate startDate, LocalDate endDate) {
		List<Termin> result = new ArrayList<>();
		for (Order order : OrderReader.INSTANCE.fetchByDates(startDate, endDate)) {
			if (!order.getSettlementDateTime().isEmpty() && !order.getSettlementDateTime().isBefore(startDate) && order.getSettlementDateTime().isBefore(endDate)) {
				result.add(new SettlementTermin(order, TerminCategoryEnum.notar));
			}
			if (!order.getBillDate().isEmpty() && !order.getBillDate().isBefore(startDate) && order.getBillDate().isBefore(endDate)) {
				result.add(new BillTermin(order, TerminCategoryEnum.billed));
			}
			if (!order.getHandoverDateTime().isEmpty() && !order.getHandoverDateTime().isBefore(startDate) && order.getHandoverDateTime().isBefore(endDate)) {
				result.add(new HandoverTermin(order, TerminCategoryEnum.handover));
			}
		}
		return result;
	}

	private List<Termin> fetchBirthdays(LocalDate startDate, LocalDate endDate) {
		List<Termin> result = new ArrayList<>();
		LocalDate now = LocalDate.now();
		for (Person p : PersonReader.INSTANCE.fetchAll()) {
			if (p.getBirthday().isEmpty()) {
				continue;
			}
			LocalDate currentBirthDay = getCurrentBirthDay(p.getBirthday().toLocalDate(), now);
			if (!currentBirthDay.isBefore(startDate)  &&  !currentBirthDay.isAfter(endDate)) {
				result.add(new BirthdayTermin(p, currentBirthDay));
			}
		}
		return result;
	}

	private List<Termin> fetchIndependent(LocalDate startDate, LocalDate endDate) {
		List<Termin> result = new ArrayList<>();
		for (TerminEntity t : dao.fetchByDates(startDate, endDate)) {
			result.add(new IndependentTermin(TerminCategoryEnum.byId(t.getCategoryId()),  t));
		}
		return result;
	}

	private LocalDate getCurrentBirthDay(LocalDate birthday, LocalDate now) {
		LocalDate currentBirthDay = LocalDate.of(now.getYear(), birthday.getMonth(), birthday.getDayOfMonth());
		if (currentBirthDay.isBefore(now)) {
			currentBirthDay = currentBirthDay.plusYears(1);
		}
		return currentBirthDay;
	}

	public void save(Termin termin) {
		if (termin.isOrder()) {
			new OrderManager().save(termin.getOrder());
		} else if (termin.isRequest()) {
			new RequestManager().save(termin.getRequest());
		} else if (termin.isPerson()) {
			if (termin.getCategory() == TerminCategoryEnum.birthday) {
				new PersonManager().save(termin.getPerson());
			}
		} else if (termin.isIndependentTermin()) {
			saveDomain((IndependentTermin)termin);
		}
	}

	private void saveDomain(IndependentTermin termin) {
		try {
			dao.startTransaction();
			persist(termin);
			dao.commit();
		} catch (Exception e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
		}
	}

	void persist(IndependentTermin termin) {
		dao.save(termin.getEntity());
	}

	public void delete(IndependentTermin termin) {
		try {
			dao.removeCommited(termin.getEntity());
		} catch (IntegrityConstraintViolationException e) {
			throw new DbRemoveException(termin, "der Termin", e.getCause());
		}
	}

}
