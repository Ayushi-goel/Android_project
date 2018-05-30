package com.example.aayushi.designapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class invoiceActivity extends Activity {

    EditText et3,et4,et5,et8,et9,et10;
    Button btnc,btns,btnab;
    TextView pname,amount,et1,et2,et6,et7;
    String date,st,na,am,ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        et1= (TextView) findViewById(R.id.t2);
        et2= (TextView) findViewById(R.id.t4);
        et3= (EditText) findViewById(R.id.t6);
        et4= (EditText) findViewById(R.id.t9);
        et5= (EditText) findViewById(R.id.t11);
        et6= (TextView) findViewById(R.id.t13);
        et7= (TextView) findViewById(R.id.t15);
        et8= (EditText) findViewById(R.id.t19);
        et9= (EditText) findViewById(R.id.t21);
        pname=(TextView)findViewById(R.id.t7);
        amount=(TextView)findViewById(R.id.t16);
        btns=(Button)findViewById(R.id.btns);
        btnab=(Button)findViewById(R.id.btnab);
        btnc=(Button)findViewById(R.id.btnc);

        Random r = new Random();
        st=r.nextInt(1000)+"";
        et1.setText(st);
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        et2.setText(date);

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ids = et3.getText().toString();
                new FindTask().execute(ids);
            }
        });

        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Save().execute();
            }
        });

        btnab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Create().execute();
            }
        });

    }

    public class FindTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            String res=FindData(params);
            return res;
        }

        private String FindData(String[] params) {
            String s="";
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/check.jsp?t1="+params[0]);
                HttpResponse httpResponse=	httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                s = readResponse(httpResponse);

            }
            catch(Exception e) 	{ Log.e("log_tag", "Error: main  " + e.toString());}


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

                if (use.equalsIgnoreCase("false")) {

                    Toast.makeText(invoiceActivity.this, "You are not a authenticated id user.Please enter correct value", 1).show();
                } else {
                    String name=json.getString("name");
                    String quan=json.getString("quan");
                    String bill=json.getString("bill");
                     na=json.getString("na");
                    String num=json.getString("num");
                    pname.setText(name);
                    et4.setText(bill);
                    et5.setText(quan);
                    et6.setText("10%");
                    et7.setText("30%");
                    et8.setText(na);
                    et9.setText(num);
                    int p=Integer.parseInt(quan);
                    int b=Integer.parseInt(bill);

                    int total= (int) ((0.3*(0.9*(p*b)))+(0.9*p*b));
                    am=""+total;
                    amount.setText(""+total);

                    Toast.makeText(invoiceActivity.this, "You are a authenticated id user.", 1).show();

                }
            } catch (Exception e) {
            }
        }
    }
    public  class Create extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            String res=FindData();


            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject json = new JSONObject(s);
                String use = json.getString("key");

                if (use.equalsIgnoreCase("false")) {

                    Toast.makeText(invoiceActivity.this, "Some problem encountered", 1).show();
                } else {
                    Toast.makeText(invoiceActivity.this,am+ "invoice create succesfully", 1).show();

                }
            }
            catch (Exception e){Log.e("log_tag", "Error:   " + e.toString());}


                }

        private String FindData() {
            String s="";
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/create.jsp");
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("no", st));
                list.add(new BasicNameValuePair("name", na));
                list.add(new BasicNameValuePair("date",date));
                list.add(new BasicNameValuePair("amount",am));
                list.add(new BasicNameValuePair("id",ids));
                httpPost.setEntity(new UrlEncodedFormEntity(list));

                HttpResponse httpResponse=	httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                s = readResponse(httpResponse);

            }
            catch(Exception e) 	{Log.e("log_tag", "Error: main  " + e.toString());}


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

    }

    public  class Save extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            String res=FindData();


            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject json = new JSONObject(s);
                String use = json.getString("key");

                if (use.equalsIgnoreCase("false")) {

                    Toast.makeText(invoiceActivity.this, "Some problem encountered", 1).show();
                } else {
                    Toast.makeText(invoiceActivity.this,am+ "invoice create succesfully", 1).show();

                }
            }
            catch (Exception e){Log.e("log_tag", "Error:   " + e.toString());}


        }

        private String FindData() {
            String s="";
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/send.jsp");
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("no", st));
                list.add(new BasicNameValuePair("name", na));
                list.add(new BasicNameValuePair("date",date));
                list.add(new BasicNameValuePair("amount",am));
                list.add(new BasicNameValuePair("id",ids));
                httpPost.setEntity(new UrlEncodedFormEntity(list));

                HttpResponse httpResponse=	httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                s = readResponse(httpResponse);

            }
            catch(Exception e) 	{Log.e("log_tag", "Error: main  " + e.toString());}


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

    }


}


