package com.optimagrowth.organization.services;

import com.optimagrowth.organization.model.Organization;

public interface OrganizationService {
    Organization findById(Long organizationId);
    Organization create(Organization organization);
    void update(Organization organization);
    void deleteById(Long organizationId);
}
