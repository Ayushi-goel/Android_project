package com.example.aayushi.designapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product extends AppCompatActivity {
    String id,price,name,date,quan,test;
    TabHost t1;
    EditText et1,et2,et3,et4,et5;
    Button btn;
    HttpResponse httpResponse;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

         date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        t1=(TabHost) findViewById(R.id.tabHost);
        et1= (EditText) findViewById(R.id.textView8);
        et2= (EditText) findViewById(R.id.textView9);
        et3= (EditText) findViewById(R.id.textView10);
        et4= (EditText) findViewById(R.id.textView11);
        et5=(EditText)findViewById(R.id.editText3);
        btn=(Button)findViewById(R.id.button11);

        et4.setText(date);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=et1.getText().toString();

                id=  et2.getText().toString();
                price=  et3.getText().toString();

                quan=  et5.getText().toString();

                new ExecuteTask().execute(id,price,date,name,quan);
            }
        });



        t1.setup();
        TabHost.TabSpec th=t1.newTabSpec("Aaaa");
        th.setIndicator("**Add**");
        th.setContent(R.id.tab1);
        t1.addTab(th);


        TabHost.TabSpec th2=t1.newTabSpec("Aaaa");
        th2.setIndicator("**View**");
        th2.setContent(R.id.tab2);
        t1.addTab(th2);


    }

    public class ExecuteTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            String r=PostData(params);
            return r;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Product.this);
            pd.setTitle("Account info");
            pd.setMessage("Adding the details");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            try {
                JSONObject json = new JSONObject(s);
                String use = json.getString("res");

                if (use.equalsIgnoreCase("add")) {
                    Toast.makeText(Product.this, "Details added successfuly", 1).show();
                } else {
                    Toast.makeText(Product.this, "Some Error occured", 1).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        private String PostData(String[] params) {
            String s="";
            test=params[0];
            try {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/getData.jsp");
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("id", params[0]));
                list.add(new BasicNameValuePair("price", params[1]));
                list.add(new BasicNameValuePair("date",params[2]));
                list.add(new BasicNameValuePair("name",params[3]));
                list.add(new BasicNameValuePair("quan",params[4]));

                httpPost.setEntity(new UrlEncodedFormEntity(list));
                httpResponse=httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                s = readResponse(httpResponse);

                // Toast.makeText(MainActivity.this, "Welcome to Account", 1).show();

                //Intent i=new Intent(MainActivity.this,MenuOptions_Activity.class);
                //startActivity(i);


            } catch(Exception e) {

                Log.e("log_tag", "Error: main  " + e.toString());
            }
             return  s;

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
