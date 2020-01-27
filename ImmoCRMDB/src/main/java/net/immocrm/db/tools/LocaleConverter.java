package net.immocrm.db.tools;


import java.util.Locale;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocaleConverter implements AttributeConverter<Locale, String> {
    @Override
    public String convertToDatabaseColumn(Locale val) {
        return val.getLanguage() + "_" + val.getCountry();
    }
    @Override
    public Locale convertToEntityAttribute(String str) {
        if (str == null || str.isEmpty())
            return null;
        String[] params = str.split("_", 2);
        if (params.length < 2)
            return null;
        return new Locale(params[0], params[1]);
    }
}