package org.personal.addons_service.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddonException extends RuntimeException {

	private final AddonErrorResult errorResult;


}

