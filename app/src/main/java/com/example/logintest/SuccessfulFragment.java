package com.example.logintest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.logintest.Models.BookedRestaurant;
import com.example.logintest.Models.Restaurant;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class SuccessfulFragment extends Fragment {
    private static final String TAG = "SuccessfulFragment";

    private Button btnFinish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.success_fragment,container,false);
        btnFinish = view.findViewById(R.id.btnFinish);

        final Bundle bundle = getArguments();

        final Utils utils = new Utils(getActivity());

        try{
            Restaurant restaurant = bundle.getParcelable("restaurant");
            utils.addPopularityPoint(restaurant);
            utils.increaseUserPoint(restaurant,4);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle != null){
                    BookedRestaurant bookedRestaurant = new BookedRestaurant();
                    Restaurant rest = bundle.getParcelable("restaurant");
                    bookedRestaurant.setRestaurant(rest);
                    bookedRestaurant.setDate(bundle.getString("date"));
                    bookedRestaurant.setMode(bundle.getString("mode"));
                    bookedRestaurant.setTime(bundle.getString("time"));
                    bookedRestaurant.setTable(bundle.getString("table"));

                    Log.d(TAG, "onClick: " + bundle.getString("time"));

                    String qrString = rest.getName();
                    qrString = qrString + "\n" + bundle.getString("date");
                    qrString = qrString + "\n" + bundle.getString("time");
                    qrString = qrString + "\n" + bundle.getString("table");
                    Log.d(TAG, "onClick: " + qrString);

                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    try {
                        BitMatrix bitMatrix = qrCodeWriter.encode(qrString, BarcodeFormat.QR_CODE,150,150);
                        Bitmap bitmap = Bitmap.createBitmap(150,150,Bitmap.Config.RGB_565);

                        for(int x=0; x<150; x++){
                            for(int y=0; y<150; y++){
                                bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                            }
                        }
                        bookedRestaurant.setQrImage(bitmap);

                        utils.addBookedRest(bookedRestaurant);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(),MyBookingsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("bookedRest",bookedRestaurant);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
