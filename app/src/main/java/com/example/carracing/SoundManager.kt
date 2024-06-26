package com.example.carracing

import android.content.Context
import android.media.MediaPlayer

class SoundManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun playSound(resourceId: Int) {
        // Release any existing MediaPlayer instance
        mediaPlayer?.release()
        // Create a new MediaPlayer instance for the specified sound resource
        mediaPlayer = MediaPlayer.create(context, resourceId)

        // Start playing the sound
        mediaPlayer?.start()
        
        mediaPlayer?.isLooping = true

    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}