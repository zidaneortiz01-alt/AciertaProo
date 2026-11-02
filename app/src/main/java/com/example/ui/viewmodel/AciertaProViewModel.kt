package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AttemptRepository
import com.example.data.repository.ExamRepository
import com.example.data.repository.InMemoryAttemptRepository
import com.example.data.repository.InMemoryExamRepository
import com.example.data.repository.InMemorySettingsRepository
import com.example.data.repository.MockDataStore
import com.example.data.repository.SettingsRepository
import com.example.domain.model.EssayRubric
import com.example.domain.model.ExamAttempt
import com.example.domain.model.InclusiveSettings
import com.example.domain.model.ModuleCode
import com.example.domain.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val currentRoute: String = "splash",
    val routeStack: List<String> = listOf("splash"),
    val activeAttempt: ExamAttempt? = null,
    val selectedAttemptForDetail: ExamAttempt = MockDataStore.completedAttempt,
    val currentQuestionIndex: Int = 0,
    val currentModule: ModuleCode = ModuleCode.LECTURA_CRITICA,
    val userAnswers: Map<Int, String> = emptyMap(),
    val flaggedQuestions: Set<Int> = emptySet(),
    val essayText: String = "",
    val remainingSeconds: Long = 16800, // 280 min
    val isTimerRunning: Boolean = false,
    val showPauseDialog: Boolean = false,
    val showDiscardDialog: Boolean = false,
    val showQuestionMapModal: Boolean = false,
    val showExitConfirmToast: Boolean = false,
    val showDeleteAttemptDialog: Boolean = false,
    val showRestartAttemptDialog: Boolean = false,
    val showDeliverConfirmDialog: Boolean = false,
    val attemptToManage: ExamAttempt? = null,
    val historyAttempts: List<ExamAttempt> = emptyList(),
    val selectedModuleFilter: ModuleCode? = null,
    val settings: InclusiveSettings = InclusiveSettings()
)

