package com.example.logintest.Models;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class BookedRestaurant implements Parcelable {
    private Restaurant restaurant;
    private String date;
    private String time;
    private String table;
    private String mode;
    private Bitmap qrImage;

    public BookedRestaurant() {
    }

    public BookedRestaurant(Restaurant restaurant, String date, String time, String table, String mode, Bitmap qrImage) {
        this.restaurant = restaurant;
        this.date = date;
        this.time = time;
        this.table = table;
        this.mode = mode;
        this.qrImage = qrImage;
    }

    protected BookedRestaurant(Parcel in) {
        restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        date = in.readString();
        time = in.readString();
        table = in.readString();
        mode = in.readString();
        qrImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<BookedRestaurant> CREATOR = new Creator<BookedRestaurant>() {
        @Override
        public BookedRestaurant createFromParcel(Parcel in) {
            return new BookedRestaurant(in);
        }

        @Override
        public BookedRestaurant[] newArray(int size) {
            return new BookedRestaurant[size];
        }
    };

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Bitmap getQrImage() {
        return qrImage;
    }

    public void setQrImage(Bitmap qrImage) {
        this.qrImage = qrImage;
    }

    @Override
    public String toString() {
        return "BookedRestaurant{" +
                "restaurant=" + restaurant +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", table='" + table + '\'' +
                ", mode='" + mode + '\'' +
                ", qrImage=" + qrImage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(restaurant, flags);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(table);
        dest.writeString(mode);
        dest.writeParcelable(qrImage, flags);
    }
}
