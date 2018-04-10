package com.example.wind.liberarymanege.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.TUser;
import com.example.wind.liberarymanege.httpdb.DBUtil;
import com.example.wind.liberarymanege.httpdb.RegExpValidatorUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by wind on 2018/1/10.
 */

public class UpdateUserActivity extends AppCompatActivity {
    private ImageView image;
    private EditText name,phone,email;
    private RadioButton man,woman;
    private Button bt1,bt2;
    private DBUtil dbUtil;
    private String NAME;
    private int id=0;

    public static final int TP1=1;
    public static final int TP2=2;
    public static final int SHOWIMAGE=3;

    private Uri mCutUri;
    //private HttpConnSoap2 httpConnSoap2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);
        image = (ImageView) findViewById(R.id.updateuserImage);
        name = (EditText) findViewById(R.id.updateusertname);
        phone = (EditText) findViewById(R.id.updateuserPhone);
        email = (EditText) findViewById(R.id.updauserEmail);
        man = (RadioButton) findViewById(R.id.updaterb_man);
        woman = (RadioButton) findViewById(R.id.updaterb_wnam);
        bt1= (Button) findViewById(R.id.bn_resetupdate_user);
        bt2= (Button) findViewById(R.id.bn_subupdate_user);
        Intent in = getIntent();
        NAME = in.getStringExtra("name");
        /*tt1=(TextView) findViewById(R.id.t1);
        tt2=(TextView) findViewById(R.id.t2);*/
        final String[] a = new String[]{"username"};
        final String[] b = new String[]{NAME};
        dbUtil = new DBUtil();
        //httpConnSoap2=new HttpConnSoap2();
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = dbUtil.ShowUser(a, b);
                msg.what = 1;
                handler.sendMessage(msg);

            }
        }.start();
    }
    public void setdate(Object obj){
        List<TUser> S= (List<TUser>) obj;
        TUser tUser=S.get(0);
        TUser ts=S.get(1);
        id=ts.getId();

        setEnB();
        Bitmap bitmap=dbUtil.stringToBitmap1(tUser.getPhoto());
        image.setImageBitmap(bitmap);
        name.setText(tUser.getUsername());
        if(tUser.getSex().equals("男")){
            man.setChecked(true);
            woman.setChecked(false);
        }else {
            man.setChecked(false);
            woman.setChecked(true);
        }
        phone.setText(tUser.getPhone());
        email.setText(tUser.getEmail());
    }

    public void fanhuiup(View view) {
        UpdateUserActivity.this.finish();
    }

    public void subupdateonClik(View view) {
        if(id==0)
        {
            UpdateUserActivity.this.finish();
        }
        String uname=name.getText().toString();
        String usex="男";
        if(woman.isChecked())
        {
            usex="女";
        }
        String uid=id+"";
        String uphone=phone.getText().toString();
        String uemail=email.getText().toString();
        image.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(dbUtil.convertViewToBitmap(image));
        image.setDrawingCacheEnabled(false);
        String uphoto=dbUtil.bitmapToBase64(bitmap);
        if(uname.equals("")|| uname==null){
            Toast.makeText(UpdateUserActivity.this,"用户名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        RegExpValidatorUtils regExpValidatorUtils=new RegExpValidatorUtils();
        if(!regExpValidatorUtils.IsHandset(uphone)){
            Toast.makeText(UpdateUserActivity.this,"手机号码有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!regExpValidatorUtils.isEmail(uemail)){
            Toast.makeText(UpdateUserActivity.this,"邮箱有误", Toast.LENGTH_SHORT).show();
            return;
        }
        //image.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        man.setEnabled(false);
        woman.setEnabled(false);
        bt1.setEnabled(false);
        bt2.setEnabled(false);

        final String[] aa=new String[]{"id","username","sex","phone","email","imga"};
        final String[] bb=new String[]{uid,uname,usex,uphone,uemail,uphoto};
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj =dbUtil.Updateuser(aa,bb);
                msg.what = 2;
                handler.sendMessage(msg);

            }
        }.start();
    }

    private void godate(Object obj){
        String c= (String) obj;
        //TextView tt= (TextView) findViewById(R.id.testtxt);
        //tt.setText(c);
        if(c.equals("true")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("修改成功！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //commit();
                            Intent intent=new Intent();
                            intent.putExtra("name",name.getText().toString());
                            setResult(RESULT_OK,intent);
                            UpdateUserActivity.this.finish();
                        }
                    });
            builder.create().show();
            setEnB();
        }
        else if(c.equals("false")){
            Toast.makeText(UpdateUserActivity.this,"修改失败！", Toast.LENGTH_SHORT).show();
            setEnB();
        }
        else if(c.equals("xo5001")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("该用户名已存在！")
                    .setPositiveButton("确认",null);
            builder.create().show();
            setEnB();
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("网络连接错误！")
                    .setPositiveButton("确认", null);
            builder.create().show();
            setEnB();
        }
    }

    private void setEnB() {
        //image.setEnabled(true);
        name.setEnabled(true);
        phone.setEnabled(true);
        email.setEnabled(true);
        man.setEnabled(true);
        woman.setEnabled(true);
        bt1.setEnabled(true);
        bt2.setEnabled(true);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:setdate(msg.obj);break;
                case 2:godate(msg.obj);break;
                default:
                    Toast.makeText(UpdateUserActivity.this, "程序出错！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    /*public Bitmap convertViewToBitmap(View view){
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }*/

    public void setuserimg(View view) {
        showDialog(1);
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
                        R.array.setimg,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(AdminActivity.this, "您选择了"+getResources().getStringArray(R.array.msa)[which], Toast.LENGTH_LONG).show();
                                if(which==0) {
                                    showCamera();
                                }
                                else {
                                    showPhoto();
                                }

                            }
                        }
                );
                dialog=b.create();
                break;
        }
        return dialog;
    }

    private void showCamera() {
        //创建一个file，用来存储拍照后的照片
        File outputfile = new File(getExternalCacheDir(),"output.png");
        try {
            if (outputfile.exists()){
                outputfile.delete();//删除
            }
            outputfile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageuri ;
        if (Build.VERSION.SDK_INT >= 24){
            imageuri = FileProvider.getUriForFile(UpdateUserActivity.this,
                    "com.wind.updatauser.userimage", //可以是任意字符串
                    outputfile);
        }else{
            imageuri = Uri.fromFile(outputfile);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
        startActivityForResult(intent,TP1);
    }

    private void showPhoto() {
        if(ContextCompat.checkSelfPermission(UpdateUserActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(UpdateUserActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }else
        {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,TP2);
    }

    public void onRequestPermissionsResult(int requestCode,String [] permissions,int [] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    openAlbum();
                }else
                {
                    Toast.makeText(this,"你否认了许可",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case TP2: //从相册图片后返回的uri
                    //启动裁剪
                    startActivityForResult(CutForPhoto(data.getData()),SHOWIMAGE);
                    break;
                case TP1: //相机返回的 uri
                    //启动裁剪
                    String path = this.getExternalCacheDir().getPath();
                    String name = "output.png";
                    startActivityForResult(CutForCamera(path,name),SHOWIMAGE);
                    /*tt.setText("daici");
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri));
                        iv_photo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }*/
                    //startActivityForResult(CutForPhoto(imageFileUri),3);
                    break;
                case SHOWIMAGE:
                    try {
                        //获取裁剪后的图片，并显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mCutUri));
                        image.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * 图片裁剪
     * @param uri
     * @return
     */
    @NonNull
    private Intent CutForPhoto(Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            //Log.d(TAG, "CutForPhoto: "+cutfile);
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            //Log.d(TAG, "mCameraUri: "+mCutUri);
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",3);
            intent.putExtra("aspectY",4);
            //设置要裁剪的宽高
            intent.putExtra("outputX", 240); //200dp
            intent.putExtra("outputY",320);
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 拍照之后，启动裁剪
     * @param camerapath 路径
     * @param imgname img 的名字
     * @return
     */
    @NonNull
    private Intent CutForCamera(String camerapath,String imgname) {
        try {

            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = null; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            Intent intent = new Intent("com.android.camera.action.CROP");
            //拍照留下的图片
            File camerafile = new File(camerapath,imgname);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageUri = FileProvider.getUriForFile(UpdateUserActivity.this,
                        "com.wind.updatauser.userimage",
                        camerafile);
            } else {
                imageUri = Uri.fromFile(camerafile);
            }
            outputUri = Uri.fromFile(cutfile);
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",3);
            intent.putExtra("aspectY",4);
            //设置要裁剪的宽高
            intent.putExtra("outputX", 240);
            intent.putExtra("outputY",320);
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
