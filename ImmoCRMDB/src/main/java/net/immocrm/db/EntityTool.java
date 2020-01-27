package net.immocrm.db;

public class EntityTool {

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
	
	public static boolean isEqual(Number o1, Number o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return Math.abs(o1.doubleValue() - o2.doubleValue()) < 0.01;
	}
	
}
