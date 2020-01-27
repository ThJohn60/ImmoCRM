package net.immocrm.db.tools;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DateConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    
	@Override
    public Timestamp convertToDatabaseColumn(LocalDateTime ldt) {
    	if (ldt == null) {
    		return null;
    	}
		Timestamp result = Timestamp.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        return result;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp d) {
    	if (d == null) {
    		return null;
    	}
        LocalDateTime result = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
		return result;
    }

}