package com.example.wind.liberarymanege.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.wind.liberarymanege.R;

/**
 * Created by wind on 2018/3/31.
 */

public class BorrowBooks extends AppCompatActivity {
    private EditText name;
    private Button test;
    private AutoCompleteTextView asd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowbook);
        name= (EditText) findViewById(R.id.borrowbookname);
        test= (Button) findViewById(R.id.ttbbtest);

        asd= (AutoCompleteTextView) findViewById(R.id.auto_cp);
        asd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ting=asd.getText().toString();
                test.setText("i"+ting);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ting=name.getText().toString();
                test.setText("i"+ting);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }
}
