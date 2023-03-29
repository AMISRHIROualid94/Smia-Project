package com.optimagrowth.organization.controller;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long organizationId){
        Organization organization = organizationService.findById(organizationId);
        return ResponseEntity.ok(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization){
        return ResponseEntity.ok(organizationService.create(organization));
    }

    @PutMapping(value = "/{organizationId}")
    public ResponseEntity<String> updateOrganization(@PathVariable Long organizationId,@RequestBody Organization organization){
        organization.setId(organizationId);
        organizationService.update(organization);
        return ResponseEntity.ok(String.format("Organization %s updated Successfully!!",organizationId));
    }

    @DeleteMapping(value = "/{organizationId}")
    public ResponseEntity<String> deleteOrganization(@PathVariable Long organizationId){
        organizationService.deleteById(organizationId);
        return ResponseEntity.ok(String.format("Organization %s deleted successfully",organizationId));
    }
}
