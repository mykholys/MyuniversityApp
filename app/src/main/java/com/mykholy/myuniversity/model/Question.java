package com.mykholy.myuniversity.model;

import java.io.Serializable;

public class Question implements Serializable {

    private Integer qId;

    private String title;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String ans;

    private Integer dId;

    private Integer cId;

    private Integer eID;

    public Question(Integer qId, String title, String optionA, String optionB, String optionC, String optionD, String ans, Integer dId, Integer cId, Integer eID) {
        this.qId = qId;
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.ans = ans;
        this.dId = dId;
        this.cId = cId;
        this.eID = eID;
    }

    public Integer getQId() {
        return qId;
    }

    public void setQId(Integer qId) {
        this.qId = qId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
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

    public Integer getEID() {
        return eID;
    }

    public void setEID(Integer eID) {
        this.eID = eID;
    }

}
