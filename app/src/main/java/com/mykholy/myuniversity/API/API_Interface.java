package com.mykholy.myuniversity.API;

import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.Lecture;
import com.mykholy.myuniversity.model.Login;
import com.mykholy.myuniversity.model.Question;
import com.mykholy.myuniversity.model.Student;

import java.util.List;

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
    Call<Student> Register(@Body Student student);


    @GET("api/studentCourse/{AcademicYear}/department/{DeptID}")
    Call<List<Course>> getAllCourses(@Header("Authorization") String header, @Path("AcademicYear") int AcademicYear, @Path("DeptID") int DeptID);

    @POST("api/student/{studentID}")
    Call<Student> updateStudent(@Header("Authorization") String header, @Path("studentID") int studentID, @Body Student student);


    @GET("api/studentLecture/{courseID}")
    Call<List<Lecture>> getAllLectures(@Header("Authorization") String header, @Path("courseID") int courseID);

    @GET("api/studentNotSolveExam/{studentID}/Course/{courseID}")
    Call<List<Exam>> getAllUnSolvedExam(@Header("Authorization") String header, @Path("studentID") int studentID, @Path("courseID") int courseID);

    @GET("api/studentSolveExam/{studentID}/Course/{courseID}")
    Call<List<Exam>> getAllSolvedExam(@Header("Authorization") String header, @Path("studentID") int studentID, @Path("courseID") int courseID);

    @GET("api/getAllQuestionForExam/{examID}/Course/{courseID}")
    Call<List<Question>> getAllQuestionForExam(@Header("Authorization") String header, @Path("examID") int examID, @Path("courseID") int courseID);

    @POST("api/studentAnswer")
    Call<Question> saveMyAnswer(@Header("Authorization") String header, @Body Question question);

    @POST("api/studentSolveExam")
    Call<Exam> saveExam(@Header("Authorization") String header, @Body Exam exam);




}
