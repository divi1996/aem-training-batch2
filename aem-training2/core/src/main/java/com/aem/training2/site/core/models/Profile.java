package com.aem.training2.site.core.models;

public class Profile {
    private String name;
    private String gender;
    private String occupation;
    private String country;
    private String preference;

    public Profile(String name, String gender, String occupation, String country, String preference) {
        this.name = name;
        this.gender = gender;
        this.occupation = occupation;
        this.country = country;
        this.preference = preference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
