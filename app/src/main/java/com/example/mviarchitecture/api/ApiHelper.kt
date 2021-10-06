package com.example.mviarchitecture.api

import com.example.mviarchitecture.model.User

interface ApiHelper {
    suspend fun getUsers(): List<User>

}