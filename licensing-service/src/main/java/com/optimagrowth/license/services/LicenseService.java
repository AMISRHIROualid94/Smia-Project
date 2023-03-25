package com.optimagrowth.license.services;

import com.optimagrowth.license.models.License;

import java.util.Locale;

public interface LicenseService {
    License getLicense(Long licenseId,Long organizationId);
    License createLicense(License license);
    License updateLicense(License license);
    String deleteLicense(Long licenseId);
}
