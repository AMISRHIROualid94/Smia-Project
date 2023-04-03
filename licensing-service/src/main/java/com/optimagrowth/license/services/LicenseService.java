package com.optimagrowth.license.services;

import com.optimagrowth.license.models.License;
import com.optimagrowth.license.models.Organization;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface LicenseService {
    //License getLicense(Long licenseId,Long organizationId);
    License getLicense(Long licenseId, Long organizationId, String clientType);
    Organization retrieveOrganizationInfo(Long organizationId, String clientType);
    List<License> getLicensesByOrganization(Long organizationId) throws TimeoutException ;
    License createLicense(License license);
    License updateLicense(License license);
    String deleteLicense(Long licenseId);


    void randomlyRunLong() throws TimeoutException;
    void sleep() throws TimeoutException ;
}
