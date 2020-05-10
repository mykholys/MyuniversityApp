package com.mykholy.myuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorStudentRegister {
    @SerializedName("Email")
    @Expose
    private List<String> email = null;
    @SerializedName("N_id_n")
    @Expose
    private List<String> nIdN = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getNIdN() {
        return nIdN;
    }

    public void setNIdN(List<String> nIdN) {
        this.nIdN = nIdN;
    }
}
