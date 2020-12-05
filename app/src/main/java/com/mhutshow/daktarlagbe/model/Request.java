package com.mhutshow.daktarlagbe.model;

public class Request {
    private  String id_patient;
    private String id_doctor;
    private String hour_path;
    public Request(){

    }

    public String getHour_path() {
        return hour_path;
    }

    public void setHour_path(String hour_path) {
        this.hour_path = hour_path;
    }

    public String getId_patient() {
        return id_patient;
    }

    public void setId_patient(String id_patient) {
        this.id_patient = id_patient;
    }

    public String getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(String id_doctor) {
        this.id_doctor = id_doctor;
    }

    public Request(String id_patient, String id_doctor) {
        this.id_patient = id_patient;
        this.id_doctor = id_doctor;
    }
}
