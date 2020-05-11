package com.mykholy.myuniversity.utilities;

import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.model.ErrorStudentRegister;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static ErrorStudentRegister parseError(Response<?> response) {
        Converter<ResponseBody, ErrorStudentRegister> converter =
                AppClient.getClient().responseBodyConverter(ErrorStudentRegister.class, new Annotation[0]);
        ErrorStudentRegister errorStudentRegister;
        try {
            errorStudentRegister = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorStudentRegister();
        }

        return errorStudentRegister;
    }
}
