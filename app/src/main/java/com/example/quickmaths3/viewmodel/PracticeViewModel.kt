package com.example.quickmaths3.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmaths3.data.Question
import com.example.quickmaths3.data.QuestionGenerator
import com.example.quickmaths3.data.TopicGroup
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PracticeViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("quickmaths_progress", Context.MODE_PRIVATE)

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var currentIndex by mutableIntStateOf(0)
        private set

    var answeredCorrectly by mutableStateOf<Boolean?>(null)
        private set

    var selectedAnswerIndex by mutableStateOf<Int?>(null)
        private set

    var correctCount by mutableIntStateOf(0)
        private set

    var currentTopicGroup by mutableStateOf<TopicGroup?>(null)
        private set

    private val bestScores = mutableMapOf<String, Int>()

    init {
        loadProgress()
    }

    private fun loadProgress() {
        val progressString = prefs.getString("best_scores_v2", "") ?: ""
        if (progressString.isNotEmpty()) {
            progressString.split(";").forEach { entry ->
                val parts = entry.split(":")
                if (parts.size == 2) {
                    bestScores[parts[0]] = parts[1].toIntOrNull() ?: 0
                }
            }
        }
    }

    private fun saveProgress() {
        val progressString = bestScores.entries.joinToString(";") { "${it.key}:${it.value}" }
        prefs.edit().putString("best_scores_v2", progressString).apply()
    }

    fun loadQuestionsForTopicGroup(group: TopicGroup) {
        currentTopicGroup = group
        questions = QuestionGenerator.generateQuestionsForTopicGroup(group)
        currentIndex = 0
        answeredCorrectly = null
        selectedAnswerIndex = null
        correctCount = 0
    }

    fun selectAnswer(index: Int) {
        if (answeredCorrectly != null) return

        val currentQuestion = questions.getOrNull(currentIndex) ?: return
        selectedAnswerIndex = index
        val isCorrect = index == currentQuestion.correctIndex
        answeredCorrectly = isCorrect

        if (isCorrect) {
            correctCount++
            viewModelScope.launch {
                delay(1000)
                if (currentIndex < questions.size - 1) {
                    navigateForward()
                } else {
                    saveBestScore()
                }
            }
        }
    }

    private fun saveBestScore() {
        currentTopicGroup?.let { group ->
            val current = bestScores[group.name] ?: 0
            if (correctCount > current) {
                bestScores[group.name] = correctCount
                saveProgress()
            }
        }
    }

    fun navigateBack() {
        if (currentIndex > 0) {
            currentIndex--
            answeredCorrectly = null
            selectedAnswerIndex = null
        }
    }

    fun navigateForward() {
        if (currentIndex < questions.size - 1) {
            currentIndex++
            answeredCorrectly = null
            selectedAnswerIndex = null
        } else {
            saveBestScore()
        }
    }

    fun getTopicProgress(): Map<String, Int> {
        return bestScores.toMap()
    }

    fun isComplete(): Boolean = currentIndex >= questions.size - 1 && answeredCorrectly != null

    fun resetProgress() {
        bestScores.clear()
        prefs.edit().remove("best_scores_v2").apply()
    }
}
