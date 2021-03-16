package com.project.workfix.errors;

public class ProductNotFound {

	private long id;
	
	private String message;
	
	private int code;
	
	ProductNotFound(long id, String message, int code){
		this.id=id;
		this.message=message;
		this.code=code;
	}
}
