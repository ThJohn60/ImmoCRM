package net.immocrm.gui.property;

import net.immocrm.domain.Request;

public class RequestImmoActionRow extends ImmobilieActionRow {

	private final Request request;

	public RequestImmoActionRow(Request domain) {
		super(domain.getOrder().getImmobilie());
		request = domain;
	}


	@Override
	public String getRole() {
		return request.getOrder().isSaleOrder() ? "Kaufinteressent" : "Mietinteressent";
	}

	@Override
	public String getPrice() {
		return request.getRequestPrice().toString();
	}

	@Override
	public String getStartDate() {
		if (request.getBesichtigungstermin() != null) {
			return request.getBesichtigungstermin().toString();
		}
		return null;
	}

}
