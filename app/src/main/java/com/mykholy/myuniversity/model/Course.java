package com.mykholy.myuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Course implements Serializable {


    @SerializedName("C_id")
    @Expose
    private Integer cId;
    @SerializedName("C_Name")
    @Expose
    private String cName;
    @SerializedName("C_image")
    @Expose
    private String cImage;
    @SerializedName("AcademicYear")
    @Expose
    private Integer academicYear;
    @SerializedName("Term")
    @Expose
    private Integer term;
    @SerializedName("D_id")
    @Expose
    private Integer dId;
    @SerializedName("Dept_ID")
    @Expose
    private Integer deptID;

    public Course(Integer cId, String cName, String cImage, Integer academicYear, Integer term, Integer dId, Integer deptID) {
        this.cId = cId;
        this.cName = cName;
        this.cImage = cImage;
        this.academicYear = academicYear;
        this.term = term;
        this.dId = dId;
        this.deptID = deptID;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCImage() {
        return cImage;
    }

    public void setCImage(String cImage) {
        this.cImage = cImage;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getDId() {
        return dId;
    }

    public void setDId(Integer dId) {
        this.dId = dId;
    }

    public Integer getDeptID() {
        return deptID;
    }

    public void setDeptID(Integer deptID) {
        this.deptID = deptID;
    }
}
