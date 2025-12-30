package com.example.quickmaths3.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MathText(
    text: String,
    fontSize: TextUnit = 38.sp,
    color: Color = Color.White,
    modifier: Modifier = Modifier
) {
    val parsed = parseMathExpression(text)
    
    when (parsed) {
        is MathExpr.Integral -> {
            IntegralDisplay(
                integrand = parsed.integrand,
                fontSize = fontSize,
                color = color,
                modifier = modifier
            )
        }
        is MathExpr.Derivative -> {
            DerivativeDisplay(
                inner = parsed.inner,
                fontSize = fontSize,
                color = color,
                modifier = modifier
            )
        }
        is MathExpr.Plain -> {
            Text(
                text = parsed.text,
                fontSize = fontSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                color = color,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
        }
    }
}

@Composable
fun AnswerMathText(
    text: String,
    fontSize: TextUnit = 17.sp,
    color: Color = Color.White,
    modifier: Modifier = Modifier
) {
    // Check if has fraction
    if (text.contains("/") && !text.contains("(") && !text.contains("|")) {
        val parts = text.split(" + C")
        val mainPart = parts[0].trim()
        val hasC = parts.size > 1
        
        val fractionMatch = Regex("""^([^/]+)/(\d+)$""").find(mainPart)
        if (fractionMatch != null) {
            val num = fractionMatch.groupValues[1]
            val denom = fractionMatch.groupValues[2]
            
            FractionDisplay(
                numerator = num,
                denominator = denom,
                suffix = if (hasC) " + C" else "",
                fontSize = fontSize,
                color = color,
                modifier = modifier
            )
            return
        }
    }
    
    // Plain text fallback
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        color = color,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
private fun IntegralDisplay(
    integrand: String,
    fontSize: TextUnit,
    color: Color,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        // Large integral sign
        Text(
            text = "∫",
            fontSize = (fontSize.value * 1.4f).sp,
            fontWeight = FontWeight.Normal,
            color = color
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = integrand,
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            color = color
        )
    }
}

@Composable
private fun DerivativeDisplay(
    inner: String,
    fontSize: TextUnit,
    color: Color,
    modifier: Modifier
) {
    val smallSize = (fontSize.value * 0.6f).sp
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        // Fraction part: d/dx
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "d",
                fontSize = smallSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                color = color
            )
            // Horizontal line
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(1.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawLine(
                        color = color,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 2f
                    )
                }
            }
            Text(
                text = "dx",
                fontSize = smallSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                color = color
            )
        }
        
        Spacer(modifier = Modifier.width(6.dp))
        
        // The expression [inner]
        Text(
            text = "[$inner]",
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            color = color
        )
    }
}

@Composable
private fun FractionDisplay(
    numerator: String,
    denominator: String,
    suffix: String = "",
    fontSize: TextUnit,
    color: Color,
    modifier: Modifier
) {
    val smallSize = (fontSize.value * 0.85f).sp
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = numerator,
                fontSize = smallSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                color = color
            )
            // Fraction line - adaptive width
            Box(
                modifier = Modifier
                    .widthIn(min = 12.dp, max = 50.dp)
                    .height(1.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawLine(
                        color = color,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 1.5f
                    )
                }
            }
            Text(
                text = denominator,
                fontSize = smallSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                color = color
            )
        }
        
        if (suffix.isNotEmpty()) {
            Text(
                text = suffix,
                fontSize = smallSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                color = color
            )
        }
    }
}

sealed class MathExpr {
    data class Integral(val integrand: String) : MathExpr()
    data class Derivative(val inner: String) : MathExpr()
    data class Plain(val text: String) : MathExpr()
}

private fun parseMathExpression(text: String): MathExpr {
    // Integral: ∫...dx
    if (text.startsWith("∫")) {
        val integrand = text.removePrefix("∫")
        return MathExpr.Integral(integrand)
    }
    
    // Derivative: d/dx[...]
    val derivativeRegex = Regex("""d/dx\[(.+?)\]""")
    val match = derivativeRegex.find(text)
    if (match != null) {
        return MathExpr.Derivative(match.groupValues[1])
    }
    
    return MathExpr.Plain(text)
}
