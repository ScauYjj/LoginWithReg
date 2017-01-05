package com.chinamobile.anthortest;

import org.litepal.crud.DataSupport;

/**
 * Created by yjj on 2016/12/30.
 */

public class User extends DataSupport{
    private int id;
    private String name;
    private String pwd;

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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
