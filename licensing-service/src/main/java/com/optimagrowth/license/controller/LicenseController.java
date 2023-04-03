package com.optimagrowth.license.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optimagrowth.license.models.License;
import com.optimagrowth.license.services.LicenseService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    @CircuitBreaker(name = "licenseService",fallbackMethod = "fbMethod")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") Long organizationId,
                                              @PathVariable("licenseId") Long licenseId){
        License license = licenseService.getLicense(licenseId,organizationId,"");
        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId())).withRel("deleteLicense")
        );
        return ResponseEntity.ok(license);
    }

    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicenseWithClient( @PathVariable("organizationId") Long organizationId,
                                         @PathVariable("licenseId") Long licenseId,
                                         @PathVariable("clientType") String clientType){
        return licenseService.getLicense(licenseId,organizationId,clientType);
    }
    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License license){
        return ResponseEntity.ok(licenseService.updateLicense(license));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License license){
        return ResponseEntity.ok(licenseService.createLicense(license));
    }

    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") Long licenseId){
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }

    @GetMapping(value = "/")
    public List<License> getLicenses(@PathVariable("organizationId") Long organizationId) throws TimeoutException {
        return licenseService.getLicensesByOrganization(organizationId);
    }

    //Circuitbreaker fallback Method
    public ResponseEntity<License> fbMethod(Long organizationId,Long licenseId, RuntimeException runtimeException){
        License license = new License();
        license.setDescription("Circuit breaker");
        return ResponseEntity.ok(license);
    }
}
