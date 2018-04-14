package com.example.wind.liberarymanege.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.TBook;
import com.example.wind.liberarymanege.bean.TUser;
import com.example.wind.liberarymanege.httpdb.DBUtil;
import com.example.wind.liberarymanege.httpdb.RegExpValidatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wind on 2018/3/31.
 */

public class BorrowBooks extends AppCompatActivity {
    private EditText uname,bname,jcount;
    private DBUtil dbUtil;
    private String uid="";
    private String bid="";
    String s1="",s2="";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:gouserlist(msg.obj);break;
                case 2:gobooklist(msg.obj);break;
                case 3:godate(msg.obj);break;
                default:
                    Toast.makeText(BorrowBooks.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowbook);
        dbUtil=new DBUtil();
        uname= (EditText) findViewById(R.id.borrowbookUsername);
        bname= (EditText) findViewById(R.id.borrowbookBookname);
        jcount= (EditText) findViewById(R.id.borrowbookBookday);

        uname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ting=uname.getText().toString();
                if(s1.equals(ting))
                {
                    return;
                }else {
                    final String[] aa = new String[]{"username"};
                    final String[] bb = new String[]{ting};
                    new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.obj = dbUtil.Likeuser(aa, bb);
                            msg.what = 1;
                            handler.sendMessage(msg);

                        }
                    }.start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ting=bname.getText().toString();
                if(s2.equals(ting))
                {
                    return;
                }else {
                    final String[] aa = new String[]{"bname"};
                    final String[] bb = new String[]{ting};
                    new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.obj = dbUtil.Likebook(aa, bb);
                            msg.what = 2;
                            handler.sendMessage(msg);

                        }
                    }.start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void gouserlist(Object obj) {
        ListView lv4= (ListView) findViewById(R.id.lv4);
        SimpleAdapter listItemAdapter=new SimpleAdapter(
                this,
                ShowusersgetData(obj),
                R.layout.likeuserlist,
                new String[]{"Uname","Usex","Uphone","Uphoto"},
                new int[]{R.id.user_name,R.id.user_sex,R.id.user_phone,R.id.user_image});
        listItemAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap)
                {
                    ImageView iv= (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        lv4.setAdapter(listItemAdapter);
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> clkmap= (Map<String, Object>) parent.getItemAtPosition(position);
                s1=(String)clkmap.get("Uname");
                uname.setText((String)clkmap.get("Uname"));
                uid=(int)clkmap.get("Uid")+"";
            }
        });
    }
    private List<Map<String,Object>> ShowusersgetData(Object obj)
    {
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        //List<BType> types= (List<BType>) obj;
        List<TUser> users= (List<TUser>) obj;
        int conut=users.size();
        for(int i=0;i<conut;i++){
            TUser m=users.get(i);
            if(i!=0){map=new HashMap<String,Object>();}
            map.put("Uid",m.getId());
            map.put("Uname",m.getUsername());
            map.put("Usex",m.getSex());
            map.put("Uphone",m.getPhone());
            Bitmap bitmap=dbUtil.stringToBitmap1(m.getPhoto());
            map.put("Uphoto",bitmap);
            list.add(map);
        }
        return list;
    }
    private void gobooklist(Object obj) {
        ListView lv4= (ListView) findViewById(R.id.lv4);
        SimpleAdapter listItemAdapter=new SimpleAdapter(
                this,
                ShowbooksgetData(obj),
                R.layout.weihubooklist,
                new String[]{"Bname","Bauthor","Bcount","Btype","Bphoto"},
                new int[]{R.id.bookweihuname,R.id.bookweihuauthor,R.id.bookweihucount,R.id.bookweihutype,R.id.bookweihuimg});
        listItemAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap)
                {
                    ImageView iv= (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        lv4.setAdapter(listItemAdapter);
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> clkmap= (Map<String, Object>) parent.getItemAtPosition(position);
                int c=(int) clkmap.get("Bcount");
                //Toast.makeText(BorrowBooks.this, "该书尚无库存！"+c, Toast.LENGTH_SHORT).show();
                if(c<1) {
                    Toast.makeText(BorrowBooks.this, "该书尚无库存！", Toast.LENGTH_SHORT).show();
                }else {
                    s2=(String) clkmap.get("Bname");
                    bname.setText((String) clkmap.get("Bname"));
                    bid = (int) clkmap.get("Bid") + "";
                }
            }
        });
    }

    private List<Map<String,Object>> ShowbooksgetData(Object obj)
    {
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        List<TBook> tBooks= (List<TBook>) obj;
        int conut=tBooks.size();
        for(int i=0;i<conut;i++){
            TBook m=tBooks.get(i);
            if(i!=0){map=new HashMap<String,Object>();}
            map.put("Bid",m.getId());
            map.put("Bname",m.getBname());
            map.put("Bauthor",m.getBauthor());
            map.put("Bcount",m.getCount());
            map.put("Btype",m.getBtype());
            Bitmap bitmap=dbUtil.stringToBitmap1(m.getBphoto());
            map.put("Bphoto",bitmap);
            list.add(map);
        }
        return list;
    }

    public void borrowbooksend(View view) {
        BorrowBooks.this.finish();
    }

    public void borrowbookssub(View view) {
        String day=jcount.getText().toString();
        //String username=uname.getText().toString();
        //String bookname=bname.getText().toString();
        if(uid.equals("") || uid==null)
        {
            Toast.makeText(BorrowBooks.this,"请选择借书人！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bid.equals("")|| bid==null){
            Toast.makeText(BorrowBooks.this,"请选择要借的图书！", Toast.LENGTH_SHORT).show();
            return;
        }
        RegExpValidatorUtils regExpValidatorUtils=new RegExpValidatorUtils();
        if(!regExpValidatorUtils.IsIntNumber(day)){
            Toast.makeText(BorrowBooks.this,"天数错误", Toast.LENGTH_SHORT).show();
            return;
        }
        final String [] aa={"uid","bid","day"};
        final String [] bb={uid,bid,day};
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj =dbUtil.JieBook(aa,bb);
                msg.what = 3;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void godate(Object obj){
        String c= (String) obj;
        if(c.equals("true")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("借书成功！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //commit();
                            BorrowBooks.this.finish();

                        }
                    });
            builder.create().show();
        }
        else if(c.equals("false")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("借书失败！")
                    .setPositiveButton("确认",null);
            builder.create().show();
        }else if(c.equals("ox7001")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("用户未还此书！")
                    .setPositiveButton("确认",null);
            builder.create().show();
        }
        else if(c.equals("ox6001")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("该书无库存！")
                    .setPositiveButton("确认",null);
            builder.create().show();
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("网络连接错误！")
                    .setPositiveButton("确认", null);
            builder.create().show();
        }
    }
}
