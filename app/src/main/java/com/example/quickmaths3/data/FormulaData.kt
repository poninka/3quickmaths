package com.example.quickmaths3.data

enum class FormulaCategory {
    DERIVATIVES,
    INTEGRALS
}

// Topic groups for menu display
enum class TopicGroup(val displayName: String, val category: FormulaCategory) {
    // Derivatives
    DERIV_BASIC_TRIG("Basic Trig (sin, cos, tan)", FormulaCategory.DERIVATIVES),
    DERIV_INVERSE_TRIG("Inverse Trig (arcsin, arccos, arctan)", FormulaCategory.DERIVATIVES),
    DERIV_HYPERBOLIC("Hyperbolic (sinh, cosh, tanh)", FormulaCategory.DERIVATIVES),
    DERIV_EXP_LOG("Exponential & Logarithmic", FormulaCategory.DERIVATIVES),
    DERIV_POWER("Power Rule", FormulaCategory.DERIVATIVES),
    
    // Integrals
    INT_BASIC_TRIG("Basic Trig (sin, cos, tan, sec)", FormulaCategory.INTEGRALS),
    INT_INVERSE_TRIG("Inverse Trig Results", FormulaCategory.INTEGRALS),
    INT_HYPERBOLIC("Hyperbolic", FormulaCategory.INTEGRALS),
    INT_EXP_LOG("Exponential & Logarithmic", FormulaCategory.INTEGRALS),
    INT_POWER("Power Rule", FormulaCategory.INTEGRALS),
    INT_USUB("U-Substitution Patterns", FormulaCategory.INTEGRALS),
    INT_BYPARTS("Integration by Parts", FormulaCategory.INTEGRALS),
    INT_TRIG_POWERS("Trig Powers (sin², cos², tan²)", FormulaCategory.INTEGRALS)
}

data class Formula(
    val id: String,
    val category: FormulaCategory,
    val topicGroup: TopicGroup,
    val topicName: String,
    val formulaDisplay: String,
    val questionTemplate: String,
    val answerTemplate: String,
    val distractors: List<String>,
    // For generating coefficient variations
    val supportsCoefficients: Boolean = false
)

object FormulaData {

