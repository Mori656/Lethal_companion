package com.example.lethal_companion.bestiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lethal_companion.MonsterAdapter
import com.example.lethal_companion.R
import com.example.lethal_companion.ResponseModel
import com.example.lethal_companion.RetrofitAPI
import com.example.lethal_companion.logs.Logs
import com.example.lethal_companion.rv_item_disp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Bestiary : AppCompatActivity() {

    lateinit var data: ResponseModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bestiary)

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

                    val adapter = MonsterAdapter(data)

                    adapter.setOnItemClickListener(object : MonsterAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Bestiary, rv_item_disp::class.java)
                            intent.putExtra("type","Monsters")
                            intent.putExtra("name", data.Monsters[position].name)
                            intent.putExtra("dangerLvl", data.Monsters[position].dangerLevel)
                            intent.putExtra("sName", data.Monsters[position].sName)
                            intent.putExtra("desc", data.Monsters[position].desc)
                            intent.putExtra("img", data.Monsters[position].img)
                            startActivity(intent)
                        }
                    })

                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@Bestiary)

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