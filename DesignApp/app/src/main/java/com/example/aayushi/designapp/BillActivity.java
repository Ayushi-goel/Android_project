package com.example.aayushi.designapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
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
import java.util.ArrayList;
import java.util.List;

public class BillActivity extends Activity {
    EditText b2,b4,b6;
    Button btn,btni,btnn;
    String name,id,number,para;
    ListView l2,l3,l4;
    ArrayAdapter<String> aa;
    ArrayList<String> list;
    TabHost t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        b2=(EditText)findViewById(R.id.b2);
        b4=(EditText)findViewById(R.id.b4);
        b6=(EditText)findViewById(R.id.b6);
        l2=(ListView)findViewById(R.id.listView2);
        btn=(Button)findViewById(R.id.but);
        btni=(Button)findViewById(R.id.but2);
        btnn=(Button)findViewById(R.id.but3);
        t1=(TabHost)findViewById(R.id.tabHost4);
        l3=(ListView)findViewById(R.id.listView3);
        l4=(ListView)findViewById(R.id.listView4);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                para=b2.getText().toString();
                new Find().execute();
                l2.setAdapter(null);


            }
        });
        btni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                para = b4.getText().toString();
                new Find().execute();
                l3.setAdapter(null);

            }
        });

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                para = b6.getText().toString();
                new Find().execute();
                l4.setAdapter(null);

            }
        });


        t1.setup();
        TabHost.TabSpec th=t1.newTabSpec("Aaaa");
        th.setIndicator("Find by name");
        th.setContent(R.id.set1);
        t1.addTab(th);


        TabHost.TabSpec th2=t1.newTabSpec("Aaaa");
        th2.setIndicator("Find by id");
        th2.setContent(R.id.set2);
        t1.addTab(th2);
        TabHost.TabSpec th3=t1.newTabSpec("Aaaa");
        th3.setIndicator("Find by number");
        th3.setContent(R.id.set3);
        t1.addTab(th3);
    }


    public  class Find extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String res=FindData();


            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          // Toast.makeText(BillActivity.this, "Data: " + s, 1).show();
            try {JSONObject json = new JSONObject(s);


                    list = new ArrayList<String>();
                    for (int i = 0; i < json.length(); i++) {

                        list.add(json.getString(i + "key"));
                    }
                    aa = new ArrayAdapter<String>(BillActivity.this, android.R.layout.simple_list_item_1, list);
                    l2.setAdapter(aa);
                    l3.setAdapter(aa);
                    l4.setAdapter(aa);

            }catch(Exception e){}


        }

        private String FindData() {
            String s="";
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.22:8080/invoice/pareshan.jsp");
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                    list.add(new BasicNameValuePair("params", para));
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
