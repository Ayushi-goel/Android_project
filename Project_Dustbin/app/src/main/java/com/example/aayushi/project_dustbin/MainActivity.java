package com.example.aayushi.project_dustbin;

import android.app.ActionBar;
import android.app.TabActivity;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPassword,inputID,inputAddress,inputcID,inputcPassword;
    private TextView link,link2;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword,inputLayoutID,inputLayoutAddress,inputLayoutcheckID,inputLayoutcheckPassword;
    private Button btnSignUp;
    ScrollView scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        link=(TextView)findViewById(R.id.link_login);
        link2=(TextView)findViewById(R.id.link_regster);
        TabHost t1;
        t1=(TabHost)findViewById(R.id.tabHost);
        t1.setup();
        TabHost.TabSpec th=t1.newTabSpec("Intern");
        th.setIndicator("Register");
        th.setContent(R.id.tab1);
        t1.addTab(th);

        TabHost.TabSpec th2=t1.newTabSpec("Intern");
        th2.setIndicator("Sign-In");
        th2.setContent(R.id.tab2);
        t1.addTab(th2);


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        inputLayoutID=(TextInputLayout)findViewById(R.id.input_layout_ID);
        inputLayoutAddress=(TextInputLayout)findViewById(R.id.input_layout_Address);
        inputID=(EditText)findViewById(R.id.input_ID);
        inputAddress=(EditText)findViewById(R.id.input_address);
        inputLayoutcheckID=(TextInputLayout)findViewById(R.id.input_check_ID);
        inputLayoutcheckPassword=(TextInputLayout)findViewById(R.id.input_check_password);
        inputcID=(EditText)findViewById(R.id.input_c_id);
        inputcPassword=(EditText)findViewById(R.id.input_c_password);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  getActionBar().setSelectedNavigationItem(1);
            }
        });


    }
}
