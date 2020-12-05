package com.mhutshow.daktarlagbe.model.fireStoreApi;

import com.mhutshow.daktarlagbe.model.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorHelper {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference DoctorRef = db.collection("Doctor");

    public static void addDoctor(String name, String adresse, String tel,String specialite){
        Doctor doctor = new Doctor(name,adresse,tel, FirebaseAuth.getInstance().getCurrentUser().getEmail(),specialite);

        DoctorRef.document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).set(doctor);

    }
}
