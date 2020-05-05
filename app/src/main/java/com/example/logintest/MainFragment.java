package com.example.logintest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logintest.Models.Restaurant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment{

    private BottomNavigationView bottomNavigationView;
    private Spinner spinner;
    private RelativeLayout firstLayout,secondLayout,thirdLayout;
    private RecyclerView newRestRecView,popRestRecView,sugRestRecView;
    private RestaurantAdapter newRestAdapter,popRestAdapter,sugRestAdapter;

    private Utils utils;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavigation();

        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("Popular ");
        options.add("Suggested ");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,options);
        spinner.setAdapter(adapter);

        utils = new Utils(getActivity());
        utils.initDatabase();

        initRecViews();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    setNew();
                }else if(position == 1){
                    setPopular();
                }else if(position == 2){
                    setSuggested();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void initRecViews(){
        newRestAdapter = new RestaurantAdapter(getActivity());
        popRestAdapter = new RestaurantAdapter(getActivity());
        sugRestAdapter = new RestaurantAdapter(getActivity());

        newRestRecView.setAdapter(newRestAdapter);
        popRestRecView.setAdapter(popRestAdapter);
        sugRestRecView.setAdapter(sugRestAdapter);

        newRestRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        popRestRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sugRestRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateRecViews();
    }

    private void updateRecViews(){

        ArrayList<Restaurant> newRestaurants = utils.getAllRestaurants();
        Comparator<Restaurant> newComparator = new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return o1.getId() - o2.getId();
            }
        };
        Comparator<Restaurant> revNewComparator = Collections.reverseOrder(newComparator);
        Collections.sort(newRestaurants,revNewComparator);
        if(null != newRestaurants){
            newRestAdapter.setRestaurants(newRestaurants);
        }


        ArrayList<Restaurant> popRestaurants = utils.getAllRestaurants();
        Comparator<Restaurant> popularComparator = new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return compareByPopularity(o1, o2);
            }
        };
        Comparator<Restaurant> revPopComparator =  Collections.reverseOrder(popularComparator);
        Collections.sort(popRestaurants,revPopComparator);
        popRestAdapter.setRestaurants(popRestaurants);


        ArrayList<Restaurant> suggestedRestaurants = utils.getAllRestaurants();
        Comparator<Restaurant> suggestedComparator = new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return o1.getUserPoint() - o2.getUserPoint();
            }
        };
        Comparator<Restaurant> revSugComparator = Collections.reverseOrder(suggestedComparator);
        Collections.sort(suggestedRestaurants,revSugComparator);
        sugRestAdapter.setRestaurants(suggestedRestaurants);
    }

    private int compareByPopularity (Restaurant item1, Restaurant item2){
        if(item1.getPopularityPoint() > item2.getPopularityPoint()){
            return 1;
        }else if(item1.getPopularityPoint()< item2.getPopularityPoint()){
            return -1;
        }else{
            return 0;
        }
    }

    public void setPopular(){
        firstLayout.setVisibility(View.GONE);
        secondLayout.setVisibility(View.VISIBLE);
        thirdLayout.setVisibility(View.GONE);
    }

    public void setNew(){
        firstLayout.setVisibility(View.VISIBLE);
        secondLayout.setVisibility(View.GONE);
        thirdLayout.setVisibility(View.GONE);
    }

    public void setSuggested(){
        firstLayout.setVisibility(View.GONE);
        secondLayout.setVisibility(View.GONE);
        thirdLayout.setVisibility(View.VISIBLE);
    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.home_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.search:
                        Intent intent = new Intent(getActivity(),SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.home_item:
                        break;
                    case R.id.btnBookings:
                        Intent intent2 = new Intent(getActivity(),MyBookingsActivity.class);
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

    private void initViews(View view) {
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        newRestRecView = view.findViewById(R.id.newRestRecView);
        popRestRecView = view.findViewById(R.id.popRestRecView);
        sugRestRecView = view.findViewById(R.id.sugRestRecView);
        firstLayout = view.findViewById(R.id.firstLayout);
        secondLayout = view.findViewById(R.id.secondLayout);
        thirdLayout = view.findViewById(R.id.thirdLayout);
        spinner = view.findViewById(R.id.spinner);
    }

    @Override
    public void onResume() {
        updateRecViews();
        super.onResume();
    }

}