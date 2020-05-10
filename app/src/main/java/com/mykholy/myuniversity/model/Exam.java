package com.mykholy.myuniversity.model;

import java.io.Serializable;

public class Exam  implements Serializable {

    private Integer eId;

    private Integer sId;

    private Integer result;

    private String timeSpend;

    private String title;

    private String startDate;

    private String endDate;

    private Integer timer;

    private Integer dId;

    private Integer cId;

    private String createdAt;

    private String status;
    private Integer num_questions;

    public Exam(Integer eId, String title, String startDate, String endDate, Integer timer, Integer dId, Integer cId, String createdAt, String status,Integer num_questions) {
        this.eId = eId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.dId = dId;
        this.cId = cId;
        this.createdAt = createdAt;
        this.status = status;
        this.num_questions=num_questions;

    }

    public Exam(Integer eId, Integer sId, Integer result, String timeSpend, String title, String startDate, String endDate, Integer timer, Integer dId, Integer cId, String createdAt, String status,Integer num_questions) {
        this.eId = eId;
        this.sId = sId;
        this.result = result;
        this.timeSpend = timeSpend;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.dId = dId;
        this.cId = cId;
        this.createdAt = createdAt;
        this.status = status;
       this.num_questions=num_questions;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(String timeSpend) {
        this.timeSpend = timeSpend;
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

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
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

    public Integer getNum_questions() {
        return num_questions;
    }

    public void setNum_questions(Integer num_questions) {
        this.num_questions = num_questions;
    }
}
