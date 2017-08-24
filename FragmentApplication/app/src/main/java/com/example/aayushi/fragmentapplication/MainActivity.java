package com.example.aayushi.fragmentapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
    Button btn;
    boolean status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    btn= (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                if(!status){

                    FirstFragment f=new FirstFragment();
                    ft.add(R.id.f1,f);
                    ft.commit();
                btn.setText("Load second Fragment");
                    status=true;


                }
                else
                {
                    Fragmenttwo t=new Fragmenttwo();
                    ft.add(R.layout.fragment_fragmenttwo,t);
                    ft.commit();
                    btn.setText("Load first fragment");
                    status=false;
                }
            }
        });


    }
}
