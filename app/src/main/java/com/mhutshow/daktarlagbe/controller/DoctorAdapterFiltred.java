package com.mhutshow.daktarlagbe.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhutshow.daktarlagbe.R;
import com.mhutshow.daktarlagbe.model.Common.Common;
import com.mhutshow.daktarlagbe.model.Doctor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorAdapterFiltred  extends RecyclerView.Adapter<DoctorAdapterFiltred.DoctoreHolder2> implements Filterable {
    public static boolean specialiteSearch = false;
    static String doc;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference addRequest = db.collection("Request");
    private List<Doctor> mTubeList;
    private List<Doctor> mTubeListFiltered;
    StorageReference pathReference ;


    public DoctorAdapterFiltred(List<Doctor> tubeList){
        mTubeList = tubeList;
        mTubeListFiltered = tubeList;
    }

    @NonNull
    @Override
    public DoctoreHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,
                parent, false);
        return new DoctoreHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctoreHolder2 doctoreHolder, int i) {
        final Doctor doctor = mTubeListFiltered.get(i);
        final TextView t = doctoreHolder.title ;
        doctoreHolder.title.setText(doctor.getName());
        /// ajouter l'image

        String imageId = doctor.getEmail()+".jpg";
        pathReference = FirebaseStorage.getInstance().getReference().child("DoctorProfile/"+ imageId);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(doctoreHolder.image.getContext())
                        .load(uri)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(doctoreHolder.image);//Image location

                // profileImage.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
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
                Common.CurrentDoctorName = doctor.getName();
                Common.CurrentPhone = doctor.getTel();
                openPage(v.getContext());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTubeListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String pattern = constraint.toString().toLowerCase();
                if(pattern.isEmpty()){
                    mTubeListFiltered = mTubeList;
                } else {
                    List<Doctor> filteredList = new ArrayList<>();
                    for(Doctor tube: mTubeList){
                        if(specialiteSearch == false) {
                            if (tube.getName().toLowerCase().contains(pattern) || tube.getName().toLowerCase().contains(pattern)) {
                                filteredList.add(tube);
                            }
                        }
                        else{
                            if (tube.getSpecialite().toLowerCase().contains(pattern) || tube.getSpecialite().toLowerCase().contains(pattern)) {
                                filteredList.add(tube);
                            }
                        }
                    }
                    mTubeListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mTubeListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mTubeListFiltered = (ArrayList<Doctor>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    class DoctoreHolder2 extends RecyclerView.ViewHolder {

        Button appointemenBtn;
        TextView title;
        TextView specialite;
        ImageView image;
        Button addDoc;
        Button load;
        public DoctoreHolder2(@NonNull View itemView) {
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
