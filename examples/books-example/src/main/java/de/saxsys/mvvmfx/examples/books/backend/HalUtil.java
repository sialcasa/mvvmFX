package de.saxsys.mvvmfx.examples.books.backend;

public class HalUtil {
	
	public static String replaceParam(String href, String param) {
		return href.replaceFirst("\\{.+\\}", param);
	}
	
	public static Integer toInt(Object value) {
		if (value.getClass().isAssignableFrom(String.class)) {
			String valueStr = (String) value;
			return Integer.valueOf(valueStr);
		}
		return null;
	}
}
