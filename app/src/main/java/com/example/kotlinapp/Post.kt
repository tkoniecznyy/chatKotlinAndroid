package com.example.kotlinapp

import com.google.gson.annotations.SerializedName

class Post(content: String, login: String, date: String, id: String) {
    @SerializedName("content") val content: String? = null

    @SerializedName("login") var login: String? = null

    @SerializedName("date") var date: String? = null

    @SerializedName("id") var id: String? = null
}