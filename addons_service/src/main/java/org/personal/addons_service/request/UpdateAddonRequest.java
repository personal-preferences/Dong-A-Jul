package org.personal.addons_service.request;

import lombok.Builder;

public record UpdateAddonRequest (String memoContent,
								  Boolean isBookmarked) {

	@Builder
	public UpdateAddonRequest(String memoContent, Boolean isBookmarked) {
		this.memoContent = memoContent;
		this.isBookmarked = isBookmarked;
	}
}
