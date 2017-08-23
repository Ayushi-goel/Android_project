package com.example.aayushi.gpsapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.Provider;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView t1, t2;
    LocationManager lm;
    Location l;
    LocationListener ll;
    String p;

    TextToSpeech t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn1);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);



        t=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status!=TextToSpeech.ERROR){
                    t.setLanguage(Locale.UK);
                }

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Criteria c=new Criteria();
                p=lm.getBestProvider(c,false);
                lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);



                try {
                    l = lm.getLastKnownLocation(p);

                                      }
                catch(SecurityException e)
                {
                    t1.setText(""+e);

                }


                if(l==null)
                {
                    t1.setText("Service not available");
                }
                else {


                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    t1.setText("" + lat);
                    t2.setText("" + lon);
                    t.speak("Latitude is"+t1,TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });

    }
}