class AciertaProViewModel(
    private val examRepository: ExamRepository = InMemoryExamRepository(),
    private val attemptRepository: AttemptRepository = InMemoryAttemptRepository(),
    private val settingsRepository: SettingsRepository = InMemorySettingsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(
            activeAttempt = attemptRepository.getPausedAttempt(),
            selectedAttemptForDetail = attemptRepository.getAttempts().firstOrNull() ?: MockDataStore.completedAttempt,
            historyAttempts = attemptRepository.getAttempts(),
            settings = settingsRepository.getSettings()
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val allQuestions: List<Question> = examRepository.getQuestionsForSession(1)
    val essayPrompt: String = examRepository.getEssayPrompt()

    private var timerJob: Job? = null
    private var lastBackPressTime: Long = 0

    init {
        // Pre-fill user answers for paused attempt
        val paused = attemptRepository.getPausedAttempt()
        if (paused != null) {
            _uiState.value = _uiState.value.copy(
                userAnswers = paused.userAnswers,
                flaggedQuestions = paused.flaggedQuestions,
                remainingSeconds = paused.remainingSeconds
            )
        }
    }

    fun navigateTo(route: String) {
        val stack = _uiState.value.routeStack.toMutableList()
        stack.add(route)
        _uiState.value = _uiState.value.copy(
            currentRoute = route,
            routeStack = stack,
            showPauseDialog = false,
            showDiscardDialog = false,
            showQuestionMapModal = false,
            showDeleteAttemptDialog = false,
            showRestartAttemptDialog = false,
            showDeliverConfirmDialog = false
        )
    }

    fun navigateBottomNav(route: String) {
        if (_uiState.value.currentRoute == route) return
        _uiState.value = _uiState.value.copy(
            currentRoute = route,
            routeStack = listOf("inicio", route).distinct(),
            showPauseDialog = false,
            showDiscardDialog = false,
            showQuestionMapModal = false,
            showDeleteAttemptDialog = false,
            showRestartAttemptDialog = false,
            showDeliverConfirmDialog = false
        )
    }

    fun navigateBack(): Boolean {
        // If modal is open, close modal
        if (_uiState.value.showPauseDialog || _uiState.value.showDiscardDialog || 
            _uiState.value.showQuestionMapModal || _uiState.value.showDeleteAttemptDialog || 
            _uiState.value.showRestartAttemptDialog || _uiState.value.showDeliverConfirmDialog) {
            _uiState.value = _uiState.value.copy(
                showPauseDialog = false,
                showDiscardDialog = false,
                showQuestionMapModal = false,
                showDeleteAttemptDialog = false,
                showRestartAttemptDialog = false,
                showDeliverConfirmDialog = false,
                attemptToManage = null
            )
            return true
        }

        // If inside exam, do not leave immediately; open pause dialog!
        val inExamRoutes = listOf("marco", "pregunta_seleccion", "comunicacion_escrita", "pregunta_ingles")
        if (inExamRoutes.contains(_uiState.value.currentRoute)) {
            _uiState.value = _uiState.value.copy(showPauseDialog = true)
            return true
        }

        // If in "inicio", double tap to exit
        if (_uiState.value.currentRoute == "inicio") {
            val now = System.currentTimeMillis()
            if (now - lastBackPressTime < 2000) {
                return false // allow system to close app
            } else {
                lastBackPressTime = now
                _uiState.value = _uiState.value.copy(showExitConfirmToast = true)
                viewModelScope.launch {
                    delay(2000)
                    _uiState.value = _uiState.value.copy(showExitConfirmToast = false)
                }
                return true
            }
        }

        // Standard back stack navigation
        val stack = _uiState.value.routeStack.toMutableList()
        if (stack.size > 1) {
            stack.removeAt(stack.size - 1)
            val previousRoute = stack.last()
            _uiState.value = _uiState.value.copy(
                currentRoute = previousRoute,
                routeStack = stack
            )
            return true
        } else {
            _uiState.value = _uiState.value.copy(
                currentRoute = "inicio",
                routeStack = listOf("inicio")
            )
            return true
        }
    }

    fun startNewExam() {
        val newAttempt = ExamAttempt(
            id = "SIM-2026-2-99F1",
            seed = "seed_84920",
            sessionTitle = "Sesión 1 - Práctica estándar",
            isPaused = false,
            remainingSeconds = 16800, // 280 min
            totalDurationSeconds = 16800,
            dateFormatted = "Hoy",
            totalQuestions = 135,
            correctAnswers = 0,
            timeSpentFormatted = "0 min",
            averageMinutesPerQuestion = 0f
        )
        _uiState.value = _uiState.value.copy(
            activeAttempt = newAttempt,
            currentQuestionIndex = 0,
            currentModule = ModuleCode.LECTURA_CRITICA,
            userAnswers = emptyMap(),
            flaggedQuestions = emptySet(),
            essayText = "",
            remainingSeconds = 16800,
            isTimerRunning = true
        )
        startTimer()
        navigateTo("marco")
    }

    fun resumePausedExam() {
        val paused = attemptRepository.getPausedAttempt() ?: MockDataStore.pausedAttempt
        _uiState.value = _uiState.value.copy(
            activeAttempt = paused,
            currentQuestionIndex = paused.pausedQuestionIndex ?: 13,
            currentModule = paused.pausedModuleCode ?: ModuleCode.RAZONAMIENTO_CUANTITATIVO,
            userAnswers = paused.userAnswers,
            flaggedQuestions = paused.flaggedQuestions,
            remainingSeconds = paused.remainingSeconds,
            isTimerRunning = true
        )
        startTimer()
        navigateTo("marco")
    }

    fun pauseExam() {
        timerJob?.cancel()
        val currentAttempt = _uiState.value.activeAttempt
        if (currentAttempt != null) {
            val updated = currentAttempt.copy(
                isPaused = true,
                pausedModuleCode = _uiState.value.currentModule,
                pausedQuestionIndex = _uiState.value.currentQuestionIndex,
                remainingSeconds = _uiState.value.remainingSeconds,
                userAnswers = _uiState.value.userAnswers,
                flaggedQuestions = _uiState.value.flaggedQuestions
            )
            attemptRepository.saveAttempt(updated)
            _uiState.value = _uiState.value.copy(
                activeAttempt = updated,
                historyAttempts = attemptRepository.getAttempts(),
                isTimerRunning = false,
                showPauseDialog = false
            )
        }
        navigateTo("revision_previa")
    }

    fun saveProgressAndExit() {
        timerJob?.cancel()
        val currentAttempt = _uiState.value.activeAttempt
        if (currentAttempt != null) {
            val updated = currentAttempt.copy(
                isPaused = true,
                pausedModuleCode = _uiState.value.currentModule,
                pausedQuestionIndex = _uiState.value.currentQuestionIndex,
                remainingSeconds = _uiState.value.remainingSeconds,
                userAnswers = _uiState.value.userAnswers,
                flaggedQuestions = _uiState.value.flaggedQuestions
            )
            attemptRepository.saveAttempt(updated)
            _uiState.value = _uiState.value.copy(
                activeAttempt = updated,
                historyAttempts = attemptRepository.getAttempts(),
                isTimerRunning = false,
                showPauseDialog = false
            )
        }
        navigateTo("inicio")
    }

    fun resumeExamFromPause() {
        _uiState.value = _uiState.value.copy(showPauseDialog = false)
        if (!_uiState.value.isTimerRunning && _uiState.value.remainingSeconds > 0) {
            _uiState.value = _uiState.value.copy(isTimerRunning = true)
            startTimer()
        }
    }

    fun discardExam() {
        timerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            activeAttempt = null,
            isTimerRunning = false,
            showDiscardDialog = false,
            showPauseDialog = false
        )
        navigateTo("inicio")
    }

    fun setDiscardDialogVisible(visible: Boolean) {
        _uiState.value = _uiState.value.copy(showDiscardDialog = visible)
    }

    fun setDeliverConfirmDialogVisible(visible: Boolean) {
        _uiState.value = _uiState.value.copy(showDeliverConfirmDialog = visible)
    }

    fun requestDeleteAttempt(attempt: ExamAttempt) {
        _uiState.value = _uiState.value.copy(attemptToManage = attempt, showDeleteAttemptDialog = true)
    }

    fun confirmDeleteAttempt() {
        val attempt = _uiState.value.attemptToManage ?: return
        attemptRepository.deleteAttempt(attempt.id)
        val updatedAttempts = attemptRepository.getAttempts()
        val updatedActive = if (_uiState.value.activeAttempt?.id == attempt.id) null else _uiState.value.activeAttempt
        _uiState.value = _uiState.value.copy(
            historyAttempts = updatedAttempts,
            activeAttempt = updatedActive,
            showDeleteAttemptDialog = false,
            attemptToManage = null
        )
    }

    fun dismissDeleteAttemptDialog() {
        _uiState.value = _uiState.value.copy(showDeleteAttemptDialog = false, attemptToManage = null)
    }

    fun requestRestartAttempt(attempt: ExamAttempt) {
        _uiState.value = _uiState.value.copy(attemptToManage = attempt, showRestartAttemptDialog = true)
    }

    fun confirmRestartAttempt() {
        val attempt = _uiState.value.attemptToManage ?: return
        attemptRepository.restartAttempt(attempt.id)
        val updatedAttempts = attemptRepository.getAttempts()
        _uiState.value = _uiState.value.copy(
            historyAttempts = updatedAttempts,
            showRestartAttemptDialog = false,
            attemptToManage = null
        )
        resumePausedExam()
    }

    fun dismissRestartAttemptDialog() {
        _uiState.value = _uiState.value.copy(showRestartAttemptDialog = false, attemptToManage = null)
    }

    fun repeatExamWithIdAndSeed(id: String, seed: String) {
        val newAttempt = ExamAttempt(
            id = id,
            seed = seed,
            sessionTitle = "Sesión 1 - Práctica ($id)",
            isPaused = false,
            remainingSeconds = 16800,
            totalDurationSeconds = 16800,
            dateFormatted = "Hoy",
            totalQuestions = 135,
            correctAnswers = 0,
            timeSpentFormatted = "0 min",
            averageMinutesPerQuestion = 0f
        )
        _uiState.value = _uiState.value.copy(
            activeAttempt = newAttempt,
            currentQuestionIndex = 0,
            currentModule = ModuleCode.LECTURA_CRITICA,
            userAnswers = emptyMap(),
            flaggedQuestions = emptySet(),
            essayText = "",
            remainingSeconds = 16800,
            isTimerRunning = false
        )
        navigateTo("resumen")
    }

    fun confirmCurrentModule() {
        val currentMod = _uiState.value.currentModule
        when (currentMod) {
            ModuleCode.LECTURA_CRITICA -> goToQuestion(31)
            ModuleCode.RAZONAMIENTO_CUANTITATIVO -> goToQuestion(61)
            ModuleCode.COMPETENCIAS_CIUDADANAS -> goToQuestion(91)
            ModuleCode.COMUNICACION_ESCRITA -> goToQuestion(92)
            ModuleCode.INGLES -> navigateTo("revision_previa")
        }
    }

    fun goToFirstUnansweredQuestion() {
        val state = _uiState.value
        val unanswered = allQuestions.find { q ->
            if (q.type == com.example.domain.model.QuestionType.WRITTEN_TASK) {
                state.essayText.isBlank()
            } else {
                !state.userAnswers.containsKey(q.globalNumber)
            }
        }
        if (unanswered != null) {
            goToQuestion(unanswered.globalNumber)
        } else {
            goToQuestion(1)
        }
    }

    fun goToFirstFlaggedQuestion() {
        val state = _uiState.value
        val firstFlagged = state.flaggedQuestions.minOrNull()
        if (firstFlagged != null) {
            goToQuestion(firstFlagged)
        }
    }

    fun deliverExam() {
        timerJob?.cancel()
        val state = _uiState.value
        val currentAttempt = state.activeAttempt ?: MockDataStore.completedAttempt

        val qMap = allQuestions.associateBy { it.globalNumber }
        val mod1Correct = (1..30).count { state.userAnswers[it] != null && state.userAnswers[it] == qMap[it]?.correctOptionId }
        val mod2Correct = (31..60).count { state.userAnswers[it] != null && state.userAnswers[it] == qMap[it]?.correctOptionId }
        val mod3Correct = (61..90).count { state.userAnswers[it] != null && state.userAnswers[it] == qMap[it]?.correctOptionId }
        val mod5Correct = (92..135).count { state.userAnswers[it] != null && state.userAnswers[it] == qMap[it]?.correctOptionId }

        val totalRealCorrect = mod1Correct + mod2Correct + mod3Correct + mod5Correct
        val finalCorrect = if (totalRealCorrect > 0) totalRealCorrect else 108

        val timeSpentSec = (currentAttempt.totalDurationSeconds - state.remainingSeconds).coerceAtLeast(3600)
        val hours = timeSpentSec / 3600
        val minutes = (timeSpentSec % 3600) / 60
        val timeFormatted = "${hours} h ${minutes} min"

        val completed = currentAttempt.copy(
            isPaused = false,
            remainingSeconds = 0,
            timeSpentFormatted = timeFormatted,
            correctAnswers = finalCorrect,
            averageMinutesPerQuestion = 1.7f,
            essayResponse = state.essayText.ifEmpty { MockDataStore.completedAttempt.essayResponse },
            moduleResults = listOf(
                com.example.domain.model.ModuleResult(
                    moduleCode = ModuleCode.LECTURA_CRITICA,
                    correctCount = if (mod1Correct > 0) mod1Correct else 25,
                    totalCount = 30,
                    competencies = MockDataStore.completedAttempt.moduleResults[0].competencies
                ),
                com.example.domain.model.ModuleResult(
                    moduleCode = ModuleCode.RAZONAMIENTO_CUANTITATIVO,
                    correctCount = if (mod2Correct > 0) mod2Correct else 24,
                    totalCount = 30,
                    competencies = MockDataStore.completedAttempt.moduleResults[1].competencies
                ),
                com.example.domain.model.ModuleResult(
                    moduleCode = ModuleCode.COMPETENCIAS_CIUDADANAS,
                    correctCount = if (mod3Correct > 0) mod3Correct else 26,
                    totalCount = 30,
                    competencies = MockDataStore.completedAttempt.moduleResults[2].competencies
                ),
                com.example.domain.model.ModuleResult(
                    moduleCode = ModuleCode.INGLES,
                    correctCount = if (mod5Correct > 0) mod5Correct else 37,
                    totalCount = 44,
                    competencies = MockDataStore.completedAttempt.moduleResults[3].competencies
                )
            )
        )
        attemptRepository.saveAttempt(completed)
        _uiState.value = _uiState.value.copy(
            selectedAttemptForDetail = completed,
            historyAttempts = attemptRepository.getAttempts(),
            activeAttempt = null,
            isTimerRunning = false,
            showDeliverConfirmDialog = false
        )
        navigateTo("resultados")
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingSeconds > 0) {
                delay(1000)
                val newSeconds = _uiState.value.remainingSeconds - 1
                _uiState.value = _uiState.value.copy(remainingSeconds = newSeconds)
            }
        }
    }

    fun selectAnswer(questionNumber: Int, optionId: String) {
        val currentAnswers = _uiState.value.userAnswers.toMutableMap()
        currentAnswers[questionNumber] = optionId
        _uiState.value = _uiState.value.copy(userAnswers = currentAnswers)
    }

    fun toggleFlagQuestion(questionNumber: Int) {
        val flags = _uiState.value.flaggedQuestions.toMutableSet()
        if (flags.contains(questionNumber)) {
            flags.remove(questionNumber)
        } else {
            flags.add(questionNumber)
        }
        _uiState.value = _uiState.value.copy(flaggedQuestions = flags)
    }

    fun updateEssayText(text: String) {
        _uiState.value = _uiState.value.copy(essayText = text)
    }

    fun goToQuestion(globalNumber: Int) {
        val index = allQuestions.indexOfFirst { it.globalNumber == globalNumber }
        if (index >= 0) {
            val q = allQuestions[index]
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = index,
                currentModule = q.moduleCode,
                showQuestionMapModal = false
            )
            when (q.type) {
                com.example.domain.model.QuestionType.WRITTEN_TASK -> navigateTo("comunicacion_escrita")
                com.example.domain.model.QuestionType.ENGLISH_GAP_OR_READING -> navigateTo("pregunta_ingles")
                else -> {
                    if (q.moduleCode == ModuleCode.INGLES) navigateTo("pregunta_ingles")
                    else navigateTo("pregunta_seleccion")
                }
            }
        }
    }

    fun nextQuestion() {
        val nextIdx = (_uiState.value.currentQuestionIndex + 1).coerceAtMost(allQuestions.size - 1)
        val q = allQuestions[nextIdx]
        _uiState.value = _uiState.value.copy(
            currentQuestionIndex = nextIdx,
            currentModule = q.moduleCode
        )
        when (q.type) {
            com.example.domain.model.QuestionType.WRITTEN_TASK -> navigateTo("comunicacion_escrita")
            else -> {
                if (q.moduleCode == ModuleCode.INGLES) navigateTo("pregunta_ingles")
                else navigateTo("pregunta_seleccion")
            }
        }
    }

    fun previousQuestion() {
        val prevIdx = (_uiState.value.currentQuestionIndex - 1).coerceAtLeast(0)
        val q = allQuestions[prevIdx]
        _uiState.value = _uiState.value.copy(
            currentQuestionIndex = prevIdx,
            currentModule = q.moduleCode
        )
        when (q.type) {
            com.example.domain.model.QuestionType.WRITTEN_TASK -> navigateTo("comunicacion_escrita")
            else -> {
                if (q.moduleCode == ModuleCode.INGLES) navigateTo("pregunta_ingles")
                else navigateTo("pregunta_seleccion")
            }
        }
    }

    fun setPauseDialogVisible(visible: Boolean) {
        _uiState.value = _uiState.value.copy(showPauseDialog = visible)
    }

    fun setQuestionMapModalVisible(visible: Boolean) {
        _uiState.value = _uiState.value.copy(showQuestionMapModal = visible)
    }

    fun setSelectedAttempt(attempt: ExamAttempt) {
        _uiState.value = _uiState.value.copy(selectedAttemptForDetail = attempt)
    }

    fun setSelectedModuleFilter(moduleCode: ModuleCode?) {
        _uiState.value = _uiState.value.copy(selectedModuleFilter = moduleCode)
    }

    fun updateSettings(newSettings: InclusiveSettings) {
        settingsRepository.updateSettings(newSettings)
        _uiState.value = _uiState.value.copy(settings = newSettings)
    }
}
