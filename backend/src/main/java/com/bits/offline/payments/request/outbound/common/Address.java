package com.bits.offline.payments.request.outbound.common;

public class Address {
    private String country;
    private String county;
    private String state;
    private String zipCode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    static Address createAddress() {
        Address address = new Address();
        address.setCountry("USA");
        address.setCounty("San Mateo");
        address.setState("CA");
        address.setZipCode("94404");
        return address;
    }
}
