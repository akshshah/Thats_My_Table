package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.logintest.Models.Restaurant;

import java.util.ArrayList;

public class ShowRestaurantsByCuisine extends AppCompatActivity {
    private static final String TAG = "ShowRestaurantsByCuisine";
    private TextView cuisineName;
    private RecyclerView recView;

    private RestaurantAdapter adapter;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurants_by_cuisine);

        utils = new Utils(this);
        cuisineName = findViewById(R.id.cuisineName);
        recView = findViewById(R.id.recView);
        adapter = new RestaurantAdapter(this);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        try {
            Intent intent = getIntent();
            String cuisine =intent.getStringExtra("cuisine");
            ArrayList<Restaurant> restaurants = utils.getRestByCuisine(cuisine);
            adapter.setRestaurants(restaurants);
            cuisineName.setText(cuisine);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}
