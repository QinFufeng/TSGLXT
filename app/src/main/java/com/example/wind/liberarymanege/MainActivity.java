package com.example.wind.liberarymanege;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout main1,type1,find1,user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main1= (LinearLayout) findViewById(R.id.main);
        type1= (LinearLayout) findViewById(R.id.type);
        find1= (LinearLayout) findViewById(R.id.find);
        user1= (LinearLayout) findViewById(R.id.user);
    }

    public void mainClick(View view) {
        //Toast.makeText(MainActivity.this,"点击main", Toast.LENGTH_SHORT).show();
        main1.setVisibility(View.VISIBLE);
        type1.setVisibility(View.GONE);
        find1.setVisibility(View.GONE);
        user1.setVisibility(View.GONE);
    }

    public void typeClick(View view) {
        main1.setVisibility(View.GONE);
        type1.setVisibility(View.VISIBLE);
        find1.setVisibility(View.GONE);
        user1.setVisibility(View.GONE);
    }

    public void findClick(View view) {
        main1.setVisibility(View.GONE);
        type1.setVisibility(View.GONE);
        find1.setVisibility(View.VISIBLE);
        user1.setVisibility(View.GONE);
    }

    public void userClick(View view) {
        main1.setVisibility(View.GONE);
        type1.setVisibility(View.GONE);
        find1.setVisibility(View.GONE);
        user1.setVisibility(View.VISIBLE);
    }
}
