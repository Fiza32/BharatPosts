package com.blog.exceptions;

public class DuplicateDataFoundException extends RuntimeException{

	public DuplicateDataFoundException(String msg) {
		super(msg);
	}
}
