package com.mhutshow.daktarlagbe.model;

import java.util.Date;

public class Dossier {
    private String dossierID;
    private Date dateCreation;

    public Dossier(String dossierID, Date dateCreation) {
        this.dossierID = dossierID;
        this.dateCreation = dateCreation;
    }

    public String getDossierID() {
        return dossierID;
    }

    public void setDossierID(String dossierID) {
        this.dossierID = dossierID;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
