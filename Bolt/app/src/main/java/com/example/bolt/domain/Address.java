package com.example.bolt.domain;

public class Address {
    String Address;
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public Address() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
