package com.example.projekt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterResponse {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("userId")
    @Expose
    var userId: Int? = null

    @SerializedName("username")
    @Expose
    var username: String? = null
}