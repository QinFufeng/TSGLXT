package com.example.wind.liberarymanege.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.example.wind.liberarymanege.R;

/**
 * Created by wind on 2017/12/20.
 */

public class SpashActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        countDownTimer.start();
    }
    CountDownTimer countDownTimer=new CountDownTimer(1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            //Intent intent=new Intent(SpashActivity.this,MainActivity.class);
            Intent intent=new Intent(SpashActivity.this,LoginActivity.class);
            startActivity(intent) ;
            SpashActivity.this.finish();
        }
    };
}
