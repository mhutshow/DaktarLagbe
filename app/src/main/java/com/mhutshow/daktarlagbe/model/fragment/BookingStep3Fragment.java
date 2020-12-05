package com.mhutshow.daktarlagbe.model.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mhutshow.daktarlagbe.model.Common.Common;
import com.mhutshow.daktarlagbe.R;
import com.mhutshow.daktarlagbe.model.ApointementInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class BookingStep3Fragment extends Fragment {

    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;
    @BindView(R.id.txt_booking_berber_text)
    TextView txt_booking_berber_text;
    @BindView(R.id.txt_booking_time_text)
    TextView txt_booking_time_text;
    @BindView(R.id.txt_booking_type)
    TextView txt_booking_type;
    @BindView(R.id.txt_booking_phone)
    TextView txt_booking_phone;

    @OnClick(R.id.btn_confirm)
    void confirmeApointement(){
        ApointementInformation apointementInformation = new ApointementInformation();
        apointementInformation.setApointementType(Common.Currentaappointementatype);
        apointementInformation.setDoctorId(Common.CurreentDoctor);
        apointementInformation.setDoctorName(Common.CurrentDoctorName);
        apointementInformation.setPatientName(Common.CurrentUserName);
        apointementInformation.setPatientId(Common.CurrentUserid);
        apointementInformation.setChemin("Doctor/"+Common.CurreentDoctor+"/"+Common.simpleFormat.format(Common.currentDate.getTime())+"/"+String.valueOf(Common.currentTimeSlot));
        apointementInformation.setType("Checked");
        apointementInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append("at")
                .append(simpleDateFormat.format(Common.currentDate.getTime())).toString());
        apointementInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

        DocumentReference bookingDate = FirebaseFirestore.getInstance()
                .collection("Doctor")
                .document(Common.CurreentDoctor)
                .collection(Common.simpleFormat.format(Common.currentDate.getTime()))
                .document(String.valueOf(Common.currentTimeSlot));

        bookingDate.set(apointementInformation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getActivity().finish();
                        Toast.makeText(getContext(),"Success!",Toast.LENGTH_SHORT).show();
                        Common.currentTimeSlot = -1;
                        Common.currentDate = Calendar.getInstance();
                        Common.step = 0;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseFirestore.getInstance().collection("Doctor").document(Common.CurreentDoctor)
                        .collection("apointementrequest").document(apointementInformation.getTime().replace("/","_")).set(apointementInformation);
                FirebaseFirestore.getInstance().collection("Patient").document(apointementInformation.getPatientId()).collection("calendar")
                        .document(apointementInformation.getTime().replace("/","_")).set(apointementInformation);

            }
        });

//
    }


    BroadcastReceiver confirmBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "onReceive: heave been receiver" );
            setData();
        }
    };


    private void setData() {
        txt_booking_berber_text.setText(Common.CurrentDoctorName);
        txt_booking_time_text.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
        .append("at")
        .append(simpleDateFormat.format(Common.currentDate.getTime())));
        txt_booking_phone.setText(Common.CurrentPhone);
        txt_booking_type.setText(Common.Currentaappointementatype);
    }

    public BookingStep3Fragment() {
        // Required empty public constructor
    }


    public static BookingStep3Fragment newInstance(String param1, String param2) {
        BookingStep3Fragment fragment = new BookingStep3Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());

        localBroadcastManager.registerReceiver(confirmBookingReceiver,new IntentFilter(Common.KEY_CONFIRM_BOOKING));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
        super.onDestroy();
    }

    static BookingStep3Fragment instance;
    public  static  BookingStep3Fragment getInstance(){
        if(instance == null )
            instance = new BookingStep3Fragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step3, container, false);
        unbinder = ButterKnife.bind(this,itemView);

        return itemView;
    }
}
