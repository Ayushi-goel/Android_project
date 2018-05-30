package com.example.aayushi.designapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    int httpStatus,count=0 ;
    HttpResponse httpResponse;
    ProgressDialog pd;
    EditText t1,t2;


    Button btn,btn3;
    String n,p,test,name,pass;
    public static final String Session = "session";
    public static final String Name = "nameKey";
    public static final String Pass = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






            t1 = (EditText) findViewById(R.id.editText);
            t2 = (EditText) findViewById(R.id.editText2);
            btn = (Button) findViewById(R.id.button);
            btn3 = (Button) findViewById(R.id.button10);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean ans=isAvailable();
                    if(ans=true)
                    {


                        n = t1.getText().toString();
                        p = t2.getText().toString();
                        new ExecuteTask().execute(n, p);
                    }
                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count == 0) {
                        Toast.makeText(MainActivity.this, "Enter Account Detail First", 1).show();
                    } else {

                        Intent i = new Intent(MainActivity.this, MenuOptions_Activity.class);
                        startActivity(i);

                    }

                }
            });

    }
    public class ExecuteTask extends AsyncTask <String, String, String> {

        @Override
        protected String doInBackground(String...params) {

            String res=PostData(params);
            return res;
        }

        private String PostData(String[] params) {
            String s="";
            test=params[0];
            try {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/Log.jsp");
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("t1", params[1]));
                list.add(new BasicNameValuePair("t2", params[0]));
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
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Web Data");
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            try {
               JSONObject json = new JSONObject(s);
                String use=json.getString("key");

                if(use.equalsIgnoreCase("false"))
                {
                   Toast.makeText(MainActivity.this,"try later",1).show();
                }
                else
                {
                     name=json.getString("name");
                     pass=json.getString("pass");
                    SharedPreferences sp=getSharedPreferences(Session, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(Name,name);
                    editor.putString(Pass,pass);
                    editor.commit();
                    count++;
                    Intent i = new Intent(MainActivity.this, MenuOptions_Activity.class);
                    startActivity(i);

                    Vibrator vv= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vv.vibrate(1000);

                    Toast.makeText(MainActivity.this,"Welcome to your Account.Now you can Access Account",1).show();

                }

            }catch(Exception e){
                Log.e("log_tag", "Error:  " + e.toString());
            }



        }
    }

    public  boolean isAvailable(){
        Boolean isAvailable=false;

        try{
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();

            if(networkInfo!=null &&networkInfo.isConnected()) {
                isAvailable=true;
            }
            else{

            }

        }
        catch (Exception e)
        {



        }
return isAvailable;
    }

}
