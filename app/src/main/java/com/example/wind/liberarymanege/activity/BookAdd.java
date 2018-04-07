package com.example.wind.liberarymanege.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.BType;
import com.example.wind.liberarymanege.httpdb.DBUtil;

import java.util.List;

/**
 * Created by wind on 2018/3/30.
 */

public class BookAdd extends AppCompatActivity {
    private DBUtil dbU;
    private Spinner addbooksp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookadd);

        addbooksp= (Spinner) findViewById(R.id.addbookspinner);
        ShowType();
    }

    private void ShowType(){
        dbU=new DBUtil();
        List<BType> bTypes=dbU.HuoquBTypes();
        int typecount=bTypes.size();

        final String []tids=new String[typecount];
        final String [] tnames=new String[typecount];
        final String [] tdescs=new String[typecount];
        for(int i=0;i<typecount;i++)
        {
            BType bType=bTypes.get(i);
            tids[i]=bType.getTid()+"";
            tnames[i]=bType.getTname();
            tdescs[i]=bType.getTdesc();
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                tnames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addbooksp.setAdapter(adapter);
        addbooksp.setPrompt("请选择书籍类别:");
        //typesp.setSelection(0,true);
        addbooksp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                parent.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
