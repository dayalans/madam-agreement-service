package com.telecom.admin.exception;

public class SubscriptionNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubscriptionNotFoundException(String subsciptionCode){
        super("Subscription with"+subsciptionCode+"Not Found");
    }

}