    val derivativeFormulas: List<Formula> = listOf(
        // Basic Trig Derivatives - grouped
        Formula(
            id = "d_sin",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_BASIC_TRIG,
            topicName = "d/dx[sin(x)]",
            formulaDisplay = "d/dx[sin(x)] = cos(x)",
            questionTemplate = "d/dx[sin(x)]",
            answerTemplate = "cos(x)",
            distractors = listOf("-cos(x)", "sin(x)", "-sin(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_cos",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_BASIC_TRIG,
            topicName = "d/dx[cos(x)]",
            formulaDisplay = "d/dx[cos(x)] = -sin(x)",
            questionTemplate = "d/dx[cos(x)]",
            answerTemplate = "-sin(x)",
            distractors = listOf("sin(x)", "cos(x)", "-cos(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_tan",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_BASIC_TRIG,
            topicName = "d/dx[tan(x)]",
            formulaDisplay = "d/dx[tan(x)] = sec²(x)",
            questionTemplate = "d/dx[tan(x)]",
            answerTemplate = "sec²(x)",
            distractors = listOf("csc²(x)", "tan²(x)", "-sec²(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_sec",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_BASIC_TRIG,
            topicName = "d/dx[sec(x)]",
            formulaDisplay = "d/dx[sec(x)] = sec(x)tan(x)",
            questionTemplate = "d/dx[sec(x)]",
            answerTemplate = "sec(x)tan(x)",
            distractors = listOf("csc(x)cot(x)", "sec²(x)", "tan(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_csc",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_BASIC_TRIG,
            topicName = "d/dx[csc(x)]",
            formulaDisplay = "d/dx[csc(x)] = -csc(x)cot(x)",
            questionTemplate = "d/dx[csc(x)]",
            answerTemplate = "-csc(x)cot(x)",
            distractors = listOf("csc(x)cot(x)", "-sec(x)tan(x)", "csc²(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_cot",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_BASIC_TRIG,
            topicName = "d/dx[cot(x)]",
            formulaDisplay = "d/dx[cot(x)] = -csc²(x)",
            questionTemplate = "d/dx[cot(x)]",
            answerTemplate = "-csc²(x)",
            distractors = listOf("csc²(x)", "-sec²(x)", "cot²(x)"),
            supportsCoefficients = true
        ),

        // Inverse Trig Derivatives
        Formula(
            id = "d_arcsin",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arcsin(x)]",
            formulaDisplay = "d/dx[arcsin(x)] = 1/√(1-x²)",
            questionTemplate = "d/dx[arcsin(x)]",
            answerTemplate = "1/√(1-x²)",
            distractors = listOf("-1/√(1-x²)", "1/√(1+x²)", "1/(1-x²)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_arccos",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arccos(x)]",
            formulaDisplay = "d/dx[arccos(x)] = -1/√(1-x²)",
            questionTemplate = "d/dx[arccos(x)]",
            answerTemplate = "-1/√(1-x²)",
            distractors = listOf("1/√(1-x²)", "-1/√(1+x²)", "1/(1-x²)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_arctan",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arctan(x)]",
            formulaDisplay = "d/dx[arctan(x)] = 1/(1+x²)",
            questionTemplate = "d/dx[arctan(x)]",
            answerTemplate = "1/(1+x²)",
            distractors = listOf("-1/(1+x²)", "1/(1-x²)", "1/√(1+x²)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_arccot",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arccot(x)]",
            formulaDisplay = "d/dx[arccot(x)] = -1/(1+x²)",
            questionTemplate = "d/dx[arccot(x)]",
            answerTemplate = "-1/(1+x²)",
            distractors = listOf("1/(1+x²)", "-1/(1-x²)", "1/√(1+x²)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_arcsec",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arcsec(x)]",
            formulaDisplay = "d/dx[arcsec(x)] = 1/(|x|√(x²-1))",
            questionTemplate = "d/dx[arcsec(x)]",
            answerTemplate = "1/(|x|√(x²-1))",
            distractors = listOf("-1/(|x|√(x²-1))", "1/(x√(x²+1))", "1/√(x²-1)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_arccsc",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arccsc(x)]",
            formulaDisplay = "d/dx[arccsc(x)] = -1/(|x|√(x²-1))",
            questionTemplate = "d/dx[arccsc(x)]",
            answerTemplate = "-1/(|x|√(x²-1))",
            distractors = listOf("1/(|x|√(x²-1))", "-1/(x√(x²+1))", "-1/√(x²-1)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_arcsin2x",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arcsin(2x)]",
            formulaDisplay = "d/dx[arcsin(2x)] = 2/√(1-4x²)",
            questionTemplate = "d/dx[arcsin(2x)]",
            answerTemplate = "2/√(1-4x²)",
            distractors = listOf("1/√(1-4x²)", "2/√(1-x²)", "-2/√(1-4x²)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_arctan2x",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arctan(2x)]",
            formulaDisplay = "d/dx[arctan(2x)] = 2/(1+4x²)",
            questionTemplate = "d/dx[arctan(2x)]",
            answerTemplate = "2/(1+4x²)",
            distractors = listOf("1/(1+4x²)", "2/(1+x²)", "-2/(1+4x²)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_arcsinx2",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arcsin(x²)]",
            formulaDisplay = "d/dx[arcsin(x²)] = 2x/√(1-x⁴)",
            questionTemplate = "d/dx[arcsin(x²)]",
            answerTemplate = "2x/√(1-x⁴)",
            distractors = listOf("x/√(1-x⁴)", "2x/√(1-x²)", "1/√(1-x⁴)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_arctanex",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arctan(eˣ)]",
            formulaDisplay = "d/dx[arctan(eˣ)] = eˣ/(1+e²ˣ)",
            questionTemplate = "d/dx[arctan(eˣ)]",
            answerTemplate = "eˣ/(1+e²ˣ)",
            distractors = listOf("1/(1+e²ˣ)", "eˣ/(1+eˣ)", "e²ˣ/(1+e²ˣ)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_arcsinex",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_INVERSE_TRIG,
            topicName = "d/dx[arcsin(eˣ)]",
            formulaDisplay = "d/dx[arcsin(eˣ)] = eˣ/√(1-e²ˣ)",
            questionTemplate = "d/dx[arcsin(eˣ)]",
            answerTemplate = "eˣ/√(1-e²ˣ)",
            distractors = listOf("1/√(1-e²ˣ)", "eˣ/√(1-eˣ)", "-eˣ/√(1-e²ˣ)"),
            supportsCoefficients = false
        ),

        // Hyperbolic Derivatives
        Formula(
            id = "d_sinh",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[sinh(x)]",
            formulaDisplay = "d/dx[sinh(x)] = cosh(x)",
            questionTemplate = "d/dx[sinh(x)]",
            answerTemplate = "cosh(x)",
            distractors = listOf("-cosh(x)", "sinh(x)", "tanh(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_cosh",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[cosh(x)]",
            formulaDisplay = "d/dx[cosh(x)] = sinh(x)",
            questionTemplate = "d/dx[cosh(x)]",
            answerTemplate = "sinh(x)",
            distractors = listOf("-sinh(x)", "cosh(x)", "-cosh(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_tanh",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[tanh(x)]",
            formulaDisplay = "d/dx[tanh(x)] = sech²(x)",
            questionTemplate = "d/dx[tanh(x)]",
            answerTemplate = "sech²(x)",
            distractors = listOf("-sech²(x)", "csch²(x)", "tanh²(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_sech",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[sech(x)]",
            formulaDisplay = "d/dx[sech(x)] = -sech(x)tanh(x)",
            questionTemplate = "d/dx[sech(x)]",
            answerTemplate = "-sech(x)tanh(x)",
            distractors = listOf("sech(x)tanh(x)", "-csch(x)coth(x)", "sech²(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_csch",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[csch(x)]",
            formulaDisplay = "d/dx[csch(x)] = -csch(x)coth(x)",
            questionTemplate = "d/dx[csch(x)]",
            answerTemplate = "-csch(x)coth(x)",
            distractors = listOf("csch(x)coth(x)", "-sech(x)tanh(x)", "csch²(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_coth",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[coth(x)]",
            formulaDisplay = "d/dx[coth(x)] = -csch²(x)",
            questionTemplate = "d/dx[coth(x)]",
            answerTemplate = "-csch²(x)",
            distractors = listOf("csch²(x)", "-sech²(x)", "coth²(x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_sinh2x",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[sinh(2x)]",
            formulaDisplay = "d/dx[sinh(2x)] = 2cosh(2x)",
            questionTemplate = "d/dx[sinh(2x)]",
            answerTemplate = "2cosh(2x)",
            distractors = listOf("cosh(2x)", "2sinh(2x)", "-2cosh(2x)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_cosh2x",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[cosh(2x)]",
            formulaDisplay = "d/dx[cosh(2x)] = 2sinh(2x)",
            questionTemplate = "d/dx[cosh(2x)]",
            answerTemplate = "2sinh(2x)",
            distractors = listOf("sinh(2x)", "2cosh(2x)", "-2sinh(2x)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_sinhx2",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[sinh(x²)]",
            formulaDisplay = "d/dx[sinh(x²)] = 2xcosh(x²)",
            questionTemplate = "d/dx[sinh(x²)]",
            answerTemplate = "2xcosh(x²)",
            distractors = listOf("xcosh(x²)", "2xsinh(x²)", "cosh(x²)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_tanhex",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[tanh(eˣ)]",
            formulaDisplay = "d/dx[tanh(eˣ)] = eˣsech²(eˣ)",
            questionTemplate = "d/dx[tanh(eˣ)]",
            answerTemplate = "eˣsech²(eˣ)",
            distractors = listOf("sech²(eˣ)", "eˣtanh(eˣ)", "eˣcsch²(eˣ)"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_coshex",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_HYPERBOLIC,
            topicName = "d/dx[cosh(eˣ)]",
            formulaDisplay = "d/dx[cosh(eˣ)] = eˣsinh(eˣ)",
            questionTemplate = "d/dx[cosh(eˣ)]",
            answerTemplate = "eˣsinh(eˣ)",
            distractors = listOf("sinh(eˣ)", "eˣcosh(eˣ)", "-eˣsinh(eˣ)"),
            supportsCoefficients = false
        ),

        // Exp/Log Derivatives
        Formula(
            id = "d_ex",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[eˣ]",
            formulaDisplay = "d/dx[eˣ] = eˣ",
            questionTemplate = "d/dx[eˣ]",
            answerTemplate = "eˣ",
            distractors = listOf("xeˣ⁻¹", "ln(x)", "eˣ⁻¹"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_ln",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[ln(x)]",
            formulaDisplay = "d/dx[ln(x)] = 1/x",
            questionTemplate = "d/dx[ln(x)]",
            answerTemplate = "1/x",
            distractors = listOf("x", "ln(x)", "eˣ"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_ax",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[aˣ]",
            formulaDisplay = "d/dx[aˣ] = aˣln(a)",
            questionTemplate = "d/dx[aˣ]",
            answerTemplate = "aˣln(a)",
            distractors = listOf("aˣ", "xaˣ⁻¹", "aˣ/ln(a)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_e2x",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[e²ˣ]",
            formulaDisplay = "d/dx[e²ˣ] = 2e²ˣ",
            questionTemplate = "d/dx[e²ˣ]",
            answerTemplate = "2e²ˣ",
            distractors = listOf("e²ˣ", "2xe²ˣ", "eˣ"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_e3x",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[e³ˣ]",
            formulaDisplay = "d/dx[e³ˣ] = 3e³ˣ",
            questionTemplate = "d/dx[e³ˣ]",
            answerTemplate = "3e³ˣ",
            distractors = listOf("e³ˣ", "3xe³ˣ", "eˣ"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_ex2",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[eˣ²]",
            formulaDisplay = "d/dx[eˣ²] = 2xeˣ²",
            questionTemplate = "d/dx[eˣ²]",
            answerTemplate = "2xeˣ²",
            distractors = listOf("eˣ²", "xeˣ²", "2eˣ²"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_ln2x",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[ln(2x)]",
            formulaDisplay = "d/dx[ln(2x)] = 1/x",
            questionTemplate = "d/dx[ln(2x)]",
            answerTemplate = "1/x",
            distractors = listOf("2/x", "1/(2x)", "ln(2)/x"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_lnx2",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[ln(x²)]",
            formulaDisplay = "d/dx[ln(x²)] = 2/x",
            questionTemplate = "d/dx[ln(x²)]",
            answerTemplate = "2/x",
            distractors = listOf("1/x", "2x", "1/x²"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_lnex",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[ln(eˣ)]",
            formulaDisplay = "d/dx[ln(eˣ)] = 1",
            questionTemplate = "d/dx[ln(eˣ)]",
            answerTemplate = "1",
            distractors = listOf("eˣ", "1/eˣ", "x"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_loga",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[logₐ(x)]",
            formulaDisplay = "d/dx[logₐ(x)] = 1/(xln(a))",
            questionTemplate = "d/dx[logₐ(x)]",
            answerTemplate = "1/(xln(a))",
            distractors = listOf("1/x", "ln(a)/x", "a/(xln(a))"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_xex",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[xeˣ]",
            formulaDisplay = "d/dx[xeˣ] = eˣ + xeˣ",
            questionTemplate = "d/dx[xeˣ]",
            answerTemplate = "eˣ + xeˣ",
            distractors = listOf("xeˣ", "eˣ", "2xeˣ"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_xlnx",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_EXP_LOG,
            topicName = "d/dx[xln(x)]",
            formulaDisplay = "d/dx[xln(x)] = ln(x) + 1",
            questionTemplate = "d/dx[xln(x)]",
            answerTemplate = "ln(x) + 1",
            distractors = listOf("ln(x)", "1/x", "1 + 1/x"),
            supportsCoefficients = false
        ),

        // Power Rule
        Formula(
            id = "d_power",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[xⁿ]",
            formulaDisplay = "d/dx[xⁿ] = nxⁿ⁻¹",
            questionTemplate = "d/dx[x³]",
            answerTemplate = "3x²",
            distractors = listOf("x²", "3x³", "x³"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_sqrt",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[√x]",
            formulaDisplay = "d/dx[√x] = 1/(2√x)",
            questionTemplate = "d/dx[√x]",
            answerTemplate = "1/(2√x)",
            distractors = listOf("2√x", "√x/2", "-1/(2√x)"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_1overx",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[1/x]",
            formulaDisplay = "d/dx[1/x] = -1/x²",
            questionTemplate = "d/dx[1/x]",
            answerTemplate = "-1/x²",
            distractors = listOf("1/x²", "-1/x", "1/x"),
            supportsCoefficients = true
        ),
        Formula(
            id = "d_x4",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[x⁴]",
            formulaDisplay = "d/dx[x⁴] = 4x³",
            questionTemplate = "d/dx[x⁴]",
            answerTemplate = "4x³",
            distractors = listOf("x³", "4x⁴", "3x²"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_x5",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[x⁵]",
            formulaDisplay = "d/dx[x⁵] = 5x⁴",
            questionTemplate = "d/dx[x⁵]",
            answerTemplate = "5x⁴",
            distractors = listOf("x⁴", "5x⁵", "4x³"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_x2",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[x²]",
            formulaDisplay = "d/dx[x²] = 2x",
            questionTemplate = "d/dx[x²]",
            answerTemplate = "2x",
            distractors = listOf("x", "2x²", "x²"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_1overx2",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[1/x²]",
            formulaDisplay = "d/dx[1/x²] = -2/x³",
            questionTemplate = "d/dx[1/x²]",
            answerTemplate = "-2/x³",
            distractors = listOf("2/x³", "-1/x³", "1/x²"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_cbrt",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[³√x]",
            formulaDisplay = "d/dx[³√x] = 1/(3³√x²)",
            questionTemplate = "d/dx[³√x]",
            answerTemplate = "1/(3³√x²)",
            distractors = listOf("3³√x²", "1/(3√x)", "1/³√x"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_x32",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[x³ᵠ²]",
            formulaDisplay = "d/dx[x³ᵠ²] = (3/2)√x",
            questionTemplate = "d/dx[x³ᵠ²]",
            answerTemplate = "(3/2)√x",
            distractors = listOf("√x/2", "(3/2)x", "3√x"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_2x3",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[2x³]",
            formulaDisplay = "d/dx[2x³] = 6x²",
            questionTemplate = "d/dx[2x³]",
            answerTemplate = "6x²",
            distractors = listOf("2x²", "6x³", "3x²"),
            supportsCoefficients = false
        ),
        Formula(
            id = "d_3x2",
            category = FormulaCategory.DERIVATIVES,
            topicGroup = TopicGroup.DERIV_POWER,
            topicName = "d/dx[3x²]",
            formulaDisplay = "d/dx[3x²] = 6x",
            questionTemplate = "d/dx[3x²]",
            answerTemplate = "6x",
            distractors = listOf("3x", "6x²", "2x"),
            supportsCoefficients = false
        )
    )

    val integralFormulas: List<Formula> = listOf(
        // Basic Trig Integrals - grouped
        Formula(
            id = "i_sin",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BASIC_TRIG,
            topicName = "∫sin(x)dx",
            formulaDisplay = "∫sin(x)dx = -cos(x) + C",
            questionTemplate = "∫sin(x)dx",
            answerTemplate = "-cos(x) + C",
            distractors = listOf("cos(x) + C", "-sin(x) + C", "sin(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_cos",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BASIC_TRIG,
            topicName = "∫cos(x)dx",
            formulaDisplay = "∫cos(x)dx = sin(x) + C",
            questionTemplate = "∫cos(x)dx",
            answerTemplate = "sin(x) + C",
            distractors = listOf("-sin(x) + C", "cos(x) + C", "-cos(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_tan",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BASIC_TRIG,
            topicName = "∫tan(x)dx",
            formulaDisplay = "∫tan(x)dx = -ln|cos(x)| + C",
            questionTemplate = "∫tan(x)dx",
            answerTemplate = "-ln|cos(x)| + C",
            distractors = listOf("ln|cos(x)| + C", "ln|sin(x)| + C", "sec(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_sec",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BASIC_TRIG,
            topicName = "∫sec(x)dx",
            formulaDisplay = "∫sec(x)dx = ln|sec(x)+tan(x)| + C",
            questionTemplate = "∫sec(x)dx",
            answerTemplate = "ln|sec(x)+tan(x)| + C",
            distractors = listOf("sec(x)tan(x) + C", "tan(x) + C", "ln|sec(x)| + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_csc",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BASIC_TRIG,
            topicName = "∫csc(x)dx",
            formulaDisplay = "∫csc(x)dx = -ln|csc(x)+cot(x)| + C",
            questionTemplate = "∫csc(x)dx",
            answerTemplate = "-ln|csc(x)+cot(x)| + C",
            distractors = listOf("ln|csc(x)+cot(x)| + C", "cot(x) + C", "-csc(x)cot(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_sec2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BASIC_TRIG,
            topicName = "∫sec²(x)dx",
            formulaDisplay = "∫sec²(x)dx = tan(x) + C",
            questionTemplate = "∫sec²(x)dx",
            answerTemplate = "tan(x) + C",
            distractors = listOf("sec(x) + C", "sec(x)tan(x) + C", "-tan(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_csc2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BASIC_TRIG,
            topicName = "∫csc²(x)dx",
            formulaDisplay = "∫csc²(x)dx = -cot(x) + C",
            questionTemplate = "∫csc²(x)dx",
            answerTemplate = "-cot(x) + C",
            distractors = listOf("cot(x) + C", "csc(x) + C", "-csc(x)cot(x) + C"),
            supportsCoefficients = true
        ),

        // Inverse Trig Results
        Formula(
            id = "i_arcsin",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫1/√(1-x²)dx",
            formulaDisplay = "∫1/√(1-x²)dx = arcsin(x) + C",
            questionTemplate = "∫1/√(1-x²)dx",
            answerTemplate = "arcsin(x) + C",
            distractors = listOf("arccos(x) + C", "-arcsin(x) + C", "arctan(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_arctan",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫1/(1+x²)dx",
            formulaDisplay = "∫1/(1+x²)dx = arctan(x) + C",
            questionTemplate = "∫1/(1+x²)dx",
            answerTemplate = "arctan(x) + C",
            distractors = listOf("arcsin(x) + C", "-arctan(x) + C", "ln(1+x²) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_arcsec",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫1/(x√(x²-1))dx",
            formulaDisplay = "∫1/(x√(x²-1))dx = arcsec(x) + C",
            questionTemplate = "∫1/(x√(x²-1))dx",
            answerTemplate = "arcsec(x) + C",
            distractors = listOf("arctan(x) + C", "arcsin(x) + C", "arccos(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_arccos",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫-1/√(1-x²)dx",
            formulaDisplay = "∫-1/√(1-x²)dx = arccos(x) + C",
            questionTemplate = "∫-1/√(1-x²)dx",
            answerTemplate = "arccos(x) + C",
            distractors = listOf("arcsin(x) + C", "-arcsin(x) + C", "arctan(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_arccsc",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫-1/(x√(x²-1))dx",
            formulaDisplay = "∫-1/(x√(x²-1))dx = arccsc(x) + C",
            questionTemplate = "∫-1/(x√(x²-1))dx",
            answerTemplate = "arccsc(x) + C",
            distractors = listOf("arcsec(x) + C", "-arcsec(x) + C", "arctan(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_arccot",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫-1/(1+x²)dx",
            formulaDisplay = "∫-1/(1+x²)dx = arccot(x) + C",
            questionTemplate = "∫-1/(1+x²)dx",
            answerTemplate = "arccot(x) + C",
            distractors = listOf("arctan(x) + C", "-arctan(x) + C", "arcsin(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_arctan_a",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫1/(a²+x²)dx",
            formulaDisplay = "∫1/(a²+x²)dx = (1/a)arctan(x/a) + C",
            questionTemplate = "∫1/(4+x²)dx",
            answerTemplate = "(1/2)arctan(x/2) + C",
            distractors = listOf("arctan(x/2) + C", "(1/4)arctan(x) + C", "2arctan(x/2) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_arcsin_a",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫1/√(a²-x²)dx",
            formulaDisplay = "∫1/√(a²-x²)dx = arcsin(x/a) + C",
            questionTemplate = "∫1/√(9-x²)dx",
            answerTemplate = "arcsin(x/3) + C",
            distractors = listOf("arcsin(3x) + C", "(1/3)arcsin(x) + C", "arccos(x/3) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_arcsec_a",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_INVERSE_TRIG,
            topicName = "∫1/(x√(x²-a²))dx",
            formulaDisplay = "∫1/(x√(x²-a²))dx = (1/a)arcsec(x/a) + C",
            questionTemplate = "∫1/(x√(x²-4))dx",
            answerTemplate = "(1/2)arcsec(x/2) + C",
            distractors = listOf("arcsec(x/2) + C", "(1/4)arcsec(x) + C", "2arcsec(x/2) + C"),
            supportsCoefficients = false
        ),

        // Hyperbolic Integrals
        Formula(
            id = "i_sinh",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫sinh(x)dx",
            formulaDisplay = "∫sinh(x)dx = cosh(x) + C",
            questionTemplate = "∫sinh(x)dx",
            answerTemplate = "cosh(x) + C",
            distractors = listOf("-cosh(x) + C", "sinh(x) + C", "tanh(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_cosh",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫cosh(x)dx",
            formulaDisplay = "∫cosh(x)dx = sinh(x) + C",
            questionTemplate = "∫cosh(x)dx",
            answerTemplate = "sinh(x) + C",
            distractors = listOf("-sinh(x) + C", "cosh(x) + C", "-cosh(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_sech2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫sech²(x)dx",
            formulaDisplay = "∫sech²(x)dx = tanh(x) + C",
            questionTemplate = "∫sech²(x)dx",
            answerTemplate = "tanh(x) + C",
            distractors = listOf("sech(x) + C", "-tanh(x) + C", "coth(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_csch2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫csch²(x)dx",
            formulaDisplay = "∫csch²(x)dx = -coth(x) + C",
            questionTemplate = "∫csch²(x)dx",
            answerTemplate = "-coth(x) + C",
            distractors = listOf("coth(x) + C", "csch(x) + C", "tanh(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_tanh",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫tanh(x)dx",
            formulaDisplay = "∫tanh(x)dx = ln|cosh(x)| + C",
            questionTemplate = "∫tanh(x)dx",
            answerTemplate = "ln|cosh(x)| + C",
            distractors = listOf("ln|sinh(x)| + C", "sech²(x) + C", "-ln|cosh(x)| + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_coth",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫coth(x)dx",
            formulaDisplay = "∫coth(x)dx = ln|sinh(x)| + C",
            questionTemplate = "∫coth(x)dx",
            answerTemplate = "ln|sinh(x)| + C",
            distractors = listOf("ln|cosh(x)| + C", "-csch²(x) + C", "-ln|sinh(x)| + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_sech_tanh",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫sech(x)tanh(x)dx",
            formulaDisplay = "∫sech(x)tanh(x)dx = -sech(x) + C",
            questionTemplate = "∫sech(x)tanh(x)dx",
            answerTemplate = "-sech(x) + C",
            distractors = listOf("sech(x) + C", "tanh(x) + C", "-tanh(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_csch_coth",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫csch(x)coth(x)dx",
            formulaDisplay = "∫csch(x)coth(x)dx = -csch(x) + C",
            questionTemplate = "∫csch(x)coth(x)dx",
            answerTemplate = "-csch(x) + C",
            distractors = listOf("csch(x) + C", "coth(x) + C", "-coth(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_sech",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_HYPERBOLIC,
            topicName = "∫sech(x)dx",
            formulaDisplay = "∫sech(x)dx = arctan(sinh(x)) + C",
            questionTemplate = "∫sech(x)dx",
            answerTemplate = "arctan(sinh(x)) + C",
            distractors = listOf("tanh(x) + C", "ln|sech(x)| + C", "sinh(x) + C"),
            supportsCoefficients = false
        ),

        // Exp/Log Integrals
        Formula(
            id = "i_ex",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫eˣdx",
            formulaDisplay = "∫eˣdx = eˣ + C",
            questionTemplate = "∫eˣdx",
            answerTemplate = "eˣ + C",
            distractors = listOf("xeˣ + C", "eˣ⁺¹ + C", "ln(x) + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_1overx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫1/x dx",
            formulaDisplay = "∫1/x dx = ln|x| + C",
            questionTemplate = "∫1/x dx",
            answerTemplate = "ln|x| + C",
            distractors = listOf("ln(x) + C", "1/x² + C", "eˣ + C"),
            supportsCoefficients = true
        ),
        Formula(
            id = "i_ax",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫aˣdx",
            formulaDisplay = "∫aˣdx = aˣ/ln(a) + C",
            questionTemplate = "∫aˣdx",
            answerTemplate = "aˣ/ln(a) + C",
            distractors = listOf("aˣln(a) + C", "aˣ + C", "xaˣ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_e2x",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫e²ˣdx",
            formulaDisplay = "∫e²ˣdx = e²ˣ/2 + C",
            questionTemplate = "∫e²ˣdx",
            answerTemplate = "e²ˣ/2 + C",
            distractors = listOf("e²ˣ + C", "2e²ˣ + C", "e²ˣ/4 + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_e3x",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫e³ˣdx",
            formulaDisplay = "∫e³ˣdx = e³ˣ/3 + C",
            questionTemplate = "∫e³ˣdx",
            answerTemplate = "e³ˣ/3 + C",
            distractors = listOf("e³ˣ + C", "3e³ˣ + C", "e³ˣ/9 + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_enx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫eⁿˣdx",
            formulaDisplay = "∫eⁿˣdx = eⁿˣ/n + C",
            questionTemplate = "∫e⁵ˣdx",
            answerTemplate = "e⁵ˣ/5 + C",
            distractors = listOf("e⁵ˣ + C", "5e⁵ˣ + C", "e⁵ˣ/25 + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_2x",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫2ˣdx",
            formulaDisplay = "∫2ˣdx = 2ˣ/ln(2) + C",
            questionTemplate = "∫2ˣdx",
            answerTemplate = "2ˣ/ln(2) + C",
            distractors = listOf("2ˣln(2) + C", "2ˣ + C", "x2ˣ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_10x",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫10ˣdx",
            formulaDisplay = "∫10ˣdx = 10ˣ/ln(10) + C",
            questionTemplate = "∫10ˣdx",
            answerTemplate = "10ˣ/ln(10) + C",
            distractors = listOf("10ˣln(10) + C", "10ˣ + C", "x10ˣ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_eminusx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_EXP_LOG,
            topicName = "∫e⁻ˣdx",
            formulaDisplay = "∫e⁻ˣdx = -e⁻ˣ + C",
            questionTemplate = "∫e⁻ˣdx",
            answerTemplate = "-e⁻ˣ + C",
            distractors = listOf("e⁻ˣ + C", "-xe⁻ˣ + C", "e⁻ˣ/x + C"),
            supportsCoefficients = false
        ),

        // Power Rule
        Formula(
            id = "i_power",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫xⁿdx",
            formulaDisplay = "∫xⁿdx = xⁿ⁺¹/(n+1) + C",
            questionTemplate = "∫x³dx",
            answerTemplate = "x⁴/4 + C",
            distractors = listOf("x³/3 + C", "4x⁴ + C", "3x² + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_power_x2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫x²dx",
            formulaDisplay = "∫x²dx = x³/3 + C",
            questionTemplate = "∫x²dx",
            answerTemplate = "x³/3 + C",
            distractors = listOf("x²/2 + C", "2x³ + C", "3x² + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_power_x4",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫x⁴dx",
            formulaDisplay = "∫x⁴dx = x⁵/5 + C",
            questionTemplate = "∫x⁴dx",
            answerTemplate = "x⁵/5 + C",
            distractors = listOf("x⁴/4 + C", "4x⁵ + C", "5x⁴ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_power_x5",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫x⁵dx",
            formulaDisplay = "∫x⁵dx = x⁶/6 + C",
            questionTemplate = "∫x⁵dx",
            answerTemplate = "x⁶/6 + C",
            distractors = listOf("x⁵/5 + C", "5x⁶ + C", "6x⁵ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_power_1overx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫(1/x)dx",
            formulaDisplay = "∫(1/x)dx = ln|x| + C",
            questionTemplate = "∫(1/x)dx",
            answerTemplate = "ln|x| + C",
            distractors = listOf("1/x² + C", "-1/x² + C", "x ln(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_power_1overx2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫(1/x²)dx",
            formulaDisplay = "∫x⁻²dx = -1/x + C",
            questionTemplate = "∫(1/x²)dx",
            answerTemplate = "-1/x + C",
            distractors = listOf("1/x + C", "-2/x³ + C", "ln|x| + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_sqrt",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫√x dx",
            formulaDisplay = "∫√x dx = (2/3)x³ᐟ² + C",
            questionTemplate = "∫√x dx",
            answerTemplate = "(2/3)x³ᐟ² + C",
            distractors = listOf("(1/2)√x + C", "2√x + C", "(3/2)x³ᐟ² + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_cbrt",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫∛x dx",
            formulaDisplay = "∫x¹ᐟ³dx = (3/4)x⁴ᐟ³ + C",
            questionTemplate = "∫∛x dx",
            answerTemplate = "(3/4)x⁴ᐟ³ + C",
            distractors = listOf("(1/3)x¹ᐟ³ + C", "3∛x + C", "(4/3)x⁴ᐟ³ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_power_1oversqrtx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫(1/√x)dx",
            formulaDisplay = "∫x⁻¹ᐟ²dx = 2√x + C",
            questionTemplate = "∫(1/√x)dx",
            answerTemplate = "2√x + C",
            distractors = listOf("√x + C", "-2/√x + C", "(1/2)√x + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_const",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_POWER,
            topicName = "∫k dx",
            formulaDisplay = "∫k dx = kx + C",
            questionTemplate = "∫5 dx",
            answerTemplate = "5x + C",
            distractors = listOf("5 + C", "x/5 + C", "5x² + C"),
            supportsCoefficients = false
        ),

        // U-Substitution Patterns
        Formula(
            id = "i_usub_sin",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫cos(x)eˢⁱⁿ⁽ˣ⁾dx",
            formulaDisplay = "u = sin(x) → ∫eᵘdu",
            questionTemplate = "∫cos(x)eˢⁱⁿ⁽ˣ⁾dx",
            answerTemplate = "eˢⁱⁿ⁽ˣ⁾ + C",
            distractors = listOf("sin(x)eˢⁱⁿ⁽ˣ⁾ + C", "-eˢⁱⁿ⁽ˣ⁾ + C", "eᶜᵒˢ⁽ˣ⁾ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_tan",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫sec²(x)tan(x)dx",
            formulaDisplay = "u = tan(x) → ∫u du",
            questionTemplate = "∫sec²(x)tan(x)dx",
            answerTemplate = "tan²(x)/2 + C",
            distractors = listOf("sec²(x)/2 + C", "tan(x) + C", "sec(x)tan(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_exp",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫xeˣ²dx",
            formulaDisplay = "u = x² → (1/2)∫eᵘdu",
            questionTemplate = "∫xeˣ²dx",
            answerTemplate = "eˣ²/2 + C",
            distractors = listOf("eˣ² + C", "xeˣ² + C", "2eˣ² + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_sqrt",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫x√(x²+1)dx",
            formulaDisplay = "u = x²+1 → (1/2)∫√u du",
            questionTemplate = "∫x√(x²+1)dx",
            answerTemplate = "(x²+1)³ᐟ²/3 + C",
            distractors = listOf("√(x²+1) + C", "(x²+1)³ᐟ² + C", "x²√(x²+1) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_ln",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫(1/x)ln(x)dx",
            formulaDisplay = "u = ln(x) → ∫u du",
            questionTemplate = "∫(1/x)ln(x)dx",
            answerTemplate = "ln²(x)/2 + C",
            distractors = listOf("ln(x)/x + C", "ln²(x) + C", "xln(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_sincos",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫sin(x)cos(x)dx",
            formulaDisplay = "u = sin(x) → ∫u du",
            questionTemplate = "∫sin(x)cos(x)dx",
            answerTemplate = "sin²(x)/2 + C",
            distractors = listOf("-cos²(x)/2 + C", "sin(x)cos(x) + C", "cos²(x)/2 + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_x2ex3",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫x²eˣ³dx",
            formulaDisplay = "u = x³ → (1/3)∫eᵘdu",
            questionTemplate = "∫x²eˣ³dx",
            answerTemplate = "eˣ³/3 + C",
            distractors = listOf("eˣ³ + C", "3eˣ³ + C", "x³eˣ³ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_tanln",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫tan(x)dx",
            formulaDisplay = "u = cos(x) → -∫(1/u)du",
            questionTemplate = "∫tan(x)dx",
            answerTemplate = "-ln|cos(x)| + C",
            distractors = listOf("ln|cos(x)| + C", "sec²(x) + C", "ln|sin(x)| + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_usub_cot",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_USUB,
            topicName = "∫cot(x)dx",
            formulaDisplay = "u = sin(x) → ∫(1/u)du",
            questionTemplate = "∫cot(x)dx",
            answerTemplate = "ln|sin(x)| + C",
            distractors = listOf("-ln|sin(x)| + C", "csc²(x) + C", "ln|cos(x)| + C"),
            supportsCoefficients = false
        ),

        // Integration by Parts
        Formula(
            id = "i_byparts_xex",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫xeˣdx",
            formulaDisplay = "IBP: u=x, dv=eˣdx",
            questionTemplate = "∫xeˣdx",
            answerTemplate = "xeˣ - eˣ + C",
            distractors = listOf("xeˣ + C", "eˣ + C", "xeˣ + eˣ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_xsinx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫xsin(x)dx",
            formulaDisplay = "IBP: u=x, dv=sin(x)dx",
            questionTemplate = "∫xsin(x)dx",
            answerTemplate = "-xcos(x) + sin(x) + C",
            distractors = listOf("xcos(x) + C", "-xcos(x) + C", "xsin(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_lnx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫ln(x)dx",
            formulaDisplay = "IBP: u=ln(x), dv=dx",
            questionTemplate = "∫ln(x)dx",
            answerTemplate = "xln(x) - x + C",
            distractors = listOf("ln(x)/x + C", "xln(x) + C", "1/x + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_xcosx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫xcos(x)dx",
            formulaDisplay = "IBP: u=x, dv=cos(x)dx",
            questionTemplate = "∫xcos(x)dx",
            answerTemplate = "xsin(x) + cos(x) + C",
            distractors = listOf("xsin(x) + C", "-xsin(x) + C", "xcos(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_x2ex",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫x²eˣdx",
            formulaDisplay = "IBP twice: u=x², dv=eˣdx",
            questionTemplate = "∫x²eˣdx",
            answerTemplate = "x²eˣ - 2xeˣ + 2eˣ + C",
            distractors = listOf("x²eˣ + C", "x²eˣ - 2eˣ + C", "x²eˣ + 2xeˣ + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_xlnx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫xln(x)dx",
            formulaDisplay = "IBP: u=ln(x), dv=xdx",
            questionTemplate = "∫xln(x)dx",
            answerTemplate = "(x²/2)ln(x) - x²/4 + C",
            distractors = listOf("xln(x) + C", "x²ln(x)/2 + C", "(x²/2)ln(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_exsinx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫eˣsin(x)dx",
            formulaDisplay = "IBP twice (tabular)",
            questionTemplate = "∫eˣsin(x)dx",
            answerTemplate = "(eˣ/2)(sin(x) - cos(x)) + C",
            distractors = listOf("eˣsin(x) + C", "eˣcos(x) + C", "(eˣ/2)sin(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_excosx",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫eˣcos(x)dx",
            formulaDisplay = "IBP twice (tabular)",
            questionTemplate = "∫eˣcos(x)dx",
            answerTemplate = "(eˣ/2)(sin(x) + cos(x)) + C",
            distractors = listOf("eˣcos(x) + C", "eˣsin(x) + C", "(eˣ/2)cos(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_arctan",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫arctan(x)dx",
            formulaDisplay = "IBP: u=arctan(x), dv=dx",
            questionTemplate = "∫arctan(x)dx",
            answerTemplate = "xarctan(x) - ln(1+x²)/2 + C",
            distractors = listOf("arctan(x)/x + C", "xarctan(x) + C", "1/(1+x²) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_byparts_arcsin",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_BYPARTS,
            topicName = "∫arcsin(x)dx",
            formulaDisplay = "IBP: u=arcsin(x), dv=dx",
            questionTemplate = "∫arcsin(x)dx",
            answerTemplate = "xarcsin(x) + √(1-x²) + C",
            distractors = listOf("arcsin(x)/x + C", "xarcsin(x) + C", "-√(1-x²) + C"),
            supportsCoefficients = false
        ),

        // Trig Powers
        Formula(
            id = "i_sin2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫sin²(x)dx",
            formulaDisplay = "∫sin²(x)dx = x/2 - sin(2x)/4 + C",
            questionTemplate = "∫sin²(x)dx",
            answerTemplate = "x/2 - sin(2x)/4 + C",
            distractors = listOf("x/2 + sin(2x)/4 + C", "-cos²(x) + C", "sin(x)cos(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_cos2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫cos²(x)dx",
            formulaDisplay = "∫cos²(x)dx = x/2 + sin(2x)/4 + C",
            questionTemplate = "∫cos²(x)dx",
            answerTemplate = "x/2 + sin(2x)/4 + C",
            distractors = listOf("x/2 - sin(2x)/4 + C", "sin²(x) + C", "cos(x)sin(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_tan2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫tan²(x)dx",
            formulaDisplay = "∫tan²(x)dx = tan(x) - x + C",
            questionTemplate = "∫tan²(x)dx",
            answerTemplate = "tan(x) - x + C",
            distractors = listOf("sec²(x) - x + C", "tan(x) + x + C", "sec(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_sec2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫sec²(x)dx",
            formulaDisplay = "∫sec²(x)dx = tan(x) + C",
            questionTemplate = "∫sec²(x)dx",
            answerTemplate = "tan(x) + C",
            distractors = listOf("sec(x)tan(x) + C", "-tan(x) + C", "sec(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_csc2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫csc²(x)dx",
            formulaDisplay = "∫csc²(x)dx = -cot(x) + C",
            questionTemplate = "∫csc²(x)dx",
            answerTemplate = "-cot(x) + C",
            distractors = listOf("cot(x) + C", "-csc(x)cot(x) + C", "csc(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_cot2",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫cot²(x)dx",
            formulaDisplay = "∫cot²(x)dx = -cot(x) - x + C",
            questionTemplate = "∫cot²(x)dx",
            answerTemplate = "-cot(x) - x + C",
            distractors = listOf("-csc²(x) - x + C", "cot(x) - x + C", "-cot(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_sin3",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫sin³(x)dx",
            formulaDisplay = "∫sin³(x)dx = -cos(x) + cos³(x)/3 + C",
            questionTemplate = "∫sin³(x)dx",
            answerTemplate = "-cos(x) + cos³(x)/3 + C",
            distractors = listOf("-cos³(x)/3 + C", "sin³(x)/3 + C", "cos(x) - cos³(x)/3 + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_cos3",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫cos³(x)dx",
            formulaDisplay = "∫cos³(x)dx = sin(x) - sin³(x)/3 + C",
            questionTemplate = "∫cos³(x)dx",
            answerTemplate = "sin(x) - sin³(x)/3 + C",
            distractors = listOf("sin³(x)/3 + C", "cos³(x)/3 + C", "-sin(x) + sin³(x)/3 + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_sectan",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫sec(x)tan(x)dx",
            formulaDisplay = "∫sec(x)tan(x)dx = sec(x) + C",
            questionTemplate = "∫sec(x)tan(x)dx",
            answerTemplate = "sec(x) + C",
            distractors = listOf("-sec(x) + C", "tan(x) + C", "sec²(x) + C"),
            supportsCoefficients = false
        ),
        Formula(
            id = "i_csccot",
            category = FormulaCategory.INTEGRALS,
            topicGroup = TopicGroup.INT_TRIG_POWERS,
            topicName = "∫csc(x)cot(x)dx",
            formulaDisplay = "∫csc(x)cot(x)dx = -csc(x) + C",
            questionTemplate = "∫csc(x)cot(x)dx",
            answerTemplate = "-csc(x) + C",
            distractors = listOf("csc(x) + C", "-cot(x) + C", "csc²(x) + C"),
            supportsCoefficients = false
        )
    )

    // Cached version of all formulas
    val allFormulas: List<Formula> by lazy {
        derivativeFormulas + integralFormulas
    }
    
    // Pre-computed cache for formulas by topic group (avoids filtering on each call)
    private val formulasByTopicGroup: Map<TopicGroup, List<Formula>> by lazy {
        allFormulas.groupBy { it.topicGroup }
    }
    
    // Pre-computed cache for formula counts by topic group
    private val formulaCountByTopicGroup: Map<TopicGroup, Int> by lazy {
        formulasByTopicGroup.mapValues { it.value.size }
    }

    fun getFormulasByCategory(category: FormulaCategory): List<Formula> {
        return allFormulas.filter { it.category == category }
    }

    fun getFormulasByTopicGroup(group: TopicGroup): List<Formula> {
        return formulasByTopicGroup[group] ?: emptyList()
    }
    
    fun getFormulaCountByTopicGroup(group: TopicGroup): Int {
        return formulaCountByTopicGroup[group] ?: 0
    }

    fun getFormulaById(id: String): Formula? {
        return allFormulas.find { it.id == id }
    }
    
    fun getTopicGroups(category: FormulaCategory): List<TopicGroup> {
        return TopicGroup.entries.filter { it.category == category }
    }
}
