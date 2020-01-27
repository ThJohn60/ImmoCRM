package net.immocrm.domain;

import java.util.List;

import net.immocrm.domain.img.DocumentDirectory;
import net.immocrm.domain.ref.OrderStateEnum;
import net.immocrm.domain.ref.OrderTypeEnum;
import net.immocrm.domain.vc.ImmoDate;
import net.immocrm.domain.vc.ImmoDateTime;
import net.immocrm.domain.vc.Percent;
import net.immocrm.domain.vc.Price;

public interface Order extends BaseDomain {

	Immobilie getImmobilie();
	void setImmobilie(Immobilie immobilie);

	Person getCustomer();
	void setCustomer(Person customer);

	Person getBuyer();
	void setBuyer(Person buyer);

	Address getImmobilieAddress();
	String getImmobilieAddressText();

	OrderTypeEnum getOrderType();
	void setOrderType(OrderTypeEnum type);
	boolean isSaleOrder();
	boolean isRentOrder();

	OrderStateEnum getOrderState();
	void setOrderState(OrderStateEnum settled);
	boolean isFinished();

	Price getCustomerPrice();
	void setCustomerPrice(Price desiredPrice);
	Price getEstimatedPrice();
	void setEstimatedPrice(Price estimatedPrice);
	Price getSettlementPrice();
	void setSettlementPrice(Price settlementPrice);

	ImmoDate getStartDate();
	ImmoDateTime getSettlementDateTime();
	void setSettlementDateTime(ImmoDateTime dt);
	ImmoDateTime getHandoverDateTime();
	void setHandoverDateTime(ImmoDateTime dt);
	ImmoDate getBillDate();
	void setBillDate(ImmoDate dt);
	ImmoDate getPayedDate();
	void setPayedDate(ImmoDate dt);

	String getNotice();
	void setNotice(String description);

	Notar getNotar();
	void setNotar(Notar notar);

	List<Request> getRequests();
	List<OrderHistory> getHistory();

	Percent getBuyerProvision();
	void setBuyerProvision(Percent provision);
	Percent getCustomerProvision();
	void setCustomerProvision(Percent provision);

	String getAblage();
	void setAblage(String ablage);

	String getField1();
	void setField1Id(String field1Id);
	String getField2();
	void setField2Id(String field2Id);

	DocumentDirectory getDocumentManager();
	List<Document> getDocuments();

}
