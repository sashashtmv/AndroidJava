package com.sashashtmv06.waterorder.model;

public class Customer {
    private String name;
    private String nameCompany;
    private String telephoneNumber;
    private String nameStreet;
    private String numberHouse;
    private String flatNumber;

    public Customer(String name, String nameCompany, String telephoneNumber, String nameStreet, String numberHouse, String flatNumber) {
        this.name = name;
        this.nameCompany = nameCompany;
        this.telephoneNumber = telephoneNumber;
        this.nameStreet = nameStreet;
        this.numberHouse = numberHouse;
        this.flatNumber = flatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getNameStreet() {
        return nameStreet;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public String getNumberHouse() {
        return numberHouse;
    }

    public void setNumberHouse(String numberHouse) {
        this.numberHouse = numberHouse;
    }
}
