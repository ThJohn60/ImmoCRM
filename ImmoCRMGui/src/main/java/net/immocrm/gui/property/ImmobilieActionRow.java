package net.immocrm.gui.property;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Person;
import net.immocrm.domain.TextMaker;
import net.immocrm.domain.vc.ImmoDate;

public class ImmobilieActionRow extends AbstractImmoActionRow<Immobilie> {

	public ImmobilieActionRow(Immobilie immobilie) {
		super(immobilie);
	}


	@Override
	public Person getPerson() {
		return domain.getOwner();
	}

	@Override
	public String getName() {
		return domain.getOwner().getName();
	}

	@Override
	public String getRole() {
		return "Eigentümer";
	}

	@Override
	public String getType() {
		return domain.getImmobilieTypeName();
	}

	@Override
	public String getAddress() {
		return TextMaker.INSTANCE.getStreetAndCity(domain.getAddress());
	}

	@Override
	public String getPrice() {
		return domain.getPurchasePrice().toString();
	}

	@Override
	public ImmoDate getStartImmoDate() {
		return domain.getPurchaseDate();
	}

	@Override
	public ImmoDate getEndImmoDate() {
		return null;
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
	public String getZustand() {
		return domain.getZustandName();
	}

	@Override
	public String getRoomCnt() {
		return domain.getRoomCnt().toString();
	}

	@Override
	public Immobilie getImmobilie() {
		return domain;
	}

}
