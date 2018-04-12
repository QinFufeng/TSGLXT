package com.example.wind.liberarymanege.httpdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;

import com.example.wind.liberarymanege.bean.BType;
import com.example.wind.liberarymanege.bean.TBook;
import com.example.wind.liberarymanege.bean.TUser;

import org.ksoap2.serialization.SoapObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public String[] Login(String[] par1,String[] par2){
        //String [] bool=new String[]{"2","0"};
        //int c=2;
        httpConnSoap=new HttpConnSoap();
        //String[] es=httpConnSoap.HttpGo(par1,par2,"IsMatchUser");
        String[] es=new String[]{""};
        es=httpConnSoap.HttpGo(par1,par2,"IsMatchUser");
        /*if(es.length>0) {
            bool[0] = es[0];
            bool[1] = es[1];
        }*/
        return es;
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
    /*图片与64位转换*/
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
    public String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /*********************************************************************/

    public List<TUser> ShowUser(String[] par1, String[] par2){
        httpConnSoap2=new HttpConnSoap2();
        List<TUser> list=new ArrayList<>();
        SoapObject primitive=httpConnSoap2.HttpGo(par1,par2,"IsShowUser2");
        for(int i=0;i<primitive.getPropertyCount();i++){
                    SoapObject mstr= (SoapObject) primitive.getProperty(i);
                    int uid=Integer.parseInt(mstr.getProperty(0).toString());
                    String username=mstr.getProperty(1).toString();
                    String userpwd=mstr.getProperty(2).toString();
                    String sex=mstr.getProperty(3).toString();
                    String phone=mstr.getProperty(4).toString();
                    String email=mstr.getProperty(5).toString();
                    String photo=mstr.getProperty(6).toString();

                    TUser tUser=new TUser();
                    tUser.setId(uid);
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

        List<TBook> lb=HuoquBooks();
        int conut=lb.size();
        for(int i=0;i<conut;i++){
            TBook m=lb.get(i);
            Bitmap bp=stringToBitmap1(m.getBphoto());
            if(i!=0){map=new HashMap<String,Object>();}
            map.put("BImg",bp);
            map.put("BName",m.getBname());
            map.put("BAuthor",m.getBauthor());
            map.put("BCount",m.getCount()+"本");
            map.put("BId",m.getId());

            listitem.add(map);
        }

        return listitem;
    }

    public List<TBook> HuoquBooks(){
        bookHttpConnSoap=new BookHttpConnSoap();
        List<TBook> list=new ArrayList<>();
        SoapObject primitive=bookHttpConnSoap.HttpGo("IsShowBooks");

        for(int i=0;i<primitive.getPropertyCount();i++){
            SoapObject mstr= (SoapObject) primitive.getProperty(i);

            int id= Integer.parseInt(mstr.getProperty(0).toString()) ;
            String bname=mstr.getProperty(1).toString();
            String bauthor=mstr.getProperty(2).toString();
            int bcount= Integer.parseInt(mstr.getProperty(3).toString()) ;
            String bphoto=mstr.getProperty(4).toString();

            TBook tbook=new TBook(id,bname,bauthor,bcount,bphoto);
            //TBook tbook=new TBook(1,"ddd","pppp",10.5,123,"qqqq");
            list.add(tbook);
        }
        return list;
    }

    public List<TBook> HuoquBooks2(){
        bookHttpConnSoap=new BookHttpConnSoap();
        List<TBook> list=new ArrayList<>();
        SoapObject primitive=bookHttpConnSoap.HttpGo("IsShowBooks2");

        for(int i=0;i<primitive.getPropertyCount();i++){
            SoapObject mstr= (SoapObject) primitive.getProperty(i);

            int id= Integer.parseInt(mstr.getProperty(0).toString()) ;
            String bname=mstr.getProperty(1).toString();
            String bauthor=mstr.getProperty(2).toString();
            String bsex=mstr.getProperty(3).toString();
            int bcount= Integer.parseInt(mstr.getProperty(4).toString()) ;
            String bdesc=mstr.getProperty(5).toString();
            String btype=mstr.getProperty(6).toString();
            String bphoto=mstr.getProperty(7).toString();
            String location=mstr.getProperty(8).toString();

            TBook tbook=new TBook(id,bname,bauthor,bsex,bcount,bdesc,btype,bphoto,location);
            //TBook tbook=new TBook(1,"ddd","pppp",10.5,123,"qqqq");
            list.add(tbook);
        }
        return list;
    }

    public TBook HuoquBook(String id) {
        bookHttpConnSoap=new BookHttpConnSoap();
        //String id2=id+"";
        String [] a={"id"};String [] b={id};
        SoapObject so=bookHttpConnSoap.HttpGo2(a,b,"IsShowBook");

            int bid= Integer.parseInt(so.getProperty(0).toString()) ;
            String bname=so.getProperty(1).toString();
            String bauthor=so.getProperty(2).toString();
            String bsex=so.getProperty(3).toString();
            int bcount= Integer.parseInt(so.getProperty(4).toString()) ;
            String bdesc=so.getProperty(5).toString();
            String btype= so.getProperty(6).toString() ;
            String bphoto=so.getProperty(7).toString();
            String blocation=so.getProperty(8).toString();
        TBook book=new TBook(bid,bname,bauthor,bsex,bcount,bdesc,btype,bphoto,blocation);
            //TBook book=new TBook();
        return book;
    }

    public List<BType> HuoquBTypes(){
        bookHttpConnSoap=new BookHttpConnSoap();

        List<BType> list=new ArrayList<>();

        //httpConnSoap2=new HttpConnSoap2();
        //String [] a={"username"};String [] b={"aa"};
        //String [] a={""};String [] b={""};
        //SoapObject primitive=httpConnSoap2.HttpGo(a,b,"IsShowUser2");
        SoapObject primitive=bookHttpConnSoap.HttpGo("IsBookTypes");

        for(int i=0;i<primitive.getPropertyCount();i++){
            SoapObject mstr= (SoapObject) primitive.getProperty(i);

            int tid= Integer.parseInt(mstr.getProperty(0).toString()) ;
            String tname=mstr.getProperty(1).toString();
            String tdesc=mstr.getProperty(2).toString();

            BType bType=new BType(tid,tname,tdesc);
            //TBook tbook=new TBook(1,"ddd","pppp",10.5,123,"qqqq");
            list.add(bType);
        }
        return list;
    }

    public List<TBook> HuoquTypeBooks(String tid) {
        bookHttpConnSoap=new BookHttpConnSoap();
        List<TBook> list=new ArrayList<>();
        String [] a={"tid"};String [] b={tid};
        SoapObject so=bookHttpConnSoap.HttpGo2(a,b,"IsTypeBooks");
        for(int i=0;i<so.getPropertyCount();i++) {
            SoapObject mstr= (SoapObject) so.getProperty(i);
            int bid = Integer.parseInt(mstr.getProperty(0).toString());
            String bname = mstr.getProperty(1).toString();
            String bauthor = mstr.getProperty(2).toString();
            int bcount = Integer.parseInt(mstr.getProperty(3).toString());
            String btype = mstr.getProperty(4).toString();
            String bphoto = mstr.getProperty(5).toString();
            TBook user = new TBook(bid, bname, bauthor,bcount,btype,bphoto);
            list.add(user);
        }
        return list;
    }

    public List<TBook> HuoquFindBooks(String txt) {
        bookHttpConnSoap=new BookHttpConnSoap();
        List<TBook> list=new ArrayList<>();

        String [] a={"txt"};String [] b={txt};
        SoapObject so=bookHttpConnSoap.HttpGo2(a,b,"IsFindBooks");
        for(int i=0;i<so.getPropertyCount();i++) {
            SoapObject mstr= (SoapObject) so.getProperty(i);
            int bid = Integer.parseInt(mstr.getProperty(0).toString());
            String bname = mstr.getProperty(1).toString();
            String bauthor = mstr.getProperty(2).toString();
            int bcount = Integer.parseInt(mstr.getProperty(3).toString());
            String btype = mstr.getProperty(4).toString();
            String bphoto = mstr.getProperty(5).toString();
            TBook user = new TBook(bid, bname, bauthor,bcount,btype,bphoto);
            list.add(user);
        }
        return list;
    }

    public String TypeAddBool(String tnsme,String tdesc) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String [] a={"typename","typedesc"};String [] b={tnsme,tdesc};
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsAddType");
        return so;
    }

    public String TypeAltBool(String tid,String tnsme,String tdesc) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String [] a={"tid","typename","typedesc"};String [] b={tid,tnsme,tdesc};
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsAltType");
        return so;
    }

    public String TypeDelBool(String tid) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String [] a={"tid"};String [] b={tid};
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsDelType");
        return so;
    }

    public String BookDelBool(String bid) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String [] a={"bid"};String [] b={bid};
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsDelBook");
        return so;
    }

    public String Updateuser(String []a,String [] b) {
        httpConnSoap=new HttpConnSoap();
        String so=httpConnSoap.HttpGo3(a,b,"IsUpdateUser");
        return so;
    }

    public Bitmap convertViewToBitmap(View view){
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public String BookAddBool(String []a,String [] b) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsAddBook");
        return so;
    }

    public String BookAltBool(String []a,String [] b) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsAltBook");
        return so;
    }

    public List<TUser> Likeuser(String[] a,String[] b)
    {
        httpConnSoap=new HttpConnSoap();
        List<TUser> list=new ArrayList<>();
        SoapObject so=httpConnSoap.HttpGo2(a,b,"IsLikeUsers");

        for(int i=0;i<so.getPropertyCount();i++) {
            SoapObject mstr= (SoapObject) so.getProperty(i);

            int uid = Integer.parseInt(mstr.getProperty(0).toString());
            String uname = mstr.getProperty(1).toString();
            String usex = mstr.getProperty(2).toString();
            String uphone = mstr.getProperty(3).toString();
            String uphoto = mstr.getProperty(4).toString();
            TUser user = new TUser(uid, uname, usex,uphone,uphoto);
            list.add(user);
        }
        return list;
    }

    public List<TBook> Likebook(String[] a,String[] b)
    {
        bookHttpConnSoap=new BookHttpConnSoap();
        List<TBook> list=new ArrayList<>();
        SoapObject so=bookHttpConnSoap.HttpGo2(a,b,"IsLikeBooks");

        for(int i=0;i<so.getPropertyCount();i++) {
            SoapObject mstr= (SoapObject) so.getProperty(i);

            int bid = Integer.parseInt(mstr.getProperty(0).toString());
            String bname = mstr.getProperty(1).toString();
            String bauthor = mstr.getProperty(2).toString();
            int bcount = Integer.parseInt(mstr.getProperty(3).toString());
            String btype = mstr.getProperty(4).toString();
            String bphoto = mstr.getProperty(5).toString();
            TBook user = new TBook(bid, bname, bauthor,bcount,btype,bphoto);
            list.add(user);
        }
        return list;
    }

    public String JieBook(String []a,String [] b) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsJieBook");
        return so;
    }

    public String HuanBook(String []a,String [] b) {
        bookHttpConnSoap=new BookHttpConnSoap();
        String so=bookHttpConnSoap.HttpGo3(a,b,"IsHuanBook");
        return so;
    }

    public List<TBook> Lookhuanbook(String[] a,String[] b)
    {
        bookHttpConnSoap=new BookHttpConnSoap();
        List<TBook> list=new ArrayList<>();
        SoapObject so=bookHttpConnSoap.HttpGo2(a,b,"IsUserJieBooks");

        for(int i=0;i<so.getPropertyCount();i++) {
            SoapObject mstr= (SoapObject) so.getProperty(i);

            int bid = Integer.parseInt(mstr.getProperty(0).toString());
            String bname = mstr.getProperty(1).toString();
            String bauthor = mstr.getProperty(2).toString();
            int bcount = Integer.parseInt(mstr.getProperty(3).toString());
            String btype = mstr.getProperty(4).toString();
            String bphoto = mstr.getProperty(5).toString();
            TBook user = new TBook(bid, bname, bauthor,bcount,btype,bphoto);
            list.add(user);
        }
        return list;
    }

    public String[] BookJieTime(String[] a,String[] b)
    {
        bookHttpConnSoap=new BookHttpConnSoap();
        SoapObject so=bookHttpConnSoap.HttpGo2(a,b,"IsBookJieTime");
        String [] cc=new String[so.getPropertyCount()];
        for(int i=0;i<so.getPropertyCount();i++) {
            //SoapObject mstr= (SoapObject) so.getProperty(i);
            cc[i]=so.getProperty(i).toString();
        }
        return cc;
    }
}
