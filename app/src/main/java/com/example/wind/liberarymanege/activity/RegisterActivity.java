package com.example.wind.liberarymanege.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.httpdb.DBUtil;
import com.example.wind.liberarymanege.httpdb.RegExpValidatorUtils;

/**
 * Created by wind on 2017/12/26.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText tname,tpwd,trpwd,tphone,teameil;
    private RadioButton rman,rwonman;
    private RegExpValidatorUtils regExpValidatorUtils;
    private DBUtil dbUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tname= (EditText) findViewById(R.id.tname);
        tpwd= (EditText) findViewById(R.id.tpwd);
        trpwd= (EditText) findViewById(R.id.trpwd);
        tphone= (EditText) findViewById(R.id.tphone);
        teameil= (EditText) findViewById(R.id.temail);
        rman= (RadioButton) findViewById(R.id.rb_man);
        rwonman= (RadioButton) findViewById(R.id.rb_wnam);
        regExpValidatorUtils=new RegExpValidatorUtils();
        dbUtil=new DBUtil();

    }

    public void reset(View view) {
        tname.setText("");
        tpwd.setText("");
        trpwd.setText("");
        tphone.setText("");
        teameil.setText("");
        rman.setChecked(true);
        //wonman.setChecked(false);
    }

    public void sub(View view) {
        String name=tname.getText().toString();
        String pwd=tpwd.getText().toString();
        String rpwd=trpwd.getText().toString();
        String phone=tphone.getText().toString();
        String eamil=teameil.getText().toString();
        String sex="男";
        if(rwonman.isChecked()){sex="女";}
        //Toast.makeText(RegisterActivity.this,sex, Toast.LENGTH_SHORT).show();
        if(!pwd.equals(rpwd)){
            Toast.makeText(RegisterActivity.this,"密码与确认密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!regExpValidatorUtils.IsHandset(phone)){
            Toast.makeText(RegisterActivity.this,"手机号码有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!regExpValidatorUtils.isEmail(eamil)){
            Toast.makeText(RegisterActivity.this,"邮箱有误", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] a=new String[]{"username","userpassword","sex","phone","email"};
        String[] b=new String[]{name,pwd,sex,phone,eamil};
        int c= dbUtil.Register(a,b);
        if(c==0){
            //Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            //startActivity(intent) ;
            Toast.makeText(RegisterActivity.this,"注册成功！", Toast.LENGTH_SHORT).show();
        }
        else if(c==1){
            Toast.makeText(RegisterActivity.this,"注册失败！", Toast.LENGTH_SHORT).show();
        }
        else if(c==2){
            Toast.makeText(RegisterActivity.this,"该用户名已存在", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(RegisterActivity.this,"网络连接错误！", Toast.LENGTH_SHORT).show();
    }
}
