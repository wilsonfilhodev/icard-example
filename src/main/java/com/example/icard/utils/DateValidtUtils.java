package com.example.icard.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidtUtils {
	
	private DateValidtUtils() {
		throw new IllegalStateException("Utility class");
	}

	private static final String FORMAT_DATE = "MM/yy";
	private static final int PLUS_YEAR = 2;

	public static String generate(LocalDate date) {
		DateTimeFormatter formatador = 
				  DateTimeFormatter.ofPattern(FORMAT_DATE);
		return date.plusYears(PLUS_YEAR).format(formatador);
	}

}
