package com.example.lethal_companion.logs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lethal_companion.LogsAdapter
import com.example.lethal_companion.MonsterAdapter
import com.example.lethal_companion.R
import com.example.lethal_companion.ResponseModel
import com.example.lethal_companion.RetrofitAPI
import com.example.lethal_companion.rv_item_disp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Logs : AppCompatActivity() {

    lateinit var data: ResponseModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        recyclerView = findViewById(R.id.recyclerView)

        fetchDataFromApi()
    }
    fun back(view: View) {
        finish()
    }
    private fun fetchDataFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mikolajniewola.github.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitAPI::class.java)

        val call: Call<ResponseModel> = service.getData()

        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    data = response.body()!!

                    val adapter = LogsAdapter(data)

                    adapter.setOnItemClickListener(object : LogsAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Logs, rv_item_disp::class.java)
                            intent.putExtra("type","Logs")
                            intent.putExtra("name", data.Logs[position].name)
                            intent.putExtra("desc", data.Logs[position].desc)
                            intent.putExtra("img", data.Logs[position].img)
                            startActivity(intent)
                        }
                    })

                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@Logs)

                } else {
                    Log.e("MainActivity", "Błąd: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.e("MainActivity", "Błąd: ${t.message}", t)
            }
        })
    }
}