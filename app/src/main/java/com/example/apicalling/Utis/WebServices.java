package com.example.apicalling.Utis;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServices {
    @FormUrlEncoded
    @POST(Keys.SignUp)
    Call<GeneralModel> SignUp(@FieldMap Map<String, String> Map);

    @FormUrlEncoded
    @POST(Keys.SignIn)
    Call<GeneralModel> SignIn(@FieldMap Map<String, String> Map);

    @GET(Keys.ServiceCategoryList)
    Call<CategoryModel> ServiceCategoryList();


}
