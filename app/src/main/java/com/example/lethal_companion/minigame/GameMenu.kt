package com.example.lethal_companion.minigame

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.lethal_companion.ElementGame
import com.example.lethal_companion.R
import com.example.lethal_companion.Record
import com.example.lethal_companion.ResponseModel
import com.example.lethal_companion.RetrofitAPI
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameMenu : AppCompatActivity() {

    var findnewbest = true
    lateinit var data: ResponseModel
    var playerName = ""
    val handler = Handler()
    var latestscore = "0"
    var bestscore = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_menu)
        fetchDataFromApi()

        var input_nick = findViewById<TextInputEditText>(R.id.input_nickname)

        val buttonClickTop = findViewById<Button>(R.id.but_top)
        buttonClickTop.setOnClickListener {
            val intent = Intent(this, Scoreboard::class.java)
            startActivity(intent)
        }

        val buttonClickStart = findViewById<Button>(R.id.but_start)
        buttonClickStart.setOnClickListener {
            val intent = Intent(this@GameMenu, Minigame::class.java)
            saveSharePref()
            intent.putExtra("PLAYER", input_nick.text.toString())
            startActivityForResult(intent,100)

        }
    }


    fun setLatestScore(score: Int) {




        findnewbest = true
        val scorelatest = findViewById<TextView>(R.id.score_latest)
        scorelatest.setText(score.toString())
        var player = ElementGame(playerName, score)

        for (i in 0..data.record.Game.size - 1) {
            Log.d("Datatable", data.record.Game[i].name)
        }
        var tmp: ElementGame = player
        var tmp2: ElementGame = player
        for (i in 0..data.record.Game.size - 1) {
            if (data.record.Game[i].name == player.name && data.record.Game[i].hiScore < player.hiScore) {

                data.record.Game.removeAt(i)
                break;
            } else if (data.record.Game[i].name == player.name && data.record.Game[i].hiScore > player.hiScore) {
                findnewbest = false
            }
        }
        if (findnewbest) {
            for (i in 0..data.record.Game.size - 1) {

                if (tmp != player) {
                    tmp2 = data.record.Game[i]
                    data.record.Game[i] = tmp
                    tmp = tmp2
                } else {
                    if (data.record.Game[i].hiScore < score) {
                        tmp = data.record.Game[i]
                        data.record.Game[i] = player
                    }
                }

            }
            data.record.Game.add(tmp)
            for (i in 0..data.record.Game.size - 1) {
                Log.d("Datatable", data.record.Game[i].name)
            }
            while (data.record.Game.size > 100) {
                Log.d("za duzo w tabeli", "usun ")
                data.record.Game.removeLast()
            }
        }

        handler.postDelayed({
            pushDataToApi(data)
        }, 500)


    }

    fun setBestScore(score: Int) {
        val scorebest = findViewById<TextView>(R.id.score_best)
        scorebest.setText(score.toString())
    }

    private fun fetchDataFromApi() {
        Log.d("Fetch", "działa1: ")
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
                    Log.d("Fetch", "działa2: ")
                } else {
                    Log.e("MainActivity", "Błąd: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.e("MainActivity", "Błąd: ${t.message}", t)
            }
        })

    }

    private fun pushDataToApi(d: ResponseModel) {
        Log.d("Push", "działa1: ")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitAPI::class.java)

        val call: Call<Record> = service.updateGameData(d.record)

        call.enqueue(object : Callback<Record> {
            override fun onResponse(call: Call<Record>, response: Response<Record>) {
                if (response.isSuccessful) {
                    Log.d("Push", "działa2: ")
                } else {
                    Log.e("MainActivity", "Błąd odpowiedzi: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Record>, t: Throwable) {
                Log.e("MainActivity", "Błąd połaczenia: ${t.message}", t)
            }
        })
    }

    fun back(view: View) {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val newscore = data.getIntExtra("SCORE",0)
                playerName = data.getStringExtra("PLAYER").toString()

                setLatestScore(newscore)
                var actualbestscore = findViewById<TextView>(R.id.score_best)
                if (newscore > actualbestscore.text.toString().toInt()) {
                    setBestScore(newscore)
                }
                saveSharePref()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveSharePref()
    }

    override fun onResume() {
        super.onResume()
        readSharePref()
    }

    fun readSharePref(){
        val sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)
        latestscore = sharedPreferences.getString("LatestScore","0").toString()
        bestscore = sharedPreferences.getString("BestScore","0").toString()
        playerName = sharedPreferences.getString("PlayerName","").toString()
        updateScores()
        fetchDataFromApi()

    }

    fun saveSharePref(){
        val sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)
        var input_nick = findViewById<TextInputEditText>(R.id.input_nickname)
        var view_latest_score = findViewById<TextView>(R.id.score_latest)
        var view_best_score = findViewById<TextView>(R.id.score_best)

        with(sharedPreferences.edit()) {
            putString("LatestScore", view_latest_score.text.toString())
            putString("BestScore", view_best_score.text.toString())
            putString("PlayerName", input_nick.text.toString())
            apply()
        }
    }
    fun updateScores(){
        var input_nick = findViewById<TextInputEditText>(R.id.input_nickname)
        var view_latest_score = findViewById<TextView>(R.id.score_latest)
        var view_best_score = findViewById<TextView>(R.id.score_best)

        input_nick.setText(playerName)
        view_best_score.setText(bestscore)
        view_latest_score.setText(latestscore)

    }
}