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
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.BType;
import com.example.wind.liberarymanege.httpdb.DBUtil;

/**
 * Created by wind on 2018/3/21.
 */

public class TypeAdd extends AppCompatActivity {
    private Button btalt,btadd,quxiao;
    private EditText name,desc;
    private LinearLayout lading;
    DBUtil dbUtil=new DBUtil();
    BType type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeadd);
        btalt= (Button) findViewById(R.id.typealt);
        btadd= (Button) findViewById(R.id.typeadd);
        quxiao= (Button) findViewById(R.id.quxiao12);
        name= (EditText) findViewById(R.id.typeaddname);
        desc= (EditText) findViewById(R.id.typeadddesc);
        lading= (LinearLayout) findViewById(R.id.layout_loading4);
        Intent intent=getIntent();
        /*if(intent.getStringExtra("Tid")!=null){
            btadd.setVisibility(View.GONE);
            btalt.setVisibility(View.VISIBLE);
            name.setText(intent.getStringExtra("Tid"));
        }*/
        if(intent.getSerializableExtra("type")!=null){
            btadd.setVisibility(View.GONE);
            btalt.setVisibility(View.VISIBLE);
            type= (BType) intent.getSerializableExtra("type");
            name.setText(type.getTname());
            desc.setText(type.getTdesc());
        }
    }

    public void typequxiao(View view) {
        TypeAdd.this.finish();
    }

    public void typeaddCk(View view) {
        final String tname=name.getText().toString();
        final String tdesc=desc.getText().toString();
        if(tname.equals("")||tname==null||tdesc.equals("")||tdesc==null)
        {
            Toast.makeText(TypeAdd.this, "名字和描述不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        btadd.setEnabled(false);
        quxiao.setEnabled(false);
        name.setEnabled(false);
        desc.setEnabled(false);
        lading.setVisibility(View.VISIBLE);
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj=dbUtil.TypeAddBool(tname,tdesc);
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:goshowaddend(msg.obj);break;
                //Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
                case 2:goshowAltend(msg.obj);break;
                default:
                    Toast.makeText(TypeAdd.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private void goshowaddend(Object obj) {
        btadd.setEnabled(true);
        quxiao.setEnabled(true);
        name.setEnabled(true);
        desc.setEnabled(true);
        lading.setVisibility(View.INVISIBLE);
        String bool= (String) obj;
        if(bool.equals("true")) {
            Toast.makeText(TypeAdd.this, "添加成功！", Toast.LENGTH_LONG).show();
        }
        else if(bool.equals("false")) {
            Toast.makeText(TypeAdd.this, "添加失败！", Toast.LENGTH_LONG).show();
        }
        else if(bool.equals("ox003")) {
            Toast.makeText(TypeAdd.this, "类型名已存在！", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(TypeAdd.this, "连接错误！", Toast.LENGTH_LONG).show();
        }
    }

    public void typeAltCk(View view) {
        final String tid=type.getTid()+"";
        final String tname=name.getText().toString();
        final String tdesc=desc.getText().toString();
        if(tname.equals("")||tname==null||tdesc.equals("")||tdesc==null)
        {
            Toast.makeText(TypeAdd.this, "名字和描述不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        btadd.setEnabled(false);
        quxiao.setEnabled(false);
        name.setEnabled(false);
        desc.setEnabled(false);
        lading.setVisibility(View.VISIBLE);
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj=dbUtil.TypeAltBool(tid,tname,tdesc);
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void goshowAltend(Object obj) {
        final String tid=type.getTid()+"";
        btadd.setEnabled(true);
        quxiao.setEnabled(true);
        name.setEnabled(true);
        desc.setEnabled(true);
        lading.setVisibility(View.INVISIBLE);
        String bool= (String) obj;
        if(bool.equals("true")) {
            //Toast.makeText(TypeAdd.this, "修改成功！", Toast.LENGTH_LONG).show();
            Intent intent=new Intent();
            //intent.putExtra("name",name.getText().toString());
            setResult(RESULT_OK,intent);
            TypeAdd.this.finish();
        }
        else if(bool.equals("false")) {
            Toast.makeText(TypeAdd.this, "修改失败！", Toast.LENGTH_LONG).show();
        }
        else if(bool.equals("ox003")) {
            Toast.makeText(TypeAdd.this, "类型名已存在！", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(TypeAdd.this, "连接错误！", Toast.LENGTH_LONG).show();
        }
    }
}
