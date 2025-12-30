package com.example.quickmaths3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmaths3.data.FormulaCategory
import com.example.quickmaths3.data.FormulaData
import com.example.quickmaths3.data.TopicGroup
import com.example.quickmaths3.ui.theme.*

@Composable
fun InfinityModeSettingsScreen(
    enabledTopics: Set<TopicGroup>,
    onTopicsChanged: (Set<TopicGroup>) -> Unit,
    onStartInfinityMode: () -> Unit,
    onBackToMenu: () -> Unit
) {
    var selectedTopics by remember(enabledTopics) { mutableStateOf(enabledTopics) }

    val derivativeGroups = FormulaData.getTopicGroups(FormulaCategory.DERIVATIVES)
    val integralGroups = FormulaData.getTopicGroups(FormulaCategory.INTEGRALS)
    val allGroups = derivativeGroups + integralGroups

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

            // Top Bar
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
                    text = "∞ Select Topics",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
            }

            // Select All / Deselect All buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { selectedTopics = allGroups.toSet() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentPurple
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Select All", fontFamily = FontFamily.Serif, fontSize = 14.sp)
                }
                
                OutlinedButton(
                    onClick = { selectedTopics = emptySet() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("Deselect All", fontFamily = FontFamily.Serif, fontSize = 14.sp)
                }
            }

            // Topic list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    Text(
                        text = "📐 Derivatives",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(derivativeGroups) { group ->
                    TopicToggleCard(
                        group = group,
                        isSelected = group in selectedTopics,
                        onToggle = {
                            selectedTopics = if (group in selectedTopics) {
                                selectedTopics - group
                            } else {
                                selectedTopics + group
                            }
                        }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "∫ Integrals",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(integralGroups) { group ->
                    TopicToggleCard(
                        group = group,
                        isSelected = group in selectedTopics,
                        onToggle = {
                            selectedTopics = if (group in selectedTopics) {
                                selectedTopics - group
                            } else {
                                selectedTopics + group
                            }
                        }
                    )
                }
                
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }

            // Start Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        onTopicsChanged(selectedTopics)
                        onStartInfinityMode()
                    },
                    enabled = selectedTopics.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentPurple,
                        disabledContainerColor = DarkCard
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Start ∞ Mode",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(${selectedTopics.size} topics)",
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopicToggleCard(
    group: TopicGroup,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AccentPurple.copy(alpha = 0.2f) else DarkCard
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
            Text(
                text = group.displayName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isSelected) AccentPurple else Color.Transparent
                    )
                    .then(
                        if (!isSelected) Modifier.background(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
