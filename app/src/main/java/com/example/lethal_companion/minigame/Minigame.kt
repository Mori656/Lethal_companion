package com.example.lethal_companion.minigame

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lethal_companion.R
import java.util.LinkedList
import java.util.Queue
import java.util.Random


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
    lateinit var table_menu_ele: Array<View>
    lateinit var table_game_ele: Array<View>
    var screenWidth:Int = 0
    var screenHeight:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minigame)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        Log.d("height", screenHeight.toString())
        Log.d("width", screenWidth.toString())


        //Pobranie referencji do elementów widoku
        //Menu
        val button_start = findViewById<Button>(R.id.but_start)
        val button_top = findViewById<Button>(R.id.but_top)
        val text_latest_score = findViewById<TextView>(R.id.text_latests_score)
        val text_best_score = findViewById<TextView>(R.id.text_best_score)
        val score_latest = findViewById<TextView>(R.id.score_latest)
        val score_best = findViewById<TextView>(R.id.score_best)

        table_menu_ele = arrayOf(button_start,button_top,text_latest_score,text_best_score,score_best,score_latest)
        //Gra
        playerImg = findViewById(R.id.player_img)
        val button1 = findViewById<Button>(R.id.but_move1)
        val button2 = findViewById<Button>(R.id.but_move2)
        val button3 = findViewById<Button>(R.id.but_move3)
        val button4 = findViewById<Button>(R.id.but_move4)
        val button5 = findViewById<Button>(R.id.but_move5)
        val scoreBox = findViewById<TextView>(R.id.text_score)
        val healthBox = findViewById<TextView>(R.id.text_health)

        table_game_ele = arrayOf(button1,button2,button3,button4,button5,scoreBox,healthBox,playerImg)

        //Przygotowanie scrapów






        //Działania przycisków
        button1.setOnClickListener {
            playerImg.x = (screenWidth.toFloat()/6) - playerImg.width/2
            player_pos = 0
        }

        button2.setOnClickListener {
            playerImg.x = (screenWidth.toFloat()/6 * 2) - playerImg.width/2
            player_pos = 1
        }

        button3.setOnClickListener {
            playerImg.x = (screenWidth.toFloat()/6 * 3) - playerImg.width/2
            player_pos = 2
        }

        button4.setOnClickListener {
            playerImg.x = (screenWidth.toFloat()/6 * 4) - playerImg.width/2
            player_pos = 3
        }

        button5.setOnClickListener {
            playerImg.x = (screenWidth.toFloat()/6 * 5) - playerImg.width/2
            player_pos = 4
        }

        button_start.setOnClickListener {
            button1.x = screenWidth.toFloat()/6 - (button1.width/2)
            button2.x = (screenWidth.toFloat()/6 * 2) - (button1.width/2)
            button3.x = (screenWidth.toFloat()/6 * 3) - (button1.width/2)
            button4.x = (screenWidth.toFloat()/6 * 4) - (button1.width/2)
            button5.x = (screenWidth.toFloat()/6 * 5) - (button1.width/2)

            button1.height = screenHeight
            button2.height = screenHeight
            button3.height = screenHeight
            button4.height = screenHeight
            button5.height = screenHeight


            button1.alpha = 0.0f
            button2.alpha = 0.0f
            button3.alpha = 0.0f
            button4.alpha = 0.0f
            button5.alpha = 0.0f

            //Tablica przechowująca możliwe miejsca pojawienia się scrapów
            val fixScrapPosX = 22
            scrap_respawn_table = arrayOf(
                screenWidth.toFloat()/6 - fixScrapPosX,
                (screenWidth.toFloat()/6 * 2) - fixScrapPosX,
                (screenWidth.toFloat()/6 * 3) - fixScrapPosX,
                (screenWidth.toFloat()/6 * 4) - fixScrapPosX,
                (screenWidth.toFloat()/6 * 5) - fixScrapPosX
            )
            player_pos = 2

            //Pozycja gracza po starcie gry
            playerImg.x = (screenWidth.toFloat()/6 * 3) - (playerImg.width/2)
            playerImg.y = screenHeight * 0.63f

            scoreBox.y = screenHeight * 0.85f
            healthBox.y = screenHeight * 0.90f

            //Przygotowanie wartości i elementów gry
            hp = 3
            timer = 1500
            actualscore = 0
            setScore(0)
            setHealth(hp)
            showMenuElements(false)
            showGameElements(true)
            gameContinue()
        }


    }
    //Pętla gry
    fun gameContinue() {

        handler.postDelayed({
            //Stworzenie nowego elementu który ma zacząc spadać
            var newScrap = createScrap(scrap_table[0])
            var a: Int = rand.nextInt(5)
            newScrap.x = scrap_respawn_table[a]
            var newScrapPosition = a
            scrapQueue.offer(newScrap)
            //Przygotowanie animacji
            animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, screenHeight * 0.75f // Zmieniona wartość dla toYDelta
            )

            animation.duration = 3000

            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {

                    if(hp <= 0) {
                        endAllAnimation()
                    }

                    Log.d("x", newScrap.x.toString())
                    //sprawdzenie kolizji 1
                    var findcollision = false

                    handler.postDelayed({
                        if (player_pos == newScrapPosition){
                            findcollision = true
                        }
                    },2200)
                    //sprawdzenie kolizji 2
                    handler.postDelayed({
                        if (player_pos == newScrapPosition){
                            findcollision = true
                        }
                    },2250)
                    //sprawdzenie kolizji 3
                    handler.postDelayed({
                        if (player_pos == newScrapPosition){
                            findcollision = true
                        }
                        //Aktualizacja score oraz hp
                        if (findcollision){
                            newScrap.clearAnimation()
                            actualscore+=50
                            setScore(actualscore)
                            setHealth(hp)
                            if (timer > 4000){
                                timer -= 200
                            }else  if (timer > 3000){
                                timer -= 150
                            }else if (timer > 2000){
                                timer -= 120
                            }else if (timer > 1500){
                                timer -= 80
                            }else if (timer > 1000){
                                timer -= 50
                            }else if (timer > 500){
                                timer -= 20
                            }
                        }else{
                            hp -=1
                        }
                        scrapQueue.poll()
                    },2290)


                }

                override fun onAnimationEnd(animation: Animation?) {
                    setHealth(hp)
                    if(hp <= 0) {
                        endAllAnimation()
                        showMenuElements(true)
                        showGameElements(false)
                    }
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            newScrap.startAnimation(animation)
            //Sprawdzenie końca gry
            if(hp <= 0){
                setLatestScore(actualscore)
                val actualbestscore = findViewById<TextView>(R.id.score_best).text
                if(actualscore > actualbestscore.toString().toInt()){
                    setBestScore(actualscore)
                }
            }
            else{
                gameContinue()
            }

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
    fun setLatestScore(score: Int){
        val scorelatest = findViewById<TextView>(R.id.score_latest)
        scorelatest.setText(score.toString())
    }
    fun setBestScore(score: Int){
        val scorebest = findViewById<TextView>(R.id.score_best)
        scorebest.setText(score.toString())
    }

    fun showGameElements(show: Boolean){
        if(show){
            for (ele in table_game_ele){
                ele.visibility = View.VISIBLE
            }
        }else{
            for (ele in table_game_ele){
                ele.visibility = View.INVISIBLE
            }
        }
    }

    fun showMenuElements(show: Boolean){
        if(show){
            for (ele in table_menu_ele){
                ele.visibility = View.VISIBLE
            }
        }else{
            for (ele in table_menu_ele){
                ele.visibility = View.INVISIBLE
            }
        }
    }

    fun endAllAnimation(){
        for (ele in scrapQueue){
            ele.clearAnimation()
        }
        scrapQueue.clear()
    }

}
