package com.example.vamsi.login.models;

public class Client {

    private String fullName;
    private String username;
    private String email;
    private String cellnumber;
    private String medialAid;

    public Client() {
    }

    public Client(String fullName, String username, String email, String cellnumber) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.cellnumber = cellnumber;
    }

    public Client(String fullName, String username, String email, String cellnumber, String medicalAid) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.cellnumber = cellnumber;
        this.medialAid = medicalAid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellnumber() {
        return cellnumber;
    }

    public void setCellnumber(String cellnumber) {
        this.cellnumber = cellnumber;
    }

    public String getMedialAid() {
        return medialAid;
    }

    public void setMedialAid(String medialAid) {
        this.medialAid = medialAid;
    }
}
