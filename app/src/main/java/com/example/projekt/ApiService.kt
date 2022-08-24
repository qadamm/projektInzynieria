package com.example.projekt

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/questions/")
    fun getQuestions(): Call<Questions>
//    suspend fun getQuestions(): Response<Questions>

    @POST("/api/token")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/user/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

}