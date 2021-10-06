package com.example.mviarchitecture.api

import com.example.mviarchitecture.model.User
import retrofit2.http.GET

interface  ApiService {

    @GET("posts")
     suspend  fun getUsers(): List<User>

}