package org.personal.addons_service.mapper;

import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.response.AddonCreateResponse;
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

	public AddonCreateResponse toDto(Addon addon) {
		return AddonCreateResponse.builder()
			.addonId(addon.getAddonId())
			.userEmail(addon.getUserEmail())
			.toiletLocationId(addon.getToiletLocationId())
			.isBookmarked(addon.isBookmarked())
			.memoContent(addon.getMemoContent())
			.build();
	}


}
