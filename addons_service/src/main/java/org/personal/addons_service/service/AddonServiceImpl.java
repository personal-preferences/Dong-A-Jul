package org.personal.addons_service.service;

import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.repository.AddonRepository;
import org.personal.addons_service.request.CreateAddonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddonServiceImpl implements AddonService {

	private final AddonRepository addonRepository;

	@Autowired
	public AddonServiceImpl(AddonRepository addonRepository) {
		this.addonRepository = addonRepository;
	}

	@Override
	public void createAddon(CreateAddonRequest request) {

		addonRepository.findByUserEmailAndToiletLocationId(request.userEmail(), request.toiletLocationId())
			.ifPresent(existingAddon -> {
				throw new AddonException(AddonErrorResult.DUPLICATED_ADDON_CREATE);
			});

		Addon addon = Addon.builder()
			.memoContent(request.memoContent())
			.isBookmarked(request.isBookmarked())
			.userEmail(request.userEmail())
			.toiletLocationId(request.toiletLocationId())
			.build();

		addonRepository.save(addon);

	}

}
