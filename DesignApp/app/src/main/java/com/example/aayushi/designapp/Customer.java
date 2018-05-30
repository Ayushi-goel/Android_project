package com.example.aayushi.designapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Customer extends AppCompatActivity {

    TabHost t1;
    AutoCompleteTextView at;
    ArrayAdapter<String> aa;
    ArrayList<String> list;
    ListView lv,lv2,lv3;
    String a,name;
    Button btn;
    EditText et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        t1=(TabHost)findViewById(R.id.tabHost2);
        btn=(Button)findViewById(R.id.options);
        at= (AutoCompleteTextView) findViewById(R.id.auto);
        lv=(ListView)findViewById(R.id.listView);
      //  aa=new ArrayAdapter<String>(Customer.this,android.R.layout.simple_list_item_1,country);
      //  at.setThreshold(1);
        //at.setAdapter(aa);
        Toast.makeText(Customer.this, "Data: "  , Toast.LENGTH_LONG).show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = at.getText().toString();

                new ExecuteTask().execute(a);

            }
        });

        t1.setup();
        TabHost.TabSpec th=t1.newTabSpec("Aaaa");
        th.setIndicator("**Enquiry**");
        th.setContent(R.id.tab3);
        t1.addTab(th);


        TabHost.TabSpec th2=t1.newTabSpec("Aaaa");
        th2.setIndicator("**Return Damaged Product**");
        th2.setContent(R.id.tab4);
        t1.addTab(th2);

    }
    public class ExecuteTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String r=ReadData(params);
            return r;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Customer.this, "Data: " + s, 1).show();

            try {
                JSONObject json = new JSONObject(s);

                list=new ArrayList<String>();
                 for(int i=0;i<json.length();i++)
                {
                    list.add(json.getString(i+"key"));
                }
                 aa=new ArrayAdapter<String>(Customer.this,android.R.layout.simple_list_item_1,list);
                  lv.setAdapter(aa);

            }catch(Exception e){}

            }


        private String ReadData(String[] params) {
            String s="";
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.43.154:8080/invoice/enquire.jsp?t1="+a);
                HttpResponse httpResponse=	httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                s = readResponse(httpResponse);

            }
            catch(Exception exception) 	{}

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
