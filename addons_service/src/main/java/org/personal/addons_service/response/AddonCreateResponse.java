package org.personal.addons_service.response;

import lombok.Builder;

public record AddonCreateResponse(Long addonId,
								  String memoContent,
								  boolean isBookmarked,
								  String userEmail,
								  Long toiletLocationId) {

	@Builder
	public AddonCreateResponse(Long addonId, String memoContent, boolean isBookmarked, String userEmail,
		Long toiletLocationId) {
		this.addonId = addonId;
		this.memoContent = memoContent;
		this.isBookmarked = isBookmarked;
		this.userEmail = userEmail;
		this.toiletLocationId = toiletLocationId;
	}
}
