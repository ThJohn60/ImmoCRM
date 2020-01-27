package net.immocrm.gui.property;

import net.immocrm.domain.ImmoTransaction;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Person;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.vc.ImmoDate;

public class TransactionRow extends AbstractImmoActionRow<ImmoTransaction> {

	public TransactionRow(ImmoTransaction domain) {
		super(domain);
	}


	@Override
	public Person getPerson() {
		return domain.getPerson();
	}

	@Override
	public String getName() {
		return getPerson().getName();
	}

	@Override
	public String getRole() {
		if (domain.isSaleEvent()) {
			if (domain.getPerson().equals(domain.getImmobilie().getOwner())) {
				return "Eigentümer";
			}
			return "Ehem. Eigentümer";
		}
		return "Mieter";
	}

	@Override
	public String getType() {
		return domain.getImmobilie().getImmobilieTypeName();
	}

	@Override
	public String getAddress() {
		return TextMaker.INSTANCE.getStreetAndCity(domain.getImmobilie().getAddress());
	}

	@Override
	public String getPrice() {
		return domain.getPrice().toString();
	}


	@Override
	public ImmoDate getStartImmoDate() {
		return domain.getStartDate();
	}

	@Override
	public ImmoDate getEndImmoDate() {
		return domain.getEndDate();
	}


	@Override
	public String getZustand() {
		if (domain.getZustand() == null) {
			return "";
		}
		return domain.getZustand().toString();
	}

	@Override
	public String getHausgeld() {
		return domain.getHausgeld().toString();
	}

	@Override
	public String getMiete() {
		return domain.getMiete().toString();
	}

	@Override
	public String getRoomCnt() {
		return domain.getImmobilie().getRoomCnt().toString();
	}

	@Override
	public Immobilie getImmobilie() {
		return domain.getImmobilie();
	}

}
