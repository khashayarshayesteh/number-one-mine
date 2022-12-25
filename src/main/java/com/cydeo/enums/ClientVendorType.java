package com.cydeo.enums;

public enum ClientVendorType {

    VENDOR("Vendor"), CLIENT("Client");

    private final String value;

    ClientVendorType(String value) {
        this.value = value;
    }
}
