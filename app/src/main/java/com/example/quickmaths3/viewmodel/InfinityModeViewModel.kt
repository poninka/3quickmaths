package com.example.quickmaths3.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmaths3.data.FormulaCategory
import com.example.quickmaths3.data.FormulaData
import com.example.quickmaths3.data.Question
import com.example.quickmaths3.data.QuestionGenerator
import com.example.quickmaths3.data.TopicGroup
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfinityModeViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("quickmaths_infinity", Context.MODE_PRIVATE)

    var currentQuestion by mutableStateOf<Question?>(null)
        private set

    var streak by mutableIntStateOf(0)
        private set

    var answeredCorrectly by mutableStateOf<Boolean?>(null)
        private set

    var selectedAnswerIndex by mutableStateOf<Int?>(null)
        private set

    // Track error rates per formula for adaptive learning
    private val errorCounts = mutableMapOf<String, Int>()
    private val attemptCounts = mutableMapOf<String, Int>()
    
    // Enabled topics for infinity mode
    private var _enabledTopics by mutableStateOf<Set<TopicGroup>>(emptySet())
    val enabledTopics: Set<TopicGroup> get() = _enabledTopics

    // Recently shown formulas to avoid repetition
    private val recentFormulas = mutableListOf<String>()
    private val maxRecentFormulas = 5
    
    // Hint state
    var showHint by mutableStateOf(false)
        private set

    init {
        loadStats()
        loadEnabledTopics()
    }

    private fun loadStats() {
        val errorString = prefs.getString("error_counts", "") ?: ""
        val attemptString = prefs.getString("attempt_counts", "") ?: ""
        
        if (errorString.isNotEmpty()) {
            errorString.split(";").forEach { entry ->
                val parts = entry.split(":")
                if (parts.size == 2) {
                    errorCounts[parts[0]] = parts[1].toIntOrNull() ?: 0
                }
            }
        }
        
        if (attemptString.isNotEmpty()) {
            attemptString.split(";").forEach { entry ->
                val parts = entry.split(":")
                if (parts.size == 2) {
                    attemptCounts[parts[0]] = parts[1].toIntOrNull() ?: 0
                }
            }
        }
    }

    private fun loadEnabledTopics() {
        val topicsString = prefs.getString("enabled_topics", "") ?: ""
        _enabledTopics = if (topicsString.isEmpty()) {
            // Default: all topics enabled
            TopicGroup.entries.toSet()
        } else {
            topicsString.split(",").mapNotNull { name ->
                try { TopicGroup.valueOf(name) } catch (e: Exception) { null }
            }.toSet()
        }
    }

    private fun saveStats() {
        val errorString = errorCounts.entries.joinToString(";") { "${it.key}:${it.value}" }
        val attemptString = attemptCounts.entries.joinToString(";") { "${it.key}:${it.value}" }
        prefs.edit()
            .putString("error_counts", errorString)
            .putString("attempt_counts", attemptString)
            .apply()
    }

    fun setEnabledTopics(topics: Set<TopicGroup>) {
        _enabledTopics = topics
        val topicsString = topics.joinToString(",") { it.name }
        prefs.edit().putString("enabled_topics", topicsString).apply()
    }

    fun generateNextQuestion() {
        val errorRates = calculateErrorRates()
        currentQuestion = QuestionGenerator.generateInfinityQuestion(enabledTopics, errorRates)
        answeredCorrectly = null
        selectedAnswerIndex = null
        
        // Track recent to avoid repetition
        currentQuestion?.let { q ->
            recentFormulas.add(q.formulaId)
            if (recentFormulas.size > maxRecentFormulas) {
                recentFormulas.removeAt(0)
            }
        }
    }

    private fun calculateErrorRates(): Map<String, Float> {
        val rates = mutableMapOf<String, Float>()
        for ((formulaId, attempts) in attemptCounts) {
            if (attempts > 0) {
                val errors = errorCounts[formulaId] ?: 0
                rates[formulaId] = errors.toFloat() / attempts.toFloat()
            }
        }
        return rates
    }

    fun selectAnswer(index: Int) {
        if (answeredCorrectly != null) return

        val question = currentQuestion ?: return
        selectedAnswerIndex = index
        val isCorrect = index == question.correctIndex
        answeredCorrectly = isCorrect

        // Track attempts and errors
        val formulaId = question.formulaId
        attemptCounts[formulaId] = (attemptCounts[formulaId] ?: 0) + 1
        
        if (isCorrect) {
            streak++
            val answeredQuestion = currentQuestion
            viewModelScope.launch {
                delay(1000)
                // Only auto-advance if still on the same question
                if (currentQuestion == answeredQuestion) {
                    generateNextQuestion()
                }
            }
        } else {
            errorCounts[formulaId] = (errorCounts[formulaId] ?: 0) + 1
            streak = 0
        }
        
        saveStats()
    }

    fun nextQuestion() {
        generateNextQuestion()
    }

    fun resetStats() {
        errorCounts.clear()
        attemptCounts.clear()
        streak = 0
        prefs.edit()
            .remove("error_counts")
            .remove("attempt_counts")
            .apply()
    }
    
    fun useHint(penalty: Int) {
        if (answeredCorrectly != null) return // Already answered
        streak = maxOf(0, streak - penalty)
        showHint = true
    }
    
    fun dismissHint() {
        showHint = false
    }
    
    fun getCurrentHint(): String? {
        return currentQuestion?.formulaHint
    }
}
