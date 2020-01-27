package net.immocrm.gui.request;

import net.immocrm.domain.Request;
import net.immocrm.gui.AbstractRow;

public class RequestRow extends AbstractRow<Request> {

	public RequestRow(Request request) {
		super(request);
	}


	public String getName() {
		return domain.getPurchaser().getName();
	}

	public String getAddress() {
		return domain.getPurchaser().getHomeAddressText();
	}

	public String getPrice() {
		return domain.getRequestPrice().toString();
	}

	public String getDate() {
		return domain.getCreateTimestamp().getDateAsText();
	}

}
