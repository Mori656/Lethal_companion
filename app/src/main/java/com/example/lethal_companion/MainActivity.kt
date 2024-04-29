package com.example.lethal_companion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.lethal_companion.bestiary.Bestiary
import com.example.lethal_companion.store.Store
import com.example.lethal_companion.logs.Logs
import com.example.lethal_companion.minigame.Minigame
import com.example.lethal_companion.tips.Tips

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activitybuttontable = arrayOf(R.id.but_bestiary,R.id.but_items,R.id.but_logs,R.id.but_tips,R.id.but_minigame);
        val classtable = arrayOf(Bestiary::class.java,Store::class.java,Logs::class.java,Tips::class.java,Minigame::class.java);

        for (i  in 0 until activitybuttontable.size){
            val buttonClick = findViewById<Button>(activitybuttontable[i])
            buttonClick.setOnClickListener {
                val intent = Intent(this, classtable[i])
                startActivity(intent)
            }
        }

    }
}
