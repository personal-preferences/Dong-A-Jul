package org.personal.addons_service.service;

import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.response.AddonCreateResponse;

public interface AddonService {
	AddonCreateResponse createAddon(CreateAddonRequest request);
}
