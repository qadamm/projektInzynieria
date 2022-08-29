package com.example.projekt

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/questions/")
    fun getQuestions(): Call<Questions>
//    suspend fun getQuestions(): Response<Questions>

    @GET("api/questions/random")
    fun getRandomQuestion(): Call<Questions2>

    //http://3.82.177.82/api/questions/1/Polski/2021/2021
    @GET("api/questions/{quantity}/{subject}/{first}/{last}")
    fun getSelectedQuestions(@Path("quantity") quantity: Int, @Path("subject") subject: String, @Path("first") first: Int, @Path("last") last: Int ): Call<Questions>

    @POST("/api/token")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/user/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

}