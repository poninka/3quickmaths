package com.example.quickmaths3.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.example.quickmaths3.R

class FeedbackManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("quickmaths_settings", Context.MODE_PRIVATE)
    
    private var soundPool: SoundPool? = null
    private var correctSoundId: Int = 0
    private var wrongSoundId: Int = 0
    private var soundsLoaded: Boolean = false
    
    var hapticEnabled: Boolean
        get() = prefs.getBoolean("haptic_enabled", false)
        set(value) = prefs.edit().putBoolean("haptic_enabled", value).apply()
    
    var soundEnabled: Boolean
        get() = prefs.getBoolean("sound_enabled", false)
        set(value) = prefs.edit().putBoolean("sound_enabled", value).apply()
    
    var hintPenalty: Int
        get() = prefs.getInt("hint_penalty", 3)
        set(value) = prefs.edit().putInt("hint_penalty", value).apply()
    
    var maxPracticeHints: Int
        get() = prefs.getInt("max_practice_hints", 3)
        set(value) = prefs.edit().putInt("max_practice_hints", value).apply()

    init {
        initSoundPool()
    }

    private fun initSoundPool() {
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            
            soundPool = SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build()
            
            soundPool?.setOnLoadCompleteListener { _, _, status ->
                if (status == 0) {
                    soundsLoaded = true
                }
            }
            
            // Load custom sound files from res/raw
            correctSoundId = soundPool?.load(context, R.raw.sound_correct, 1) ?: 0
            wrongSoundId = soundPool?.load(context, R.raw.sound_wrong, 1) ?: 0
        } catch (e: Exception) {
            // Ignore - sounds won't work but app will function
        }
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
            if (correctSoundId != 0) {
                soundPool?.play(correctSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun playErrorSound() {
        try {
            if (wrongSoundId != 0) {
                soundPool?.play(wrongSoundId, 0.8f, 0.8f, 1, 0, 1.0f)
            }
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
