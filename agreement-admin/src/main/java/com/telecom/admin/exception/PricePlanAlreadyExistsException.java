package com.telecom.admin.exception;

public class PricePlanAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PricePlanAlreadyExistsException(String pricePlanCode){
        super("Price Plan With PricePlanCode "+pricePlanCode+" Already exists");
    }

}