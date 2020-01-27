package net.immocrm.domain;

import java.util.EnumSet;
import java.util.List;

import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.Price;

public interface ImmoTransaction extends BaseDomain {

	Order getOrder();

	Person getPerson();
	void setPerson(Person p);

	boolean isSaleEvent();
	void setIsSaleEvent(boolean b);

	Price getPrice();
	void setPrice(Price price);

	ImmoDate getStartDate();
	void setStartDate(ImmoDate d);
	ImmoDate getEndDate();
	void setEndDate(ImmoDate d);

	String getNotice();
	void setNotice(String text);

	Immobilie getImmobilie();
	void setImmobilie(Immobilie immobilie);

	Merkmal getMerkmal(MerkmalType type);
	List<Merkmal> getMerkmalList(EnumSet<Category> categories);
	List<Merkmal> getMerkmalList(Category category);

	MerkmalType getZustand();

	Price getMiete();
	Price getHausgeld();

}