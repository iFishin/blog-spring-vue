package com.fish.entity;


import java.sql.Date;

public class Critic {
    private Integer id;
    private String account;
    private Date c_date;
    private String c_comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getC_date() {
        return c_date;
    }

    public void setC_date(Date c_date) {
        this.c_date = c_date;
    }

    public String getC_comment() {
        return c_comment;
    }

    public void setC_comment(String c_comment) {
        this.c_comment = c_comment;
    }

    @Override
    public String toString() {
        return "Critic{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", c_date=" + c_date +
                ", c_comment='" + c_comment + '\'' +
                '}';
    }
}
