package com.example.icard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.example.icard.service.execption.BusinessException;

public class DateValidtUtils {

	private DateValidtUtils() {
		throw new IllegalStateException("Utility class");
	}

	private static final String FORMAT_DATE = "MM/yy";
	private static final int PLUS_YEAR = 2;

	public static String generate(LocalDate date) {
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern(FORMAT_DATE);
		return date.plusYears(PLUS_YEAR).format(formatador);
	}

	public static boolean cardExpired(String date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar newDate = Calendar.getInstance();
			newDate.set(2000+Integer.parseInt(date.substring(3, 5)), Integer.parseInt(date.substring(0, 2)), 01);
			String strDateExpirate = formatter.format(newDate.getTime());
			String strDateNow = formatter.format(new Date());
			return formatter.parse(strDateExpirate).before(formatter.parse(strDateNow));
		} catch (ParseException e) {
			throw new BusinessException("Error. Try again.", "500");
		}
	}

}
