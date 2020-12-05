package com.mhutshow.daktarlagbe.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhutshow.daktarlagbe.R;
import com.mhutshow.daktarlagbe.model.Hour;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppointementActivity extends AppCompatActivity {

    private LinearLayout lis;
    //final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement);
        lis = findViewById(R.id.listDate);
        String patient_email = getIntent().getStringExtra("key1");
        String day = getIntent().getStringExtra("key2");
        final CollectionReference addRequest = db.collection("Doctor").document(patient_email).collection("calendar").document(day).collection("hour");


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(140, 398);
        layoutParams.setMargins(200, 0, 300, 0);

        for (int i = 8; i<19;i++){
            final int j = i;
            TextView text = new TextView(this);
            text.setText(i + ":00");
            LinearLayout l = new LinearLayout(this);
            l.setMinimumHeight(250);
            l.addView(text, layoutParams);
            final Button btn = new Button(this);
            addRequest.document(i+"").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Hour note = documentSnapshot.toObject(Hour.class);
                    if(note != null){
                        btn.setText("already choosen");
                    }
                    else{
                        btn.setText("confirme this hour");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Hour h =new Hour(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                                addRequest.document(j+"").set(h);
                            }
                        });
                    }

                }
            });

            l.addView(btn);
            lis.addView(l);
        }

    }


}