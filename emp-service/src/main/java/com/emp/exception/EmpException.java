package com.emp.exception;

public class EmpException extends RuntimeException {

	String code;

	public EmpException(String code) {

		this.code = code;
	}

	public EmpException(String code, Exception e) {
		super(e);
		this.code = code;
	}
}
