package com.example.mymallapp;
public class AddressesModel {
    private String fullName;
    private String addresses;
    private String pinCode;
    private Boolean selected;

    public AddressesModel(String fullName, String addresses, String pinCode,Boolean selected) {
        this.fullName = fullName;
        this.addresses = addresses;
        this.pinCode = pinCode;
        this.selected=selected;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
