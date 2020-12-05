package com.mhutshow.daktarlagbe.model.fireStoreApi;

import com.mhutshow.daktarlagbe.model.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientHelper {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference PatientRef = db.collection("Patient");

    public static void addPatient(String name, String adresse, String tel){
        Patient patient = new Patient(name,adresse,tel,FirebaseAuth.getInstance().getCurrentUser().getEmail(),"aaa", "aaa");
        System.out.println("Create object patient");
        PatientRef.document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).set(patient);
    }
}
