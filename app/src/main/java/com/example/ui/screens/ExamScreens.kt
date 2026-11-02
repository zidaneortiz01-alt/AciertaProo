package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ModuleCode
import com.example.domain.model.Question
import com.example.ui.components.BrutalistButton
import com.example.ui.components.BrutalistCard
import com.example.ui.components.ExamTimerBadge
import com.example.ui.components.NeoBadge
import com.example.ui.components.WayuuPatternBanner
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.InkDark
import com.example.ui.theme.MonospaceCodeStyle
import com.example.ui.theme.PrimaryContainerGold
import com.example.ui.theme.SecondaryContainerCyan
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.SuccessContainerGreen
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceCream
import com.example.ui.theme.TertiaryContainerPink
import com.example.ui.theme.TertiaryMagenta
import com.example.ui.viewmodel.AciertaProViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun ExamFrameScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val currentQuestion = viewModel.allQuestions.getOrNull(uiState.currentQuestionIndex)
        ?: viewModel.allQuestions.first()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Exam Header
        ExamHeader(
            remainingSeconds = uiState.remainingSeconds,
            onPauseClick = { viewModel.setPauseDialogVisible(true) },
            onMapClick = { viewModel.setQuestionMapModalVisible(true) },
            onExitClick = { viewModel.setPauseDialogVisible(true) }
        )

        // Module Indicator Subheader
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainerLow)
                .border(width = 1.dp, color = InkDark.copy(alpha = 0.2f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "MÓDULO ${currentQuestion.moduleCode.number}: ${currentQuestion.moduleCode.title.uppercase()}",
                    style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InkDark)
                )
                Text(
                    text = "Pregunta ${currentQuestion.globalNumber} de 135",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
            }

            val isFlagged = uiState.flaggedQuestions.contains(currentQuestion.globalNumber)
            IconButton(
                onClick = { viewModel.toggleFlagQuestion(currentQuestion.globalNumber) },
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isFlagged) PrimaryContainerGold else SurfaceContainerLowest)
                    .border(1.5.dp, InkDark, RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = if (isFlagged) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Marcar para revisión",
                    tint = InkDark,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Question Content (Scrollable)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Context passage box
            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp,
                cornerRadius = 12.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "ENUNCIADO Y CONTEXTO",
                        style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryTeal)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = currentQuestion.contextText,
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = InkDark
                    )
                }
            }

            // Question prompt
            Text(
                text = currentQuestion.questionText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.sp,
                color = InkDark
            )

            // Options List
            val selectedOption = uiState.userAnswers[currentQuestion.globalNumber]
            currentQuestion.options.forEach { option ->
                val isSelected = selectedOption == option.id
                BrutalistCard(
                    backgroundColor = if (isSelected) PrimaryContainerGold else SurfaceContainerLowest,
                    shadowOffset = if (isSelected) 2.5.dp else 1.5.dp,
                    cornerRadius = 10.dp,
                    onClick = { viewModel.selectAnswer(currentQuestion.globalNumber, option.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) InkDark else SurfaceContainerLow)
                                .border(1.5.dp, InkDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option.id,
                                style = MonospaceCodeStyle.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) SurfaceCream else InkDark
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = option.text,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = InkDark,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Exam Navigation Footer
        ExamFooter(
            onPreviousClick = { viewModel.previousQuestion() },
            onNextClick = { viewModel.nextQuestion() },
            onConfirmModuleClick = { viewModel.confirmCurrentModule() }
        )
    }
}

