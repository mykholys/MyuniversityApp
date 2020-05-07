package com.mykholy.myuniversity.model;

import java.io.Serializable;

public class Course implements Serializable {


    public Course(Integer cId, String cName, String cImage) {
        this.cId = cId;
        this.cName = cName;
        this.cImage = cImage;
    }

    private Integer cId;

    private String cName;

    private String cImage;

    private Integer academicYear;

    private Integer term;

    private Integer dId;

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
}
