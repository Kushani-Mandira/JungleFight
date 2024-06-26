package com.example.carracing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView (var c:Context ,var gameTask :GameTask):View(c){
    private var myPaint : Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var dearPosition = 0
    private val otherPngs = ArrayList<HashMap<String,Any>>()

    var viewWidth = 0
    var viewHeight = 0
    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if(time % 700 < 10 + speed){
            val map = HashMap<String ,Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherPngs.add(map)
        }
        time = time + 10 + speed
        val pngWidth = viewWidth/5
        val pngHeight = pngWidth + 10
        myPaint!!.style = Paint.Style.FILL
        var d = resources.getDrawable(R.drawable.dear,null)
        d.setBounds(
            dearPosition * viewWidth / 3 + viewWidth / 15 + 25 ,
            viewHeight-2 - pngHeight,
            dearPosition * viewWidth /3 + viewWidth / 15 + pngWidth - 25 ,
            viewHeight - 2

        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for (i in otherPngs.indices){
            try{
                val pngX = otherPngs[i]["lane"] as Int * viewWidth / 3 + viewHeight / 15
                var pngY = time - otherPngs[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.lion , null)

                d2.setBounds(
                    pngX + 25 , pngY - pngHeight , pngX + pngWidth - 25, pngY
                )
                d2.draw(canvas)
                if (otherPngs[i]["lane"] as Int == dearPosition){
                    if (pngY > viewHeight - 2 - pngHeight && pngY < viewHeight - 2){
                        gameTask.closeGame(score)
                    }
                }
                if (pngY > viewHeight + pngHeight) {
                    otherPngs.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if (score > highScore){
                        highScore = score
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        myPaint!!.color = Color.DKGRAY
        myPaint!!.textSize = 40f

        // Draw the score bar
        val barWidth = 400f
        val barHeight = 60f
        val barX = 80f
        val barY = 50f
        canvas.drawRect(barX, barY, barX + barWidth, barY + barHeight, myPaint!!)

// Calculate the percentage of the score within the bar
        val maxScore = 1000 // Example maximum score
        val scorePercentage = (score.toFloat() / maxScore) * barWidth

// Draw the filled portion of the score bar
        myPaint!!.color = Color.GREEN // Set color to green for the filled portion
        canvas.drawRect(barX, barY, barX + scorePercentage, barY + barHeight, myPaint!!)

// Calculate the text position for the score and speed
        val textX = barX + 20f // Adjust this value for text positioning
        val textY = barY + barHeight / 2 + myPaint!!.textSize / 2 // Adjust this value for text positioning

// Draw the score text inside the score bar
        myPaint!!.color = Color.WHITE // Set color to white for the text
        canvas.drawText("Score : $score", textX, textY, myPaint!!)

// Calculate the width of the score text to position the speed text
        val scoreTextWidth = myPaint!!.measureText("Score : $score")

// Calculate the text position for the speed
        val speedTextX = textX + scoreTextWidth + 20f // Adjust this value for text positioning
        canvas.drawText("Speed : $speed", speedTextX, textY, myPaint!!)

// Invalidate the canvas to trigger redraw
        invalidate()


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if(x1 < viewWidth / 2){
                    if (dearPosition > 0){
                        dearPosition --
                    }
                }
                if (x1 > viewWidth / 2){
                    if (dearPosition < 2){
                        dearPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {}
        }
        return true
    }



}