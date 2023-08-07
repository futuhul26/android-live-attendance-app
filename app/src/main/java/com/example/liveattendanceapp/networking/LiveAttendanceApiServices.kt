package com.example.liveattendanceapp.networking

import com.example.liveattendanceapp.model.*
import com.example.liveattendanceapp.views.forgotpass.ForgotPasswordRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface LiveAttendanceApiServices {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/login")
    fun loginRequest(@Body body: String): Call<LoginResponse>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/password/forgot")
    fun forgotPasswordRequest(@Body body: String): Call<ForgotPasswordResponse>

    @Multipart
    @Headers("Accept: application/json")
    @POST("attendance")
    fun attend(@Header("Authorization") token: String,
               @PartMap params: HashMap<String, RequestBody>,
               @Part photo: MultipartBody.Part
    ): Call<AttendanceResponse>

    @Headers("Accept: application/json")
    @GET("attendance/history")
    fun getHistoryAttendance(@Header("Authorization") token: String,
                             @Query("from") fromDate: String,
                             @Query("to") toDate: String
    ): Call<HistoryResponse>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/logout")
    fun logoutRequest(@Header("Authorization") token: String): Call<LogoutResponse>

    @FormUrlEncoded
    @POST("auth/password/reset")
    fun changePassword(@Header("Authorization") token: String, @Field("password_old") password_old: String,
                       @Field("password") passwordNew: String, @Field("password_confirmation") passwordConfirm: String): Call<ChangePasswordResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("logbook")
    fun logbook(@Header("Authorization") token: String, @Field("task") task: String,
                @Field("start_time") start_time: String, @Field("end_time") end_time: String,
                @Field("description") description: String
    ): Call<AttendanceResponse>

}