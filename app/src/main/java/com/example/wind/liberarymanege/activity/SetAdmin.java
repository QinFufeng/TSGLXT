package com.example.wind.liberarymanege.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.TUser;
import com.example.wind.liberarymanege.httpdb.DBUtil;
import com.example.wind.liberarymanege.httpdb.RegExpValidatorUtils;

public class SetAdmin extends AppCompatActivity {
    private EditText Ename,Emima,Ephone,Eemail;
    private RadioButton Rman,Rwman,R01,R02,R03;
    private String ID;
    private DBUtil dbUtil;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:goshowuser(msg.obj);break;
                case 2://goshowDelend(msg.obj);break;
                case 3://goshowday(msg.obj);break;
                case 4://godate(msg.obj);break;
                default:
                    Toast.makeText(SetAdmin.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setadmin);
        dbUtil=new DBUtil();
        Ename= (EditText) findViewById(R.id.setname);
        Emima= (EditText)findViewById(R.id.setpwd);
        Ephone= (EditText)findViewById(R.id.setphone);
        Eemail= (EditText)findViewById(R.id.setemail);
        Rman= (RadioButton) findViewById(R.id.rb_man5);
        Rwman= (RadioButton)findViewById(R.id.rb_wnam5);
        R01= (RadioButton)findViewById(R.id.rb_00R);
        R02= (RadioButton)findViewById(R.id.rb_02R);
        R03= (RadioButton)findViewById(R.id.rb_03R);

        Intent intent=getIntent();
        ID=intent.getStringExtra("uid");

        Ename.setText(ID);
        final String[] aa = new String[]{"uid"};
        final String[] bb = new String[]{ID};
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = dbUtil.LookUser(aa, bb);
                msg.what = 1;
                handler.sendMessage(msg);

            }
        }.start();
    }

    private void goshowuser(Object obj) {
        TUser user= (TUser) obj;
        Ename.setText(user.getUsername());
        Emima.setText(user.getUserpassword());
        Ephone.setText(user.getPhone());
        Eemail.setText(user.getEmail());
        if(user.getSex().equals("男"))
        {
            Rman.setChecked(true);
        }else
        {
            Rwman.setChecked(true);
        }
        if(user.getRank()==3)
        {
            R03.setChecked(true);
        }else if(user.getRank()==2)
        {
            R02.setChecked(true);
        }else
        {
            R01.setChecked(true);
        }
    }


    public void setadminend(View view) {
        SetAdmin.this.finish();
    }

    public void AddAdminsub(View view) {
        String id=ID;
        String name=Ename.getText().toString();
        String mima=Emima.getText().toString();
        String phone=Ephone.getText().toString();
        String email=Eemail.getText().toString();
        String sex="男";
        if(Rwman.isChecked())
        {
            sex="女";
        }
        String rank="0";
        if(R02.isChecked())
        {
            rank="2";
        }else if(R03.isChecked())
        {
            rank="3";
        }

        if(name.equals("")){
            Toast.makeText(SetAdmin.this,"请输入用户名！", Toast.LENGTH_SHORT).show();
            return;
        }
        RegExpValidatorUtils regExpValidatorUtils=new RegExpValidatorUtils();
        if(!regExpValidatorUtils.IsHandset(phone)){
            Toast.makeText(SetAdmin.this,"手机号码有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!regExpValidatorUtils.isEmail(email)){
            Toast.makeText(SetAdmin.this,"邮箱有误", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
