package com.example.domain.model

enum class QuestionType {
    MULTIPLE_CHOICE,
    WRITTEN_TASK,
    ENGLISH_GAP_OR_READING
}

enum class ModuleCode(val number: String, val title: String, val questionCount: Int, val startNumber: Int, val endNumber: Int) {
    LECTURA_CRITICA("01", "Lectura Crítica", 30, 1, 30),
    RAZONAMIENTO_CUANTITATIVO("02", "Razonamiento Cuantitativo", 30, 31, 60),
    COMPETENCIAS_CIUDADANAS("03", "Competencias Ciudadanas", 30, 61, 90),
    COMUNICACION_ESCRITA("04", "Comunicación Escrita", 1, 91, 91),
    INGLES("05", "Inglés", 45, 91, 135)
}

data class QuestionOption(
    val id: String, // "A", "B", "C", "D"
    val text: String
)

data class Question(
    val globalNumber: Int,
    val moduleQuestionIndex: Int,
    val moduleCode: ModuleCode,
    val contextText: String,
    val questionText: String,
    val options: List<QuestionOption>,
    val correctOptionId: String,
    val explanation: String,
    val competence: String,
    val type: QuestionType = QuestionType.MULTIPLE_CHOICE
)

data class EssayRubric(
    val planteamientoScore: Int = 5,
    val planteamientoMax: Int = 6,
    val organizacionScore: Int = 4,
    val organizacionMax: Int = 6,
    val lenguajeScore: Int = 5,
    val lenguajeMax: Int = 6,
    val feedback: String = "El texto presenta una tesis clara y argumentación sólida orientada al problema de automatización en Colombia. Organización lógica por párrafos, con adecuada coherencia y conectores cohesivos. Nivel 3 Orientativo."
) {
    val totalScore: Int get() = planteamientoScore + organizacionScore + lenguajeScore
    val maxScore: Int get() = planteamientoMax + organizacionMax + lenguajeMax
}

data class ModuleResult(
    val moduleCode: ModuleCode,
    val correctCount: Int,
    val totalCount: Int,
    val competencies: List<CompetencyScore>
) {
    val percentage: Float get() = if (totalCount > 0) (correctCount.toFloat() / totalCount) * 100f else 0f
}

data class CompetencyScore(
    val name: String,
    val correctCount: Int,
    val totalCount: Int
) {
    val percentage: Float get() = if (totalCount > 0) (correctCount.toFloat() / totalCount) * 100f else 0f
}

data class ExamAttempt(
    val id: String,
    val seed: String,
    val sessionTitle: String,
    val isPaused: Boolean,
    val pausedModuleCode: ModuleCode? = null,
    val pausedQuestionIndex: Int? = null,
    val remainingSeconds: Long,
    val totalDurationSeconds: Long,
    val dateFormatted: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val timeSpentFormatted: String,
    val averageMinutesPerQuestion: Float,
    val moduleResults: List<ModuleResult> = emptyList(),
    val essayResponse: String = "",
    val essayRubric: EssayRubric = EssayRubric(),
    val userAnswers: Map<Int, String> = emptyMap(),
    val flaggedQuestions: Set<Int> = emptySet()
) {
    val scorePercentage: Float get() = if (totalQuestions > 0) (correctAnswers.toFloat() / totalQuestions) * 100f else 0f
}

data class InclusiveSettings(
    val fontSizePx: Int = 16, // 16, 18, 21, 24
    val highContrast: Boolean = false,
    val dyslexiaFont: Boolean = false,
    val reduceMotion: Boolean = false,
    val hapticFeedback: Boolean = true,
    val autoAdvance: Boolean = false,
    val showTimerWarning: Boolean = true
)
