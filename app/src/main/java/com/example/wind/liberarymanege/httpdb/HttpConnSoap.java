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
 * Created by wind on 2017/12/21.
 */

public class HttpConnSoap {
    //访问网络同时加入这个
    @SuppressLint("NewApi")
    public String HttpGo(String[] par1,String[] par2,String way){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String namespace="http://tempuri.org/";//namespace
        String soapAction = "http://tempuri.org//"+way+"/";
        String url = "http://192.168.123.112/WebService1.asmx";
        String cc="错误！";

        String methodName=way;
        SoapObject re=new SoapObject(namespace,methodName);
        for(int i=0;i<par1.length;i++){
            re.addProperty(par1[i],par2[i]);
        }

        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        env.bodyOut=re;
        env.dotNet=true;
        (new MarshalBase64()).register(env);
        env.setOutputSoapObject(re);
        HttpTransportSE transport=new HttpTransportSE(url);
        transport.debug=true;
        try {
            transport.call(soapAction,env);

            //SoapObject primitive =  (SoapObject) env.getResponse();

            //cc=primitive.getProperty(0).toString();
            //int i=primitive.getPropertyCount();
            //cc=""+i;
            //cc=ss[0];

            cc=String.valueOf(env.getResponse());
            return cc;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return cc;
    }
}
