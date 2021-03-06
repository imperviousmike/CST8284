package cst8284.asgmt2.roomScheduler;

import java.io.Serializable;

/*
	Course Name: CST8284
	Student Name: Michael Daly
	Class name: Object Oriented Programming (Java)
	Date: March 2nd, 2020
*/


public class ContactInfo implements Serializable{

    private String firstName,lastName,phoneNumber,organization;
    
    public static final long serialVersionUID = 1L;

    public ContactInfo(String firstName, String lastName, String phoneNumber, String organization) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setOrganization(organization);
    }

    public ContactInfo(String firstName, String lastName, String phoneNumber) {
        this(firstName, lastName, phoneNumber, "Algonquin College");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return String.format("Contact Information:%s %s \nPhone: %s \n%s", getFirstName(), getLastName(), getPhoneNumber(), (getOrganization()) == null || getOrganization().isEmpty() ? "" : getOrganization() + "\n");
    }

}
