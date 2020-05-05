package com.example.logintest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.logintest.Models.Restaurant;

import java.util.Calendar;

public class BookingDialog extends DialogFragment {
    private static final String TAG = "BookingDialog";

    private TextView restName,btnDate,btnTime,tableNo,bookCost,warning;
    private Button btnPayment;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String amPm = "";
    private boolean dateSelected = false;
    private boolean timeSelected = false;
    private Restaurant rest;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.booking_details_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Booking")
                .setView(view);

        initialize(view);
        final Bundle bundle = getArguments();

        try{
            rest = bundle.getParcelable("restaurant");
            String table = bundle.getString("table");
            restName.setText(rest.getName());
            bookCost.setText(String.valueOf(rest.getBookingCost()) + " ₹");
            tableNo.setText(table);
        }catch (Exception e){
            e.printStackTrace();
        }

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int m_year, int m_month, int m_dayOfMonth) {
                            m_month = m_month + 1;
                            btnDate.setText(m_dayOfMonth + "/" + m_month + "/" + m_year);
                            dateSelected = true;
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int min = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay > 12 ){
                            hourOfDay = hourOfDay - 12;
                            amPm = "PM";
                        }else if(hourOfDay == 12){
                            amPm = "PM";
                        }else{
                            amPm = "AM";
                        }
                        btnTime.setText(String.format("%02d : %02d",hourOfDay,minute) + " " + amPm);
                        timeSelected = true;
                    }
                },hour,min,false);
                timePickerDialog.show();
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateSelected && timeSelected){
                    warning.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getActivity(),BookingActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelable("restaurant",rest);
                    bundle1.putString("date",btnDate.getText().toString());
                    bundle1.putString("time",btnTime.getText().toString());
                    bundle1.putString("table",tableNo.getText().toString());
                    intent.putExtra("bookingBundle",bundle1);
                    startActivity(intent);
                }else {
                    warning.setVisibility(View.VISIBLE);
                }
            }
        });

        return builder.show();
    }

    private void initialize(View view){
        restName = view.findViewById(R.id.restName);
        btnDate = view.findViewById(R.id.btnDate);
        btnTime = view.findViewById(R.id.btnTime);
        tableNo = view.findViewById(R.id.tableNo);
        bookCost = view.findViewById(R.id.bookCost);
        warning = view.findViewById(R.id.warning);

        btnPayment = view.findViewById(R.id.btnPayment);
    }
}
