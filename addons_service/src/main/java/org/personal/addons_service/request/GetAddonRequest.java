package org.personal.addons_service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record GetAddonRequest(@NotBlank String userEmail,
							  @NotNull Long toiletLocationId) {

	@Builder
	public GetAddonRequest(String userEmail, Long toiletLocationId) {
		this.userEmail = userEmail;
		this.toiletLocationId = toiletLocationId;
	}
}
