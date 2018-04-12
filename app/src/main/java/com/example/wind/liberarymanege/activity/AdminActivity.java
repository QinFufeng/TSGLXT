package com.example.wind.liberarymanege.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wind.liberarymanege.R;

/**
 * Created by wind on 2018/3/20.
 */

public class AdminActivity extends AppCompatActivity {
    final int typeId=1;
    final int bookId=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button bt1= (Button) findViewById(R.id.typeGl);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(typeId);
            }
        });

        Button bt2= (Button) findViewById(R.id.bookGl);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(bookId);
            }
        });
    }
    protected Dialog onCreateDialog(int id)
    {
        Dialog dialog=null;
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        switch (id)
        {
            case 1:
                b.setIcon(R.mipmap.blank);
                b.setTitle("请选择操作");
                b.setItems(
                        R.array.msa,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(AdminActivity.this, "您选择了"+getResources().getStringArray(R.array.msa)[which], Toast.LENGTH_LONG).show();
                                if(which==0) {
                                    Intent intent = new Intent(AdminActivity.this, TypeAdd.class);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent(AdminActivity.this, WeihuType.class);
                                    startActivity(intent);
                                }

                            }
                        }
                );
                dialog=b.create();
                break;
            case 2:
                b.setIcon(R.mipmap.splash1);
                b.setTitle("请选择操作2");
                b.setItems(
                        R.array.msb,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(AdminActivity.this, "您选择了"+getResources().getStringArray(R.array.msb)[which], Toast.LENGTH_LONG).show();
                                if(which==0){
                                    Intent intent=new Intent(AdminActivity.this,BookAdd.class);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent=new Intent(AdminActivity.this,WeihuBook.class);
                                    startActivity(intent);
                                }
                            }
                        }
                );
                dialog=b.create();
                break;
        }
        return dialog;
    }

    public void goBorrowBooks(View view) {
        Intent intent=new Intent(AdminActivity.this,BorrowBooks.class);
        startActivity(intent);
    }

    public void gohuanshuCk(View view) {
        Intent intent=new Intent(AdminActivity.this,HuanshuActivity.class);
        startActivity(intent);
    }

    public void goxujieshu(View view) {
        Intent intent=new Intent(AdminActivity.this,Xujieshu.class);
        startActivity(intent);
    }
}
