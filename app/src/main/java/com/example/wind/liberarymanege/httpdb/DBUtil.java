package com.example.wind.liberarymanege.httpdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.wind.liberarymanege.bean.TUser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wind on 2017/12/21.
 */

public class DBUtil {
    private HttpConnSoap httpConnSoap;
    private HttpConnSoap2 httpConnSoap2;
    //登陆验证
    public int Login(String[] par1,String[] par2){
        //boolean cc=false;
        int c=2;
        httpConnSoap=new HttpConnSoap();
        //String[] es=httpConnSoap.HttpGo(par1,par2,"IsMatchUser");
        String[] es=new String[]{""};
        es=httpConnSoap.HttpGo(par1,par2,"IsMatchUser");
        //String es=se.getProperty(0).toString();
        if(es[0].equals("0"))
        {
            //Toast.makeText(LoginActivity.class, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
            c=0;
        }else if(es[0].equals("1")){
            c=1;
        }
        //cc=Boolean.parseBoolean(es);
        return c;
    }
    public int Register(String[] par1,String[] par2){
        int c=3;
        httpConnSoap=new HttpConnSoap();
        String[] es=new String[]{""};
        es=httpConnSoap.HttpGo(par1,par2,"IsRegister");
        if(es[0].equals("0"))
        {
            c=0;
        }
        else if(es[0].equals("1")){
            c=1;
        }
        else if(es[0].equals("2")){
            c=2;
        }
        //cc=Boolean.parseBoolean(es);
        return c;
    }

    public int Updatepwd(String[] par1,String[] par2){
        int c=3;
        httpConnSoap=new HttpConnSoap();
        String[] es=new String[]{""};
        es=httpConnSoap.HttpGo(par1,par2,"IsUpdatePwd");
        if(es[0].equals("0"))
        {
            c=0;
        }
        else if(es[0].equals("1")){
            c=1;
        }
        else if(es[0].equals("2")){
            c=2;
        }
        //cc=Boolean.parseBoolean(es);
        return c;
    }

    public String UserImage(String[] par1, String[] par2) {
        httpConnSoap=new HttpConnSoap();
        String[] es=httpConnSoap.HttpGo(par1,par2,"IsShowUser");
        return es[5];
    }
    public Bitmap stringToBitmap1(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bytes = org.apache.ws.commons.util.Base64.decode(string);
            //YuvImage yuvImage=new YuvImage(bytes, ImageFormat.NV21,200,200,null);
            //ByteArrayOutputStream boos=new ByteArrayOutputStream();
            //yuvImage.compressToJpeg(new Rect(0,0,0,0),80,boos);
            //byte[] b=boos.toByteArray();
            bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        } catch (org.apache.ws.commons.util.Base64.DecodingException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
    public List<TUser> ShowUser(String[] par1, String[] par2){
        httpConnSoap2=new HttpConnSoap2();
        List<TUser> list=new ArrayList<>();
        SoapObject primitive=httpConnSoap2.HttpGo(par1,par2,"IsShowUser2");
        for(int i=0;i<primitive.getPropertyCount();i++){
                    SoapObject mstr= (SoapObject) primitive.getProperty(i);

                    String username=mstr.getProperty(1).toString();
                    String userpwd=mstr.getProperty(2).toString();
                    String sex=mstr.getProperty(3).toString();
                    String phone=mstr.getProperty(4).toString();
                    String email=mstr.getProperty(5).toString();
                    String photo=mstr.getProperty(6).toString();
                    //String sss=username+userpwd+sex+phone+email+photo;
                    //strings[i]=sss;

                    //ttt[i]=new TUser(username,userpwd,sex,phone,email,photo);
                    TUser tUser=new TUser();
                    tUser.setUsername(username);
                    tUser.setUserpassword(userpwd);
                    tUser.setSex(sex);
                    tUser.setPhone(phone);
                    tUser.setEmail(email);
                    tUser.setPhoto(photo);

                    //list.add(ttt[i]);
                    list.add(tUser);
                }
        return list;
    }
}
