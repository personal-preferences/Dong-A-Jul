package org.personal.addons_service.response;

import lombok.Builder;
import lombok.NoArgsConstructor;

public record AddonResponse(Long addonId,
							String memoContent,
							boolean isBookmarked,
							String userEmail,
							Long toiletLocationId) {

	@Builder
	public AddonResponse(Long addonId, String memoContent, boolean isBookmarked, String userEmail,
		Long toiletLocationId) {
		this.addonId = addonId;
		this.memoContent = memoContent;
		this.isBookmarked = isBookmarked;
		this.userEmail = userEmail;
		this.toiletLocationId = toiletLocationId;
	}

}
