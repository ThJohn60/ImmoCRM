package net.immocrm.domain.vc;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class ImmoDateTime implements Comparable<CommonImmoDate>, CommonImmoDate  {

	public static ImmoDateTime now() {
		return new ImmoDateTime(LocalDateTime.now());
	}

	private final LocalDateTime dateTime;


	public ImmoDateTime() {
		dateTime = null;
	}

	public ImmoDateTime(LocalDate date) {
		dateTime = date != null ? date.atStartOfDay() : null;
	}

	public ImmoDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public ImmoDateTime(Date d) {
		dateTime = d == null ? null : LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
	}


	public Year getYear() {
		return new Year(dateTime.getYear());
	}

	public int getHour() {
		return dateTime.getHour();
	}

	public int getMinute() {
		return dateTime.getMinute();
	}


	public LocalDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public boolean isEmpty() {
		return dateTime == null;
	}

 	@Override
	public LocalDateTime toLocalDateTime() {
		return dateTime;
	}

	public ImmoDate toImmoDate() {
		return new ImmoDate(dateTime.toLocalDate());
	}

	public Date toPersistenceDate() {
		if (isEmpty()) {
			return null;
		}
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public Date toPersistenceTimestamp() {
		if (isEmpty()) {
			return null;
		}
		Timestamp result = Timestamp.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		return result;
	}

	public String getDateAsText() {
		if (isEmpty()) {
			return "";
		}
		if (dateTime.getHour() == 0 && dateTime.getMinute() == 0) {
			return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(dateTime.toLocalDate());
		}
		String result = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateTime);
		if (result.length() > 15) {
			result = result.substring(0, 16);
		}
		return result;
	}


	public boolean isAfter(ImmoDateTime other) {
		return dateTime.isAfter(other.toLocalDateTime());
	}

	public boolean isAfter(LocalDateTime other) {
		return dateTime.isAfter(other);
	}

	public boolean isAfter(LocalDate other) {
		return dateTime.isAfter(other.atStartOfDay());
	}


	public boolean isBefore(ImmoDateTime other) {
		return dateTime.isBefore(other.toLocalDateTime());
	}

	public boolean isBefore(LocalDateTime other) {
		return dateTime.isBefore(other);
	}

	public boolean isBefore(LocalDate other) {
		return dateTime.isBefore(other.atStartOfDay());
	}


	public boolean isEqual(ImmoDateTime other) {
		return dateTime.isEqual(other.toLocalDateTime());
	}

	@Override
	public String toString() {
		return getDateAsText();
	}

	@Override
	public int compareTo(CommonImmoDate o) {
		return toLocalDateTime().compareTo(o.toLocalDateTime());
	}

}
