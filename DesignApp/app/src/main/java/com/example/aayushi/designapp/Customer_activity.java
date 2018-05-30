package com.example.aayushi.designapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.List;

public class Customer_activity extends Activity {

    EditText et1,et2,et3;
    TextView et4;
    Button btn;
    String name,number,que;
    HttpResponse httpResponse;
    TabHost t1;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_activity);
        et1=(EditText)findViewById(R.id.editText10);
        et2=(EditText)findViewById(R.id.editText11);
        et3=(EditText)findViewById(R.id.editText12);
        et4=(TextView)findViewById(R.id.textView24);

        btn= (Button) findViewById(R.id.button2);
        t1= (TabHost) findViewById(R.id.tabHost3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et1.getText().toString();
                number = et2.getText().toString();
                que = et3.getText().toString();
                new ExecuteTask().execute(name, number, que);

            }
        });

        t1.setup();
        TabHost.TabSpec th=t1.newTabSpec("Aaaa");
        th.setIndicator("**Enquiry**");
        th.setContent(R.id.linearLayout);
        t1.addTab(th);


        TabHost.TabSpec th2=t1.newTabSpec("Aaaa");
        th2.setIndicator("**Return Product**");
        th2.setContent(R.id.linearLayout3);
        t1.addTab(th2);


    }

    public  class ExecuteTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            String res=SendData(params);
            return res;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Customer_activity.this, ""+s, 1).show();



            try {

                JSONObject json = new JSONObject(s);
                String use = json.getString("key");

                if (use.equalsIgnoreCase("false")) {
                    et4.setText("You are not a authenticated user.Please Register");
                } else {
                    et4.setText("Your query is added in our systems .We will respond further");

                }
            }

                catch(JSONException e){
                    e.printStackTrace();
                    Log.e("log_tag", "Error: " + e.toString());
                }
            }
            private String SendData(String[] params) {
            String s="";
            try {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/query.jsp");
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("t1", params[0]));
                list.add(new BasicNameValuePair("t2", params[1]));
                list.add(new BasicNameValuePair("t3", params[2]));

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


    }
}
