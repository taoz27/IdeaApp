package com.taoz27.ideaapp.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by taoz27 on 2017/11/15.
 */

public class CategoryResponse implements Serializable{
    private static final long serialVersionUID = -7558016189753675652L;

    int id;
    String desc;
    List<Category2> categoryIdInfo2s;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Category2> getCategoryIdInfo2s() {
        return categoryIdInfo2s;
    }

    public void setCategoryIdInfo2s(List<Category2> categoryIdInfo2s) {
        this.categoryIdInfo2s = categoryIdInfo2s;
    }
}
