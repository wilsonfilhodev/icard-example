package com.example.icard.service.execption;

public class CardNotFoundException extends Exception {

	private static final long serialVersionUID = 4372822393481381960L;
	
	public CardNotFoundException(String message) {
		super(message);
	}

}
