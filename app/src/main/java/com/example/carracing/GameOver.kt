package com.example.carracing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameOver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // Retrieve extras
        val score = intent.getIntExtra("score", 0)
        val highestScore = intent.getIntExtra("highestScore", 0)

        // Update TextViews with score and highest score
        val scoreEnd = findViewById<TextView>(R.id.scoreEnd)
        val highestScoreEnd = findViewById<TextView>(R.id.highestScoreEnd)
        scoreEnd.text = "Score: $score"
        highestScoreEnd.text = "Highest Score: $highestScore"
    }
    fun homeScreen(view: View){
    var i = Intent(applicationContext,MainActivity::class.java)
    startActivity(i)
    }
}