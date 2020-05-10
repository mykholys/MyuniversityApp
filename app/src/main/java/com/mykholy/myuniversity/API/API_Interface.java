package com.mykholy.myuniversity.API;

import com.mykholy.myuniversity.model.Login;
import com.mykholy.myuniversity.model.Student;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Header;

import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {
    @POST("oauth/token")
    Call<Login> Login(@Body Login login);

    @GET("api/student_email/{email}")
    Call<Student> getInfoStudent(@Header("Authorization") String header, @Path("email") String email);


    @POST("api/student/register")
    Call<Student> Register( @Body Student student);


}
