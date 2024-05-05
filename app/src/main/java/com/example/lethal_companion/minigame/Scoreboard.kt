package com.example.lethal_companion.minigame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lethal_companion.MonsterAdapter
import com.example.lethal_companion.R
import com.example.lethal_companion.R.id.back_button
import com.example.lethal_companion.ResponseModel
import com.example.lethal_companion.RetrofitAPI
import com.example.lethal_companion.ScoreAdapter
import com.example.lethal_companion.bestiary.Bestiary
import com.example.lethal_companion.rv_item_disp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Scoreboard : AppCompatActivity() {
    lateinit var data: ResponseModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        recyclerView = findViewById(R.id.recyclerView)

        fetchDataFromApi()
    }

    fun back(view: View) {
        finish()
    }

    private fun fetchDataFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitAPI::class.java)

        val call: Call<ResponseModel> = service.getData()

        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    data = response.body()!!

                    val adapter = ScoreAdapter(data)

                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@Scoreboard)

                } else {
                    Log.e("MainActivity", "Błąd response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.e("MainActivity", "Błąd laczenia: ${t.message}", t)
            }
        })
    }
}