package org.personal.addons_service.exception;

public abstract class AddonException extends RuntimeException {
	public AddonException(String message) {
		super(message);
	}
}
