package com.example.wind.liberarymanege.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wind.liberarymanege.MainActivity;
import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.httpdb.DBUtil;

/**
 * Created by wind on 2017/12/20.
 */

public class LoginActivity extends AppCompatActivity{
    private EditText username,password;
    private Button register,login;
    private DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= (EditText) findViewById(R.id.userName);
        password= (EditText) findViewById(R.id.userPassword);
        register= (Button) findViewById(R.id.bn_register);
        login= (Button) findViewById(R.id.bn_login);
        dbUtil=new DBUtil();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pwd=password.getText().toString();
                String [] a=new String[]{"username","userpassword"};
                String [] b=new String[]{name,pwd};
                int cc=dbUtil.Login(a,b);
                //DialogUIUtils.showMdLoadingHorizontal(this, "加载中...").show();
                //Toast.makeText(LoginActivity.this, cc, Toast.LENGTH_SHORT).show();
                if(cc==0){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent) ;
                    LoginActivity.this.finish();
                }
                else if(cc==1){
                    Toast.makeText(LoginActivity.this,"用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginActivity.this,"网络连接错误！", Toast.LENGTH_SHORT).show();

            }
        });
        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2) ;
            }
        });*/
    }

    public void reg(View view) {
        Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent2) ;
    }
}
