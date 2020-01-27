package net.immocrm.gui.termin;

import java.util.Arrays;

import net.immocrm.domain.TextMaker;
import net.immocrm.domain.termin.Termin;

public class TerminRow  {

	private final Termin termin;

	public TerminRow(Termin termin) {
		this.termin = termin;
	}


	public Termin getTermin() {
		return termin;
	}


	public String getDate() {
		return termin.getDate().toImmoDate().toString();
	}

	public String getTime() {
		String date = termin.getDate().getDateAsText();
		if (date.length() <= 10) {
			return "";
		}
		return date.substring(10, 16);
	}

	public String getCategoryName() {
		return termin.getCategoryName();
	}

	public String getPlace() {
		return TextMaker.INSTANCE.getStreetAndCity(termin.getAddress());
	}

	public String getParticipants() {
		return Arrays.toString(termin.getParticipants().toArray()).replaceAll("\\]", "").replaceAll("\\[", "");
	}

	public String getDetails() {
		return termin.getDetails();
	}

}
