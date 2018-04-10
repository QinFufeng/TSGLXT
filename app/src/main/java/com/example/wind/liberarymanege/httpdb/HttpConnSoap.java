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
 * Created by wind on 2017/12/21.
 */

public class HttpConnSoap {

    //访问网络同时加入这个
    @SuppressLint("NewApi")
    public String[] HttpGo(String[] par1,String[] par2,String way){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String namespace="http://tempuri.org/";//namespace
        String soapAction = "http://tempuri.org//"+way+"/";

        String url = "http://10.16.96.73/WebService1.asmx";
        String[] cc;
        //String cc="asd";

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

                    SoapObject primitive = (SoapObject) env.getResponse();
                    int cont = primitive.getPropertyCount();
                    if(cont!=0) {
                        cc = new String[cont];
                        for (int i = 0; i < cont; i++) {
                            cc[i] = primitive.getProperty(i).toString();
                        }
                        return cc;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
        return cc=new String[]{""} ;
    }

    //访问网络同时加入这个
    @SuppressLint("NewApi")
    public String HttpGo3( String [] par1,String [] par2,String way){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //SoapObject primitive=new SoapObject();

        String namespace="http://tempuri.org/";//namespace
        String soapAction = "http://tempuri.org//"+way+"/";
        String url = "http://10.16.96.73/WebService1.asmx";
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
