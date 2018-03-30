package com.example.wind.liberarymanege;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by wind on 2018/1/7.
 */

public class Test extends AppCompatActivity {
    private TextView txt;
    private static final int it1= Menu.FIRST;
    private static final int it2= Menu.FIRST+1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        txt= (TextView) findViewById(R.id.t01);
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuItem b=menu.add(0,it1,0,"开始");
        b.setIcon(R.mipmap.blank);
        MenuItem b2=menu.add(0,it2,0,"fanhui1");
        MenuItem.OnMenuItemClickListener lsn=new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case 1:txt.setText("asd");break;
                    case 2:txt.setText("asd2");break;
                }
                return true;
            }
        };
        b.setOnMenuItemClickListener(lsn);
        b2.setOnMenuItemClickListener(lsn);
        return true;
    }

}
