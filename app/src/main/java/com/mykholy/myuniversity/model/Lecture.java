package com.mykholy.myuniversity.model;

public class Lecture {

    private Integer lecId;

    private String name;

    private String file;

    private Integer cId;

    private Integer dId;

    private String createdAt;

    public Lecture(Integer lecId, String name, String file, Integer cId, Integer dId, String createdAt) {
        this.lecId = lecId;
        this.name = name;
        this.file = file;
        this.cId = cId;
        this.dId = dId;
        this.createdAt = createdAt;
    }

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
