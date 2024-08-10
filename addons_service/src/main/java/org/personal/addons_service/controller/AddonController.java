package org.personal.addons_service.controller;

import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.request.CreateAddonRequest;
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
	public ResponseEntity<Void> createAddon(@RequestBody @Valid CreateAddonRequest request) {
		try {
			addonService.createAddon(request);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (AddonException e) {
			if (e.getErrorResult() == AddonErrorResult.DUPLICATED_ADDON_CREATE) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
