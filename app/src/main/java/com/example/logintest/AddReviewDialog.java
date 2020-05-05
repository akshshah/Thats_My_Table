package com.example.logintest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.logintest.Models.Restaurant;
import com.example.logintest.Models.Review;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReviewDialog extends DialogFragment {

    private EditText editName,editReview;
    private TextView restName,warning;
    private Button addReview;
    private int itemId = 0;

    public interface AddReview{
        void onAddReviewResult(Review review);
    }

    private AddReview add_review;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Review")
                .setView(view);

        initialize(view);
        Bundle bundle = getArguments();
        try{

            Restaurant rest = bundle.getParcelable("restaurant");
            restName.setText(rest.getName());
            this.itemId = rest.getId();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddReview();
            }
        });
        return builder.show();
    }
    private void initialize(View view){
        editName = view.findViewById(R.id.editName);
        editReview = view.findViewById(R.id.editReview);
        warning = view.findViewById(R.id.warning);
        restName = view.findViewById(R.id.restName);
        addReview = (Button) view.findViewById(R.id.addReview);
    }

    private void toAddReview(){
        if(validateData()){
            String name = editName.getText().toString();
            String reviewText = editReview.getText().toString();
            String date = getCurrentDate();

            Review review = new Review(itemId,name,date,reviewText);
            try{
                add_review = (AddReview)getActivity();
                add_review.onAddReviewResult(review);
                dismiss();
            }catch (ClassCastException e){
                e.printStackTrace();
            }
        }else{
            warning.setVisibility(View.VISIBLE);
        }
    }

    private Boolean validateData(){
        if(editName.getText().toString().equals("")){
            return false;
        }
        if(editReview.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private String getCurrentDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

}
