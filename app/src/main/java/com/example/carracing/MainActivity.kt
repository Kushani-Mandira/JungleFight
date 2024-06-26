package com.example.carracing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(),GameTask {
    private lateinit var sharedPreferences: SharedPreferences //for saving highest score
//    private lateinit var mediaPlayer: MediaPlayer //for background music
    private val soundManager = SoundManager(this)

    lateinit var rootLayout : LinearLayout
    lateinit var startBtn : Button
    lateinit var mGameView: GameView
    lateinit var logoImageContainer : LinearLayout
    lateinit var lionLogo : ImageView
    lateinit var dearLogoImage : ImageView
    lateinit var scoreCradContainer : LinearLayout
    lateinit var highestScore : TextView
    lateinit var startButtonContainer : LinearLayout
    lateinit var headerName : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        soundManager.stopSound()
        soundManager.playSound(R.raw.background_music)
        super.onCreate(savedInstanceState)
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//        gameLayout = layoutInflater.inflate(R.layout.game_layout, null)

        // Initialize MediaPlayer and load background music

        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
//        score = findViewById(R.id.score)H
        lionLogo = findViewById(R.id.lionLogo)
        dearLogoImage = findViewById(R.id.lionLogo)
        logoImageContainer = findViewById(R.id.logoImageContainer)
        scoreCradContainer = findViewById(R.id.scoreCradContainer)
        highestScore = findViewById(R.id.highestScore)
        startButtonContainer = findViewById(R.id.startButtonContainer)
        headerName = findViewById(R.id.headerName)


        mGameView = GameView(this,this)
        highestScore.text = "Highest Score: ${getHighScore()}" // Set initial highest score
//        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
//        mediaPlayer.isLooping = true // Loop the music


        startBtn.setOnClickListener{
            mGameView.setBackgroundResource(R.drawable.min)
////            rootLayout.removeView(rootLayout)
////            rootLayout.addView(gameLayout)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
//            score.visibility = View.GONE
            lionLogo.visibility = View.GONE
            dearLogoImage.visibility = View.GONE
            logoImageContainer.visibility = View.GONE
            scoreCradContainer.visibility = View.GONE
            highestScore.visibility = View.GONE
            startButtonContainer.visibility = View.GONE
            headerName.visibility = View.GONE

        }
    }

    override fun closeGame(mScore: Int) {
        soundManager.stopSound()
        val highScore = getHighScore()
        if (mScore > highScore) {
            saveHighScore(mScore)
            highestScore.text = "Highest Score: $mScore" // Update displayed highest score
        }
        rootLayout.removeView(mGameView)
        var i =Intent(applicationContext,GameOver::class.java)
        i.putExtra("score", mScore)
        i.putExtra("highestScore", getHighScore())
        startActivity(i)
        finish()


    }

    private fun saveHighScore(score: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("highScore", score)
        editor.apply()
    }

    private fun getHighScore(): Int {
        return sharedPreferences.getInt("highScore", 0)
    }


}