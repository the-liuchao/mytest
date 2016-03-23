package com.example.the_liuchao.myapplication.db;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

/**
 * Created by the_liuchao on 2016/3/20.
 */
@Table(name="child")
public class Child {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "parentId")
    private long parentId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
  public Parent getParent(DbManager db) throws DbException {
       return db.findById(Parent.class,this.parentId);
  }
    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
