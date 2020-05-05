package com.example.logintest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.logintest.Models.Restaurant;
import com.google.android.gms.common.util.RetainForClient;

public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";

    private TextView restName,btnDate,btnTime,tableNo,bookCost;
    private RadioGroup rgPaymentMethod;
    private Button btnBack,btnNext;
    private Restaurant rest;
    private String date,time,table;
    private RadioButton radioButton;
    private String radioText;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_booking,container,false);

        initViews(view);

        Bundle bundle = getArguments();
        try{
            rest = bundle.getParcelable("restaurant");
            date = bundle.getString("date");
            time = bundle.getString("time");
            table = bundle.getString("table");
            restName.setText(rest.getName());
            btnDate.setText(date);
            btnTime.setText(time);
            tableNo.setText(table);
            bookCost.setText(String.valueOf(rest.getBookingCost()) + " ₹");
        }catch (Exception e){
            e.printStackTrace();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: next" );
                int selectedId = rgPaymentMethod.getCheckedRadioButtonId();
                radioButton = (RadioButton)view.findViewById(selectedId);
                radioText = radioButton.getText().toString();
                goToPayment();
            }
        });

        return view;
    }

    private void goToPayment(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        goToPaymentResult(isConnected);
    }

    private void goToPaymentResult(boolean success){
        Log.d(TAG, "goToPaymentResult: " + success);
        if(success){
            SuccessfulFragment successfulFragment = new SuccessfulFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("restaurant",rest);
            bundle2.putString("date",btnDate.getText().toString());
            bundle2.putString("time",btnTime.getText().toString());
            bundle2.putString("table",tableNo.getText().toString());
            bundle2.putString("mode",radioText);
            successfulFragment.setArguments(bundle2);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in,R.anim.out)
                    .replace(R.id.frameLayout,successfulFragment).commit();
        }else{
            FailureFragment failureFragment = new FailureFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in,R.anim.out)
                    .replace(R.id.frameLayout,failureFragment).commit();
        }
    }
    private void initViews(View view){
        restName = view.findViewById(R.id.restName);
        btnDate = view.findViewById(R.id.btnDate);
        btnTime = view.findViewById(R.id.btnTime);
        tableNo = view.findViewById(R.id.tableNo);
        bookCost = view.findViewById(R.id.bookCost);

        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnNext = (Button) view.findViewById(R.id.btnFinish);

        rgPaymentMethod = (RadioGroup) view.findViewById(R.id.rgPaymentMethods);
    }
}
