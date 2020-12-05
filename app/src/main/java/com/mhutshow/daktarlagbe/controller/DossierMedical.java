package com.mhutshow.daktarlagbe.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhutshow.daktarlagbe.R;
import com.mhutshow.daktarlagbe.model.Common.Common;
import com.mhutshow.daktarlagbe.model.adapter.ConsultationFragmentAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DossierMedical extends AppCompatActivity {
    private final static String TAG = "DossierMedical";
    private FloatingActionButton createNewFicheButton;
    private String patient_email;
    private Button infobtn;
    private String patient_name;
    private String patient_phone;
    final String patientID = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference patRef = db.collection("Patient").document("" + patientID + "");
    StorageReference pathReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_medical);
        ImageView image = findViewById(R.id.imageView2);
        patient_email = getIntent().getStringExtra("patient_email");
        this.configureViewPager();

        Log.d(TAG, "onCreate dossier medical activity: started");
        getIncomingIntent();

        createNewFicheButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        createNewFicheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPatientFiche();
            }
        });
        if(Common.CurrentUserType.equals("patient")){
            createNewFicheButton.setVisibility(View.GONE);
        }
        infobtn= findViewById(R.id.infobtn);
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientInfo();
            }
        });

        String imageId = patient_email+".jpg"; //add a title image
        pathReference = FirebaseStorage.getInstance().getReference().child("DoctorProfile/"+ imageId); //storage the image
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(image.getContext())
                        .load(uri)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(image);//Image location

                // profileImage.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }

    //Receive patient informations from the previous activity
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        //Check if the incoming intents exist
        if(getIntent().hasExtra("patient_name") && getIntent().hasExtra("patient_email")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            patient_name = getIntent().getStringExtra("patient_name");
            patient_email = getIntent().getStringExtra("patient_email");
            patient_phone = getIntent().getStringExtra("patient_phone");

            //set patient name, email, phone number
            setPatientInfos(patient_name, patient_email, patient_phone);
        }else{
            Log.d(TAG, "No intent");
            patRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    patient_name = documentSnapshot.getString("name");
                    patient_phone = documentSnapshot.getString("tel");
                    patient_email = documentSnapshot.getString("email");

                    //set patient name, email, phone number
                    setPatientInfos(patient_name, patient_email, patient_phone);
                }
            });
        }



    }

    //Add patient name, email, phone number to the medical folder
    private void setPatientInfos(String patient_name, String patient_email, String patient_phone){
        Log.d(TAG, "setPatientInfos: put patient infos");

        TextView name = findViewById(R.id.patient_name);
        name.setText(patient_name);

        TextView email = findViewById(R.id.patient_address);
        email.setText(patient_email);

        TextView number = findViewById(R.id.phone_number);
        number.setText(patient_phone);



    }

    private void configureViewPager() {
        // 1 - Get ViewPager from layout
        ViewPager pager = (ViewPager) findViewById(R.id.ViewPagerDossier);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new ConsultationFragmentAdapter(getSupportFragmentManager()));
        // 1 - Get TabLayout from layout
        TabLayout tabs = (TabLayout) findViewById(R.id.activity_main_tabs);
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
        //Set Adapter PageAdapter and glue it
        TextView text = new TextView(this);
        //((ViewGroup) tabs.getChildAt(0)).getChildAt(0).setBackgroundColor(0xFF00FF00);
        //((ViewGroup) tabs.getChildAt(0)).getChildAt(1).setBackgroundColor(0xFF00FF00);

    }

    private void openPatientFiche(){
        Intent intent = new Intent(this, FicheActivity.class);
        String patient_name = getIntent().getStringExtra("patient_name");
        String patient_email = getIntent().getStringExtra("patient_email");
        intent.putExtra("patient_email", patient_email);
        intent.putExtra("patient_name", patient_name);
        startActivity(intent);
    }

    private void openPatientInfo(){
        Intent intent = new Intent(this, PatientInfoActivity.class);
        String patient_name = getIntent().getStringExtra("patient_name");
        String patient_email = getIntent().getStringExtra("patient_email");
        intent.putExtra("patient_email", patient_email);
        intent.putExtra("patient_name", patient_name);
        startActivity(intent);
    }

}