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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;
import com.example.wind.liberarymanege.bean.BType;
import com.example.wind.liberarymanege.httpdb.DBUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by wind on 2018/3/30.
 */

public class BookAdd extends AppCompatActivity {
    private DBUtil dbU;
    private TextView tname,tauthor,tcount,tdesc,tlocation;
    private ImageView tphoto;
    private RadioButton aman;
    private Spinner addbooksp;
    private Button bt1,bt2,bt3;

    public static final int TP1=1;
    public static final int TP2=2;
    public static final int SHOWIMAGE=3;

    private String ttypeid;

    private Uri mCutUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookadd);

        tname= (TextView) findViewById(R.id.addbookname);
        tauthor= (TextView) findViewById(R.id.addbookauthor);
        tcount= (TextView) findViewById(R.id.addbookcount);
        tdesc= (TextView) findViewById(R.id.addbookdesc);
        tlocation= (TextView) findViewById(R.id.addbooklocation);
        tphoto= (ImageView) findViewById(R.id.addbookimg);
        aman= (RadioButton) findViewById(R.id.rb_man2);
        bt1= (Button) findViewById(R.id.addbookfanhui);
        bt2= (Button) findViewById(R.id.addbookquxiang);
        bt3= (Button) findViewById(R.id.addbookadd);

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
                ttypeid=tids[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addbookimage(View view) {
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
            imageuri = FileProvider.getUriForFile(BookAdd.this,
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
        if(ContextCompat.checkSelfPermission(BookAdd.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(BookAdd.this,new String[]{
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
                        tphoto.setImageBitmap(bitmap);
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
                imageUri = FileProvider.getUriForFile(BookAdd.this,
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

    public void addbookEnd(View view) {
        BookAdd.this.finish();
    }

    public void regbookEnd(View view) {
        tname.setText("");
        tauthor.setText("");
        tcount.setText("");
        tdesc.setText("");
        tlocation.setText("");
        tphoto.setImageResource(R.mipmap.addimg);
        aman.setChecked(true);
    }

    public void geAddbookto(View view) {
        String bname=tname.getText().toString();
        String bauthor=tauthor.getText().toString();
        String bsex="女";
        if(aman.isChecked())
        {
            bsex="男";
        }
        String bcount=tcount.getText().toString();
        String bdesc=tdesc.getText().toString();
        String btypeid=ttypeid;
        tphoto.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(dbU.convertViewToBitmap(tphoto));
        tphoto.setDrawingCacheEnabled(false);
        String uphoto=dbU.bitmapToBase64(bitmap);
        String blocation=tlocation.getText().toString();
        tdesc.setText(btypeid+uphoto);
    }
}
