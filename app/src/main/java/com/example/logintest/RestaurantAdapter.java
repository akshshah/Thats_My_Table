package com.example.logintest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logintest.Models.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    public RestaurantAdapter(Context context) {
        this.context = context;
    }

    public RestaurantAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_rec_view_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.restName.setText(restaurants.get(position).getName());
        holder.restAddress.setText(restaurants.get(position).getAddress());
        holder.restCost2.setText(String.valueOf(restaurants.get(position).getCostForTwo() + " ₹"));
        holder.restRating.setText(String.valueOf(restaurants.get(position).getAppRating()));

        Glide.with(context)
                .asBitmap()
                .load(restaurants.get(position).getImageUrl())
                .into(holder.restImage);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RestaurantActivity.class);
                intent.putExtra("restaurant",restaurants.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView restImage;
        private TextView restName,restAddress,restRating,restCost2;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restImage = itemView.findViewById(R.id.restImage);
            restName = itemView.findViewById(R.id.restName);
            restCost2 = itemView.findViewById(R.id.restCost2);
            restAddress = itemView.findViewById(R.id.restAddress);
            restRating = itemView.findViewById(R.id.restRating);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }
}
