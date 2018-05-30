package com.example.aayushi.designapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class stock extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5,et6;
    Button btn;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        et1= (EditText) findViewById(R.id.s2);
        et2= (EditText) findViewById(R.id.s4);
        et3= (EditText) findViewById(R.id.s6);
        et4= (EditText) findViewById(R.id.s8);
        et5= (EditText) findViewById(R.id.s10);
        et6= (EditText) findViewById(R.id.s12);
        btn= (Button) findViewById(R.id.b0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=et1.getText().toString();
                new ExecuteTask().execute(id);

            }
        });


    }
    
    public  class ExecuteTask extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String res=ReadValues(params);
            return res;
        }



        private String ReadValues(String[] params) {
            String s="";
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/enquire.jsp?t1="+id);
                HttpResponse httpResponse=	httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                s = readResponse(httpResponse);

            }
            catch(Exception exception) 	{}


            return s;
        }

        private String readResponse(HttpResponse res) {
            InputStream is=null;
            String return_text="";
            try {
                is=res.getEntity().getContent();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String line="";
                StringBuffer sb=new StringBuffer();
                while ((line=bufferedReader.readLine())!=null)
                {
                    sb.append(line);
                }
                return_text=sb.toString();
            } catch (Exception e)
            {

            }
            return return_text;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject json = new JSONObject(s);
                String use = json.getString("key");

                if(use.equalsIgnoreCase("false"))
                {
                    Toast.makeText(stock.this, "You are not a authenticated id user.Please enter correct value", 1).show();
                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                    et5.setText("");
                    et6.setText("");
                }
                else{

                    String name=json.getString("name");
                    String quan=json.getString("quan");
                    String bill=json.getString("bill");
                    String date=json.getString("dates");
                    String naam=json.getString("naam");
                    et2.setText(name);
                    et3.setText(quan);
                    et4.setText(bill);
                    et5.setText(date);
                    et6.setText(naam);
                }


            }catch(Exception e){}
        }

    }
    }


