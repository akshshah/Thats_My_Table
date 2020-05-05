package com.example.logintest.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    private int restaurantId;
    private String username;
    private String date;
    private String text;

    public Review(int restaurantId, String username, String date, String text) {
        this.restaurantId = restaurantId;
        this.username = username;
        this.date = date;
        this.text = text;
    }

    protected Review(Parcel in) {
        restaurantId = in.readInt();
        username = in.readString();
        date = in.readString();
        text = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "restaurantId=" + restaurantId +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(restaurantId);
        dest.writeString(username);
        dest.writeString(date);
        dest.writeString(text);
    }
}
