package com.example.wind.liberarymanege.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.TUser;
import com.example.wind.liberarymanege.httpdb.DBUtil;

import java.util.List;

/**
 * Created by wind on 2018/1/10.
 */

public class UpdateUserActivity extends AppCompatActivity {
    private ImageView image;
    private EditText name,phone,email;
    private RadioButton man,woman;
    private DBUtil dbUtil;
    private String NAME;
    //private HttpConnSoap2 httpConnSoap2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);
        image= (ImageView) findViewById(R.id.updateuserImage);
        name= (EditText) findViewById(R.id.updateusertname);
        phone= (EditText) findViewById(R.id.updateuserPhone);
        email= (EditText) findViewById(R.id.updauserEmail);
        man= (RadioButton) findViewById(R.id.updaterb_man);
        woman= (RadioButton) findViewById(R.id.updaterb_wnam);
        Intent in=getIntent();
        NAME=in.getStringExtra("name");
        /*tt1=(TextView) findViewById(R.id.t1);
        tt2=(TextView) findViewById(R.id.t2);*/
        String[] a=new String[]{"username"};
        String[] b=new String[]{NAME};

        //httpConnSoap2=new HttpConnSoap2();
        dbUtil=new DBUtil();
        List<TUser> S=dbUtil.ShowUser(a,b);
        TUser tUser=S.get(0);
        TUser ts=S.get(1);
        Bitmap bitmap=dbUtil.stringToBitmap1(tUser.getPhoto());
        image.setImageBitmap(bitmap);
        name.setText(tUser.getUsername());
        if(tUser.getSex().equals("男")){
            man.setChecked(true);
            woman.setChecked(false);
        }else {
            man.setChecked(false);
            woman.setChecked(true);
        }
        phone.setText(tUser.getPhone());
        email.setText(tUser.getEmail());

    }

    public void fanhuiup(View view) {
    }

    public void subupdateonClik(View view) {
    }
}
