package com.elib.userclient.model;

import lombok.Data;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String streetNumber;
    private String streetName;
    private String zipCode;
    private String country;
}
