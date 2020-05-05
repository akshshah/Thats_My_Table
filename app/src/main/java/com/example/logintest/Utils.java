package com.example.logintest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.logintest.Models.BookedRestaurant;
import com.example.logintest.Models.Restaurant;
import com.example.logintest.Models.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";

    private static int ID = 0;

    public static final String DATABASE_NAME = "fake_database";

    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static int getID() {
        ID++;
        return ID;
    }

    public void initDatabase() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<Restaurant>>() {
        }.getType();
        ArrayList<Restaurant> possibleRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants", ""), type);
        if (null == possibleRestaurants) {
            initAllItems();
        }
    }

    private void initAllItems() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Restaurant> allRestaurants = new ArrayList<>();


        allRestaurants.add(new Restaurant("Level 5-Terrace Restro & Cafe", "5th Floor, Royal Trade Centre, Opposite Star Bazaar, Adajan, Adajan Gam, Surat", "https://miro.medium.com/max/1100/1*Aga1tt3ig1upLalRhhMzqw.jpeg",
                new ArrayList<String>(Arrays.asList(new String[]{"North Indian","Italian"})),900,4.9, new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10"}))));

        allRestaurants.add(new Restaurant("Coffee King","UG-1, Shubh Universal,Opposite Vijay Laxmi Hall, Near Western Vesu Point, Vesu, Surat","https://media-cdn.tripadvisor.com/media/photo-s/08/d2/f2/00/getlstd-property-photo.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"Cafe"})),500,4.8,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8"}))));

        allRestaurants.add(new Restaurant("The Hog Spot Café","Opposite Lourdes Convent School, Athwalines, Athwa, Surat","https://www.shoppingbazar.in/uploads/images/cc2f2b403b3d7a19f83b9196189cc5641508233307.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"Italian","Cafe"})),650,4.5,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9"}))));

        allRestaurants.add(new Restaurant("Wok On Fire","G 2, Golden Square, Near Sargam Shopping Center, Parle Point, Surat, Piplod, Surat","https://content3.jdmagicbox.com/comp/vadodara/l9/0265px265.x265.160622081827.j2l9/catalogue/wok-on-fire-old-padra-road-vadodara-thai-restaurants-1cjo9qu2p8.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"Chinese"})),550,4.0,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7"}))));

        allRestaurants.add(new Restaurant("Kansar Gujarati Thali","A Wing, President Plaza, Near RTO, Nanpura, Surat","https://b.zmtcdn.com/data/pictures/chains/2/3800002/63e37a12f69c170965d9e6c9bb6749f2_featured_v2.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"Gujarati"})),550,4.0,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}))));

        allRestaurants.add(new Restaurant("Coffee Culture","36/AN. No. 062, Paiki, Khodiyar Nivas, Opposite Sargam Shopping, Athwalines, Umra, City Light, Surat","https://media-cdn.tripadvisor.com/media/photo-s/05/f1/61/3a/coffee-culture.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"Fast Food","Cafe","Chinese"})),800,4.3,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"}))));

        allRestaurants.add(new Restaurant("Avadh Family Restaurant","Yash Plaza, Varachha Road, Opposite Dhana Mill, Varachha, Surat","https://images.jdmagicbox.com/comp/surat/s6/0261px261.x261.150313184947.v8s6/catalogue/avadh-family-restaurant-varachha-road-surat-chinese-fast-food-joints-p3sc52.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"North Indian","Chinese"})),600,4.1,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10"}))));

        allRestaurants.add(new Restaurant("Mysore Cafe","Daruwala Building, Athwalines, Athwa, Surat","https://images.jdmagicbox.com/comp/surat/05/0261p261std250205/catalogue/mysore-cafe-athwalines-surat-south-indian-restaurants-udydbrya8i.jpg?clr=#57ff",
                new ArrayList<String>(Arrays.asList(new String[]{"South Indian"})),250,4.4,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8"}))));

        allRestaurants.add(new Restaurant("Barbeque Nation","First Floor, Golden Square, Near Sagam Shopping Centre, Athwa-Dumas Road, Parle Point, Surat","https://img4.nbstatic.in/tr:w-500/5c750d9552faff000d4b483a.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"North Indian"})),1500,4.6,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13"}))));

        allRestaurants.add(new Restaurant("Taste Of Bhagwati","Sakaar Textile House, Near Jash Market, Ring Road, Udhna Gam, Surat","https://d2jz4nqvi4omcr.cloudfront.net/customerimages/large/889377_image_2_2019-05-08-11-15-10-000078.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"North Indian","Fast Food","Chinese"})),1200,3.7,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10","11"}))));

        allRestaurants.add(new Restaurant("J D Restaurant","Lakhani Chambers, Mini Bazar, Varacha Road, Surat","https://b.zmtcdn.com/data/pictures/chains/5/3800295/512895e446d8ce9a75a764461b0a6ea0_featured_v2.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"North Indian","Fast Food","Chinese"})),500,4.1,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10"}))));

        allRestaurants.add(new Restaurant("K's Charcoal","Upper Ground Floor, Ganga House, Near Iscon Mall, Piplod, Surat","https://i.ytimg.com/vi/QUNhwMh2zpg/maxresdefault.jpg",
                new ArrayList<String>(Arrays.asList(new String[]{"Fast Food","Italian"})),600,4.4,new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9"}))));

        String finalString =gson.toJson(allRestaurants);
        editor.putString("allRestaurants",finalString);
        editor.commit();
    }

    public ArrayList<Restaurant> getAllRestaurants(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<Restaurant>>(){}.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants",null),type);
        return allRestaurants;
    }

    public ArrayList<Review> getReviewForRest(int id){
        SharedPreferences sharedPreferences =context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson= new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>(){}.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants",null),type);

        if(null != allRestaurants){
            for(Restaurant rest : allRestaurants){
                if(rest.getId() == id){
                    return rest.getReviews();
                }
            }
        }
        return null;
    }

    public boolean Add_Review(Review review){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>(){}.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants",null),type);

        if(null != allRestaurants){
            ArrayList<Restaurant> newRestaurants = new ArrayList<>();
            for(Restaurant rest: allRestaurants){
                if(rest.getId() == review.getRestaurantId()){
                    ArrayList<Review> reviews = rest.getReviews();
                    reviews.add(review);
                    rest.setReviews(reviews);
                }

                newRestaurants.add(rest);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allRestaurants",gson.toJson(newRestaurants));
            editor.commit();
            return true;
        }
        return false;
    }

    public void updateTheRate(Restaurant rest,int newRate){
        Log.d(TAG, "updateTheRate: started" + rest.getName());
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>(){}.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants",null),type);
        if(null != allRestaurants){
            ArrayList<Restaurant> newRestaurants = new ArrayList<>();
            for (Restaurant i: allRestaurants){
                if(i.getId() == rest.getId()){
                    i.setUserRating(newRate);
                }
                newRestaurants.add(i);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allRestaurants",gson.toJson(newRestaurants));
            editor.commit();
        }
    }

    public ArrayList<Restaurant> searchItem(String text){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>(){}.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants",null),type);

        ArrayList<Restaurant> searchItems = new ArrayList<>();
        if(null != allRestaurants){
            for (Restaurant rest: allRestaurants){
                if(rest.getName().equalsIgnoreCase(text)){
                    searchItems.add(rest);
                }
                String []splittedString = rest.getName().split(" ");
                for (int i=0 ;i<splittedString.length; i++){
                    if (splittedString[i].equalsIgnoreCase(text)){

                        boolean doesExist = false;
                        for (Restaurant s: searchItems){
                            if(s.equals(rest)){
                                doesExist = true;
                            }
                        }
                        if (!doesExist){
                            searchItems.add(rest);
                        }
                    }
                }
            }
        }
        return searchItems;
    }

    public ArrayList<String> getThreeCategories() {
        Log.d(TAG, "getThreeCategories: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>() {
        }.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants", null), type);
        ArrayList<String> cuisines = new ArrayList<>();

        if (null != allRestaurants) {
            cuisines = allRestaurants.get(10).getCuisine();
        }
        return cuisines;
    }

    public ArrayList<String> getAllCategories(){
        Log.d(TAG, "getAllCategories: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>() {
        }.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants", null), type);
        ArrayList<String> cuisines = new ArrayList<>();
        ArrayList<String> restCuisine = new ArrayList<>();
        if (null != allRestaurants){
            for (int i=0; i<allRestaurants.size(); i++){
                restCuisine.addAll(allRestaurants.get(i).getCuisine());
            }
        }
        if (null != restCuisine){
            for (int i=0; i<restCuisine.size(); i++){
                boolean doesExist = false;
                for(String s: cuisines){
                    if(restCuisine.get(i).equals(s)){
                        doesExist = true;
                    }
                }
                if(!doesExist){
                    cuisines.add(restCuisine.get(i));
                }
            }
        }
        return cuisines;
    }

    public ArrayList<Restaurant> getRestByCuisine(String cuisine){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>() {
        }.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants", null), type);

        ArrayList<Restaurant> newRestaurants = new ArrayList<>();
        ArrayList<String> cuisines;
        if (null!=allRestaurants){
            for (Restaurant rest:allRestaurants){
                cuisines = rest.getCuisine();
                boolean doesExist = false;
                for (String s:cuisines){
                    if(s.equals(cuisine)){
                        doesExist = true;
                    }
                }
                if (doesExist){
                    newRestaurants.add(rest);
                }
            }
        }
        return newRestaurants;
    }

    public ArrayList<BookedRestaurant> getBookedRestaurants(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BookedRestaurant>>(){}.getType();
        ArrayList<BookedRestaurant> bookedRestaurants = gson.fromJson(sharedPreferences.getString("bookedRestaurants",null),type);
        return bookedRestaurants;
    }

    public void addBookedRest(BookedRestaurant bookedRest){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BookedRestaurant>>(){}.getType();
        ArrayList<BookedRestaurant> bookedRestaurants = gson.fromJson(sharedPreferences.getString("bookedRestaurants",null),type);
        if(bookedRestaurants == null){
            bookedRestaurants = new ArrayList<>();
        }
        bookedRestaurants.add(bookedRest);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bookedRestaurants",gson.toJson(bookedRestaurants));
        editor.commit();
    }

    public ArrayList<BookedRestaurant> removeBookedRest(BookedRestaurant bookedRest){
        Log.d(TAG, "removeBookedRest: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BookedRestaurant>>(){}.getType();
        ArrayList<BookedRestaurant> bookedRestaurants = gson.fromJson(sharedPreferences.getString("bookedRestaurants",null),type);
        ArrayList<BookedRestaurant> newBookedRestaurants = new ArrayList<>();

        if(bookedRestaurants != null){
            for(BookedRestaurant restaurant: bookedRestaurants){
                if(restaurant.getTime().equals(bookedRest.getTime())){

                }else{
                    newBookedRestaurants.add(restaurant);
                }
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("bookedRestaurants",gson.toJson(newBookedRestaurants));
            editor.commit();
        }
        return newBookedRestaurants;
    }

    public void addPopularityPoint(Restaurant rest){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>() {
        }.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants", null), type);

        ArrayList<Restaurant> newRestaurants = new ArrayList<>();
        for(Restaurant i:allRestaurants){
            if(rest.getId() == i.getId()){
                i.setPopularityPoint(i.getPopularityPoint()+1);
            }
            newRestaurants.add(i);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("allRestaurants",gson.toJson(newRestaurants));
        editor.apply();
    }

    public void increaseUserPoint(Restaurant rest,int points){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Restaurant>>() {
        }.getType();
        ArrayList<Restaurant> allRestaurants = gson.fromJson(sharedPreferences.getString("allRestaurants", null), type);

        if(null != allRestaurants){
            ArrayList<Restaurant> newRestaurants = new ArrayList<>();
            for(Restaurant i:allRestaurants){
                if(i.getId()==rest.getId()){
                    i.setUserPoint(i.getUserPoint()+points);
                }
                newRestaurants.add(i);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allRestaurants",gson.toJson(newRestaurants));
            editor.commit();
        }
    }
}