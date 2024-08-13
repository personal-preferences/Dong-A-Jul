package org.personal.addons_service.controller;

import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.request.GetAddonRequest;
import org.personal.addons_service.request.UpdateAddonRequest;
import org.personal.addons_service.response.AddonResponse;
import org.personal.addons_service.service.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/addons")
public class AddonController {

	private final AddonService addonService;

	@Autowired
	public AddonController(AddonService addonService) {
		this.addonService = addonService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createAddon(@RequestBody @Valid CreateAddonRequest request) {
		AddonResponse response = addonService.createAddon(request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.header("Location", "/addons/" + response.addonId())
			.body(response);
	}

	@PostMapping("/get")
	public ResponseEntity<?> getAddon(@RequestBody GetAddonRequest request) {
		AddonResponse response = addonService.getAddon(request);
		return ResponseEntity.status(HttpStatus.OK)
			.body(response);
	}

	@PatchMapping("/{addonId}")
	public ResponseEntity<?> updateAddon(@PathVariable Long addonId,
										@RequestHeader("userEmail") String userEmail,
										@RequestBody @Valid UpdateAddonRequest request) {
		AddonResponse response = addonService.updateAddon(addonId, userEmail, request);
		return ResponseEntity.status(HttpStatus.OK)
			.body(response);
	}

}
