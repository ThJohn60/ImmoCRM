package net.immocrm.domain.termin;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.domain.Address;
import net.immocrm.domain.PersonName;
import net.immocrm.domain.Request;
import net.immocrm.domain.vc.ImmoDateTime;

public class RequestTermin extends BaseTermin {

	private Request request;


	public RequestTermin(Request request) {
		super(TerminCategoryEnum.besichtigung);
		this.request = request;
	}


	@Override
	public String getTitle() {
		return "Besichtigung mit " + request.getPurchaser().getName();
	}


	@Override
	public String getCalendarLabel() {
		return "Besichtigung\n" + request.getOrder().getImmobilie().getAddress().getAddressText() + "\n" + request.getPurchaser().getName();
	}

	@Override
	public ImmoDateTime getDate() {
		return request.getBesichtigungstermin();
	}

	@Override
	public void setDate(ImmoDateTime dateTime) {
		request.setBesichtigungstermin(dateTime);
	}

	@Override
	public String getDetails() {
		boolean withLineBreaks = false;
		return getDetails(withLineBreaks);
	}

	@Override
	public String getDetailsWithLineBreaks() {
		boolean withLineBreaks = true;
		return getDetails(withLineBreaks);
	}

	private String getDetails(boolean withLineBreaks) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getPurchaser().getName())
		   .append(" will ")
		   .append(request.getOrder().getImmobilie().getImmobilieTypeName());
		if (withLineBreaks) {
			sb.append("\nfür ");
		} else {
			sb.append(" für ");
		}
		sb.append(request.getRequestPrice());
		if (request.getOrder().isSaleOrder()) {
			sb.append(" kaufen");
		} else {
			sb.append(" mieten");
		}
		return sb.toString();
	}


	@Override
	public Request getRequest() {
		return request;
	}

	@Override
	public boolean isRequest() {
		return true;
	}

	@Override
	public Address getAddress() {
		return request.getOrder().getImmobilieAddress();
	}


	@Override
	public List<PersonName> getParticipants() {
		List<PersonName> result = new ArrayList<>();
		result.add(request.getPurchaser());
		return result;
	}

}
