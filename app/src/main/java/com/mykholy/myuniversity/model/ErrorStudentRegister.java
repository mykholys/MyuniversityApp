package com.mykholy.myuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorStudentRegister {
    @SerializedName("S_Full_name")
    @Expose
    private List<String> sFullName = null;
    @SerializedName("Email")
    @Expose
    private List<String> email = null;
    @SerializedName("password")
    @Expose
    private List<String> password = null;
    @SerializedName("N_id_n")
    @Expose
    private List<String> nIdN = null;
    @SerializedName("phone")
    @Expose
    private List<String> phone = null;
    @SerializedName("Birthdate")
    @Expose
    private List<String> birthdate = null;
    @SerializedName("AcademicYear")
    @Expose
    private List<String> academicYear = null;
    @SerializedName("Dept_ID")
    @Expose
    private List<String> deptID = null;
    @SerializedName("gender")
    @Expose
    private List<String> gender = null;
    @SerializedName("state")
    @Expose
    private List<String> state = null;

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<String> getSFullName() {
        return sFullName;
    }


    public void setSFullName(List<String> sFullName) {
        this.sFullName = sFullName;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getNIdN() {
        return nIdN;
    }

    public void setNIdN(List<String> nIdN) {
        this.nIdN = nIdN;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public List<String> getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(List<String> birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(List<String> academicYear) {
        this.academicYear = academicYear;
    }

    public List<String> getDeptID() {
        return deptID;
    }

    public void setDeptID(List<String> deptID) {
        this.deptID = deptID;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<String> getState() {
        return state;
    }

    public void setState(List<String> state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
