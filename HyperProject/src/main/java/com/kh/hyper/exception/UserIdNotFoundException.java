package com.kh.hyper.exception;

public class UserIdNotFoundException extends RuntimeException{
	
	public UserIdNotFoundException(String message) {
		super(message);
	}
}
