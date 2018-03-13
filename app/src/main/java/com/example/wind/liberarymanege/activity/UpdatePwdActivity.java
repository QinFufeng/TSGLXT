package com.example.wind.liberarymanege.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.httpdb.DBUtil;

/**
 * Created by wind on 2018/1/11.
 */

public class UpdatePwdActivity extends AppCompatActivity {
    private String NAME;
    private EditText ypwd,xpwd,qrpwd;
    private Button quxiao,subpwd;
    private TextView tvUsername;
    private LinearLayout pwdloading;
    final int[] dd = new int[1];
    private DBUtil dbUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        tvUsername=(TextView) findViewById(R.id.updatepwdname);
        ypwd= (EditText) findViewById(R.id.typwd);
        xpwd= (EditText) findViewById(R.id.txpwd);
        qrpwd= (EditText) findViewById(R.id.tqrpwd);
        quxiao= (Button) findViewById(R.id.bn_resetpwd);
        subpwd= (Button) findViewById(R.id.bn_subpwd);
        pwdloading= (LinearLayout) findViewById(R.id.layout_pwdloading);

        dbUtil=new DBUtil();

        Intent in=getIntent();
        NAME=in.getStringExtra("name");
        tvUsername.setText(NAME);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:godate();break;
                //Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();

                default:
                    Toast.makeText(UpdatePwdActivity.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public void fanhui(View view) {
        //Intent intent=new Intent(UpdatePwdActivity.this,MainActivity.class);
        //intent.putExtra("name",NAME);
        //startActivity(intent) ;
        UpdatePwdActivity.this.finish();
    }

    public void subpwdonClik(View view) {
        String Typwd=ypwd.getText().toString();
        String Txpwd=xpwd.getText().toString();
        String Tqrpwd=qrpwd.getText().toString();
        if(Typwd.equals("")){
            Toast.makeText(UpdatePwdActivity.this,"密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Txpwd.equals("")){
            Toast.makeText(UpdatePwdActivity.this,"密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Tqrpwd.equals("")){
            Toast.makeText(UpdatePwdActivity.this,"密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Txpwd.equals(Tqrpwd)){
            Toast.makeText(UpdatePwdActivity.this,"新密码与确认密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        final String[] a=new String[]{"username","ypwd","xpwd"};
        final String[] b=new String[]{NAME,Typwd,Txpwd};
        ypwd.setEnabled(false);
        xpwd.setEnabled(false);
        qrpwd.setEnabled(false);
        quxiao.setEnabled(false);
        subpwd.setEnabled(false);
        pwdloading.setVisibility(View.VISIBLE);
        new Thread(){
            @Override
            public void run() {

                dd[0] =dbUtil.Updatepwd(a,b);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }
        }.start();
    }
    private void godate(){

        int c= dd[0];

        if(c==0){
            //Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            //startActivity(intent) ;
            //Toast.makeText(RegisterActivity.this,"注册成功！", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("修改成功！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //commit();
                            UpdatePwdActivity.this.finish();
                        }
                    });
            builder.create().show();
            setEnB();
        }
        else if(c==1){
            Toast.makeText(UpdatePwdActivity.this,"修改失败！", Toast.LENGTH_SHORT).show();
            setEnB();
        }
        else if(c==2){
            //Toast.makeText(RegisterActivity.this,"该用户名已存在", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("原密码错误！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //commit();
                            UpdatePwdActivity.this.finish();
                        }
                    }).setNegativeButton("取消", null);
            builder.create().show();
            setEnB();
        }
        else {
            //Toast.makeText(RegisterActivity.this, "网络连接错误！", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("网络连接错误！")
                    .setPositiveButton("确认", null);
            builder.create().show();
            setEnB();
        }
    }
    private void setEnB(){
        ypwd.setEnabled(true);
        xpwd.setEnabled(true);
        qrpwd.setEnabled(true);
        quxiao.setEnabled(true);
        subpwd.setEnabled(true);
        pwdloading.setVisibility(View.GONE);
    }
}

