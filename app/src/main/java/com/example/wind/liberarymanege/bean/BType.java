package com.example.wind.liberarymanege.bean;

import java.io.Serializable;

/**
 * Created by wind on 2018/3/18.
 */

public class BType implements Serializable{
    private int tid;
    private String tname;
    private String tdesc;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTdesc() {
        return tdesc;
    }

    public void setTdesc(String tdesc) {
        this.tdesc = tdesc;
    }

    public BType(int tid, String tname, String tdesc) {
        this.tid = tid;
        this.tname = tname;
        this.tdesc = tdesc;
    }

    public BType() {
    }
}
