package com.example.wind.liberarymanege.httpdb;

/**
 * Created by wind on 2017/12/21.
 */

public class DBUtil {
    private HttpConnSoap httpConnSoap;
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
}
