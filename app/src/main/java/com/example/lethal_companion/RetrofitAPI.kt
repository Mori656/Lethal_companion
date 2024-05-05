package com.example.lethal_companion

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface RetrofitAPI {
//    @GET("JSON_test/test.json")
//    fun getData(): Call<ResponseModel>
////    /v3/b/66377525acd3cb34a84341c6
    @Headers(
        "Content-Type: application/json",
        "X-Master-Key: \$2a\$10\$51/e7rN.Lz7XKshEJEv2B.Fqmmho3XKSuwopnBus5QhhU9Ur.6ZNa"
    )
    @GET("b/66377525acd3cb34a84341c6/latest")
    fun getData(): Call<ResponseModel>
    @Headers(
        "Content-Type: application/json",
        "X-Master-Key: \$2a\$10\$51/e7rN.Lz7XKshEJEv2B.Fqmmho3XKSuwopnBus5QhhU9Ur.6ZNa"
    )
    @PUT("b/66377525acd3cb34a84341c6")
    fun updateGameData(@Body updatedData: Record): Call<Record>
}