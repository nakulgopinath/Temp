package com.ibm.rms.exception;

public class DataException extends Exception {

	public DataException() {
		super();
	}

	public DataException(String message, Throwable e) {
		super(message, e);
	}
}