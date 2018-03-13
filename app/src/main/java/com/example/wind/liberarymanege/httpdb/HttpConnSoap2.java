package com.example.wind.liberarymanege.httpdb;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by wind on 2018/1/15.
 */

public class HttpConnSoap2 {
    //访问网络同时加入这个
    @SuppressLint("NewApi")
    public SoapObject HttpGo(String[] par1, String[] par2, String way){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //List<TUser> list=new ArrayList<>();
        //TUser[] ttt;
        SoapObject primitive=new SoapObject();

        String namespace="http://tempuri.org/";//namespace
        String soapAction = "http://tempuri.org//"+way+"/";
        String url = "http://192.168.42.222/WebService1.asmx";
        String methodName=way;//要调用的方法名称
        SoapObject re=new SoapObject(namespace,methodName);
        for(int i=0;i<par1.length;i++){
            re.addProperty(par1[i],par2[i]);
        }

        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        env.bodyOut=re;
        env.dotNet=true;
        (new MarshalBase64()).register(env);
        //env.setOutputSoapObject(re);
        HttpTransportSE transport=new HttpTransportSE(url);
        transport.debug=true;
        try {
            transport.call(soapAction, env);
            if(env.getResponse()!=null) {
                primitive = (SoapObject) env.getResponse();
                //list=fromSoapItem(primitive);
                //strings=new String[primitive.getPropertyCount()];
                //ttt=new TUser[primitive.getPropertyCount()];
                /*for(int i=0;i<primitive.getPropertyCount();i++){
                    SoapObject mstr= (SoapObject) primitive.getProperty(i);

                    String username=mstr.getProperty(1).toString();
                    String userpwd=mstr.getProperty(2).toString();
                    String sex=mstr.getProperty(3).toString();
                    String phone=mstr.getProperty(4).toString();
                    String email=mstr.getProperty(5).toString();
                    String photo=mstr.getProperty(6).toString();
                    String sss=username+userpwd+sex+phone+email+photo;
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
                }*/
                return primitive;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return primitive;//strings=new String[]{""};
    }

}
