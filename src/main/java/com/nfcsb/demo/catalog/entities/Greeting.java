package com.nfcsb.demo.catalog.entities;

import lombok.Data;


@Data
public class Greeting {
    private final String firstName;
    private final String lastName;
    private final String message;
}
