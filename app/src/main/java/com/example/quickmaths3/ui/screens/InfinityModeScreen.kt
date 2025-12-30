package com.example.quickmaths3.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.quickmaths3.data.Question
import com.example.quickmaths3.ui.components.MathText
import com.example.quickmaths3.ui.components.AnswerMathText
import com.example.quickmaths3.ui.theme.*
import com.example.quickmaths3.util.FeedbackManager

@Composable
fun InfinityModeScreen(
    currentQuestion: Question?,
    streak: Int,
    onAnswerSelected: (Int) -> Unit,
    onBackToMenu: () -> Unit,
    onNextQuestion: () -> Unit,
    answeredCorrectly: Boolean?,
    selectedAnswerIndex: Int?,
    feedbackManager: FeedbackManager?
) {
    var formulaAcknowledged by remember(currentQuestion?.id) { mutableStateOf(false) }

    LaunchedEffect(answeredCorrectly) {
        if (answeredCorrectly != null && feedbackManager != null) {
            if (answeredCorrectly) {
                feedbackManager.playCorrectFeedback()
            } else {
                feedbackManager.playWrongFeedback()
            }
        }
    }

    if (currentQuestion == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AccentPurple)
        }
        return
    }

    val buttonColors = listOf(KahootRed, KahootBlue, KahootYellow, KahootGreen)
    
    val showFormulaOnWrong = answeredCorrectly == false && !formulaAcknowledged

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkBackground, DarkSurface)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            
            InfinityTopBar(
                streak = streak,
                onBackToMenu = onBackToMenu
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                MathText(
                    text = currentQuestion.questionText,
                    fontSize = 38.sp,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfinityAnswerButton(
                        text = currentQuestion.options.getOrElse(0) { "" },
                        baseColor = buttonColors[0],
                        state = getInfinityAnswerState(0, currentQuestion.correctIndex, selectedAnswerIndex, answeredCorrectly),
                        enabled = answeredCorrectly == null,
                        onClick = { 
                            feedbackManager?.playClickFeedback()
                            onAnswerSelected(0) 
                        },
                        modifier = Modifier.weight(1f)
                    )
                    InfinityAnswerButton(
                        text = currentQuestion.options.getOrElse(1) { "" },
                        baseColor = buttonColors[1],
                        state = getInfinityAnswerState(1, currentQuestion.correctIndex, selectedAnswerIndex, answeredCorrectly),
                        enabled = answeredCorrectly == null,
                        onClick = { 
                            feedbackManager?.playClickFeedback()
                            onAnswerSelected(1) 
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfinityAnswerButton(
                        text = currentQuestion.options.getOrElse(2) { "" },
                        baseColor = buttonColors[2],
                        state = getInfinityAnswerState(2, currentQuestion.correctIndex, selectedAnswerIndex, answeredCorrectly),
                        enabled = answeredCorrectly == null,
                        onClick = { 
                            feedbackManager?.playClickFeedback()
                            onAnswerSelected(2) 
                        },
                        modifier = Modifier.weight(1f)
                    )
                    InfinityAnswerButton(
                        text = currentQuestion.options.getOrElse(3) { "" },
                        baseColor = buttonColors[3],
                        state = getInfinityAnswerState(3, currentQuestion.correctIndex, selectedAnswerIndex, answeredCorrectly),
                        enabled = answeredCorrectly == null,
                        onClick = { 
                            feedbackManager?.playClickFeedback()
                            onAnswerSelected(3) 
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                val canGoForward = answeredCorrectly != null && (answeredCorrectly == true || formulaAcknowledged)
                val showForwardPrompt = answeredCorrectly == false && formulaAcknowledged

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "forwardPrompt")
                    val promptScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = if (showForwardPrompt) 1.15f else 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(500),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "promptScale"
                    )

                    IconButton(
                        onClick = onNextQuestion,
                        enabled = canGoForward,
                        modifier = Modifier
                            .size(52.dp)
                            .scale(if (showForwardPrompt) promptScale else 1f)
                            .clip(CircleShape)
                            .background(
                                if (showForwardPrompt) {
                                    Brush.linearGradient(listOf(AccentPurple, AccentBlue))
                                } else if (canGoForward) {
                                    Brush.linearGradient(listOf(DarkCard, DarkCard))
                                } else {
                                    Brush.linearGradient(listOf(DarkCard.copy(alpha = 0.3f), DarkCard.copy(alpha = 0.3f)))
                                }
                            )
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next question",
                            tint = if (canGoForward) Color.White else Color.White.copy(alpha = 0.3f),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
        
        if (showFormulaOnWrong) {
            InfinityWrongAnswerDialog(
                formula = currentQuestion.formulaHint,
                onAcknowledge = { formulaAcknowledged = true }
            )
        }
    }
}

private fun getInfinityAnswerState(
    buttonIndex: Int,
    correctIndex: Int,
    selectedIndex: Int?,
    answeredCorrectly: Boolean?
): AnswerState {
    if (answeredCorrectly == null) return AnswerState.NEUTRAL

    return when {
        buttonIndex == correctIndex -> AnswerState.SHOW_CORRECT
        buttonIndex == selectedIndex && !answeredCorrectly -> AnswerState.INCORRECT
        else -> AnswerState.NEUTRAL
    }
}

@Composable
private fun InfinityTopBar(
    streak: Int,
    onBackToMenu: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackToMenu,
            modifier = Modifier
                .clip(CircleShape)
                .background(DarkCard)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back to menu",
                tint = Color.White
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(AccentPurple, AccentBlue)
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = KahootOrange,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "∞",
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$streak",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )
        }
        
        // Empty spacer to balance the row
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun InfinityAnswerButton(
    text: String,
    baseColor: Color,
    state: AnswerState,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (state == AnswerState.SHOW_CORRECT) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            AnswerState.NEUTRAL -> baseColor
            AnswerState.CORRECT, AnswerState.SHOW_CORRECT -> CorrectGreenBright
            AnswerState.INCORRECT -> IncorrectRedBright
        },
        animationSpec = tween(300),
        label = "bgColor"
    )

    val scale = if (state == AnswerState.SHOW_CORRECT) pulseScale else 1f

    Box(
        modifier = modifier
            .height(85.dp)
            .scale(scale)
            .clip(RoundedCornerShape(14.dp))
            .background(backgroundColor)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AnswerMathText(
            text = text,
            fontSize = 17.sp,
            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
private fun InfinityWrongAnswerDialog(
    formula: String,
    onAcknowledge: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(DarkSurface)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "❌",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "INCORRECT",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = IncorrectRedBright,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Remember this:",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkCard)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formula,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 30.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onAcknowledge,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentPurple
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("I'll remember!", fontSize = 16.sp, fontFamily = FontFamily.Serif)
                }
            }
        }
    }
}
