package com.example.quickmaths3.data

import kotlin.random.Random

object QuestionGenerator {

    private val chainRuleCoeffs = listOf(2, 3, 4, 5)
    private val chainRulePowers = listOf(2, 3, 4, 5)
    
    private val superscriptMap = mapOf(
        '0' to '⁰', '1' to '¹', '2' to '²', '3' to '³', '4' to '⁴',
        '5' to '⁵', '6' to '⁶', '7' to '⁷', '8' to '⁸', '9' to '⁹'
    )

    fun getQuestionCountForTopicGroup(group: TopicGroup): Int {
        return FormulaData.getFormulaCountByTopicGroup(group)
    }

    fun generateQuestionsForTopicGroup(group: TopicGroup, count: Int = getQuestionCountForTopicGroup(group)): List<Question> {
        val formulas = FormulaData.getFormulasByTopicGroup(group)
        if (formulas.isEmpty()) return emptyList()

        // Generate one question per formula for reliable count
        val questions = formulas.mapIndexed { index, formula ->
            generateBasicQuestion(formula, index)
        }

        return questions.shuffled()
    }

    private fun generateBasicQuestion(formula: Formula, id: Int): Question {
        val allOptions = mutableListOf(formula.answerTemplate)
        allOptions.addAll(formula.distractors)
        allOptions.shuffle()

        return Question(
            id = "q_${formula.id}_$id",
            formulaId = formula.id,
            questionText = formula.questionTemplate,
            options = allOptions,
            correctIndex = allOptions.indexOf(formula.answerTemplate),
            formulaHint = formula.formulaDisplay
        )
    }

    private fun generateChainRuleQuestion(formula: Formula, id: Int): Question {
        val power = chainRulePowers.random()
        val powerMinus1 = power - 1
        
        val powerStr = power.toString().map { superscriptMap[it] ?: it }.joinToString("")
        val powerMinus1Str = if (powerMinus1 == 1) "" else powerMinus1.toString().map { superscriptMap[it] ?: it }.joinToString("")
        
        return if (formula.category == FormulaCategory.INTEGRALS) {
            generateChainRuleIntegral(formula, id, power, powerStr, powerMinus1Str)
        } else {
            generateChainRuleDerivative(formula, id, power, powerStr, powerMinus1Str)
        }
    }

    private fun generateChainRuleIntegral(
        formula: Formula, 
        id: Int, 
        power: Int,
        powerStr: String,
        powerMinus1Str: String
    ): Question {
        val actualCoeff = power
        
        val funcName = when {
            formula.id.contains("sin") -> "sin"
            formula.id.contains("cos") -> "cos"
            formula.id.contains("tan") -> "tan"
            formula.id.contains("sec2") -> "sec²"
            formula.id.contains("csc2") -> "csc²"
            formula.id.contains("ex") -> "e"
            formula.id.contains("sinh") -> "sinh"
            formula.id.contains("cosh") -> "cosh"
            else -> return generateBasicQuestion(formula, id)
        }
        
        val innerFunc = "x$powerStr"
        
        val questionText = when (funcName) {
            "e" -> "∫${actualCoeff}x${powerMinus1Str}eˣ${powerStr}dx"
            else -> "∫${actualCoeff}x${powerMinus1Str}$funcName($innerFunc)dx"
        }
        
        val correctAnswer = when {
            formula.id == "i_sin" -> "-cos($innerFunc) + C"
            formula.id == "i_cos" -> "sin($innerFunc) + C"
            formula.id == "i_tan" -> "-ln|cos($innerFunc)| + C"
            formula.id == "i_sec2" -> "tan($innerFunc) + C"
            formula.id == "i_csc2" -> "-cot($innerFunc) + C"
            formula.id == "i_ex" -> "eˣ$powerStr + C"
            formula.id == "i_sinh" -> "cosh($innerFunc) + C"
            formula.id == "i_cosh" -> "sinh($innerFunc) + C"
            else -> formula.answerTemplate
        }
        
        val distractors = when {
            formula.id == "i_sin" -> listOf("cos($innerFunc) + C", "-sin($innerFunc) + C", "sin($innerFunc) + C")
            formula.id == "i_cos" -> listOf("-sin($innerFunc) + C", "cos($innerFunc) + C", "-cos($innerFunc) + C")
            formula.id == "i_tan" -> listOf("ln|cos($innerFunc)| + C", "sec($innerFunc) + C", "tan($innerFunc) + C")
            formula.id == "i_sec2" -> listOf("-tan($innerFunc) + C", "sec($innerFunc) + C", "cot($innerFunc) + C")
            formula.id == "i_ex" -> listOf("${actualCoeff}eˣ$powerStr + C", "x$powerStr·eˣ$powerStr + C", "eˣ + C")
            formula.id == "i_sinh" -> listOf("-cosh($innerFunc) + C", "sinh($innerFunc) + C", "tanh($innerFunc) + C")
            formula.id == "i_cosh" -> listOf("-sinh($innerFunc) + C", "cosh($innerFunc) + C", "tanh($innerFunc) + C")
            else -> formula.distractors
        }
        
        val allOptions = mutableListOf(correctAnswer)
        allOptions.addAll(distractors.take(3))
        allOptions.shuffle()
        
        return Question(
            id = "q_${formula.id}_chain_$id",
            formulaId = formula.id,
            questionText = questionText,
            options = allOptions,
            correctIndex = allOptions.indexOf(correctAnswer),
            formulaHint = "u = $innerFunc, du = ${actualCoeff}x${powerMinus1Str}dx\n${formula.formulaDisplay}"
        )
    }

