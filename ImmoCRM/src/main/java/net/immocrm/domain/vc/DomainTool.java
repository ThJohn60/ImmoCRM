package net.immocrm.domain.vc;

import java.util.List;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.quality.Merkmal;

public class DomainTool {

	public static boolean isNull(Object... args) {
		for (Object object : args) {
			if (object != null) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String... args) {
		for (String str : args) {
			if (str != null && !str.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(BaseDomain... args) {
		for (BaseDomain b : args) {
			if (b != null && !b.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEqual(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (!o1.getClass().equals(o2.getClass())) {
			throw new RuntimeException("different clases: " + o1.getClass() + " and " + o2.getClass());
		}
		return o1.equals(o2);
	}


	public static String csv(List<Merkmal> quals) {
		StringBuilder result = new StringBuilder();
		for (Merkmal q: quals) {
			result.append(q.getTypeName()).append(", ");
		}
		return removeTrailingComma(result);
	}

	private static String removeTrailingComma(StringBuilder result) {
		if (result.length() == 0) {
			return "";
		}
		return result.substring(0, result.length() - 2).toString();
	}

}
