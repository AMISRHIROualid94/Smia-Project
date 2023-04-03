package com.optimagrowth.license.repository;

import com.optimagrowth.license.models.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LicenseRepository extends CrudRepository<License,Long> {

    List<License> findByOrganizationId(Long organizationId);
    License findByOrganizationIdAndLicenseId(Long organizationId,Long licenseId);
}
