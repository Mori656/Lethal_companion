package com.example.lethal_companion.minigame

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lethal_companion.R
import java.util.LinkedList
import java.util.Queue
import java.util.Random
import kotlin.concurrent.thread

class Minigame : AppCompatActivity() {
    //Zmienne globalne używane w kodzie
    var scrap_table = arrayOf(R.id.scrap1)
    lateinit var scrap_respawn_table: Array<Float>
    var hp = 3
    lateinit var animation: Animation
    val scrapQueue: Queue<ImageView> = LinkedList()
    val handler = Handler()
    var timer = 5000
    val rand = Random()
    lateinit var playerImg: ImageView
    var player_pos = 0
    var actualscore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minigame)

        //Pobranie referencji do elementów widoku
        playerImg = findViewById(R.id.player_img)
        val button1 = findViewById<Button>(R.id.but_move1)
        val button2 = findViewById<Button>(R.id.but_move2)
        val button3 = findViewById<Button>(R.id.but_move3)
        val button4 = findViewById<Button>(R.id.but_move4)
        val button5 = findViewById<Button>(R.id.but_move5)


        val fixPosX = 22

        //Działania przycisków
        button1.setOnClickListener {
            playerImg.x = button1.x - button1.width/2 - fixPosX
            player_pos = 0
        }

        button2.setOnClickListener {
            playerImg.x = button2.x - button2.width/2 - fixPosX
            player_pos = 1
        }

        button3.setOnClickListener {
            playerImg.x = button3.x - button3.width/2 - fixPosX
            player_pos = 2
        }

        button4.setOnClickListener {
            playerImg.x = button4.x - button4.width/2 - fixPosX
            player_pos = 3
        }

        button5.setOnClickListener {
            //Tablica przechowująca możliwe miejsca pojawienia się scrapów
            scrap_respawn_table = arrayOf(button1.x + button1.width/2 - fixPosX,
                button2.x + button2.width/2 - fixPosX,
                button3.x + button3.width/2 - fixPosX,
                button4.x + button4.width/2 - fixPosX,
                button5.x + button5.width/2 - fixPosX)
            player_pos = 2

            //Pozycja gracza po starcie gry
            playerImg.x = button3.x - button3.width/2 - fixPosX
            playerImg.y = button1.y - 200

            //Ustawienie widocznosci gracza
            playerImg.visibility = View.VISIBLE
            gameStart()
        }


    }
    //Funkcja ustawiająca podstawowe parametry gry i ją rozpoczynająca
    fun gameStart() {
        hp = 3
        timer = 1500
        setScore(0)
        setHealth(hp)
        animation = AnimationUtils.loadAnimation(this, R.anim.drop_anim)
        gameContinue()

    }
    //Pętla gry
    fun gameContinue() {
        //Stworzenie nowego elementu który ma zacząc spadać
        var newScrap = createScrap(scrap_table[0])
        var a: Int = rand.nextInt(5)
        newScrap.x = scrap_respawn_table[a]
        var newScrapPosition = a


        handler.postDelayed({
            //Przygotowanie animacji
            animation = AnimationUtils.loadAnimation(this, R.anim.drop_anim)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    //sprawdzenie kolizji 1
                    var findcollision = false
                    handler.postDelayed({
                        if (player_pos == newScrapPosition){
                            findcollision = true
                        }
                    },2900)
                    //sprawdzenie kolizji 2
                    handler.postDelayed({
                        if (player_pos == newScrapPosition){
                            findcollision = true
                        }
                    },2950)
                    //sprawdzenie kolizji 3
                    handler.postDelayed({
                        if (player_pos == newScrapPosition){
                            findcollision = true
                        }
                        //Aktualizacja score oraz hp
                        if (findcollision){
                            actualscore+=50
                            setScore(actualscore)
                            setHealth(hp)
                        }else{
                            hp -=1
                            setHealth(hp)
                        }
                    },2990)


                }

                override fun onAnimationEnd(animation: Animation?) {
                    Log.d("ani1", "onAnimationEnd: ok")
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            newScrap.startAnimation(animation)
            gameContinue()
        }, timer.toLong())
    }

    fun createScrap(scrapID: Int): ImageView {
        // Tworzenie nowego ImageView
        val originalImageView = findViewById<ImageView>(scrapID)
        originalImageView.visibility = View.INVISIBLE;
        val newImageView = ImageView(this)
        // Kopiowanie właściwości z oryginalnego ImageView
        newImageView.setImageDrawable(originalImageView.drawable) // kopiowanie obrazka
        newImageView.layoutParams = originalImageView.layoutParams // kopiowanie parametrów layoutu
        newImageView.scaleType = originalImageView.scaleType // kopiowanie sposobu skalowania
        newImageView.id = View.generateViewId() // generowanie unikalnego ID dla nowego ImageView
        // Dodawanie nowego ImageView do rodzica oryginalnego ImageView
        val parentLayout = originalImageView.parent as ViewGroup
        parentLayout.addView(newImageView)
        // Ustawienie layoutu dla nowego ImageView
        val layoutParams = newImageView.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topToTop = originalImageView.id // ustawienie górnego krawędzi nowego ImageView na górną krawędź oryginalnego
        layoutParams.startToStart = originalImageView.id // ustawienie lewej krawędzi nowego ImageView na lewą krawędź oryginalnego
        layoutParams.marginStart =
            resources.getDimensionPixelSize(R.dimen.spacing) // dodanie marginesu na lewo
        layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.spacing) // dodanie marginesu na górę
        newImageView.layoutParams = layoutParams

        newImageView.visibility = View.INVISIBLE

        return newImageView;
    }
    //Funkcja ustawia atualny score
    fun setScore(score: Int){
        val scoreBox = findViewById<TextView>(R.id.text_score)
        scoreBox.setText("Score: " + score.toString())
    }
    //Funkcja ustawia aktualne hp
    fun setHealth(hp: Int){
        val healthBox = findViewById<TextView>(R.id.text_health)
        var healthnumber = ""
        for (i in 1 .. hp){
            healthnumber += "❤️ "
        }
        healthBox.setText("Score: " + healthnumber)
    }

}
