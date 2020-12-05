package com.mhutshow.daktarlagbe.model;

public class Doctor {
    private String name;
    private String adresse;
    private String tel;
    private String email;
    private String specialite;

    public Doctor(){
        //needed for firebase
    }

    public Doctor(String name, String adresse, String tel, String email, String specialite) {
        this.name = name;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
        this.specialite = specialite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