@Composable
fun MultipleChoiceQuestionScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val currentQuestion = viewModel.allQuestions.getOrNull(uiState.currentQuestionIndex)
        ?: viewModel.allQuestions.first()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header with pause and map buttons
        ExamHeader(
            remainingSeconds = uiState.remainingSeconds,
            onPauseClick = { viewModel.setPauseDialogVisible(true) },
            onMapClick = { viewModel.setQuestionMapModalVisible(true) },
            onExitClick = { viewModel.setPauseDialogVisible(true) }
        )

        // Module & Question badge
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NeoBadge(
                text = "${currentQuestion.moduleCode.title} (${currentQuestion.moduleQuestionIndex}/30)",
                containerColor = PrimaryContainerGold
            )

            Text(
                text = "Pregunta #${currentQuestion.globalNumber}",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )
        }

        // Body
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = currentQuestion.contextText,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = InkDark
                    )
                }
            }

            Text(
                text = currentQuestion.questionText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = InkDark
            )

            val selectedOption = uiState.userAnswers[currentQuestion.globalNumber]
            currentQuestion.options.forEach { option ->
                val isSelected = selectedOption == option.id
                BrutalistCard(
                    backgroundColor = if (isSelected) PrimaryContainerGold else SurfaceContainerLowest,
                    shadowOffset = if (isSelected) 2.5.dp else 1.5.dp,
                    onClick = { viewModel.selectAnswer(currentQuestion.globalNumber, option.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) InkDark else SurfaceContainerLow)
                                .border(1.5.dp, InkDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option.id,
                                style = MonospaceCodeStyle.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) SurfaceCream else InkDark
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = option.text,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = InkDark
                        )
                    }
                }
            }
        }

        // Exam Navigation Footer
        ExamFooter(
            onPreviousClick = { viewModel.previousQuestion() },
            onNextClick = { viewModel.nextQuestion() },
            onConfirmModuleClick = { viewModel.confirmCurrentModule() }
        )
    }
}

@Composable
fun WrittenTaskScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    var essayText by remember { mutableStateOf(uiState.essayText.ifEmpty { viewModel.uiState.value.selectedAttemptForDetail.essayResponse }) }
    val wordCount = essayText.split(Regex("\\s+")).filter { it.isNotBlank() }.size
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
            .imePadding()
    ) {
        WayuuPatternBanner()

        // Header
        ExamHeader(
            remainingSeconds = uiState.remainingSeconds,
            onPauseClick = { viewModel.setPauseDialogVisible(true) },
            onMapClick = { viewModel.setQuestionMapModalVisible(true) },
            onExitClick = { viewModel.setPauseDialogVisible(true) }
        )

        // Subheader
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NeoBadge(text = "MÓDULO 04: COMUNICACIÓN ESCRITA", containerColor = TertiaryContainerPink)
            Text(
                text = "$wordCount palabras",
                style = MonospaceCodeStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Task Prompt Box
            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "CONSIGNA DE ESCRITURA",
                        style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TertiaryMagenta)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = viewModel.essayPrompt,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = InkDark
                    )
                }
            }

            Text(
                text = "Redacta tu texto argumentativo (Introducción, tesis, argumentos y conclusión):",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = InkDark
            )

            // Essay Editor (large multi-line area)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                BasicTextField(
                    value = essayText,
                    onValueChange = {
                        essayText = it
                        viewModel.updateEssayText(it)
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = InkDark
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Button: submitEssayBtn -> Pregunta de Inglés
            BrutalistButton(
                text = "Guardar y pasar a Inglés",
                onClick = {
                    viewModel.updateEssayText(essayText)
                    viewModel.goToQuestion(92)
                },
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                containerColor = TertiaryContainerPink,
                contentColor = InkDark,
                shadowOffset = 3.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "submitEssayBtn"
            )
        }

        // Exam Navigation Footer
        ExamFooter(
            onPreviousClick = {
                viewModel.updateEssayText(essayText)
                viewModel.previousQuestion()
            },
            onNextClick = {
                viewModel.updateEssayText(essayText)
                viewModel.nextQuestion()
            },
            onConfirmModuleClick = {
                viewModel.updateEssayText(essayText)
                viewModel.confirmCurrentModule()
            }
        )
    }
}

