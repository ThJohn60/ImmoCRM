package net.immocrm.domain;

import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.domain.vc.Price;

public interface Request extends BaseDomain {

	Order getOrder();

	Person getPurchaser();
	void setPurchaser(Person purchaser);

	Price getRequestPrice();
	void setRequestPrice(Price requestPrice);

	String getNotice();
	void setNotice(String text);

	ImmoDateTime getBesichtigungstermin();
	void setBesichtigungstermin(ImmoDateTime termin);

}