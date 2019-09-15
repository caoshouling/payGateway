/**
 * 
 */
package com.sinosoft.payGateway.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author Administrator
 *
 */
public class VerificationCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public VerificationCodeException(String msg) {
		super(msg);
	}

}
