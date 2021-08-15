package com.example.certificate_app;

public class emplist {

    public emplist() {
    }

    String displayName, email, institute;

    public emplist(String displayName, String email, String institute) {
        this.displayName = displayName;
        this.email = email;
        this.institute = institute;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }
}
