package com.app.nycschools.Data;


//  20200423-LamarCaaddfiir-NYCSchools
//
//  Created by Lamar Caaddfiir on 4/20/23.
//  Copyright © 2020 Lamar Caaddfiir. All rights reserved.


public class SchoolData {

    public String schoolDbn;    //Primary Key
    public String schoolName;
    public String phoneNumber;
    public String website;
    public String email;
    public int totalStudents;
    public String address;
    public String city;
    public String zip;
    public String borough;

    public SchoolData(String schoolDbn, String schoolName, String phoneNumber, String website, String email,
                      int totalStudents, String address, String city, String zip, String borough) {
        this.schoolDbn = schoolDbn;
        this.schoolName = schoolName;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.email = email;
        this.totalStudents = totalStudents;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.borough = borough;
    }

    public String getSchoolDbn() {
        return schoolDbn;
    }

    public void setSchoolDbn(String schoolDbn) {
        this.schoolDbn = schoolDbn;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }
}
