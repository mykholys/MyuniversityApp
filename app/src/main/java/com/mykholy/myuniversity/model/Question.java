package com.mykholy.myuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Question implements Serializable {

    @SerializedName("Q_Id")
    @Expose
    private Integer qId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("option_a")
    @Expose
    private String optionA;
    @SerializedName("option_b")
    @Expose
    private String optionB;
    @SerializedName("option_c")
    @Expose
    private String optionC;
    @SerializedName("option_d")
    @Expose
    private String optionD;
    @SerializedName("Ans")
    @Expose
    private String ans;
    @SerializedName("D_id")
    @Expose
    private Integer dId;
    @SerializedName("C_Id")
    @Expose
    private Integer cId;
    @SerializedName("E_ID")
    @Expose
    private Integer eID;
    @SerializedName("S_id")
    @Expose
    private Integer sID;

    public Question(Integer qId, String ans, Integer sID) {
        this.qId = qId;
        this.ans = ans;
        this.sID = sID;
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

    public Integer getsID() {
        return sID;
    }

    public void setsID(Integer sID) {
        this.sID = sID;
    }
}
