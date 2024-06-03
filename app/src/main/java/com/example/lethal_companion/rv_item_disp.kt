package com.example.lethal_companion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class rv_item_disp : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var descTextView: TextView
    private lateinit var dangerLvlTextView: TextView
    private lateinit var sNameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var imgImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_item_disp)

        nameTextView = findViewById(R.id.name_disp)
        descTextView = findViewById(R.id.desc_disp)
        dangerLvlTextView = findViewById(R.id.dangerLvl_disp)
        sNameTextView = findViewById(R.id.sName_disp)
        priceTextView = findViewById(R.id.price_disp)
        imgImageView = findViewById(R.id.image_disp)
        val dispType = intent.getStringExtra("type")
        when (dispType) {
            "Monsters" -> dispMonsters()
            "Store" -> dispStore()
            "Logs" -> dispLogs()
            "Tips" -> dispTips()
        }
    }

    private fun dispMonsters() {
        priceTextView.visibility = View.GONE

        val name = intent.getStringExtra("name")
        val dangerLvl = intent.getStringExtra("dangerLvl")
        val sName = intent.getStringExtra("sName")
        val desc = intent.getStringExtra("desc")
        val img = intent.getStringExtra("img")

        nameTextView.text = name
        descTextView.text = desc
        dangerLvlTextView.text = dangerLvl
        sNameTextView.text = sName

        if (dangerLvl == "") dangerLvlTextView.visibility = View.GONE
        if (sName == "") sNameTextView.visibility = View.GONE
    }

    private fun dispStore() {
        sNameTextView.visibility = View.GONE
        dangerLvlTextView.visibility = View.GONE

        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val price = intent.getIntExtra("price",0)
        val img = intent.getStringExtra("img")

        nameTextView.text = name
        descTextView.text = desc
        priceTextView.text = "Price: $price"

    }

    private fun dispLogs() {
        priceTextView.visibility = View.GONE
        sNameTextView.visibility = View.GONE
        dangerLvlTextView.visibility = View.GONE

        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val img = intent.getStringExtra("img")

        nameTextView.text = name
        descTextView.text = desc

    }

    private fun dispTips() {
        priceTextView.visibility = View.GONE
        sNameTextView.visibility = View.GONE
        dangerLvlTextView.visibility = View.GONE
        imgImageView.visibility = View.GONE

        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")

        nameTextView.text = name
        descTextView.text = desc

    }

    fun back(view: View) {
        finish()
    }
}