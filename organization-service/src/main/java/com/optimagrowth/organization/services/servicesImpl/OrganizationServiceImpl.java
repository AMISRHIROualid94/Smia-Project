package com.optimagrowth.organization.services.servicesImpl;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import com.optimagrowth.organization.services.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public Organization findById(Long organizationId) {
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        return organization.orElse(null);
    }

    @Override
    public Organization create(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public void update(Organization organization) {
        organizationRepository.save(organization);
    }

    @Override
    public void deleteById(Long organizationId) {
        organizationRepository.deleteById(organizationId);
    }
}
