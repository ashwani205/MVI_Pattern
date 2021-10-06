package com.example.mviarchitecture.rapository

import com.example.mviarchitecture.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}