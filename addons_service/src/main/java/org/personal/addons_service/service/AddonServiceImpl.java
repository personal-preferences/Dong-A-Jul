package org.personal.addons_service.service;

import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.mapper.AddonMapper;
import org.personal.addons_service.repository.AddonRepository;
import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.request.GetAddonRequest;
import org.personal.addons_service.request.UpdateAddonRequest;
import org.personal.addons_service.response.AddonResponse;
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
	public AddonResponse createAddon(CreateAddonRequest request) {

		addonRepository.findByUserEmailAndToiletLocationId(request.userEmail(), request.toiletLocationId())
			.ifPresent(existingAddon -> {
				throw new AddonException(AddonErrorResult.DUPLICATED_ADDON_CREATE);
			});

		Addon addon = addonMapper.toEntity(request);
		Addon savedAddon = addonRepository.save(addon);

		return addonMapper.toDto(savedAddon);
	}

	@Override
	public AddonResponse getAddon(GetAddonRequest request) {

		Addon addon = addonRepository.findByUserEmailAndToiletLocationId(request.userEmail(), request.toiletLocationId())
			.orElseThrow(() -> new AddonException(AddonErrorResult.ADDON_NOT_FOUND));

		return addonMapper.toDto(addon);
	}

	@Override
	public AddonResponse updateAddon(Long addonId, String userEmail, UpdateAddonRequest request) {

		Addon addon = addonRepository.findById(addonId)
			.orElseThrow(() -> new AddonException(AddonErrorResult.ADDON_NOT_FOUND));

		if (!addon.getUserEmail().equals(userEmail)) {
			throw new AddonException(AddonErrorResult.UNAUTHORIZED_ACCESS);
		}

		Addon updatedAddon = addon.toBuilder()
			.memoContent(request.memoContent() != null ? request.memoContent() : addon.getMemoContent())
			.isBookmarked(request.isBookmarked() != null ? request.isBookmarked() : addon.isBookmarked())
			.build();

		Addon savedAddon = addonRepository.save(updatedAddon);

		return addonMapper.toDto(savedAddon);
	}
}
