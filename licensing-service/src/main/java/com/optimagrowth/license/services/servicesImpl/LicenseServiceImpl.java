package com.optimagrowth.license.services.servicesImpl;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.models.License;
import com.optimagrowth.license.models.Organization;
import com.optimagrowth.license.services.LicenseService;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.services.client.OrganizationDiscoveryClient;
import com.optimagrowth.license.services.client.OrganizationFeignClient;
import com.optimagrowth.license.services.client.OrganizationRestTemplateClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;


@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceImpl.class);
    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;

    @Autowired
    OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;

    private final ServiceConfig config;

    @Override
    public License getLicense(Long licenseId,Long organizationId,String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId,licenseId);
        if (license == null){
            throw new IllegalArgumentException(String.format(messageSource.getMessage("license.search.error.message",null,null),licenseId,organizationId));
        }
        Organization organization = retrieveOrganizationInfo(organizationId,clientType);
        if (organization != null){
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }
        return license.withComment(config.getProperty());
    }

    @Override
    public Organization retrieveOrganizationInfo(Long organizationId, String clientType) {
        Organization organization = null;
        switch (clientType){
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestTemplateClient.getOrganization(organizationId);
        }
        return organization;
    }

    @Override
    public List<License> getLicensesByOrganization(Long organizationId) throws TimeoutException {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    @Override
    public License createLicense(License license) {
            licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    @Override
    public License updateLicense(License license) {
        licenseRepository.save(license);
       return license.withComment(config.getProperty());
    }

    @Override
    public String deleteLicense(Long licenseId) {
        String responseMessage = null;
        licenseRepository.deleteById(licenseId);
        responseMessage = String.format(messageSource.getMessage("license.delete.message",null,null),licenseId);
        return responseMessage;
    }

}
