package com.example.wind.liberarymanege.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
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
    private Button bt1,bt2;
    private LinearLayout layoutLoad;
    private ProgressBar pro;
    private TextView tv;
    final int[] dd = new int[1];

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:gosub();break;
                //Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();

                default:Toast.makeText(RegisterActivity.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

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
        bt1= (Button) findViewById(R.id.bn_reset);
        bt2= (Button) findViewById(R.id.bn_sub);
        layoutLoad= (LinearLayout) findViewById(R.id.layout_loading1);
        pro= (ProgressBar) findViewById(R.id.load_dialog1);
        tv= (TextView) findViewById(R.id.tv_load1);
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
        if(name.equals("")){
            Toast.makeText(RegisterActivity.this,"请输入用户名！", Toast.LENGTH_SHORT).show();
            return;
        }
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
        final String[] a=new String[]{"username","userpassword","sex","phone","email"};
        final String[] b=new String[]{name,pwd,sex,phone,eamil};

        tname.setEnabled(false);
        tpwd.setEnabled(false);
        trpwd.setEnabled(false);
        tphone.setEnabled(false);
        teameil.setEnabled(false);
        rman.setEnabled(false);
        rwonman.setEnabled(false);
        bt1.setEnabled(false);
        bt2.setEnabled(false);
        layoutLoad.setVisibility(View.VISIBLE);

        new Thread(){
            @Override
            public void run() {

                dd[0] =dbUtil.Register(a,b);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }
        }.start();

    }
    private void gosub(){
        int c= dd[0];

        if(c==0){
            //Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            //startActivity(intent) ;
            //Toast.makeText(RegisterActivity.this,"注册成功！", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("注册成功！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //commit();
                            RegisterActivity.this.finish();
                        }
                    });
            builder.create().show();
            setEnB();
        }
        else if(c==1){
            Toast.makeText(RegisterActivity.this,"注册失败！", Toast.LENGTH_SHORT).show();
            setEnB();
        }
        else if(c==2){
            //Toast.makeText(RegisterActivity.this,"该用户名已存在", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("该用户名已存在！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //commit();
                            RegisterActivity.this.finish();
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
        tname.setEnabled(true);
        tpwd.setEnabled(true);
        trpwd.setEnabled(true);
        tphone.setEnabled(true);
        teameil.setEnabled(true);
        rman.setEnabled(true);
        rwonman.setEnabled(true);
        bt1.setEnabled(true);
        bt2.setEnabled(true);
        layoutLoad.setVisibility(View.GONE);
    }

    public void registerEnd(View view) {
        RegisterActivity.this.finish();
    }
}
