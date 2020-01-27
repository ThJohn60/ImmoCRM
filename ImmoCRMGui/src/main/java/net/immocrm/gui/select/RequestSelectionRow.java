package net.immocrm.gui.select;

import net.immocrm.domain.Request;

public class RequestSelectionRow extends SelectionRow<Request> {

	private final Request request;


	public RequestSelectionRow(Request request) {
		super(request);
		this.request = request;
	}

	
	public String getName() {
		return request.getPurchaser().getName();
	}

	
	public String getAddress() {
		return request.getPurchaser().getHomeAddressText();
	}

	public String getRequestPrice() {
		return request.getRequestPrice().toString();
	}

}
