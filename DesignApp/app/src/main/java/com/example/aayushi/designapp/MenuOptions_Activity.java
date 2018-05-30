package com.example.aayushi.designapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

public class MenuOptions_Activity extends Activity implements Animation.AnimationListener {
String pass,name;
    Button Product,Log_out,stock,customer,invoice,bill;
    Animation a;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_options_);
        Product= (Button) findViewById(R.id.button3);
        Log_out= (Button) findViewById(R.id.button7);
        stock= (Button) findViewById(R.id.button5);
        customer= (Button) findViewById(R.id.button8);
        invoice= (Button) findViewById(R.id.button4);
        bill=(Button)findViewById(R.id.button6);

        t= (TextView) findViewById(R.id.textView4);
        Intent i=getIntent();
        Bundle b=i.getBundleExtra("mybundle");

        Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOptions_Activity.this, Product.class);
                startActivity(i);


            }
        });
        Log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp= getSharedPreferences(MainActivity.Session, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent i=new Intent(MenuOptions_Activity.this,MainActivity.class);
                startActivity(i);
            }
        });
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuOptions_Activity.this,stock.class);
                startActivity(i);
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuOptions_Activity.this, Customer_activity.class);
                startActivity(i);
            }
        });
        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuOptions_Activity.this,invoiceActivity.class);
                startActivity(i);
            }
        });
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuOptions_Activity.this,BillActivity.class);
                startActivity(i);
            }
        });


    }



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
