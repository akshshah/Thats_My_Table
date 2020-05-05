package com.example.logintest.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.logintest.Utils;

import java.util.ArrayList;

public class Restaurant implements Parcelable{

    private int id;
    private String name;
    private String address;
    private String imageUrl;
    private ArrayList<String> cuisine;
    private int costForTwo;
    private double bookingCost;
    private int popularityPoint;
    private int userPoint;
    private double appRating;
    private ArrayList<String> tableCount;
    private int userRating;
    private ArrayList<Review> reviews;

    public Restaurant(String name, String address, String imageUrl,
                      ArrayList<String> cuisine, int costForTwo,
                      double appRating, ArrayList<String> tableCount) {
        this.id = Utils.getID();
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
        this.cuisine = cuisine;
        this.costForTwo = costForTwo;
        this.bookingCost = costForTwo/5;
        this.popularityPoint = 0;
        this.userPoint = 0;
        this.appRating = appRating;
        this.tableCount = tableCount;
        this.userRating = 0;
        this.reviews = new ArrayList<>();
    }

    protected Restaurant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        imageUrl = in.readString();
        cuisine = in.createStringArrayList();
        costForTwo = in.readInt();
        bookingCost = in.readDouble();
        popularityPoint = in.readInt();
        userPoint = in.readInt();
        appRating = in.readDouble();
        tableCount = in.createStringArrayList();
        userRating = in.readInt();
        reviews = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(ArrayList<String> cuisine) {
        this.cuisine = cuisine;
    }

    public int getCostForTwo() {
        return costForTwo;
    }

    public void setCostForTwo(int costForTwo) {
        this.costForTwo = costForTwo;
    }

    public double getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(double bookingCost) {
        this.bookingCost = bookingCost;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public double getAppRating() {
        return appRating;
    }

    public void setAppRating(double appRating) {
        this.appRating = appRating;
    }

    public ArrayList<String> getTableCount() {
        return tableCount;
    }

    public void setTableCount(ArrayList<String> tableCount) {
        this.tableCount = tableCount;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", cuisine=" + cuisine +
                ", costForTwo=" + costForTwo +
                ", bookingCost=" + bookingCost +
                ", popularityPoint=" + popularityPoint +
                ", userPoint=" + userPoint +
                ", appRating=" + appRating +
                ", tableCount=" + tableCount +
                ", userRating=" + userRating +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(imageUrl);
        dest.writeStringList(cuisine);
        dest.writeInt(costForTwo);
        dest.writeDouble(bookingCost);
        dest.writeInt(popularityPoint);
        dest.writeInt(userPoint);
        dest.writeDouble(appRating);
        dest.writeStringList(tableCount);
        dest.writeInt(userRating);
        dest.writeTypedList(reviews);
    }
}

