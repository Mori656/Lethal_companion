package com.example.lethal_companion

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("JSON_test/test.json")
    fun getData(): Call<ResponseModel>
}