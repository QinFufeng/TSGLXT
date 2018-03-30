package com.example.wind.liberarymanege.bean;

/**
 * Created by wind on 2018/3/14.
 */

public class TBook {
    private int id;
    private String bname;
    private String bauthor;
    private String bsex;
    private int count;
    private String bdesc;
    private String btype;
    private String bphoto;
    private String location;

    public TBook() {

    }
    public TBook(int id, String bname, String bauthor, int count, String bphoto) {
        this.id = id;
        this.bname = bname;
        this.bauthor = bauthor;
        this.count = count;
        //this.btype = btype;
        this.bphoto = bphoto;
    }

    public TBook(int id, String bname, String bauthor, String bsex, int count, String bdesc, String btype, String bphoto, String location) {
        this.id = id;
        this.bname = bname;
        this.bauthor = bauthor;
        this.bsex = bsex;
        this.count = count;
        this.bdesc = bdesc;
        this.btype = btype;
        this.bphoto = bphoto;
        this.location = location;
    }

    public TBook(int id, String bname, String bauthor, int count) {
        this.id = id;
        this.bname = bname;
        this.bauthor = bauthor;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getBsex() {
        return bsex;
    }

    public void setBsex(String bsex) {
        this.bsex = bsex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBdesc() {
        return bdesc;
    }

    public void setBdesc(String bdesc) {
        this.bdesc = bdesc;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getBphoto() {
        return bphoto;
    }

    public void setBphoto(String bphoto) {
        this.bphoto = bphoto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
