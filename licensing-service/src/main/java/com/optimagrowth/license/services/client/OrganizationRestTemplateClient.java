package com.optimagrowth.license.services.client;

import com.optimagrowth.license.models.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public Organization getOrganization(Long organizationId){
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "",
                        HttpMethod.GET,
                        null,
                        Organization.class,
                        organizationId
                );
        return restExchange.getBody();
    }

}
