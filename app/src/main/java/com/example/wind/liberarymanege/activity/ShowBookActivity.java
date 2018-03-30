package com.example.wind.liberarymanege.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.TBook;
import com.example.wind.liberarymanege.httpdb.DBUtil;

/**
 * Created by wind on 2018/3/14.
 */

public class ShowBookActivity extends AppCompatActivity {
    private String ID;
    private TextView name,zuozhe,sex,type,count,desc,location;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbook);
        Intent in=getIntent();
        ID=in.getStringExtra("id");

        name= (TextView) findViewById(R.id.showbookname);
        zuozhe= (TextView) findViewById(R.id.showbookzuozhe);
        sex= (TextView) findViewById(R.id.showbooksex);
        type= (TextView) findViewById(R.id.showbooktype);
        count= (TextView) findViewById(R.id.showbookcount);
        desc= (TextView) findViewById(R.id.showbookdesc);
        img= (ImageView) findViewById(R.id.showbookimg);
        location= (TextView) findViewById(R.id.showbooklocation);
        //name.setText(ID);
        DBUtil dbUtil=new DBUtil();
        TBook book=dbUtil.HuoquBook(ID);
        img.setImageBitmap(dbUtil.stringToBitmap1(book.getBphoto()));
        name.setText(book.getBname());
        zuozhe.setText(book.getBauthor());
        sex.setText(book.getBsex());
        type.setText(book.getBtype());
        count.setText("数量:"+book.getCount());
        desc.setText(book.getBdesc());
        location.setText(book.getLocation());
    }

    public void showbookEnd(View view) {
        ShowBookActivity.this.finish();
    }
}

