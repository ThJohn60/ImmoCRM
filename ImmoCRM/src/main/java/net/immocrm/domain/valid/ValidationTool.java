package net.immocrm.domain.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.vc.BaseValue;

public class ValidationTool {

	public static void checkEmpty(String field, String fieldname, ValidationIssues issues) {
		if (isNullOrEmpty(field)) {
			issues.addMissingField(fieldname);
		}
	}

	public static boolean isNullOrEmpty(String field) {
		return field == null || field.isEmpty();
	}

	public static boolean isNullOrEmpty(BaseValue<?> value) {
		return value == null || value.isEmpty();
	}

	public static boolean isNullOrEmpty(BaseDomain domain) {
		return domain == null || domain.isEmpty();
	}


	static void checkEmpty(BaseDomain domain, String fieldname, ValidationIssues issues) {
		if (isNullOrEmpty(domain)) {
			issues.addMissingField(fieldname);
		}
	}

	static void checkNull(Object field, String fieldname, ValidationIssues issues) {
		if (field == null) {
			issues.addMissingField(fieldname);
		}
	}

	static boolean validatePerRegex(String input, String regexPattern) {
		Pattern p = Pattern.compile(regexPattern);
		Matcher m = p.matcher(input.toUpperCase());
		boolean result = m.matches();
		return result;
	}

}
