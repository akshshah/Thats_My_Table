package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.logintest.Models.BookedRestaurant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity implements BookedAdapter.CancelBooking {
    private static final String TAG = "MyBookingsActivity";
    private RecyclerView recView;
    private RelativeLayout noBookingsLayout;
    private Button btnAddRest;
    private NestedScrollView nestedScrollView;
    private BottomNavigationView bottomNavigationView;
    private BookedAdapter adapter;
    ArrayList<BookedRestaurant> bookedRestaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        Log.d(TAG, "onCreate: started" );
        recView = findViewById(R.id.recView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        noBookingsLayout = findViewById(R.id.noBookingsLayout);
        btnAddRest = findViewById(R.id.btnAddRest);
        nestedScrollView = findViewById(R.id.nestedScroll);

        initBottomNavigation();

        adapter = new BookedAdapter(this);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        initRecView();

        btnAddRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookingsActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        try{
            BookedRestaurant bookedRestaurant = intent.getParcelableExtra("bookedRest");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initRecView(){
        Log.d(TAG, "initRecView: started");

        Utils utils = new Utils(this);
        bookedRestaurants = utils.getBookedRestaurants();
        if (bookedRestaurants !=null ){
            Log.d(TAG, "initRecView: if" + bookedRestaurants);
            noBookingsLayout.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.VISIBLE);
            adapter.setBookedRestaurants(bookedRestaurants);
        }else {
            Log.d(TAG, "initRecView: else" + bookedRestaurants);
            noBookingsLayout.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.btnBookings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.search:
                        Intent intent = new Intent(MyBookingsActivity.this,SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.home_item:
                        Intent intent2 = new Intent(MyBookingsActivity.this,MainActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        break;
                    case R.id.btnBookings:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void cancelBookingResult(BookedRestaurant restaurant) {
        Log.d(TAG, "cancelBookingResult: " + restaurant.getRestaurant().getName());
        Utils utils2 = new Utils(this);
        ArrayList<BookedRestaurant> newBookedRestaurants = utils2.removeBookedRest(restaurant);
        Log.d(TAG, "cancelBookingResult: " + newBookedRestaurants);
        if (newBookedRestaurants != null){
            if(newBookedRestaurants.isEmpty()){
                noBookingsLayout.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
            }else {
                noBookingsLayout.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.VISIBLE);
                adapter.setBookedRestaurants(newBookedRestaurants);
            }
        }else {
            noBookingsLayout.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.GONE);
        }
    }

}