    private fun generateChainRuleDerivative(
        formula: Formula,
        id: Int,
        power: Int,
        powerStr: String,
        powerMinus1Str: String
    ): Question {
        val funcName = when {
            formula.id.contains("d_sin") -> "sin"
            formula.id.contains("d_cos") -> "cos"
            formula.id.contains("d_tan") -> "tan"
            formula.id.contains("d_sec") && !formula.id.contains("csc") -> "sec"
            formula.id.contains("d_csc") -> "csc"
            formula.id.contains("d_cot") -> "cot"
            formula.id.contains("d_arcsin") -> "arcsin"
            formula.id.contains("d_arccos") -> "arccos"
            formula.id.contains("d_arctan") -> "arctan"
            formula.id.contains("d_sinh") -> "sinh"
            formula.id.contains("d_cosh") -> "cosh"
            formula.id.contains("d_tanh") -> "tanh"
            formula.id.contains("d_ex") -> "e"
            formula.id.contains("d_ln") -> "ln"
            else -> return generateBasicQuestion(formula, id)
        }
        
        val innerFunc = "x$powerStr"
        val innerDerivative = "${power}x${powerMinus1Str}"
        
        val questionText = when (funcName) {
            "e" -> "d/dx[eˣ$powerStr]"
            "ln" -> "d/dx[ln($innerFunc)]"
            else -> "d/dx[$funcName($innerFunc)]"
        }
        
        val correctAnswer = when {
            formula.id == "d_sin" -> "${innerDerivative}cos($innerFunc)"
            formula.id == "d_cos" -> "-${innerDerivative}sin($innerFunc)"
            formula.id == "d_tan" -> "${innerDerivative}sec²($innerFunc)"
            formula.id == "d_sec" -> "${innerDerivative}sec($innerFunc)tan($innerFunc)"
            formula.id == "d_csc" -> "-${innerDerivative}csc($innerFunc)cot($innerFunc)"
            formula.id == "d_cot" -> "-${innerDerivative}csc²($innerFunc)"
            formula.id == "d_arcsin" -> "$innerDerivative/√(1-x${(power*2).toString().map { superscriptMap[it] ?: it }.joinToString("")})"
            formula.id == "d_arctan" -> "$innerDerivative/(1+x${(power*2).toString().map { superscriptMap[it] ?: it }.joinToString("")})"
            formula.id == "d_sinh" -> "${innerDerivative}cosh($innerFunc)"
            formula.id == "d_cosh" -> "${innerDerivative}sinh($innerFunc)"
            formula.id == "d_tanh" -> "${innerDerivative}sech²($innerFunc)"
            formula.id == "d_ex" -> "${innerDerivative}eˣ$powerStr"
            formula.id == "d_ln" -> "$innerDerivative/x$powerStr"
            else -> formula.answerTemplate
        }
        
        val distractors = when {
            formula.id == "d_sin" -> listOf("cos($innerFunc)", "-${innerDerivative}cos($innerFunc)", "${innerDerivative}sin($innerFunc)")
            formula.id == "d_cos" -> listOf("-sin($innerFunc)", "${innerDerivative}sin($innerFunc)", "-${innerDerivative}cos($innerFunc)")
            formula.id == "d_tan" -> listOf("sec²($innerFunc)", "-${innerDerivative}sec²($innerFunc)", "${innerDerivative}tan²($innerFunc)")
            formula.id == "d_ex" -> listOf("eˣ$powerStr", "${power}eˣ$powerStr", "x${powerMinus1Str}eˣ$powerStr")
            formula.id == "d_ln" -> listOf("1/x$powerStr", "$innerDerivative", "1/($innerDerivative)")
            else -> formula.distractors
        }
        
        val allOptions = mutableListOf(correctAnswer)
        allOptions.addAll(distractors.take(3))
        allOptions.shuffle()
        
        return Question(
            id = "q_${formula.id}_chain_$id",
            formulaId = formula.id,
            questionText = questionText,
            options = allOptions,
            correctIndex = allOptions.indexOf(correctAnswer),
            formulaHint = "Chain Rule: d/dx[f(g(x))] = f'(g(x))·g'(x)\n${formula.formulaDisplay}"
        )
    }

    fun generateInfinityQuestion(
        enabledTopics: Set<TopicGroup>,
        errorRates: Map<String, Float>
    ): Question {
        val enabledFormulas = FormulaData.allFormulas.filter { it.topicGroup in enabledTopics }
        if (enabledFormulas.isEmpty()) {
            return generateBasicQuestion(FormulaData.allFormulas.first(), 0)
        }
        
        val useWeighted = Random.nextFloat() < 0.6f && errorRates.isNotEmpty()
        
        val selectedFormula = if (useWeighted) {
            val weights = enabledFormulas.map { formula ->
                val errorRate = errorRates[formula.id] ?: 0.3f
                errorRate + 0.2f
            }
            val totalWeight = weights.sum()
            var random = Random.nextFloat() * totalWeight
            
            var selected = enabledFormulas.last()
            for (i in enabledFormulas.indices) {
                random -= weights[i]
                if (random <= 0) {
                    selected = enabledFormulas[i]
                    break
                }
            }
            selected
        } else {
            enabledFormulas.random()
        }
        
        val useChainRule = selectedFormula.supportsCoefficients && Random.nextFloat() < 0.4f
        
        return if (useChainRule) {
            generateChainRuleQuestion(selectedFormula, System.currentTimeMillis().toInt())
        } else {
            generateBasicQuestion(selectedFormula, System.currentTimeMillis().toInt())
        }
    }
}
