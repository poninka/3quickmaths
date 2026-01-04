package com.example.quickmaths3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmaths3.data.TopicGroup
import com.example.quickmaths3.ui.screens.InfinityModeScreen
import com.example.quickmaths3.ui.screens.InfinityModeSettingsScreen
import com.example.quickmaths3.ui.screens.MainMenuScreen
import com.example.quickmaths3.ui.screens.PracticeScreen
import com.example.quickmaths3.ui.screens.SettingsScreen
import com.example.quickmaths3.ui.theme.QuickMaths3Theme
import com.example.quickmaths3.util.FeedbackManager
import com.example.quickmaths3.viewmodel.InfinityModeViewModel
import com.example.quickmaths3.viewmodel.PracticeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickMaths3Theme {
                QuickMathsApp()
            }
        }
    }
}

sealed class Screen {
    data object Menu : Screen()
    data class Practice(val topicGroup: TopicGroup) : Screen()
    data object InfinityModeSettings : Screen()
    data object InfinityMode : Screen()
    data object Settings : Screen()
}

@Composable
fun QuickMathsApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Menu) }
    
    val context = LocalContext.current
    val feedbackManager = remember { FeedbackManager(context) }
    
    val practiceViewModel: PracticeViewModel = viewModel()
    val infinityViewModel: InfinityModeViewModel = viewModel()

    DisposableEffect(Unit) {
        onDispose {
            feedbackManager.release()
        }
    }

    when (val screen = currentScreen) {
        is Screen.Menu -> {
            MainMenuScreen(
                onTopicSelected = { topicGroup ->
                    practiceViewModel.loadQuestionsForTopicGroup(topicGroup)
                    currentScreen = Screen.Practice(topicGroup)
                },
                onInfinityModeStart = {
                    // 1-click start - uses persisted topic selections
                    infinityViewModel.generateNextQuestion()
                    currentScreen = Screen.InfinityMode
                },
                onInfinityModeSettings = {
                    // Go to topic selection
                    currentScreen = Screen.InfinityModeSettings
                },
                onSettingsClick = {
                    currentScreen = Screen.Settings
                },
                topicProgress = practiceViewModel.getTopicProgress()
            )
        }

        is Screen.Practice -> {
            // Handle Android back button: go to previous question, or exit to menu if on first
            BackHandler {
                if (practiceViewModel.currentIndex > 0) {
                    practiceViewModel.navigateBack()
                } else {
                    currentScreen = Screen.Menu
                }
            }
            
            PracticeScreen(
                questions = practiceViewModel.questions,
                currentIndex = practiceViewModel.currentIndex,
                correctCount = practiceViewModel.correctCount,
                onAnswerSelected = { index ->
                    practiceViewModel.selectAnswer(index)
                },
                onNavigateBack = {
                    practiceViewModel.navigateBack()
                },
                onNavigateForward = {
                    if (practiceViewModel.isComplete()) {
                        currentScreen = Screen.Menu
                    } else {
                        practiceViewModel.navigateForward()
                    }
                },
                onBackToMenu = {
                    currentScreen = Screen.Menu
                },
                answeredCorrectly = practiceViewModel.answeredCorrectly,
                selectedAnswerIndex = practiceViewModel.selectedAnswerIndex,
                feedbackManager = feedbackManager,
                hintsUsed = practiceViewModel.hintsUsed,
                maxHints = feedbackManager.maxPracticeHints,
                showHint = practiceViewModel.showHint,
                onUseHint = { practiceViewModel.useHint(feedbackManager.maxPracticeHints) },
                onDismissHint = { practiceViewModel.dismissHint() },
                currentHint = practiceViewModel.getCurrentHint()
            )
        }

        is Screen.InfinityModeSettings -> {
            InfinityModeSettingsScreen(
                enabledTopics = infinityViewModel.enabledTopics,
                onTopicsChanged = { topics ->
                    infinityViewModel.setEnabledTopics(topics)
                },
                onStartInfinityMode = {
                    infinityViewModel.generateNextQuestion()
                    currentScreen = Screen.InfinityMode
                },
                onBackToMenu = {
                    currentScreen = Screen.Menu
                }
            )
        }

        is Screen.InfinityMode -> {
            InfinityModeScreen(
                currentQuestion = infinityViewModel.currentQuestion,
                streak = infinityViewModel.streak,
                onAnswerSelected = { index ->
                    infinityViewModel.selectAnswer(index)
                },
                onBackToMenu = {
                    currentScreen = Screen.Menu
                },
                onNextQuestion = {
                    infinityViewModel.nextQuestion()
                },
                answeredCorrectly = infinityViewModel.answeredCorrectly,
                selectedAnswerIndex = infinityViewModel.selectedAnswerIndex,
                feedbackManager = feedbackManager,
                hintPenalty = feedbackManager.hintPenalty,
                showHint = infinityViewModel.showHint,
                onUseHint = { infinityViewModel.useHint(feedbackManager.hintPenalty) },
                onDismissHint = { infinityViewModel.dismissHint() },
                currentHint = infinityViewModel.getCurrentHint()
            )
        }

        is Screen.Settings -> {
            SettingsScreen(
                feedbackManager = feedbackManager,
                onResetPracticeProgress = {
                    practiceViewModel.resetProgress()
                },
                onResetInfinityStats = {
                    infinityViewModel.resetStats()
                },
                onBackToMenu = {
                    currentScreen = Screen.Menu
                }
            )
        }
    }
}
