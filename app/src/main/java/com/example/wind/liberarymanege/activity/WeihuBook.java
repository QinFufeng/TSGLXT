package com.example.wind.liberarymanege.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.TBook;
import com.example.wind.liberarymanege.httpdb.DBUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeihuBook extends AppCompatActivity {
    private DBUtil dbUtil;
    private ListView lv5;
    private List<TBook> tBooks;
    private TBook book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihubook);
        dbUtil=new DBUtil();
        lv5= (ListView) findViewById(R.id.bookweuhuLv);

        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj=dbUtil.HuoquBooks2();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:goshowbooks(msg.obj);break;
                case 2:goshowDelend(msg.obj);break;
                default:
                    Toast.makeText(WeihuBook.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private void goshowbooks(Object obj) {
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
        lv5.setAdapter(listItemAdapter);
        lv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> clkmap= (Map<String, Object>) parent.getItemAtPosition(position);
                book=tBooks.get(position);
                showDialog((Integer) clkmap.get("Bid"));
            }
        });
    }

    private List<Map<String,Object>> ShowbooksgetData(Object obj)
    {
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        //List<BType> types= (List<BType>) obj;
        tBooks= (List<TBook>) obj;
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

    protected Dialog onCreateDialog(int id)
    {
        final String tid=id+"";
        Dialog dialog=null;
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setIcon(R.mipmap.blank);
        b.setTitle("请选择操作");
        b.setItems(
                R.array.msc,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(AdminActivity.this, "您选择了"+getResources().getStringArray(R.array.msa)[which], Toast.LENGTH_LONG).show();
                        if(which==0) {
                            //Toast.makeText(WeihuBook.this, "您选择了"+getResources().getStringArray(R.array.msc)[which]+book.getLocation(), Toast.LENGTH_LONG).show();
                            BookDel(tid);
                        }
                        else {
                            //Toast.makeText(WeihuBook.this, book.getBname(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(WeihuBook.this, BookAdd.class);
                            intent.putExtra("book",book);
                            //startActivity(intent);
                            startActivityForResult(intent,1);
                        }

                    }
                }
        );
        dialog=b.create();

        return dialog;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    new Thread(){
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.obj=dbUtil.HuoquBooks2();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }.start();
                }
                break;
            default:
        }
    }

    private void BookDel(String tid) {
        final String id=tid;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("是否删除！")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //commit();
                        //RegisterActivity.this.finish();
                        new Thread(){
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.obj=dbUtil.BookDelBool(id);
                                msg.what = 2;
                                handler.sendMessage(msg);
                            }
                        }.start();
                    }
                }).setNegativeButton("取消", null);;
        builder.create().show();
    }

    private void goshowDelend(Object obj) {
        String bool = (String) obj;
        if (bool.equals("true")) {
            Toast.makeText(WeihuBook.this, "删除成功！", Toast.LENGTH_LONG).show();
        } else if (bool.equals("false")) {
            Toast.makeText(WeihuBook.this, "删除失败！", Toast.LENGTH_LONG).show();
        } else if (bool.equals("oxb004")) {
            Toast.makeText(WeihuBook.this, "删除失败！此书已有借出！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(WeihuBook.this, "连接错误！", Toast.LENGTH_LONG).show();
        }
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj=dbUtil.HuoquBooks2();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
