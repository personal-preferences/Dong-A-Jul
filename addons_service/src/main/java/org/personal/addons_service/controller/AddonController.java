package org.personal.addons_service.controller;

import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.response.AddonResponse;
import org.personal.addons_service.service.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping
	public ResponseEntity<?> createAddon(@RequestBody @Valid CreateAddonRequest request) {
		AddonResponse response = addonService.createAddon(request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.header("Location", "/addons/" + response.addonId())
			.body(response);
	}

}
