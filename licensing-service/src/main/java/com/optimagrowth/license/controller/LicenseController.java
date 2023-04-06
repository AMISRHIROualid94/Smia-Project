package com.optimagrowth.license.controller;


import com.optimagrowth.license.models.License;
import com.optimagrowth.license.services.LicenseService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    @CircuitBreaker(name = "licenseService",fallbackMethod = "fbMethod")
    @Retry(name = "retryLicenseService",fallbackMethod = "fbMethod")
    @RateLimiter(name = "licenseService",fallbackMethod = "fbMethod")
    @Bulkhead(name = "bulkheadLicenseService",type = Bulkhead.Type.THREADPOOL,fallbackMethod = "fbMethod")
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
    public ResponseEntity<String> fbMethod(Long organizationId,Long licenseId, RuntimeException runtimeException){
        return ResponseEntity.ok("Somthing wrong! try after some minutes.");
    }
}
