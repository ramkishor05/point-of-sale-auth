package com.brijframework.authorization.context;

/**
 *  @author omnie
 */
public class ApiSecurityContext {
	
	private static ApiSecurityContext securityContext;
	
	private ThreadLocal<Object> userAccountRequest=new ThreadLocal<Object>();

	/**
	 * @return
	 */
	public static ApiSecurityContext getContext() {
		synchronized (ApiSecurityContext.class) {
			if(securityContext==null) {
				securityContext=new ApiSecurityContext();
			}
		}
		return securityContext;
	}

	/**
	 * @return
	 */
	public Object getCurrentAccount() {
		return userAccountRequest.get();
	}

	/**
	 * @param eoUserAccount
	 */
	public void setCurrentAccount(Object eoUserAccount) {
		this.userAccountRequest.set(eoUserAccount);
	}

}
