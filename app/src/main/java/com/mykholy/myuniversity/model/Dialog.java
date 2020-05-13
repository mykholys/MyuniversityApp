package com.mykholy.myuniversity.model;

import java.io.Serializable;

public class Dialog implements Serializable {
    private int id;

    private String name;

    public Dialog(String name) {
        this.name = name;
    }

    public Dialog(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
