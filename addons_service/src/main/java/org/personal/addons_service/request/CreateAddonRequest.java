package org.personal.addons_service.request;

import lombok.Builder;
import jakarta.validation.constraints.NotBlank;

public record CreateAddonRequest (String memoContent,
								  boolean isBookmarked,
								  @NotBlank String userEmail,
								  @NotBlank Long toiletLocationId){

	@Builder
	public CreateAddonRequest(String memoContent, boolean isBookmarked, String userEmail, Long toiletLocationId) {
		this.memoContent = memoContent;
		this.isBookmarked = isBookmarked;
		this.userEmail = userEmail;
		this.toiletLocationId = toiletLocationId;
	}
}
