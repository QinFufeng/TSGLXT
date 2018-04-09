package com.example.wind.liberarymanege.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private LinearLayout loading,main;
    private TextView tload;
    private ProgressBar pro;
    private DBUtil dbUtil;
    private String NAME;
    final int[] dd = new int[1];
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:gomain(msg.obj);break;
                    //Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();

                default:Toast.makeText(LoginActivity.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= (EditText) findViewById(R.id.userName);
        password= (EditText) findViewById(R.id.userPassword);
        register= (Button) findViewById(R.id.bn_register);
        login= (Button) findViewById(R.id.bn_login);
        loading= (LinearLayout) findViewById(R.id.layout_loading);
        main= (LinearLayout) findViewById(R.id.mainS);
        pro= (ProgressBar) findViewById(R.id.load_dialog);
        tload= (TextView) findViewById(R.id.tv_load);
        dbUtil=new DBUtil();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                NAME=name;
                String pwd=password.getText().toString();

                if(name.equals("")){
                    Toast.makeText(LoginActivity.this,"请输入用户名！", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String [] a=new String[]{"username","userpassword"};
                final String [] b=new String[]{name,pwd};

                loading.setVisibility(View.VISIBLE);
                //main.setVisibility(View.GONE);
                username.setEnabled(false);
                password.setEnabled(false);
                register.setEnabled(false);
                login.setEnabled(false);

                new Thread(){
                    @Override
                    public void run() {

                        //dd[0] =dbUtil.Login(a,b);
                        Message msg = new Message();
                        msg.obj=dbUtil.Login(a,b);
                        msg.what = 1;
                        handler.sendMessage(msg);

                    }
                }.start();

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
    public  void gomain(Object obj){
        //int cc= dd[0];//dbUtil.Login(a,b);
        //loading.setEnabled(true);

        //DialogUIUtils.showMdLoadingHorizontal(this, "加载中...").show();
        //Toast.makeText(LoginActivity.this, cc, Toast.LENGTH_SHORT).show();
        String [] ss= (String[]) obj;
        String cc=ss[0];
        if(cc.equals("0")){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("name",NAME);
            intent.putExtra("rank",ss[1]);
            startActivity(intent) ;
            LoginActivity.this.finish();
        }
        else if(cc.equals("1")){
            Toast.makeText(LoginActivity.this,"用户名或密码错误！", Toast.LENGTH_SHORT).show();
            showControl();
        }
        else{
            Toast.makeText(LoginActivity.this,"网络连接错误！", Toast.LENGTH_SHORT).show();
            showControl();
        }
    }
    public void showControl(){
        loading.setVisibility(View.GONE);
        //main.setVisibility(View.GONE);
        username.setEnabled(true);
        password.setEnabled(true);
        register.setEnabled(true);
        login.setEnabled(true);
    }
}
