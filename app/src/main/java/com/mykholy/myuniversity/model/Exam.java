package com.mykholy.myuniversity.model;

public class Exam {

    private Integer eId;

    private String title;

    private String startDate;

    private String endDate;

    private Integer timer;

    private Integer dId;

    private Integer cId;

    private String createdAt;

    private String status;

    public Exam(Integer eId, String title, String startDate, String endDate, Integer timer, Integer dId, Integer cId, String createdAt, String status) {
        this.eId = eId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.dId = dId;
        this.cId = cId;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Integer getEId() {
        return eId;
    }

    public void setEId(Integer eId) {
        this.eId = eId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public Integer getDId() {
        return dId;
    }

    public void setDId(Integer dId) {
        this.dId = dId;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
