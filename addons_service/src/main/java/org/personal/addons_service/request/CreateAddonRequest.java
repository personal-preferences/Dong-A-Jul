package org.personal.addons_service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import jakarta.validation.constraints.NotBlank;

public record CreateAddonRequest (String memoContent,
								  boolean isBookmarked,
								  @NotBlank String userEmail,
								  @NotNull Long toiletLocationId){

	@Builder
	public CreateAddonRequest(String memoContent, boolean isBookmarked, String userEmail, Long toiletLocationId) {
		this.memoContent = memoContent;
		this.isBookmarked = isBookmarked;
		this.userEmail = userEmail;
		this.toiletLocationId = toiletLocationId;
	}
}
