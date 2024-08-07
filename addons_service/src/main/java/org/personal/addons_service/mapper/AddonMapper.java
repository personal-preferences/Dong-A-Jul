package org.personal.addons_service.mapper;

import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.response.AddonResponse;
import org.springframework.stereotype.Component;

@Component
public class AddonMapper {

	public Addon toEntity (CreateAddonRequest request) {
		return Addon.builder()
			.memoContent(request.memoContent())
			.isBookmarked(request.isBookmarked())
			.userEmail(request.userEmail())
			.toiletLocationId(request.toiletLocationId())
			.build();
	}

	public AddonResponse toDto(Addon addon) {
		return AddonResponse.builder()
			.userEmail(addon.getUserEmail())
			.toiletLocationId(addon.getToiletLocationId())
			.isBookmarked(addon.isBookmarked())
			.memoContent(addon.getMemoContent())
			.build();
	}


}
