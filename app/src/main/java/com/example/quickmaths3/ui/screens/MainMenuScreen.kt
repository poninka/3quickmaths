package com.example.quickmaths3.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmaths3.data.FormulaCategory
import com.example.quickmaths3.data.FormulaData
import com.example.quickmaths3.data.TopicGroup
import com.example.quickmaths3.ui.theme.*
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onTopicSelected: (TopicGroup) -> Unit,
    onInfinityModeStart: () -> Unit,
    onInfinityModeSettings: () -> Unit,
    onSettingsClick: () -> Unit,
    topicProgress: Map<String, Int> = emptyMap()
) {
    var derivativesExpanded by remember { mutableStateOf(true) }
    var integralsExpanded by remember { mutableStateOf(true) }

    val derivativeGroups = FormulaData.getTopicGroups(FormulaCategory.DERIVATIVES)
    val integralGroups = FormulaData.getTopicGroups(FormulaCategory.INTEGRALS)

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "3 Quick Maths",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
                
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(DarkCard)
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }

            // Infinity Mode Row - Button + Settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Main Infinity Mode Button (1-click start)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onInfinityModeStart() },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(AccentPurple, AccentBlue)
                                )
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "∞ Infinity Mode",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Serif,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
                
                // Topic Selection Button
                IconButton(
                    onClick = onInfinityModeSettings,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DarkCard)
                ) {
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = "Select Topics",
                        tint = AccentPurple,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
            ) {
                item {
                    CategoryHeader(
                        title = "📐 Derivatives",
                        expanded = derivativesExpanded,
                        onToggle = { derivativesExpanded = !derivativesExpanded }
                    )
                }

                if (derivativesExpanded) {
                    items(derivativeGroups, key = { it.name }) { group ->
                        val bestScore = topicProgress[group.name] ?: 0
                        TopicGroupCard(
                            group = group,
                            bestScore = bestScore,
                            onClick = { onTopicSelected(group) }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    CategoryHeader(
                        title = "∫ Integrals",
                        expanded = integralsExpanded,
                        onToggle = { integralsExpanded = !integralsExpanded }
                    )
                }

                if (integralsExpanded) {
                    items(integralGroups, key = { it.name }) { group ->
                        val bestScore = topicProgress[group.name] ?: 0
                        TopicGroupCard(
                            group = group,
                            bestScore = bestScore,
                            onClick = { onTopicSelected(group) }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun CategoryHeader(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(DarkCard)
            .clickable { onToggle() }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Serif,
            color = Color.White
        )
        Icon(
            if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = if (expanded) "Collapse" else "Expand",
            tint = Color.White
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
private fun TopicGroupCard(
    group: TopicGroup,
    bestScore: Int,
    onClick: () -> Unit
) {
    var showFormulaPreview by remember { mutableStateOf(false) }
    
    // Use pre-cached counts - no filtering needed
    val formulaCount = FormulaData.getFormulaCountByTopicGroup(group)
    val questionCount = formulaCount // They're the same now
    
    val isPerfect = bestScore == questionCount
    val scoreColor by animateColorAsState(
        targetValue = when {
            isPerfect -> CorrectGreen
            bestScore >= questionCount * 3 / 4 -> KahootYellow
            bestScore > 0 -> KahootOrange
            else -> Color.Gray.copy(alpha = 0.5f)
        },
        animationSpec = tween(300),
        label = "scoreColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isPerfect) CorrectGreen.copy(alpha = 0.15f) else DarkCard.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = group.displayName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$formulaCount formulas • $questionCount questions",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White.copy(alpha = 0.55f)
                )
            }
            
            // Formula preview button
            IconButton(
                onClick = { showFormulaPreview = true },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Help,
                    contentDescription = "View formulas",
                    tint = AccentPurple,
                    modifier = Modifier.size(20.dp)
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(scoreColor.copy(alpha = 0.25f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$bestScore/$questionCount",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    color = scoreColor
                )
            }
        }
    }
    
    if (showFormulaPreview) {
        FormulaPreviewDialog(
            group = group,
            onDismiss = { showFormulaPreview = false }
        )
    }
}

@Composable
private fun FormulaPreviewDialog(
    group: TopicGroup,
    onDismiss: () -> Unit
) {
    val formulas = remember(group) { FormulaData.getFormulasByTopicGroup(group) }
    
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .clip(RoundedCornerShape(24.dp))
                .background(DarkSurface)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = group.displayName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${formulas.size} formulas to learn",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    formulas.forEach { formula ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkCard)
                                .padding(14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = formula.formulaDisplay,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentPurple
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Got it!", fontSize = 16.sp, fontFamily = FontFamily.Serif)
                }
            }
        }
    }
}
