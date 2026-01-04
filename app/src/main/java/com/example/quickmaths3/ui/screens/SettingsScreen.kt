package com.example.quickmaths3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmaths3.ui.theme.*
import com.example.quickmaths3.util.FeedbackManager

@Composable
fun SettingsScreen(
    feedbackManager: FeedbackManager,
    onResetPracticeProgress: () -> Unit,
    onResetInfinityStats: () -> Unit,
    onBackToMenu: () -> Unit
) {
    var hapticEnabled by remember { mutableStateOf(feedbackManager.hapticEnabled) }
    var soundEnabled by remember { mutableStateOf(feedbackManager.soundEnabled) }
    var showResetProgressDialog by remember { mutableStateOf(false) }
    var showResetInfinityDialog by remember { mutableStateOf(false) }

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
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
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.width(14.dp))
                
                Text(
                    text = "⚙️ Settings",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Feedback",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                SettingsToggle(
                    icon = Icons.Default.Vibration,
                    title = "Haptic Feedback",
                    description = "Vibrate on answer selection",
                    checked = hapticEnabled,
                    onCheckedChange = {
                        hapticEnabled = it
                        feedbackManager.hapticEnabled = it
                    }
                )

                SettingsToggle(
                    icon = Icons.Default.Notifications,
                    title = "Sound Effects",
                    description = "Play sounds for correct/wrong answers",
                    checked = soundEnabled,
                    onCheckedChange = {
                        soundEnabled = it
                        feedbackManager.soundEnabled = it
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Hints",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                var hintPenalty by remember { mutableIntStateOf(feedbackManager.hintPenalty) }
                var maxPracticeHints by remember { mutableIntStateOf(feedbackManager.maxPracticeHints) }
                
                // Infinity Mode Hint Penalty
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Infinity Hint Penalty",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                                color = Color.White
                            )
                            Text(
                                text = "-$hintPenalty streak",
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Serif,
                                color = AccentPurple
                            )
                        }
                        Slider(
                            value = hintPenalty.toFloat(),
                            onValueChange = { 
                                hintPenalty = it.toInt()
                                feedbackManager.hintPenalty = it.toInt()
                            },
                            valueRange = 1f..10f,
                            steps = 8,
                            colors = SliderDefaults.colors(
                                thumbColor = AccentPurple,
                                activeTrackColor = AccentPurple
                            )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Practice Max Hints
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Practice Max Hints",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                                color = Color.White
                            )
                            Text(
                                text = "$maxPracticeHints per section",
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Serif,
                                color = AccentPurple
                            )
                        }
                        Slider(
                            value = maxPracticeHints.toFloat(),
                            onValueChange = { 
                                maxPracticeHints = it.toInt()
                                feedbackManager.maxPracticeHints = it.toInt()
                            },
                            valueRange = 1f..10f,
                            steps = 8,
                            colors = SliderDefaults.colors(
                                thumbColor = AccentPurple,
                                activeTrackColor = AccentPurple
                            )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Reset Data",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                // Reset Practice Progress
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    ),
                    shape = RoundedCornerShape(14.dp),
                    onClick = { showResetProgressDialog = true }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null,
                            tint = KahootOrange,
                            modifier = Modifier.size(26.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(14.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Reset Practice Progress",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                                color = Color.White
                            )
                            Text(
                                text = "Clear best scores (0/20 on all topics)",
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
                
                // Reset Infinity Mode Stats
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    ),
                    shape = RoundedCornerShape(14.dp),
                    onClick = { showResetInfinityDialog = true }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null,
                            tint = AccentPurple,
                            modifier = Modifier.size(26.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(14.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Reset Infinity Mode Stats",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                                color = Color.White
                            )
                            Text(
                                text = "Clear error rates and adaptive learning data",
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Haptics and sounds are off by default",
                fontSize = 11.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White.copy(alpha = 0.4f),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 28.dp)
            )
        }
        
        if (showResetProgressDialog) {
            AlertDialog(
                onDismissRequest = { showResetProgressDialog = false },
                title = { 
                    Text(
                        "Reset Practice Progress?", 
                        color = Color.White,
                        fontFamily = FontFamily.Serif
                    ) 
                },
                text = { 
                    Text(
                        "This will reset all your best scores to 0/20.", 
                        color = Color.White.copy(alpha = 0.7f),
                        fontFamily = FontFamily.Serif
                    ) 
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onResetPracticeProgress()
                            showResetProgressDialog = false
                        }
                    ) {
                        Text("Reset", color = IncorrectRed, fontFamily = FontFamily.Serif)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetProgressDialog = false }) {
                        Text("Cancel", color = AccentBlue, fontFamily = FontFamily.Serif)
                    }
                },
                containerColor = DarkCard
            )
        }
        
        if (showResetInfinityDialog) {
            AlertDialog(
                onDismissRequest = { showResetInfinityDialog = false },
                title = { 
                    Text(
                        "Reset Infinity Mode Stats?", 
                        color = Color.White,
                        fontFamily = FontFamily.Serif
                    ) 
                },
                text = { 
                    Text(
                        "This will clear your error tracking and adaptive learning data. Topics will be selected randomly again.", 
                        color = Color.White.copy(alpha = 0.7f),
                        fontFamily = FontFamily.Serif
                    ) 
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onResetInfinityStats()
                            showResetInfinityDialog = false
                        }
                    ) {
                        Text("Reset", color = IncorrectRed, fontFamily = FontFamily.Serif)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetInfinityDialog = false }) {
                        Text("Cancel", color = AccentBlue, fontFamily = FontFamily.Serif)
                    }
                },
                containerColor = DarkCard
            )
        }
    }
}

@Composable
private fun SettingsToggle(
    icon: ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = DarkCard
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = AccentPurple,
                modifier = Modifier.size(26.dp)
            )
            
            Spacer(modifier = Modifier.width(14.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
            
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = AccentPurple,
                    uncheckedThumbColor = Color.White.copy(alpha = 0.6f),
                    uncheckedTrackColor = DarkCard
                )
            )
        }
    }
}
