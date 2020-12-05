package com.mhutshow.daktarlagbe.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mhutshow.daktarlagbe.R;
import com.mhutshow.daktarlagbe.model.Common.Common;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.mhutshow.daktarlagbe.model.Common.Common.convertBloodToInt;

public class PatientInfoActivity extends AppCompatActivity {

    EditText heightBtn;
    EditText weightBtn;
    Spinner bloodtypeSpinner;
    Button updateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        updateBtn = findViewById(R.id.updateInfoBtn);
        heightBtn = findViewById(R.id.heightBtn);
        weightBtn = findViewById(R.id.weightBtn);
        final Spinner specialiteList = (Spinner) findViewById(R.id.bloodType);
        ArrayAdapter<CharSequence> adapterSpecialiteList = ArrayAdapter.createFromResource(this,
                R.array.blood_spinner, android.R.layout.simple_spinner_item);
        adapterSpecialiteList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialiteList.setAdapter(adapterSpecialiteList);

        String patient_name = getIntent().getStringExtra("patient_name");
        String patient_email = getIntent().getStringExtra("patient_email");

        FirebaseFirestore.getInstance().collection("Patient").document(patient_email).collection("moreInfo")
                .document(patient_email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                weightBtn.setText( ""+documentSnapshot.getString("weight"));
                heightBtn.setText( ""+documentSnapshot.getString("height"));
                if(documentSnapshot.getString("bloodType") != null)
                specialiteList.setSelection(convertBloodToInt(documentSnapshot.getString("bloodType")));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("height",""+heightBtn.getText());
                map.put("weight",""+weightBtn.getText());
                map.put("bloodType",""+specialiteList.getSelectedItem().toString());
                Log.e("tag", "onClick: "+specialiteList.getTag() );
                FirebaseFirestore.getInstance().collection("Patient").document(patient_email).collection("moreInfo")
                        .document(patient_email).set(map);
                Toast.makeText(PatientInfoActivity.this,"Update Success!",Toast.LENGTH_SHORT).show();

            }
        });
       if(Common.CurrentUserType.equals("patient")){
            updateBtn.setVisibility(View.GONE);
            heightBtn.setEnabled(false);
            weightBtn.setEnabled(false);
            specialiteList.setEnabled(false);
        }
    }

}
