package com.summitthai.bonita.exception;

import java.io.Serializable;


public class SearchTaskException extends BPMException implements Serializable {

	private static final long serialVersionUID = 1L;

	public SearchTaskException() {
		super();
	}
	
	public SearchTaskException(String message) {
		super(message);
	}
	
	public SearchTaskException(Throwable throwable) {
		super(throwable);
	}
	
	public SearchTaskException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
