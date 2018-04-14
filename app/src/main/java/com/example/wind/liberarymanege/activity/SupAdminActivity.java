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
import com.example.wind.liberarymanege.bean.TUser;
import com.example.wind.liberarymanege.httpdb.DBUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupAdminActivity extends AppCompatActivity {
    private EditText uname;
    private DBUtil dbUtil;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:gouserlist(msg.obj);break;
                case 2:goshowDelend(msg.obj);break;
                case 3://goshowday(msg.obj);break;
                case 4://godate(msg.obj);break;
                default:
                    Toast.makeText(SupAdminActivity.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supadmin);
        dbUtil=new DBUtil();
        uname= (EditText) findViewById(R.id.supalookuser);

        final String[] aa = new String[]{"username"};
        final String[] bb = new String[]{""};
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = dbUtil.Likeuser(aa, bb);
                msg.what = 1;
                handler.sendMessage(msg);

            }
        }.start();

        uname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ting=uname.getText().toString();
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

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void gouserlist(Object obj) {
        ListView lv7= (ListView) findViewById(R.id.supaLv);
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
        lv7.setAdapter(listItemAdapter);
        lv7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> clkmap= (Map<String, Object>) parent.getItemAtPosition(position);
                showDialog((int) clkmap.get("Uid"));
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

    protected Dialog onCreateDialog(int id)
    {
        final String uid=id+"";
        Dialog dialog=null;
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setIcon(R.mipmap.blank);
        b.setTitle("请选择操作");
        b.setItems(
                R.array.msd,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0) {
                            //Toast.makeText(WeihuBook.this, "您选择了"+getResources().getStringArray(R.array.msc)[which]+book.getLocation(), Toast.LENGTH_LONG).show();
                            BookDel(uid);
                        }
                        else {
                            Intent intent = new Intent(SupAdminActivity.this, SetAdmin.class);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                            //startActivityForResult(intent,1);
                        }
                    }
                }
        );
        dialog=b.create();
        return dialog;
    }

    private void BookDel(String uid) {
        final String id=uid;
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
                                msg.obj=dbUtil.UserDelBool(id);
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
            Toast.makeText(SupAdminActivity.this, "删除成功！", Toast.LENGTH_LONG).show();
        } else if (bool.equals("false")) {
            Toast.makeText(SupAdminActivity.this, "删除失败！", Toast.LENGTH_LONG).show();
        } else if (bool.equals("oxb004")) {
            Toast.makeText(SupAdminActivity.this, "删除失败！此人有借书！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SupAdminActivity.this, "连接错误！", Toast.LENGTH_LONG).show();
        }
        final String[] aa = new String[]{"username"};
        final String[] bb = new String[]{""};
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
