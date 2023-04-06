package com.optimagrowth.license.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Organization {

    private Long Id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
