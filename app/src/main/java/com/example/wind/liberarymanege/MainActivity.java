package com.example.wind.liberarymanege;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wind.liberarymanege.httpdb.DBUtil;

public class MainActivity extends AppCompatActivity {
    private LinearLayout main1,type1,find1,user1,Imeu1,Imeu2,Imeu3,Imeu4;
    private TextView tvUsername;
    private ImageView ivuser;
    private String NAME;
    private int g=0;
    Bitmap ii;
    final String[] dd=new String[1];
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:goImage();break;
                //Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();

                default:
                    Toast.makeText(MainActivity.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main1= (LinearLayout) findViewById(R.id.main);
        type1= (LinearLayout) findViewById(R.id.type);
        find1= (LinearLayout) findViewById(R.id.find);
        user1= (LinearLayout) findViewById(R.id.user);
        Imeu1= (LinearLayout) findViewById(R.id.meu1);
        Imeu2= (LinearLayout) findViewById(R.id.meu2);
        Imeu3= (LinearLayout) findViewById(R.id.meu3);
        Imeu4= (LinearLayout) findViewById(R.id.meu4);
        tvUsername=(TextView) findViewById(R.id.Tusername);
        ivuser=(ImageView)findViewById(R.id.userImage);

        Intent in=getIntent();
        NAME=in.getStringExtra("name");
        tvUsername.setText(NAME);

        final String [] a=new String[]{"username"};
        final String [] b=new String[]{NAME};
        new Thread(){
            @Override
            public void run() {
                DBUtil dbUtil=new DBUtil();
                dd[0] =dbUtil.UserImage(a,b);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }
        }.start();

    }

    public void mainClick(View view) {
        //Toast.makeText(MainActivity.this,"点击main", Toast.LENGTH_SHORT).show();
        main1.setVisibility(View.VISIBLE);
        type1.setVisibility(View.GONE);
        find1.setVisibility(View.GONE);
        user1.setVisibility(View.GONE);
        Imeu1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg1));
        Imeu2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
    }
    public void typeClick(View view) {
        main1.setVisibility(View.GONE);
        type1.setVisibility(View.VISIBLE);
        find1.setVisibility(View.GONE);
        user1.setVisibility(View.GONE);
        Imeu1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg1));
        Imeu3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
    }

    public void findClick(View view) {
        main1.setVisibility(View.GONE);
        type1.setVisibility(View.GONE);
        find1.setVisibility(View.VISIBLE);
        user1.setVisibility(View.GONE);
        Imeu1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg1));
        Imeu4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
    }

    public void userClick(View view) {
        main1.setVisibility(View.GONE);
        type1.setVisibility(View.GONE);
        find1.setVisibility(View.GONE);
        user1.setVisibility(View.VISIBLE);
        Imeu1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg2));
        Imeu4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bg1));
    }
    private void goImage(){
        Test t=new Test();
        Bitmap d=t.stringToBitmap1(dd[0]);

            ivuser.setImageBitmap(d);

        //tvUsername.setText(s);
    }

}
