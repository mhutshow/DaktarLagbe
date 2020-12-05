package com.mhutshow.daktarlagbe.model;

public class Hour {
    private String patient;
    private String choosen;
    private String patientName;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Hour(String patient) {
        this.patient = patient;
        choosen = "false";
    }
    public Hour(){

    }
    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getChoosen() {
        return choosen;
    }

    public void setChoosen(String choosen) {
        this.choosen = choosen;
    }
}
