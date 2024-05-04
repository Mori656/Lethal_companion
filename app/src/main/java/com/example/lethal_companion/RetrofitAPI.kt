package com.example.lethal_companion

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface RetrofitAPI {
    @GET("JSON_test/test.json")
    fun getData(): Call<ResponseModel>

    @PUT("JSON_test/test.json")
    fun updateGameData(@Body updatedData: ResponseModel): Call<ResponseModel>
}