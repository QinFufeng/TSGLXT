package com.example.wind.liberarymanege;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wind.liberarymanege.activity.AdminActivity;
import com.example.wind.liberarymanege.activity.LoginActivity;
import com.example.wind.liberarymanege.activity.ShowBookActivity;
import com.example.wind.liberarymanege.activity.UpdatePwdActivity;
import com.example.wind.liberarymanege.activity.UpdateUserActivity;
import com.example.wind.liberarymanege.bean.BType;
import com.example.wind.liberarymanege.bean.TBook;
import com.example.wind.liberarymanege.httpdb.DBUtil;
import com.example.wind.liberarymanege.httpdb.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private LinearLayout main1,type1,find1,user1,Imeu1,Imeu2,Imeu3,Imeu4;
    private TextView tvUsername,typedesc,admintxt,supadmintxt;
    private ImageView ivuser;
    private Spinner typesp;
    private ListView typelv2;
    private String NAME,rank;
    private int g=0;
    Bitmap ii;
    DBUtil dbU=new DBUtil();
    final String[] dd=new String[1];
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:goImage(msg.obj);break;
                //Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
                case 2:Mainshow(msg.obj);break;
                case 3:ShowTypebooks(msg.obj);break;
                case 4:findShow(msg.obj);break;
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

        admintxt=(TextView) findViewById(R.id.admintxt);
        supadmintxt=(TextView) findViewById(R.id.supadminxtx);

        Intent in=getIntent();
        NAME=in.getStringExtra("name");
        rank=in.getStringExtra("rank");
        tvUsername.setText(NAME);
        switch (rank)
        {
            case "3":supadmintxt.setVisibility(View.VISIBLE);
            case "2":admintxt.setVisibility(View.VISIBLE);break;
        }

        Mainshowdata();
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

        Mainshowdata();

        /*ListView list2= (ListView) findViewById(R.id.tvS);
        SimpleAdapter listItemAdapter1=new SimpleAdapter(
                this,
                getData(),
                R.layout.listitem,
                new String[]{"BImg","BName","BAuthor","BImg2","BName2","BAuthor2"},
                new int[]{R.id.BImg,R.id.BName,R.id.BAuthor});
                list1.setAdapter(listItemAdapter1);
        SimpleAdapter listItemAdapter2=new SimpleAdapter(
                this,
                getData(),
                R.layout.listitem,
                new String[]{"BImg","BName","BAuthor"},
                new int[]{R.id.BImg,R.id.BName,R.id.BAuthor});
        list2.setAdapter(listItemAdapter2);*/

    }
    private void Mainshowdata(){
        //ListView list1= (ListView) findViewById(R.id.lv);
        //ArrayList<Map<String,Object>> listitem=new ArrayList<Map<String,Object>>();
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj =dbU.getData();
                msg.what = 2;
                handler.sendMessage(msg);

            }
        }.start();
        //MyAdapter adapter = new MyAdapter(this,R.layout.listitem,dbU.getData());
        //list1.setAdapter(adapter);
    }

    private void Mainshow(Object obj){
        ListView list1= (ListView) findViewById(R.id.lv);
        ArrayList <Map<String,Object>> list= (ArrayList<Map<String, Object>>) obj;//dbU.getData();

        MyAdapter adapter = new MyAdapter(this,R.layout.listitem,list);
        list1.setAdapter(adapter);
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
        typedesc= (TextView) findViewById(R.id.type_desc);
        typesp= (Spinner) findViewById(R.id.type_spinner);
        ShowType();
    }

    public void ShowType(){
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
        typesp.setAdapter(adapter);
        typesp.setPrompt("请选择书籍类别:");
        //typesp.setSelection(0,true);
        typesp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                typedesc.setText("关于此类:"+tdescs[position]);
                final int dd=position;
                //ShowTypebooks(tids[position]);
                parent.setVisibility(View.VISIBLE);
                new Thread(){
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj =dbU.HuoquTypeBooks(tids[dd]);
                        msg.what = 3;
                        handler.sendMessage(msg);

                    }
                }.start();
                //ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void ShowTypebooks(Object obj){
        typelv2= (ListView) findViewById(R.id.lv2);
        LinearLayout tou= (LinearLayout) findViewById(R.id.typebookstou);
        tou.setVisibility(View.VISIBLE);
        SimpleAdapter listItemAdapter3=new SimpleAdapter(
                this,
                ShowTypebooksgetData(obj),
                R.layout.typebooks,
                new String[]{"BName","BAuthor","BCount"},
                new int[]{R.id.typebooksname,R.id.typebookszuozhe,R.id.typebookspice});
        typelv2.setAdapter(listItemAdapter3);
        typelv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> clkmap= (Map<String, Object>) parent.getItemAtPosition(position);
                //typedesc.setText("id is:"+clkmap.get("BId").toString());
                Intent intype=new Intent(MainActivity.this, ShowBookActivity.class);
                intype.putExtra("id",clkmap.get("BId").toString());
                startActivity(intype);
            }
        });
    }

    private List<Map<String,Object>> ShowTypebooksgetData(Object obj)
    {
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        //dbU=new DBUtil();
        //List<TBook> books=dbU.HuoquTypeBooks(tid);
        List<TBook> books= (List<TBook>) obj;
        int conut=books.size();
        for(int i=0;i<conut;i++){
            TBook m=books.get(i);
            //Bitmap bp=dbU.stringToBitmap1(m.getBphoto());
            if(i!=0){map=new HashMap<String,Object>();}
            //map.put("BId",m.getId());
            map.put("BName",m.getBname());
            map.put("BAuthor",m.getBauthor());
            map.put("BCount",m.getCount()+"本");
            map.put("BId",m.getId());

            list.add(map);
        }

        return list;
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

        final String [] a=new String[]{"username"};
        final String [] b=new String[]{NAME};
        new Thread(){
            @Override
            public void run() {
                //DBUtil dbUtil=new DBUtil();
                //dd[0] =dbUtil.UserImage(a,b);
                Message msg = new Message();
                msg.obj =dbU.UserImage(a,b);
                msg.what = 1;
                handler.sendMessage(msg);

            }
        }.start();
    }
    private void goImage(Object obj){
        //Test t=new Test();
        dd[0]= (String) obj;
        Bitmap d=dbU.stringToBitmap1(dd[0]);

            ivuser.setImageBitmap(d);

        //tvUsername.setText(s);
    }

    public void updatepwd(View view) {
        Intent intent=new Intent(MainActivity.this,UpdatePwdActivity.class);
        intent.putExtra("name",NAME);
        startActivity(intent) ;
    }

    public void updateuseronClick(View view) {
        Intent intent=new Intent(MainActivity.this,UpdateUserActivity.class);
        intent.putExtra("name",NAME);
        startActivity(intent) ;
    }

    public void findButtonClick(View view) {
        EditText et= (EditText) findViewById(R.id.findEt);
        final String fname=et.getText().toString();
        if(fname.equals("")||fname==null){
            Toast.makeText(MainActivity.this, "请输入查找关键字！", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj =dbU.HuoquFindBooks(fname);
                msg.what = 4;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void findShow(Object obj)
    {
        ListView findlv3 = (ListView) findViewById(R.id.lv3);
        List<TBook> books= (List<TBook>) obj;
        LinearLayout tou = (LinearLayout) findViewById(R.id.findbookstou);
        LinearLayout tou2 = (LinearLayout) findViewById(R.id.chabudaoshu);
        if(books.size()>0) {
            tou.setVisibility(View.VISIBLE);
            tou2.setVisibility(View.GONE);
            findlv3.setVisibility(View.VISIBLE);
            SimpleAdapter listItemAdapter4 = new SimpleAdapter(
                    this,
                    ShowTypebooksgetData(obj),
                    R.layout.typebooks,
                    new String[]{"BName", "BAuthor", "BCount"},
                    new int[]{R.id.typebooksname, R.id.typebookszuozhe, R.id.typebookspice});
            findlv3.setAdapter(listItemAdapter4);
            findlv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String, Object> clkmap = (Map<String, Object>) parent.getItemAtPosition(position);
                    //typedesc.setText("id is:"+clkmap.get("BId").toString());
                    Intent intype = new Intent(MainActivity.this, ShowBookActivity.class);
                    intype.putExtra("id", clkmap.get("BId").toString());
                    startActivity(intype);
                }
            });
        }
        else
        {
            tou.setVisibility(View.GONE);
            tou2.setVisibility(View.VISIBLE);
            findlv3.setVisibility(View.GONE);
        }
    }

    public void goadmin(View view) {
        Intent intent=new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    public void gosupadmin(View view) {
    }

    public void gologin(View view) {
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}
