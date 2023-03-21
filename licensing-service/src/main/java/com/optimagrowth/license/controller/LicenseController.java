package com.optimagrowth.license.controller;


import com.optimagrowth.license.models.License;
import com.optimagrowth.license.services.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @RequestMapping(value = "/{licenseId}",method = RequestMethod.GET)
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
                                              @PathVariable("licenseId") String licenseId){
        License license = licenseService.getLicense(organizationId,licenseId);
        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(@RequestBody License license,
                                                @PathVariable("organizationId") String organizationId,
                                                @RequestHeader(value = "Accept-language",required = false) Locale locale){
        return ResponseEntity.ok(licenseService.updateLicense(license,organizationId,locale));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(@RequestBody License license,
                                                @PathVariable("organizationId") String organizationId,
                                                @RequestHeader(value = "Accept-language",required = false) Locale locale){
        return ResponseEntity.ok(licenseService.createLicense(license,organizationId,locale));
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deletLicense(@PathVariable("organizationId") String organizationId,
                                               @PathVariable("licenseId") String licenseId,
                                               @RequestHeader(value = "Accept-language",required = false) Locale locale){
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId,organizationId,locale));
    }
}