@Composable
fun EnglishQuestionScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val currentQuestion = viewModel.allQuestions.getOrNull(uiState.currentQuestionIndex) ?: viewModel.allQuestions.last()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header
        ExamHeader(
            remainingSeconds = uiState.remainingSeconds,
            onPauseClick = { viewModel.setPauseDialogVisible(true) },
            onMapClick = { viewModel.setQuestionMapModalVisible(true) },
            onExitClick = { viewModel.setPauseDialogVisible(true) }
        )

        // Subheader
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NeoBadge(
                text = "MÓDULO 05: INGLÉS (${currentQuestion.moduleQuestionIndex}/44)",
                containerColor = SecondaryContainerCyan
            )
            Text(
                text = "Pregunta #${currentQuestion.globalNumber} (92-135)",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "ENGLISH NOTICE / READING TEXT",
                        style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryTeal)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = currentQuestion.contextText,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = InkDark
                    )
                }
            }

            Text(
                text = currentQuestion.questionText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = InkDark
            )

            val selectedOption = uiState.userAnswers[currentQuestion.globalNumber]
            currentQuestion.options.forEach { option ->
                val isSelected = selectedOption == option.id
                BrutalistCard(
                    backgroundColor = if (isSelected) PrimaryContainerGold else SurfaceContainerLowest,
                    shadowOffset = if (isSelected) 2.5.dp else 1.5.dp,
                    onClick = { viewModel.selectAnswer(currentQuestion.globalNumber, option.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) InkDark else SurfaceContainerLow)
                                .border(1.5.dp, InkDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option.id,
                                style = MonospaceCodeStyle.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) SurfaceCream else InkDark
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = option.text,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = InkDark
                        )
                    }
                }
            }
        }

        // Footer
        val isLast = uiState.currentQuestionIndex >= viewModel.allQuestions.size - 1
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, InkDark, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(SurfaceCream)
                .navigationBarsPadding()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BrutalistButton(
                text = "Anterior",
                onClick = { viewModel.previousQuestion() },
                leadingIcon = Icons.AutoMirrored.Filled.ArrowBack,
                containerColor = SurfaceContainerLowest,
                shadowOffset = 2.dp,
                modifier = Modifier.weight(1f)
            )

            BrutalistButton(
                text = if (isLast) "Revisión final" else "Siguiente",
                onClick = {
                    if (isLast) viewModel.navigateTo("revision_previa")
                    else viewModel.nextQuestion()
                },
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                containerColor = PrimaryContainerGold,
                shadowOffset = 2.dp,
                modifier = Modifier.weight(1f)
            )

            BrutalistButton(
                text = "Entregar / Revisar",
                onClick = { viewModel.navigateTo("revision_previa") },
                containerColor = InkDark,
                contentColor = SurfaceCream,
                shadowOffset = 2.5.dp,
                modifier = Modifier.weight(1.3f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PreDeliveryReviewScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val totalCount = 135
    val answeredCount = uiState.userAnswers.size
    val flaggedCount = uiState.flaggedQuestions.size
    val pendingCount = totalCount - answeredCount
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header with continue button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo("marco") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Continuar simulacro",
                    tint = InkDark
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Revisión Previa a Entrega",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Mapa de estado del examen",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }

            ExamTimerBadge(remainingSeconds = uiState.remainingSeconds)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Summary Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SuccessContainerGreen)
                        .border(1.5.dp, InkDark, RoundedCornerShape(10.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$answeredCount Respondidas",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkDark
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PrimaryContainerGold)
                        .border(1.5.dp, InkDark, RoundedCornerShape(10.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$flaggedCount Marcadas",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkDark
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHigh)
                        .border(1.5.dp, InkDark, RoundedCornerShape(10.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$pendingCount Pendientes",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkDark
                    )
                }
            }

            // Action shortcuts: First unanswered & First flagged
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BrutalistButton(
                    text = "Ir a 1.ª sin responder",
                    onClick = { viewModel.goToFirstUnansweredQuestion() },
                    leadingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                    containerColor = SecondaryContainerCyan,
                    contentColor = InkDark,
                    shadowOffset = 2.dp,
                    modifier = Modifier.weight(1f)
                )

                BrutalistButton(
                    text = "Ir a marcadas (${flaggedCount})",
                    onClick = { viewModel.goToFirstFlaggedQuestion() },
                    leadingIcon = Icons.Default.Bookmark,
                    containerColor = PrimaryContainerGold,
                    contentColor = InkDark,
                    shadowOffset = 2.dp,
                    modifier = Modifier.weight(1f)
                )
            }

            // Written task review link card
            BrutalistCard(
                backgroundColor = TertiaryContainerPink,
                shadowOffset = 3.dp,
                cornerRadius = 12.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Tarea de Comunicación Escrita",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                        Text(
                            text = "Texto argumentativo sobre IA y selección laboral",
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.8f)
                        )
                    }

                    BrutalistButton(
                        text = "Revisar texto",
                        onClick = { viewModel.navigateTo("comunicacion_escrita") },
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        shadowOffset = 2.dp
                    )
                }
            }

            // Question Grid Map (Full 1..135 questions)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MAPA COMPLETO (1-135)",
                    style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                )
                Text(
                    text = "$answeredCount respondidas",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryTeal
                )
            }

            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        for (qNum in 1..135) {
                            val isAnswered = uiState.userAnswers.containsKey(qNum) || (qNum == 91 && uiState.essayText.isNotBlank())
                            val isFlagged = uiState.flaggedQuestions.contains(qNum)
                            val bg = when {
                                isFlagged -> PrimaryContainerGold
                                isAnswered -> SuccessContainerGreen
                                else -> SurfaceContainerLow
                            }

                            Box(
                                modifier = Modifier
                                    .size(31.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(bg)
                                    .border(1.5.dp, InkDark, RoundedCornerShape(6.dp))
                                    .clickable { viewModel.goToQuestion(qNum) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$qNum",
                                    style = MonospaceCodeStyle.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = InkDark)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Verde: Respondida • Amarillo: Marcada • Gris: Pendiente. Toca cualquier ítem para saltar directamente.",
                        fontSize = 11.sp,
                        color = InkDark.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Footer with Volver & Entregar Simulacro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, InkDark, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(SurfaceCream)
                .navigationBarsPadding()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BrutalistButton(
                text = "Volver",
                onClick = { viewModel.navigateTo("marco") },
                leadingIcon = Icons.AutoMirrored.Filled.ArrowBack,
                containerColor = SurfaceContainerLowest,
                shadowOffset = 2.dp,
                modifier = Modifier.weight(1f)
            )

            BrutalistButton(
                text = "Entregar Simulacro",
                onClick = { viewModel.setDeliverConfirmDialogVisible(true) },
                trailingIcon = Icons.Default.CheckCircle,
                containerColor = InkDark,
                contentColor = SurfaceCream,
                shadowOffset = 3.dp,
                modifier = Modifier.weight(1.3f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionMapModal(
    uiState: UiState,
    viewModel: AciertaProViewModel,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkDark.copy(alpha = 0.6f))
            .clickable { onDismiss() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        BrutalistCard(
            backgroundColor = SurfaceCream,
            shadowOffset = 4.dp,
            cornerRadius = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = false) {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Mapa del Simulacro",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                        Text(
                            text = "Toca cualquier ítem para navegar de inmediato",
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.7f)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest)
                            .border(1.5.dp, InkDark, RoundedCornerShape(8.dp))
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar mapa", tint = InkDark)
                    }
                }

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    MapLegendChip("Respondida", SuccessContainerGreen)
                    MapLegendChip("Marcada", PrimaryContainerGold)
                    MapLegendChip("Pendiente", SurfaceContainerLowest)
                }

                val modalScrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .verticalScroll(modalScrollState),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val modules = listOf(
                        Triple("Módulo 1: Lectura Crítica (1-30)", 1..30, PrimaryContainerGold),
                        Triple("Módulo 2: Razonamiento Cuantitativo (31-60)", 31..60, SecondaryContainerCyan),
                        Triple("Módulo 3: Competencias Ciudadanas (61-90)", 61..90, SuccessContainerGreen),
                        Triple("Módulo 4: Comunicación Escrita (91)", 91..91, TertiaryContainerPink),
                        Triple("Módulo 5: Inglés (92-135)", 92..135, SecondaryContainerCyan)
                    )

                    modules.forEach { (title, range, color) ->
                        Text(
                            text = title,
                            style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.5.dp),
                            verticalArrangement = Arrangement.spacedBy(4.5.dp)
                        ) {
                            for (qNum in range) {
                                val currentQ = viewModel.allQuestions.getOrNull(uiState.currentQuestionIndex)
                                val isCurrent = currentQ?.globalNumber == qNum
                                val isAnswered = uiState.userAnswers.containsKey(qNum) || (qNum == 91 && uiState.essayText.isNotBlank())
                                val isFlagged = uiState.flaggedQuestions.contains(qNum)
                                val bg = when {
                                    isFlagged -> PrimaryContainerGold
                                    isAnswered -> SuccessContainerGreen
                                    else -> SurfaceContainerLowest
                                }
                                Box(
                                    modifier = Modifier
                                        .size(31.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(bg)
                                        .border(if (isCurrent) 2.5.dp else 1.5.dp, if (isCurrent) TertiaryMagenta else InkDark, RoundedCornerShape(6.dp))
                                        .clickable {
                                            viewModel.goToQuestion(qNum)
                                            onDismiss()
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$qNum",
                                        style = MonospaceCodeStyle.copy(
                                            fontSize = 10.sp,
                                            fontWeight = if (isCurrent) FontWeight.Black else FontWeight.Bold,
                                            color = InkDark
                                        )
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MapLegendChip(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainerLowest)
            .border(1.dp, InkDark, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
                .border(1.dp, InkDark, CircleShape)
        )
        Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = InkDark)
    }
}

@Composable
private fun ExamHeader(
    remainingSeconds: Long,
    onPauseClick: () -> Unit,
    onMapClick: () -> Unit,
    onExitClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Exit button
        IconButton(
            onClick = onExitClick,
            modifier = Modifier
                .testTag("exit-btn")
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainerLowest)
                .border(2.dp, InkDark, RoundedCornerShape(10.dp))
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Salir", tint = InkDark)
        }

        // Central timer
        ExamTimerBadge(remainingSeconds = remainingSeconds)

        // Actions
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            IconButton(
                onClick = onMapClick,
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(10.dp))
            ) {
                Icon(imageVector = Icons.Default.GridView, contentDescription = "Ver mapa de preguntas", tint = InkDark)
            }

            IconButton(
                onClick = onPauseClick,
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PrimaryContainerGold)
                    .border(2.dp, InkDark, RoundedCornerShape(10.dp))
            ) {
                Icon(imageVector = Icons.Default.Pause, contentDescription = "Pausar examen", tint = InkDark)
            }
        }
    }
}

@Composable
private fun ExamFooter(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onConfirmModuleClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, InkDark, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(SurfaceCream)
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrutalistButton(
            text = "Anterior",
            onClick = onPreviousClick,
            leadingIcon = Icons.AutoMirrored.Filled.ArrowBack,
            containerColor = SurfaceContainerLowest,
            shadowOffset = 2.dp,
            modifier = Modifier.weight(1f)
        )

        BrutalistButton(
            text = "Siguiente",
            onClick = onNextClick,
            trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
            containerColor = PrimaryContainerGold,
            shadowOffset = 2.dp,
            modifier = Modifier.weight(1f)
        )

        BrutalistButton(
            text = "Confirmar Módulo",
            onClick = onConfirmModuleClick,
            containerColor = InkDark,
            contentColor = SurfaceCream,
            shadowOffset = 2.5.dp,
            modifier = Modifier.weight(1.3f)
        )
    }
}
