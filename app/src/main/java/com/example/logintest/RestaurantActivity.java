package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.logintest.Models.Restaurant;
import com.example.logintest.Models.Review;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity implements AddReviewDialog.AddReview {

    private static final String TAG = "RestaurantActivity";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackUserTime.LocalBinder binder =
                    (TrackUserTime.LocalBinder) service;
            mService = binder.getService();
            isBound = true;
            mService.setRest(incomingRest);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
    private TrackUserTime mService;
    private Boolean isBound = false;

    private TextView restName,bookCost;
    private Button btnBookTable,btnPerigon,btnMenu;
    private ImageView restImage;
    private Spinner spinner;

    private ImageView firstFilledStar,secondFilledStar,thirdFilledStar,firstEmptyStar,secondEmptyStar,thirdEmptyStar;
    private RecyclerView reviewRecView;

    private RelativeLayout reviewLayout;

    private ReviewsAdapter reviewsAdapter;

    private Restaurant incomingRest;
    private Utils utils;

    private int currentRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        utils = new Utils(this);

        initViews();

        Intent intent = getIntent();
        try {
            incomingRest = intent.getParcelableExtra("restaurant");
            this.currentRate = incomingRest.getUserRating();
            setValues();
            utils.increaseUserPoint(incomingRest,1);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void setValues(){
        restName.setText(incomingRest.getName());
        bookCost.setText(String.valueOf(incomingRest.getBookingCost() + " ₹"));

        Log.d(TAG, "setValues: " + incomingRest.getTableCount() + incomingRest.getCuisine());

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,incomingRest.getTableCount());
        spinner.setAdapter(adapter);

        Glide.with(this)
                .asBitmap()
                .load(incomingRest.getImageUrl())
                .into(restImage);

        btnBookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingDialog bookingDialog = new BookingDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("restaurant",incomingRest);
                bundle.putString("table",spinner.getSelectedItem().toString());
                bookingDialog.setArguments(bundle);
                bookingDialog.show(getSupportFragmentManager(),"Booking Dialog");
            }
        });

        handleStar();

        reviewsAdapter = new ReviewsAdapter();
        reviewRecView.setAdapter(reviewsAdapter);
        reviewRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Review> reviews = utils.getReviewForRest(incomingRest.getId());
        if(null != reviews){
            reviewsAdapter.setReviews(reviews);
        }

        reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReviewDialog addReviewDialog = new AddReviewDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("restaurant",incomingRest);
                addReviewDialog.setArguments(bundle);
                addReviewDialog.show(getSupportFragmentManager(),"Add Review Dialog");
            }
        });

        btnPerigon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this,PanoramaActivity.class);
                intent.putExtra("restaurant",incomingRest.getId());
                startActivity(intent);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void handleStar(){
        Log.d(TAG, "handleStar: started" + this.currentRate);
        changeVisibility(this.currentRate);

        firstEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(1)){
                    updateDatabase(1);
                    changeVisibility(1);
                    changeUserPoint(1);
                }
            }
        });

        secondEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(2)){
                    updateDatabase(2);
                    changeVisibility(2);
                    changeUserPoint(2);
                }
            }
        });

        thirdEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(3)){
                    updateDatabase(3);
                    changeVisibility(3);
                    changeUserPoint(3);
                }
            }
        });

        firstFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(1)){
                    updateDatabase(1);
                    changeVisibility(1);
                    changeUserPoint(1);
                }
            }
        });

        secondFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(2)){
                    updateDatabase(2);
                    changeVisibility(2);
                    changeUserPoint(2);
                }
            }
        });

        thirdFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(3)){
                    updateDatabase(3);
                    changeVisibility(3);
                    changeUserPoint(3);
                }
            }
        });
    }

    private void changeUserPoint(int stars){

        utils.increaseUserPoint(incomingRest,(stars-currentRate)*2);
    }

    private void changeVisibility(int newRate){
        Log.d(TAG, "changeVisibility: started");
        switch (newRate){
            case 0:
                firstFilledStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 1:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 2:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 3:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.GONE);
                break;
            default:
                firstFilledStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean checkIfRateHasChanged(int newRate){
        Log.d(TAG, "checkIfRateHasChanged: started");
        if(newRate == currentRate){
            return false;
        }
        else {
            return true;
        }
    }

    private void updateDatabase(int newRate){
        Log.d(TAG, "updateDatabase: started");
        utils.updateTheRate(incomingRest,newRate);
    }

    private void initViews(){

        restName = findViewById(R.id.restName);
        restImage = findViewById(R.id.restImage);
        bookCost = findViewById(R.id.bookCost);
        btnBookTable = findViewById(R.id.btnBookTable);
        btnMenu = findViewById(R.id.btnMenu);
        btnPerigon = findViewById(R.id.btnPerigon);
        spinner = findViewById(R.id.spinner);

        firstEmptyStar = findViewById(R.id.firstEmptyStar);
        firstFilledStar = findViewById(R.id.firstFilledStar);
        secondEmptyStar = findViewById(R.id.secondEmptyStar);
        secondFilledStar = findViewById(R.id.secondFilledStar);
        thirdEmptyStar = findViewById(R.id.thirdEmptyStar);
        thirdFilledStar = findViewById(R.id.thirdFilledStar);

        reviewRecView = findViewById(R.id.reviewRecView);

        reviewLayout = findViewById(R.id.reviewLayout);

    }

    @Override
    public void onAddReviewResult(Review review) {
        utils.Add_Review(review);
        utils.increaseUserPoint(incomingRest,3);
        ArrayList<Review> reviews = utils.getReviewForRest(review.getRestaurantId());
        if(null != reviews){
            reviewsAdapter.setReviews(reviews);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this,TrackUserTime.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBound){
            unbindService(connection);
        }
    }
}
