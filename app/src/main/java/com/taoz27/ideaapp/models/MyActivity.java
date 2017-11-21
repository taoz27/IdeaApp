package com.taoz27.ideaapp.models;

import java.io.Serializable;

/**
 * Created by taoz27 on 2017/11/15.
 */

public class MyActivity implements Serializable{
    private static final long serialVersionUID = 2227274890098008044L;

    private int id;
    private String name;
    private String institution;
    private String lable;
    private long createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
