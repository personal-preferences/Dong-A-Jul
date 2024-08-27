package org.personal.registration_service.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateParsing {
	public static LocalDateTime strToLdt(String str){
		if (!Objects.equals(str, ""))
			return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return null;
	}
	public static String LdtToStr(LocalDateTime localDateTime){
		if (localDateTime!=null)
			return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return null;
	}
}
