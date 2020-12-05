package com.mhutshow.daktarlagbe.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhutshow.daktarlagbe.model.Common.Common;
import com.mhutshow.daktarlagbe.R;
import com.mhutshow.daktarlagbe.controller.TestActivity;
import com.mhutshow.daktarlagbe.model.Doctor;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DoctoreAdapter extends FirestoreRecyclerAdapter<Doctor, DoctoreAdapter.DoctoreHolder> {
    static String doc;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference addRequest = db.collection("Request");

    public DoctoreAdapter(@NonNull FirestoreRecyclerOptions<Doctor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final DoctoreHolder doctoreHolder, int i, @NonNull final Doctor doctor) {
        final TextView t = doctoreHolder.title ;
        doctoreHolder.title.setText(doctor.getName());
        doctoreHolder.specialite.setText("Specialite : "+doctor.getSpecialite());
        final String idPat = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        final String idDoc = doctor.getEmail();
        // doctoreHolder.image.setImageURI(Uri.parse("drawable-v24/ic_launcher_foreground.xml"));
        doctoreHolder.addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> note = new HashMap<>();
                note.put("id_patient", idPat);
                note.put("id_doctor", idDoc);
                addRequest.document().set(note)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(t, "Demande envoyée", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                doctoreHolder.addDoc.setVisibility(View.INVISIBLE);
            }
        });
        doctoreHolder.appointemenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doc= doctor.getEmail();
                Common.CurreentDoctor = doctor.getEmail();
                openPage(v.getContext());

            }
        });

    }


    @NonNull
    @Override
    public DoctoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,
                parent, false);
        return new DoctoreHolder(v);
    }


    class DoctoreHolder extends RecyclerView.ViewHolder {
        Button appointemenBtn;
        TextView title;
        TextView specialite;
        ImageView image;
        Button addDoc;
        Button load;
        public DoctoreHolder(@NonNull View itemView) {
            super(itemView);
            addDoc = itemView.findViewById(R.id.addDocBtn);
            title= itemView.findViewById(R.id.doctor_view_title);
            specialite=itemView.findViewById(R.id.text_view_description);
            image=itemView.findViewById(R.id.doctor_item_image);
            appointemenBtn=itemView.findViewById(R.id.appointemenBtn);
        }
    }
    private void openPage(Context wf){
        Intent i = new Intent(wf, TestActivity.class);
        wf.startActivity(i);
    }


}
