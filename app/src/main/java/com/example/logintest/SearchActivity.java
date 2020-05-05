package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logintest.Models.Restaurant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ShowAllCategoriesDialog.SelectCuisine {

    private static final String TAG = "SearchActivity";
    private EditText editSearch;
    private TextView firstCat,secondCat,thirdCat,seeAll;
    private RecyclerView recView;
    private BottomNavigationView bottomNavigationView;
    private ImageView btnSearch;

    private RestaurantAdapter adapter;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        utils = new Utils(this);
        adapter = new RestaurantAdapter(this);

        initialize();
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        initBottomNavigation();
        initThreeTextViews();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch();
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Restaurant> restaurants = utils.searchItem(String.valueOf(s));
                adapter.setRestaurants(restaurants);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllCategoriesDialog showAllCategoriesDialog = new ShowAllCategoriesDialog();
                showAllCategoriesDialog.show(getSupportFragmentManager(),"All Cuisines");
            }
        });
    }

    private void initThreeTextViews(){
        Log.d(TAG, "initThreeTextViews: started");
        ArrayList<String> cuisines = utils.getThreeCategories();
        switch (cuisines.size()){
            case 1:
                firstCat.setText(cuisines.get(0));
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                break;
            case 2:
                firstCat.setText(cuisines.get(0));
                secondCat.setText(cuisines.get(1));
                thirdCat.setVisibility(View.GONE);
                break;
            case 3:
                firstCat.setText(cuisines.get(0));
                secondCat.setText(cuisines.get(1));
                thirdCat.setText(cuisines.get(2));
                break;
            default:
                break;
        }

        firstCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this,ShowRestaurantsByCuisine.class);
                    intent.putExtra("cuisine",firstCat.getText().toString());
                    startActivity(intent);
                }
        });

        secondCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,ShowRestaurantsByCuisine.class);
                intent.putExtra("cuisine",secondCat.getText().toString());
                startActivity(intent);
            }
        });

        thirdCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,ShowRestaurantsByCuisine.class);
                intent.putExtra("cuisine",thirdCat.getText().toString());
                startActivity(intent);
            }
        });

    }

    private void initialize(){
        editSearch = findViewById(R.id.editSearch);
        firstCat = findViewById(R.id.firstCategory);
        secondCat = findViewById(R.id.secondCategory);
        thirdCat = findViewById(R.id.thirdCategory);
        seeAll = findViewById(R.id.seeAll);
        recView = findViewById(R.id.recView);
        btnSearch = findViewById(R.id.btnSearch);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.search:
                        break;
                    case R.id.home_item:
                        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.btnBookings:
                        Intent intent2 = new Intent(SearchActivity.this,MyBookingsActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initiateSearch(){

        String text = editSearch.getText().toString();
        ArrayList<Restaurant> restaurants = utils.searchItem(text);
        for(Restaurant rest:restaurants){
            utils.increaseUserPoint(rest,3);
        }
        adapter.setRestaurants(restaurants);
    }


    @Override
    public void onSelectCuisine(String cuisine) {
        Log.d(TAG, "onSelectCuisine: Cuisine " + cuisine );
        Intent intent = new Intent(this,ShowRestaurantsByCuisine.class);
        intent.putExtra("cuisine",cuisine);
        startActivity(intent);
    }
}
