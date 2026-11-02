package com.example

import com.example.data.repository.InMemoryAttemptRepository
import com.example.data.repository.InMemoryExamRepository
import com.example.data.repository.InMemorySettingsRepository
import com.example.data.repository.MockDataStore
import com.example.domain.model.ModuleCode
import com.example.ui.viewmodel.AciertaProViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testCompletedAttemptMockDataCoherence() {
        val completed = MockDataStore.completedAttempt
        assertEquals("SIM-2026-2-88F4", completed.id)
        assertEquals("seed_84920", completed.seed)
        assertEquals(135, completed.totalQuestions)
        assertEquals(112, completed.correctAnswers)
        assertEquals(83.0f, completed.scorePercentage, 0.1f)
        assertEquals("3 h 55 min", completed.timeSpentFormatted)
        assertEquals(1.7f, completed.averageMinutesPerQuestion, 0.1f)

        // Verify rubric
        assertEquals(14, completed.essayRubric.totalScore)
        assertEquals(18, completed.essayRubric.maxScore)

        // Module breakdown: Lectura 25/30, Razonamiento 26/30, Ciudadanas 22/30, Inglés 39/45
        val lectura = completed.moduleResults.find { it.moduleCode == ModuleCode.LECTURA_CRITICA }
        val razonamiento = completed.moduleResults.find { it.moduleCode == ModuleCode.RAZONAMIENTO_CUANTITATIVO }
        val ciudadanas = completed.moduleResults.find { it.moduleCode == ModuleCode.COMPETENCIAS_CIUDADANAS }
        val ingles = completed.moduleResults.find { it.moduleCode == ModuleCode.INGLES }

        assertNotNull(lectura)
        assertEquals(25, lectura?.correctCount)
        assertEquals(30, lectura?.totalCount)

        assertNotNull(razonamiento)
        assertEquals(26, razonamiento?.correctCount)
        assertEquals(30, razonamiento?.totalCount)

        assertNotNull(ciudadanas)
        assertEquals(22, ciudadanas?.correctCount)
        assertEquals(30, ciudadanas?.totalCount)

        assertNotNull(ingles)
        assertEquals(39, ingles?.correctCount)
        assertEquals(45, ingles?.totalCount)
    }

    @Test
    fun testPausedAttemptMockDataCoherence() {
        val paused = MockDataStore.pausedAttempt
        assertEquals("SIM-2026-S2-44C1", paused.id)
        assertTrue(paused.isPaused)
        assertEquals(ModuleCode.RAZONAMIENTO_CUANTITATIVO, paused.pausedModuleCode)
        assertEquals(14, paused.pausedQuestionIndex)
        assertEquals(9840L, paused.remainingSeconds) // 2h 44min
    }

    @Test
    fun testSession1StructureAndOrder() {
        assertEquals("01", ModuleCode.LECTURA_CRITICA.number)
        assertEquals(1, ModuleCode.LECTURA_CRITICA.startNumber)
        assertEquals(30, ModuleCode.LECTURA_CRITICA.endNumber)

        assertEquals("02", ModuleCode.RAZONAMIENTO_CUANTITATIVO.number)
        assertEquals(31, ModuleCode.RAZONAMIENTO_CUANTITATIVO.startNumber)
        assertEquals(60, ModuleCode.RAZONAMIENTO_CUANTITATIVO.endNumber)

        assertEquals("03", ModuleCode.COMPETENCIAS_CIUDADANAS.number)
        assertEquals(61, ModuleCode.COMPETENCIAS_CIUDADANAS.startNumber)
        assertEquals(90, ModuleCode.COMPETENCIAS_CIUDADANAS.endNumber)

        assertEquals("04", ModuleCode.COMUNICACION_ESCRITA.number)
        assertEquals(1, ModuleCode.COMUNICACION_ESCRITA.questionCount)

        assertEquals("05", ModuleCode.INGLES.number)
        assertEquals(91, ModuleCode.INGLES.startNumber)
        assertEquals(135, ModuleCode.INGLES.endNumber)
    }

    @Test
    fun testViewModelNavigationAndBackHandling() {
        val vm = AciertaProViewModel(
            InMemoryExamRepository(),
            InMemoryAttemptRepository(),
            InMemorySettingsRepository()
        )

        // Starts at splash
        assertEquals("splash", vm.uiState.value.currentRoute)

        // Navigate to inicio
        vm.navigateTo("inicio")
        assertEquals("inicio", vm.uiState.value.currentRoute)

        // Navigate to exam and back handler should open pause dialog
        vm.navigateTo("marco")
        assertEquals("marco", vm.uiState.value.currentRoute)

        val handled = vm.navigateBack()
        assertTrue(handled)
        assertTrue(vm.uiState.value.showPauseDialog)
    }

    @Test
    fun testQuestionNavigationAndModuleProgression() {
        val vm = AciertaProViewModel(
            InMemoryExamRepository(),
            InMemoryAttemptRepository(),
            InMemorySettingsRepository()
        )
        vm.startNewExam(session = 1)
        assertEquals(0, vm.uiState.value.currentQuestionIndex)
        assertEquals("pregunta_seleccion", vm.uiState.value.currentRoute)

        // Advance question
        vm.nextQuestion()
        assertEquals(1, vm.uiState.value.currentQuestionIndex)

        // Previous question
        vm.previousQuestion()
        assertEquals(0, vm.uiState.value.currentQuestionIndex)

        // Confirm Module 1 (questions 1..30) jumps to Module 2 (index 30, Q31)
        vm.confirmCurrentModule()
        assertEquals(30, vm.uiState.value.currentQuestionIndex)
        assertEquals("pregunta_seleccion", vm.uiState.value.currentRoute)

        // Jump to Written Task (Module 4, index 90)
        vm.goToQuestion(91)
        assertEquals(90, vm.uiState.value.currentQuestionIndex)
        assertEquals("comunicacion_escrita", vm.uiState.value.currentRoute)

        // Confirm Written Task jumps to English (Module 5, index 91, Q92)
        vm.confirmCurrentModule()
        assertEquals(91, vm.uiState.value.currentQuestionIndex)
        assertEquals("pregunta_ingles", vm.uiState.value.currentRoute)

        // Confirm Module 5 (last module) leads to PreDelivery review
        vm.confirmCurrentModule()
        assertEquals("revision_previa", vm.uiState.value.currentRoute)
    }

    @Test
    fun testHistoryAttemptRestartAndDelete() {
        val attemptRepo = InMemoryAttemptRepository()
        val vm = AciertaProViewModel(
            InMemoryExamRepository(),
            attemptRepo,
            InMemorySettingsRepository()
        )
        val initialCount = vm.uiState.value.historyAttempts.size
        val target = vm.uiState.value.historyAttempts.first()

        // Delete flow
        vm.requestDeleteAttempt(target)
        assertTrue(vm.uiState.value.showDeleteAttemptDialog)
        assertEquals(target, vm.uiState.value.attemptToManage)

        vm.confirmDeleteAttempt()
        assertFalse(vm.uiState.value.showDeleteAttemptDialog)
        assertEquals(initialCount - 1, vm.uiState.value.historyAttempts.size)

        // Restart flow
        val restartTarget = vm.uiState.value.historyAttempts.first()
        vm.requestRestartAttempt(restartTarget)
        assertTrue(vm.uiState.value.showRestartAttemptDialog)
        assertEquals(restartTarget, vm.uiState.value.attemptToManage)

        vm.confirmRestartAttempt()
        assertFalse(vm.uiState.value.showRestartAttemptDialog)
        assertEquals("pregunta_seleccion", vm.uiState.value.currentRoute)
    }

    @Test
    fun testJumpToUnansweredAndFlagged() {
        val vm = AciertaProViewModel(
            InMemoryExamRepository(),
            InMemoryAttemptRepository(),
            InMemorySettingsRepository()
        )
        vm.startNewExam(session = 1)
        // Answer Q1 (index 0)
        vm.selectOption("A")
        // Flag Q6 (index 5)
        vm.goToQuestion(6)
        vm.toggleFlagQuestion(5)

        // Navigate to revision_previa
        vm.navigateTo("revision_previa")

        // First unanswered should jump to Q2 (index 1)
        vm.goToFirstUnansweredQuestion()
        assertEquals(1, vm.uiState.value.currentQuestionIndex)
        assertEquals("pregunta_seleccion", vm.uiState.value.currentRoute)

        // Return to review and jump to flagged (index 5)
        vm.navigateTo("revision_previa")
        vm.goToFirstFlaggedQuestion()
        assertEquals(5, vm.uiState.value.currentQuestionIndex)
        assertEquals("pregunta_seleccion", vm.uiState.value.currentRoute)
    }
}
