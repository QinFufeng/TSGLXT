package com.example.wind.liberarymanege.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.httpdb.HttpConnSoap;

/**
 * Created by wind on 2017/12/20.
 */

public class LoginActivity extends AppCompatActivity{
    private EditText username,password;
    private Button register,login;
    private HttpConnSoap Test;
    String cc="test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= (EditText) findViewById(R.id.userName);
        password= (EditText) findViewById(R.id.userPassword);
        register= (Button) findViewById(R.id.bn_register);
        login= (Button) findViewById(R.id.bn_login);
        Test=new HttpConnSoap();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pwd=password.getText().toString();
                String [] a=new String[]{"username","userpassword"};
                String [] b=new String[]{name,pwd};
                cc=Test.HttpGo(a,b,"IsMatchUser");
                Toast.makeText(LoginActivity.this, cc, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
