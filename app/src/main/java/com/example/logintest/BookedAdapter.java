package com.example.logintest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logintest.Models.BookedRestaurant;
import com.example.logintest.Models.Restaurant;

import java.util.ArrayList;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.ViewHolder> {
    private static final String TAG = "BookedAdapter";

    public interface CancelBooking{
        void cancelBookingResult(BookedRestaurant restaurant);
    }
    private CancelBooking cancelBooking;

    private Context context;

    public BookedAdapter(Context context) {
        this.context = context;
    }

    public BookedAdapter() {
    }

    private ArrayList<BookedRestaurant> bookedRestaurants = new ArrayList<>();

    private Utils utils;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booked_recview_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: started");
        holder.restName.setText(bookedRestaurants.get(position).getRestaurant().getName());
        holder.bookCost.setText(String.valueOf(bookedRestaurants.get(position).getRestaurant().getBookingCost()) + " ₹");
        holder.btnDate.setText(bookedRestaurants.get(position).getDate());
        holder.btnTime.setText(bookedRestaurants.get(position).getTime());
        holder.tableNo.setText(bookedRestaurants.get(position).getTable());
        holder.mode.setText(bookedRestaurants.get(position).getMode());

        //holder.qrCode.setImageBitmap(bookedRestaurants.get(position).getBitImage());
        Glide.with(context)
                .asBitmap()
                .load(bookedRestaurants.get(position).getQrImage())
                .into(holder.qrCode);


        cancelBooking = (CancelBooking) context;

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            double cost = Double.parseDouble(String.valueOf(bookedRestaurants.get(position).getRestaurant().getBookingCost()));
            double cancelCost = cost/2;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Cancel Booking")
                        .setMessage("You will be charged 50% of your booking amount" + "\n" +
                                     cancelCost + "₹ will be refunded to your account")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelBooking.cancelBookingResult(bookedRestaurants.get(position));
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookedRestaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView restName,btnDate,btnTime,tableNo,bookCost,mode;
        private Button btnCancel;
        private ImageView qrCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restName = itemView.findViewById(R.id.restName);
            btnDate = itemView.findViewById(R.id.btnDate);
            btnTime = itemView.findViewById(R.id.btnTime);
            tableNo = itemView.findViewById(R.id.tableNo);
            bookCost = itemView.findViewById(R.id.bookCost);
            mode = itemView.findViewById(R.id.mode);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            qrCode = itemView.findViewById(R.id.qrCode);
        }
    }

    public void setBookedRestaurants(ArrayList<BookedRestaurant> bookedRestaurants) {
        this.bookedRestaurants = bookedRestaurants;
        notifyDataSetChanged();
    }
}
