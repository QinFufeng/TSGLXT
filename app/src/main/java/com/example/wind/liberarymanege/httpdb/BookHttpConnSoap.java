package com.example.wind.liberarymanege.httpdb;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by wind on 2018/3/14.
 */

public class BookHttpConnSoap {
    final String b_ip="192.168.43.132";
    //访问网络同时加入这个
    @SuppressLint("NewApi")
    public SoapObject HttpGo( String way){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SoapObject primitive=new SoapObject();

        String namespace="http://tempuri.org/";//namespace
        String soapAction = "http://tempuri.org//"+way+"/";
        String url = "http://"+b_ip+"/WebService2.asmx";
        String methodName=way;//要调用的方法名称
        SoapObject re=new SoapObject(namespace,methodName);
        /*for(int i=0;i<par1.length;i++){
            re.addProperty(par1[i],par2[i]);
        }*/
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
                return primitive;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return primitive;
    }
    //访问网络同时加入这个
    @SuppressLint("NewApi")
    public SoapObject HttpGo2( String [] par1,String [] par2,String way){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SoapObject primitive=new SoapObject();

        String namespace="http://tempuri.org/";//namespace
        String soapAction = "http://tempuri.org//"+way+"/";
        String url = "http://"+b_ip+"/WebService2.asmx";
        String methodName=way;//要调用的方法名称
        SoapObject re=new SoapObject(namespace,methodName);
        for(int i=0;i<par1.length;i++){
            re.addProperty(par1[i],par2[i]);
        }
        //re.addProperty("id",1);
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
                return primitive;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return primitive;
    }

    //访问网络同时加入这个
    @SuppressLint("NewApi")
    public String HttpGo3( String [] par1,String [] par2,String way){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //SoapObject primitive=new SoapObject();

        String namespace="http://tempuri.org/";//namespace
        String soapAction = "http://tempuri.org//"+way+"/";
        String url = "http://"+b_ip+"/WebService2.asmx";
        String methodName=way;//要调用的方法名称
        SoapObject re=new SoapObject(namespace,methodName);
        for(int i=0;i<par1.length;i++){
            re.addProperty(par1[i],par2[i]);
        }
        //re.addProperty("id",1);
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
                SoapPrimitive primitive = (SoapPrimitive) env.getResponse();
                String s=primitive.toString();
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return "ox001";
    }
}