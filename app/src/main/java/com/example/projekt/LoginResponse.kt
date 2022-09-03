package com.example.projekt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse (
    @SerializedName("token")
    @Expose
    var token: String? = null,

    @SerializedName("username")
    @Expose
    var username: String? = null
)