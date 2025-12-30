package com.example.quickmaths3.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class FeedbackManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("quickmaths_settings", Context.MODE_PRIVATE)
    
    private var soundPool: SoundPool? = null
    
    var hapticEnabled: Boolean
        get() = prefs.getBoolean("haptic_enabled", false)
        set(value) = prefs.edit().putBoolean("haptic_enabled", value).apply()
    
    var soundEnabled: Boolean
        get() = prefs.getBoolean("sound_enabled", false)
        set(value) = prefs.edit().putBoolean("sound_enabled", value).apply()

    init {
        initSoundPool()
    }

    private fun initSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    fun playClickFeedback() {
        if (hapticEnabled) {
            vibrateClick()
        }
    }

    fun playCorrectFeedback() {
        if (hapticEnabled) {
            vibrateSuccess()
        }
        if (soundEnabled) {
            playSuccessSound()
        }
    }

    fun playWrongFeedback() {
        if (hapticEnabled) {
            vibrateError()
        }
        if (soundEnabled) {
            playErrorSound()
        }
    }

    private fun vibrateClick() {
        try {
            val vibrator = getVibrator() ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(15)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun vibrateSuccess() {
        try {
            val vibrator = getVibrator() ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Two short pleasant pulses
                val timings = longArrayOf(0, 40, 60, 40)
                val amplitudes = intArrayOf(0, 120, 0, 180)
                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(longArrayOf(0, 40, 60, 40), -1)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun vibrateError() {
        try {
            val vibrator = getVibrator() ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Single strong pulse
                vibrator.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(80)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun getVibrator(): Vibrator? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun playSuccessSound() {
        try {
            // Use notification sound for a pleasant ding
            val notification = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI
            val ringtone = android.media.RingtoneManager.getRingtone(context, notification)
            ringtone?.play()
        } catch (e: Exception) {
            // Fallback to simple tone
            try {
                val toneGen = android.media.ToneGenerator(android.media.AudioManager.STREAM_NOTIFICATION, 40)
                toneGen.startTone(android.media.ToneGenerator.TONE_PROP_ACK, 100)
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    try { toneGen.release() } catch (e: Exception) { }
                }, 150)
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    private fun playErrorSound() {
        try {
            val toneGen = android.media.ToneGenerator(android.media.AudioManager.STREAM_NOTIFICATION, 30)
            toneGen.startTone(android.media.ToneGenerator.TONE_PROP_BEEP2, 80)
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                try { toneGen.release() } catch (e: Exception) { }
            }, 100)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun release() {
        try {
            soundPool?.release()
            soundPool = null
        } catch (e: Exception) {
            // Ignore
        }
    }
}
