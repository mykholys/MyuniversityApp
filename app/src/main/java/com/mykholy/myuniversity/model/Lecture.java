package com.mykholy.myuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lecture {

    @SerializedName("Lec_id")
    @Expose
    private Integer lecId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("File")
    @Expose
    private String file;
    @SerializedName("C_id")
    @Expose
    private Integer cId;
    @SerializedName("D_id")
    @Expose
    private Integer dId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getLecId() {
        return lecId;
    }

    public void setLecId(Integer lecId) {
        this.lecId = lecId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public Integer getDId() {
        return dId;
    }

    public void setDId(Integer dId) {
        this.dId = dId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
