package com.mhutshow.daktarlagbe.model;

import java.util.Date;

public class RDV {
    private int noRDV;
    private Date dateRDV;
    private Date datePriseRDV;
    private String nomMedecin;

    public RDV(int noRDV, Date dateRDV, Date datePriseRDV, String nomMedecin) {
        this.noRDV = noRDV;
        this.dateRDV = dateRDV;
        this.datePriseRDV = datePriseRDV;
        this.nomMedecin = nomMedecin;
    }

    public int getNoRDV() {
        return noRDV;
    }

    public void setNoRDV(int noRDV) {
        this.noRDV = noRDV;
    }

    public Date getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(Date dateRDV) {
        this.dateRDV = dateRDV;
    }

    public Date getDatePriseRDV() {
        return datePriseRDV;
    }

    public void setDatePriseRDV(Date datePriseRDV) {
        this.datePriseRDV = datePriseRDV;
    }

    public String getNomMedecin() {
        return nomMedecin;
    }

    public void setNomMedecin(String nomMedecin) {
        this.nomMedecin = nomMedecin;
    }

    //Afficher l'etat de RDV
    public void etatRDV(){
        System.out.println("Le Rendez-vous "+getNoRDV()+" est en cours");
    }
}
