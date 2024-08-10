package org.personal.addons_service.service;

import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.mapper.AddonMapper;
import org.personal.addons_service.repository.AddonRepository;
import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.response.AddonCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddonServiceImpl implements AddonService {

	private final AddonRepository addonRepository;
	private final AddonMapper addonMapper;

	@Autowired
	public AddonServiceImpl(AddonRepository addonRepository, AddonMapper addonMapper) {
		this.addonRepository = addonRepository;
		this.addonMapper = addonMapper;
	}


	@Override
	public AddonCreateResponse createAddon(CreateAddonRequest request) {

		addonRepository.findByUserEmailAndToiletLocationId(request.userEmail(), request.toiletLocationId())
			.ifPresent(existingAddon -> {
				throw new AddonException(AddonErrorResult.DUPLICATED_ADDON_CREATE);
			});

		Addon addon = addonMapper.toEntity(request);
		Addon savedAddon = addonRepository.save(addon);

		return AddonCreateResponse.builder()
			.addonId(savedAddon.getAddonId())
			.memoContent(savedAddon.getMemoContent())
			.isBookmarked(savedAddon.isBookmarked())
			.userEmail(savedAddon.getUserEmail())
			.toiletLocationId(savedAddon.getToiletLocationId())
			.build();
	}
}
