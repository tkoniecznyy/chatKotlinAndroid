package com.example.kotlinapp

import retrofit2.Call
import retrofit2.http.GET

interface MessageInterface {
    @GET("messages")
    fun getMessages(): Call<List<Post?>?>?
}