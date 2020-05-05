package com.example.logintest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.logintest.Models.Restaurant;

public class TrackUserTime extends Service {

    private IBinder binder = new LocalBinder();
    private int seconds = 0;
    private Restaurant rest;
    private boolean shouldFinish = false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        trackTime();
        return binder;
    }

    public class LocalBinder extends Binder {
        TrackUserTime getService(){
            return TrackUserTime.this;
        }
    }

    private void trackTime(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shouldFinish){
                    try{
                        Thread.sleep(1000);
                        seconds++;
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }
        });

        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        shouldFinish = true;
        Utils utils = new Utils(this);
        int minutes = Integer.valueOf(seconds/60);
        utils.increaseUserPoint(rest,minutes*2);
    }

    public void setRest(Restaurant rest) {
        this.rest = rest;
    }
}
