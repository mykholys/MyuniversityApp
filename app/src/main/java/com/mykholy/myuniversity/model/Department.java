package com.mykholy.myuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("Dept_ID")
    @Expose
    private Integer deptID;
    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getDeptID() {
        return deptID;
    }

    public void setDeptID(Integer deptID) {
        this.deptID = deptID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
