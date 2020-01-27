package net.immocrm.domain.vc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class ImmoDate implements CommonImmoDate {

	private final LocalDate date;


	public ImmoDate() {
		this((Date)null);
	}

	public ImmoDate(LocalDate date) {
		this.date = date;
	}

	public ImmoDate(Date date) {
		if (date == null) {
			this.date = null;
		} else {
			this.date = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
		}
	}


	@Override
	public boolean isEmpty() {
		return date == null;
	}

	public LocalDate toLocalDate() {
		return date;
	}

	public Year getYear() {
		return new Year(date.getYear());
	}


	public boolean isAfter(ImmoDate other) {
		return date.isAfter(other.date);
	}

	public boolean isAfter(LocalDate other) {
		return date.isAfter(other);
	}

	public boolean isBefore(ImmoDate other) {
		return date.isBefore(other.date);
	}

	public boolean isBefore(LocalDate other) {
		return date.isBefore(other);
	}


	public boolean isEqual(ImmoDate other) {
		return date.isEqual(other.date);
	}

	@Override
	public LocalDateTime toLocalDateTime() {
		return date.atStartOfDay();
	}

	public ImmoDateTime toImmoDateTime() {
		return new ImmoDateTime(date.atStartOfDay());
	}

	public Date toPersistenceDate() {
		if (isEmpty()) {
			return null;
		}
		return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "";
		}
		return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date);
	}

}
