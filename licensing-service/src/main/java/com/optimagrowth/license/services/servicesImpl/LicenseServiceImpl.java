package com.optimagrowth.license.services.servicesImpl;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.models.License;
import com.optimagrowth.license.services.LicenseService;
import com.optimagrowth.license.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;

    private final ServiceConfig config;

    @Override
    public License getLicense(Long licenseId,Long organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId,licenseId);
        if (license == null){
            throw new IllegalArgumentException(String.format(messageSource.getMessage("license.search.error.message",null,null),licenseId,organizationId));
        }

        return license.withComment(config.getProperty());
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
