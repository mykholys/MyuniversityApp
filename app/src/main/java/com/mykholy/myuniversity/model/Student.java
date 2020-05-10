package com.mykholy.myuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("S_Full_name")
    @Expose
    private String sFullName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("N_id_n")
    @Expose
    private String nIdN;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("Birthdate")
    @Expose
    private String birthdate;
    @SerializedName("Score")
    @Expose
    private Integer score;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("AcademicYear")
    @Expose
    private Integer academicYear;
    @SerializedName("Dept_ID")
    @Expose
    private Integer deptID;

    public Student(String sFullName, String email, String password, String nIdN, String phone, String birthdate, String gender, String state, Integer academicYear, Integer deptID) {
        this.sFullName = sFullName;
        this.email = email;
        this.password = password;
        this.nIdN = nIdN;
        this.phone = phone;
        this.birthdate = birthdate;
        this.gender = gender;
        this.state = state;
        this.academicYear = academicYear;
        this.deptID = deptID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSFullName() {
        return sFullName;
    }

    public void setSFullName(String sFullName) {
        this.sFullName = sFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNIdN() {
        return nIdN;
    }

    public void setNIdN(String nIdN) {
        this.nIdN = nIdN;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getDeptID() {
        return deptID;
    }

    public void setDeptID(Integer deptID) {
        this.deptID = deptID;
    }
}