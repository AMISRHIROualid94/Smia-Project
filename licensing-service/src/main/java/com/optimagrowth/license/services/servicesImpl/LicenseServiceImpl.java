package com.optimagrowth.license.services.servicesImpl;

import com.optimagrowth.license.controller.LicenseController;
import com.optimagrowth.license.models.License;
import com.optimagrowth.license.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public License getLicense(String organizationId,String licenseId) {
        License license = new License();
        license.setId(new Random().nextInt(1000));
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setDescription("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");
        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId,licenseId)).withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(license,organizationId,null)).withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).updateLicense(license,organizationId,null)).withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).deletLicense(organizationId,license.getLicenseId(),null)).withRel("deleteLicense")
        );
        return license;
    }

    @Override
    public String createLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null){
            license.setOrganizationId(organizationId);
            responseMessage = String.format(messageSource.getMessage("license.create.message",null,locale),license.toString());
        }
        return responseMessage;
    }

    @Override
    public String updateLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null){
            license.setOrganizationId(organizationId);
            responseMessage = String.format(messageSource.getMessage("license.update.message",null,locale),license.toString());
        }
        return responseMessage;
    }

    @Override
    public String deleteLicense(String licenseId, String organizationId, Locale locale) {
        return String.format(messageSource.getMessage("license.delete.message",null,locale),licenseId,organizationId);
    }
}
