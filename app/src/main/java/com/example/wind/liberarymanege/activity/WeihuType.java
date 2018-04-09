package com.example.wind.liberarymanege.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.BType;
import com.example.wind.liberarymanege.httpdb.DBUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wind on 2018/3/21.
 */

public class WeihuType extends AppCompatActivity {
    DBUtil dbUtil=new DBUtil();
    BType type;
    List<BType> types;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:goshowtypes(msg.obj);break;
                //Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
                case 2:goshowDelend(msg.obj);break;
                default:
                    Toast.makeText(WeihuType.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private void goshowtypes(Object obj) {
        ListView list= (ListView) findViewById(R.id.typeweihulist);
        SimpleAdapter listItemAdapter=new SimpleAdapter(
                this,
                ShowTypebooksgetData(obj),
                R.layout.weihulist,
                new String[]{"Tname","Tdesc"},
                new int[]{R.id.weihuItname,R.id.weihuItdesc});
        list.setAdapter(listItemAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> clkmap= (Map<String, Object>) parent.getItemAtPosition(position);
                /*Intent intype=new Intent(WeihuType.this, TypeAdd.class);
                intype.putExtra("Tid",clkmap.get("Tid").toString());
                startActivity(intype);*/
                type=types.get(position);
                showDialog((Integer) clkmap.get("Tid"));
            }
        });
    }

    private List<Map<String,Object>> ShowTypebooksgetData(Object obj)
    {
        ArrayList<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        //List<BType> types= (List<BType>) obj;
        types= (List<BType>) obj;
        int conut=types.size();
        for(int i=0;i<conut;i++){
            BType m=types.get(i);
            if(i!=0){map=new HashMap<String,Object>();}
            map.put("Tname",m.getTname());
            map.put("Tdesc",m.getTdesc());
            map.put("Tid",m.getTid());

            list.add(map);
        }

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihu);


        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj=dbUtil.HuoquBTypes();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
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
                                    Toast.makeText(WeihuType.this, "您选择了"+getResources().getStringArray(R.array.msc)[which], Toast.LENGTH_LONG).show();
                                    TypeDel(tid);
                                }
                                else {
                                    Intent intent = new Intent(WeihuType.this, TypeAdd.class);
                                    intent.putExtra("type",type);
                                    startActivity(intent);
                                }

                            }
                        }
                );
                dialog=b.create();

        return dialog;
    }

    private void TypeDel(String tid) {
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
                                msg.obj=dbUtil.TypeDelBool(id);
                                msg.what = 2;
                                handler.sendMessage(msg);
                            }
                        }.start();
                    }
                }).setNegativeButton("取消", null);;
        builder.create().show();
    }

    private void goshowDelend(Object obj)
    {
        String bool= (String) obj;
        if(bool.equals("true")) {
            Toast.makeText(WeihuType.this, "删除成功！", Toast.LENGTH_LONG).show();
        }
        else if(bool.equals("false")) {
            Toast.makeText(WeihuType.this, "删除失败！", Toast.LENGTH_LONG).show();
        }
        else if(bool.equals("ox004")) {
            Toast.makeText(WeihuType.this, "删除失败！此类别还有书籍！", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(WeihuType.this, "连接错误！", Toast.LENGTH_LONG).show();
        }
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj=dbUtil.HuoquBTypes();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void weihutypeend(View view) {
        WeihuType.this.finish();
    }
}
