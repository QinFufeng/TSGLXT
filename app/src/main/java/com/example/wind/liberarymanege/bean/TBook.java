package com.example.wind.liberarymanege.bean;

/**
 * Created by wind on 2018/3/14.
 */

public class TBook {
    private int id;
    private String bname;
    private String bauthor;
    private String bsex;
    private double bprice;
    private String bdesc;
    private int btype;
    private String bphoto;

    public TBook() {

    }
    public TBook(int id, String bname, String bauthor, double bprice, int btype, String bphoto) {
        this.id = id;
        this.bname = bname;
        this.bauthor = bauthor;
        this.bprice = bprice;
        this.btype = btype;
        this.bphoto = bphoto;
    }

    public TBook(int id, String bname, String bauthor, String bsex, double bprice, String bdesc, int btype, String bphoto) {

        this.id = id;
        this.bname = bname;
        this.bauthor = bauthor;
        this.bsex = bsex;
        this.bprice = bprice;
        this.bdesc = bdesc;
        this.btype = btype;
        this.bphoto = bphoto;
    }

    public TBook(int id, String bname, String bauthor, double bprice) {
        this.id = id;
        this.bname = bname;
        this.bauthor = bauthor;
        this.bprice = bprice;
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

    public double getBprice() {
        return bprice;
    }

    public void setBprice(double bprice) {
        this.bprice = bprice;
    }

    public String getBdesc() {
        return bdesc;
    }

    public void setBdesc(String bdesc) {
        this.bdesc = bdesc;
    }

    public int getBtype() {
        return btype;
    }

    public void setBtype(int btype) {
        this.btype = btype;
    }

    public String getBphoto() {
        return bphoto;
    }

    public void setBphoto(String bphoto) {
        this.bphoto = bphoto;
    }
}
