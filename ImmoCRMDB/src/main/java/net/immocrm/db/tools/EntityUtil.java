package net.immocrm.db.tools;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class EntityUtil {

	private static final DateConverter DATE_CONVERTER = new DateConverter();


    public static Timestamp toTimestamp(LocalDateTime ldt) {
        return DATE_CONVERTER.convertToDatabaseColumn(ldt);
    }

    public static Date toDate(LocalDate ld) {
    	return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
