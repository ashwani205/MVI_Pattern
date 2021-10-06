package com.example.mviarchitecture.model

import com.squareup.moshi.Json

data class User(@Json(name = "id") val id:Int=0,
@Json(name = "name") val name:String="",
@Json(name="title") val title:String="",
@Json( name="avatar") val avatar:String="")
