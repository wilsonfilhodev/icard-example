package com.example.icard.utils;

import java.util.Random;

import org.springframework.util.StringUtils;

public class CreditCardUtils {

	private CreditCardUtils() {
		throw new IllegalStateException("Utility class");
	}

	private static final String BIN = "555555";
	private static final int LENGTH_NUMBER = 16;

	private static Random random = new Random(System.currentTimeMillis());

	public static String generateNumberCreditCard() {
		int randomNumberLength = LENGTH_NUMBER - (BIN.length() + 1);

		StringBuilder builder = new StringBuilder(BIN);

		for (int i = 0; i < randomNumberLength; i++) {
			int digit = random.nextInt(10);
			builder.append(digit);
		}

		int checkDigit = getCheckDigit(builder.toString());
		builder.append(checkDigit);

		return builder.toString();
	}
	
	public static String generatePassword() {
		int n = 1000 + random.nextInt(9000);
		return String.valueOf(n);
	}
	
	public static String generateCvv(String number, String date) {
		
		if (StringUtils.isEmpty(number) || StringUtils.isEmpty(date)) {
			return null;
		}
		
		int numberDate = Integer.parseInt(date.substring(0, 2));
		int number1 = Integer.parseInt(Character.toString(number.charAt(number.length() - 1)));
		int number2 = Integer.parseInt(Character.toString(number.charAt(number.length() - 5)));
		int number3 = Integer.parseInt(Character.toString(number.charAt(number.length() - 9)));
		
		int digit1 = numberDate - number1;
		int digit2 = numberDate - number2;
		int digit3 = numberDate - number3;
			
		if(digit1 > 9) digit1 = digit1 % 10 + digit1/10;
		if(digit2 > 9) digit2 = digit2 % 10 + digit2/10;
		if(digit3 > 9) digit3 = digit3 % 10 + digit3/10;
		
		StringBuilder builder = new StringBuilder();
		builder.append(Math.abs(digit3));
		builder.append(Math.abs(digit2));
		builder.append(Math.abs(digit1));
		
		return builder.toString();
	}

	private static int getCheckDigit(String number) {

		int sum = 0;

		for (int i = 0; i < number.length(); i++) {

			int digit = Integer.parseInt(Character.toString(number.charAt(i)));

			if ((i % 2) == 0) {
				digit = digit * 2;
				if (digit > 9) {
					digit = (digit / 10) + (digit % 10);
				}
			}
			sum += digit;
		}

		int mod = sum % 10;
		return ((mod == 0) ? 0 : 10 - mod);
	}
}
