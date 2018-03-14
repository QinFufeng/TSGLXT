package com.example.wind.liberarymanege.httpdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.wind.liberarymanege.bean.TBook;
import com.example.wind.liberarymanege.bean.TUser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wind on 2017/12/21.
 */

public class DBUtil {
    private HttpConnSoap httpConnSoap;
    private HttpConnSoap2 httpConnSoap2;
    private BookHttpConnSoap bookHttpConnSoap;
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

    public ArrayList<Map<String,Object>> getData(){
        ArrayList <Map<String,Object>> listitem=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();

        /*String [] a={"username"};
        String [] b={"aa"};
        String dd=UserImage(a,b);
        Bitmap d=stringToBitmap1(dd);*/

        List<TBook> lb=ShowBooks();
        int conut=lb.size();
        for(int i=0;i<conut;i++){
            TBook m=lb.get(i);
            Bitmap bp=stringToBitmap1(m.getBphoto());
            if(i!=0){map=new HashMap<String,Object>();}
            map.put("BImg",bp);
            map.put("BName",m.getBname());
            map.put("BAuthor",m.getBauthor());
            map.put("BPice",m.getBprice()+"元");

            listitem.add(map);
        }

        return listitem;
    }

    public List<TBook> ShowBooks(){
        bookHttpConnSoap=new BookHttpConnSoap();

        List<TBook> list=new ArrayList<>();

        //httpConnSoap2=new HttpConnSoap2();
        //String [] a={"username"};String [] b={"aa"};
        //String [] a={""};String [] b={""};
        //SoapObject primitive=httpConnSoap2.HttpGo(a,b,"IsShowUser2");
        SoapObject primitive=bookHttpConnSoap.HttpGo("IsShowBook");

        for(int i=0;i<primitive.getPropertyCount();i++){
            SoapObject mstr= (SoapObject) primitive.getProperty(i);

            int id= Integer.parseInt(mstr.getProperty(0).toString()) ;
            String bname=mstr.getProperty(1).toString();
            String bauthor=mstr.getProperty(2).toString();
            double bprice= Double.parseDouble(mstr.getProperty(3).toString()) ;
            int btype= Integer.parseInt(mstr.getProperty(4).toString()) ;
            String bphoto=mstr.getProperty(5).toString();

            TBook tbook=new TBook(id,bname,bauthor,bprice,btype,bphoto);
            //TBook tbook=new TBook(1,"ddd","pppp",10.5,123,"qqqq");
            list.add(tbook);
        }
        return list;
    }
}
