package com.example.the_liuchao.myapplication.db;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by the_liuchao on 2016/3/20.
 */
@Table(name = "parent",onCreated = "CREATE UNIQUE INDEX index_name ON parent(name,email)")/**指定创建一个patent表其中name和email唯一字段*/
public class Parent {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private String time;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Child> getChilds(DbManager db) throws DbException {
        return db.selector(Child.class).where("parentId","=",this.id).findAll();
    }
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
