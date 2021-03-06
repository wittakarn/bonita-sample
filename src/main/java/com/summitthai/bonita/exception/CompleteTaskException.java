package com.summitthai.bonita.exception;

import java.io.Serializable;


public class CompleteTaskException extends BPMException implements Serializable {

	private static final long serialVersionUID = 1L;

	public CompleteTaskException() {
		super();
	}
	
	public CompleteTaskException(String message) {
		super(message);
	}
	
	public CompleteTaskException(Throwable throwable) {
		super(throwable);
	}
	
	public CompleteTaskException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
