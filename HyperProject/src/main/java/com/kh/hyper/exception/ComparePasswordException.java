package com.kh.hyper.exception;

public class ComparePasswordException extends RuntimeException { // 예외 상속받는 클래스 생성

	public ComparePasswordException(String message) {
		super(message);
	}
}
