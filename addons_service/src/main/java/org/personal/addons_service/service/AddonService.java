package org.personal.addons_service.service;

import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.request.GetAddonRequest;
import org.personal.addons_service.request.UpdateAddonRequest;
import org.personal.addons_service.response.AddonResponse;

public interface AddonService {
	AddonResponse createAddon(CreateAddonRequest request);
	AddonResponse getAddon(GetAddonRequest request);
	AddonResponse updateAddon(Long addonId, String userEmail, UpdateAddonRequest request);
}
