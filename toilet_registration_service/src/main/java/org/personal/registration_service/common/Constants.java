package org.personal.registration_service.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	public final static String USER_ID_HEADER = "X-USER-ID";
	public final static String ADMIN = "admin";
	public final static String APPROVE = "approve";
	public final static String REJECT = "reject";
}
