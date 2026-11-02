package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ModuleCode
import com.example.domain.model.ModuleResult
import com.example.ui.components.AciertaBottomNav
import com.example.ui.components.BrutalistButton
import com.example.ui.components.BrutalistCard
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
fun ResultsScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val attempt = uiState.selectedAttemptForDetail
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Resultados Globales",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Simulacro ${attempt.id}",
                    style = MonospaceCodeStyle.copy(fontSize = 12.sp, color = InkDark.copy(alpha = 0.7f))
                )
            }

            NeoBadge(text = "83.0 % ACIERTOS", containerColor = PrimaryContainerGold)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Main score card (aciertos 112/135, no 0-300 psychometric scores)
            BrutalistCard(
                backgroundColor = PrimaryContainerGold,
                shadowOffset = 3.dp,
                cornerRadius = 16.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "DESEMPEÑO GLOBAL",
                            style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                        )
                        Text(
                            text = attempt.dateFormatted,
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.8f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "${attempt.correctAnswers}/${attempt.totalQuestions}",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Black,
                                color = InkDark
                            )
                            Text(
                                text = "Respuestas acertadas (${String.format("%.1f", attempt.scorePercentage)} %)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = attempt.timeSpentFormatted,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark
                            )
                            Text(
                                text = "1.7 min / pregunta",
                                fontSize = 12.sp,
                                color = InkDark.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Action Buttons
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BrutalistButton(
                    text = "Revisar Preguntas y Respuestas",
                    onClick = { viewModel.navigateTo("revision_preguntas") },
                    trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                    containerColor = SecondaryContainerCyan,
                    contentColor = InkDark,
                    shadowOffset = 2.5.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BrutalistButton(
                        text = "Evaluar mi texto",
                        onClick = { viewModel.navigateTo("evaluacion_escrita") },
                        containerColor = TertiaryContainerPink,
                        contentColor = InkDark,
                        shadowOffset = 2.dp,
                        modifier = Modifier.weight(1f)
                    )

                    BrutalistButton(
                        text = "Guardar informe (PDF)",
                        onClick = { viewModel.navigateTo("exportar_pdf") },
                        leadingIcon = Icons.Default.PictureAsPdf,
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        shadowOffset = 2.dp,
                        modifier = Modifier.weight(1.1f)
                    )
                }
            }

            // Section: Resultados por módulo
            Text(
                text = "RESULTADOS POR MÓDULO",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            // Module breakdown cards
            attempt.moduleResults.forEachIndexed { index, modResult ->
                BrutalistCard(
                    backgroundColor = SurfaceContainerLowest,
                    shadowOffset = 3.dp,
                    cornerRadius = 12.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(PrimaryContainerGold)
                                        .border(1.5.dp, InkDark, RoundedCornerShape(6.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = modResult.moduleCode.number,
                                        style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InkDark)
                                    )
                                }
                                Text(
                                    text = modResult.moduleCode.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InkDark
                                )
                            }

                            Text(
                                text = "${modResult.correctCount}/${modResult.totalCount} (${String.format("%.1f", modResult.percentage)}%)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryTeal
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Progress bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerHigh)
                                .border(1.5.dp, InkDark, RoundedCornerShape(6.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = (modResult.percentage / 100f).coerceIn(0f, 1f))
                                    .fillMaxSize()
                                    .background(PrimaryContainerGold)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable {
                                        viewModel.setSelectedModuleFilter(modResult.moduleCode)
                                        viewModel.navigateTo("resultados_modulo")
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Ver detalle",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryTeal
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = SecondaryTeal,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        AciertaBottomNav(
            currentRoute = "resultados",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun QuestionReviewScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    var selectedFilter by remember { mutableStateOf("ALL") } // ALL, CORRECT, INCORRECT, FLAGGED
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo("resultados") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver a resultados",
                    tint = InkDark
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Revisión de preguntas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Respuestas y retroalimentación pedagógica",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }
        }

        // Filter Pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ReviewFilterChip(label = "Todas", selected = selectedFilter == "ALL", onClick = { selectedFilter = "ALL" })
            ReviewFilterChip(label = "Acertadas", selected = selectedFilter == "CORRECT", onClick = { selectedFilter = "CORRECT" })
            ReviewFilterChip(label = "Erradas", selected = selectedFilter == "INCORRECT", onClick = { selectedFilter = "INCORRECT" })
            ReviewFilterChip(label = "Marcadas", selected = selectedFilter == "FLAGGED", onClick = { selectedFilter = "FLAGGED" })
        }

        // Questions List
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Written Task Card (Id 32)
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
                        NeoBadge(text = "MÓDULO 04: COMUNICACIÓN ESCRITA", containerColor = SurfaceCream)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tarea de Escritura Argumentativa",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                        Text(
                            text = "Rúbrica holística de 3 dimensiones · 18 puntos",
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.8f)
                        )
                    }

                    BrutalistButton(
                        text = "Ver rúbrica",
                        onClick = { viewModel.navigateTo("evaluacion_escrita") },
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        shadowOffset = 2.dp
                    )
                }
            }

            val questions = viewModel.allQuestions.filter { it.type != com.example.domain.model.QuestionType.WRITTEN_TASK }
            questions.forEach { q ->
                val userAnswer = uiState.userAnswers[q.globalNumber] ?: "B"
                val isCorrect = userAnswer == q.correctOptionId

                val matches = when (selectedFilter) {
                    "CORRECT" -> isCorrect
                    "INCORRECT" -> !isCorrect
                    "FLAGGED" -> uiState.flaggedQuestions.contains(q.globalNumber)
                    else -> true
                }

                if (matches) {
                    BrutalistCard(
                        backgroundColor = SurfaceContainerLowest,
                        shadowOffset = 3.dp,
                        cornerRadius = 12.dp
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    NeoBadge(
                                        text = "${q.moduleCode.title} · #${q.globalNumber}",
                                        containerColor = PrimaryContainerGold
                                    )
                                }

                                if (isCorrect) {
                                    NeoBadge(text = "ACERTADA", containerColor = SuccessContainerGreen, contentColor = SuccessGreen)
                                } else {
                                    NeoBadge(text = "ERRADA", containerColor = TertiaryContainerPink, contentColor = ErrorRed)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = q.questionText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Tu respuesta: Opción $userAnswer · Respuesta correcta: Opción ${q.correctOptionId}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) SuccessGreen else ErrorRed
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLow)
                                    .border(1.dp, InkDark.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "EXPLICACIÓN",
                                        style = MonospaceCodeStyle.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SecondaryTeal)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = q.explanation,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        color = InkDark
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            BrutalistButton(
                text = "Volver a resultados",
                onClick = { viewModel.navigateTo("resultados") },
                leadingIcon = Icons.AutoMirrored.Filled.ArrowBack,
                containerColor = PrimaryContainerGold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        AciertaBottomNav(
            currentRoute = "resultados",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun EssayEvaluationScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val attempt = uiState.selectedAttemptForDetail
    val rubric = attempt.essayRubric
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo("resultados") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver a Resultados",
                    tint = InkDark
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Evaluación de Escritura",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Rúbrica holística de Comunicación Escrita",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Summary score: Rúbrica 14/18, Nivel 3 Orientativo
            BrutalistCard(
                backgroundColor = TertiaryContainerPink,
                shadowOffset = 4.dp,
                cornerRadius = 16.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CALIFICACIÓN DE RÚBRICA",
                            style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                        )
                        NeoBadge(text = "NIVEL 3 ORIENTATIVO", containerColor = SurfaceContainerLowest)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${rubric.totalScore} / ${rubric.maxScore} PUNTOS",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = InkDark
                    )
                    Text(
                        text = rubric.feedback,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = InkDark.copy(alpha = 0.85f)
                    )
                }
            }

            // Dimension 1: Planteamiento
            RubricDimensionCard(
                title = "1. Planteamiento y Tesis",
                score = rubric.planteamientoScore,
                max = rubric.planteamientoMax,
                description = "Claridad y consistencia en la postura crítica asumida sobre la regulación de la IA en procesos de selección laboral."
            )

            // Dimension 2: Organización
            RubricDimensionCard(
                title = "2. Organización y Cohesión",
                score = rubric.organizacionScore,
                max = rubric.organizacionMax,
                description = "Estructura lógica por párrafos, uso adecuado de conectores argumentativos y articulación de contraargumentos."
            )

            // Dimension 3: Lenguaje y Convención
            RubricDimensionCard(
                title = "3. Dominio de Convenciones",
                score = rubric.lenguajeScore,
                max = rubric.lenguajeMax,
                description = "Precisión léxica y ortográfica, variedad sintáctica y manejo de vocabulario técnico de ingeniería."
            )

            // Button: Registrar en el historial
            BrutalistButton(
                text = "Registrar en el historial",
                onClick = { viewModel.navigateTo("historial") },
                trailingIcon = Icons.Default.History,
                containerColor = InkDark,
                contentColor = SurfaceCream,
                shadowOffset = 4.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        AciertaBottomNav(
            currentRoute = "resultados",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun ModuleCompetencyResultsScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val attempt = uiState.selectedAttemptForDetail
    val activeMod = uiState.selectedModuleFilter ?: ModuleCode.LECTURA_CRITICA
    val modResult = attempt.moduleResults.find { it.moduleCode == activeMod }
        ?: attempt.moduleResults.first()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo("resultados") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver al resumen global",
                    tint = InkDark
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Módulo y Competencias",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "${modResult.moduleCode.title} · Desglose analítico",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Module Score Banner
            BrutalistCard(
                backgroundColor = PrimaryContainerGold,
                shadowOffset = 4.dp,
                cornerRadius = 14.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "ACIERTOS EN EL MÓDULO",
                            style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        )
                        Text(
                            text = "${modResult.correctCount}/${modResult.totalCount}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = InkDark
                        )
                    }

                    NeoBadge(
                        text = "${String.format("%.1f", modResult.percentage)} %",
                        containerColor = SurfaceContainerLowest
                    )
                }
            }

            Text(
                text = "DESGLOSE POR COMPETENCIAS EVALUADAS",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            // Competency cards
            modResult.competencies.forEach { comp ->
                BrutalistCard(
                    backgroundColor = SurfaceContainerLowest,
                    shadowOffset = 3.dp,
                    cornerRadius = 10.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = comp.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${comp.correctCount}/${comp.totalCount} (${String.format("%.0f", comp.percentage)}%)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryTeal
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(SurfaceContainerHigh)
                                .border(1.dp, InkDark, RoundedCornerShape(5.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = (comp.percentage / 100f).coerceIn(0f, 1f))
                                    .fillMaxSize()
                                    .background(PrimaryContainerGold)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Footer buttons: Exportar desglose (PDF) & Volver al resumen global
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, InkDark, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(SurfaceCream)
                .navigationBarsPadding()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BrutalistButton(
                text = "Volver al resumen global",
                onClick = { viewModel.navigateTo("resultados") },
                leadingIcon = Icons.AutoMirrored.Filled.ArrowBack,
                containerColor = SurfaceContainerLowest,
                shadowOffset = 2.dp,
                modifier = Modifier.weight(1f)
            )

            BrutalistButton(
                text = "Exportar desglose (PDF)",
                onClick = { viewModel.navigateTo("exportar_pdf") },
                trailingIcon = Icons.Default.Download,
                containerColor = PrimaryContainerGold,
                shadowOffset = 2.5.dp,
                modifier = Modifier.weight(1.2f)
            )
        }
    }
}

@Composable
fun ReportPreviewScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val attempt = uiState.selectedAttemptForDetail
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Top action bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo("resultados") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver a resultados",
                    tint = InkDark
                )
            }

            Text(
                text = "Vista Previa de Informe",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = InkDark
            )

            IconButton(
                onClick = { viewModel.navigateTo("exportar_pdf") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryContainerGold)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Opciones de exportación",
                    tint = InkDark
                )
            }
        }

        // Printable-style sheet simulation
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.5.dp, InkDark, RoundedCornerShape(12.dp))
                    .padding(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    // Header inside document
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "ACIERTA PRO · INFORME OFFLINE",
                                style = MonospaceCodeStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
                            )
                            Text(
                                text = "Simulacro Saber Pro - Sistemas",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark
                            )
                        }
                        NeoBadge(text = "LOCAL", containerColor = SecondaryContainerCyan)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.5.dp)
                            .background(InkDark)
                    )

                    // Metadata block
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Identificador:", fontSize = 11.sp, color = InkDark.copy(alpha = 0.7f))
                            Text(text = attempt.id, style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark))
                        }
                        Column {
                            Text(text = "Semilla:", fontSize = 11.sp, color = InkDark.copy(alpha = 0.7f))
                            Text(text = attempt.seed, style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark))
                        }
                        Column {
                            Text(text = "Fecha:", fontSize = 11.sp, color = InkDark.copy(alpha = 0.7f))
                            Text(text = attempt.dateFormatted, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        }
                    }

                    // Global stats
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryContainerGold)
                            .border(1.5.dp, InkDark, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Aciertos", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${attempt.correctAnswers}/${attempt.totalQuestions}", fontSize = 20.sp, fontWeight = FontWeight.Black)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Porcentaje", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${String.format("%.1f", attempt.scorePercentage)} %", fontSize = 20.sp, fontWeight = FontWeight.Black)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Tiempo total", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(text = attempt.timeSpentFormatted, fontSize = 20.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }

                    // Breakdown table
                    Text(
                        text = "DESGLOSE POR MÓDULOS EVALUADOS",
                        style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InkDark)
                    )

                    attempt.moduleResults.forEach { m ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = m.moduleCode.title, fontSize = 13.sp, color = InkDark)
                            Text(
                                text = "${m.correctCount}/${m.totalCount} (${String.format("%.1f", m.percentage)}%)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Comunicación Escrita (Rúbrica)", fontSize = 13.sp, color = InkDark)
                        Text(
                            text = "${attempt.essayRubric.totalScore}/18 (Nivel 3)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Documento generado íntegramente de manera local en el dispositivo por Acierta Pro. Herramienta independiente de práctica.",
                        fontSize = 10.sp,
                        color = InkDark.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun ExportPdfScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    val context = LocalContext.current
    val attempt = uiState.selectedAttemptForDetail
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo("resultados") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver a Resultados",
                    tint = InkDark
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Exportar a PDF",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Generación de documento para impresión",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "OPCIONES DEL DOCUMENTO",
                        style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                    )
                    Text(
                        text = "El archivo PDF incluye la portada técnica con la semilla de generación, desglose de aciertos por módulo y rúbrica argumentativa.",
                        fontSize = 13.sp,
                        color = InkDark.copy(alpha = 0.8f)
                    )
                }
            }

            // btnDownload -> Share / Print system intent
            BrutalistButton(
                text = "Descargar / Compartir informe",
                onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Acierta Pro - Informe de Resultados Saber Pro\nSimulacro: ${attempt.id}\nAciertos: ${attempt.correctAnswers}/135\nTiempo: ${attempt.timeSpentFormatted}\nFecha: ${attempt.dateFormatted}\nGenerado 100% offline."
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Compartir informe PDF Acierta Pro")
                    context.startActivity(shareIntent)
                },
                leadingIcon = Icons.Default.Share,
                containerColor = PrimaryContainerGold,
                contentColor = InkDark,
                shadowOffset = 3.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "btnDownload"
            )

            BrutalistButton(
                text = "Ver vista previa del informe",
                onClick = { viewModel.navigateTo("vista_previa_informe") },
                leadingIcon = Icons.Default.PictureAsPdf,
                containerColor = SurfaceContainerLowest,
                contentColor = InkDark,
                shadowOffset = 2.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        AciertaBottomNav(
            currentRoute = "resultados",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
private fun ReviewFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) PrimaryContainerGold else SurfaceContainerLowest)
            .border(2.dp, InkDark, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = InkDark
        )
    }
}

@Composable
private fun RubricDimensionCard(
    title: String,
    score: Int,
    max: Int,
    description: String
) {
    BrutalistCard(
        backgroundColor = SurfaceContainerLowest,
        shadowOffset = 3.dp,
        cornerRadius = 12.dp
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                NeoBadge(
                    text = "$score / $max PUNTOS",
                    containerColor = PrimaryContainerGold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = InkDark.copy(alpha = 0.8f)
            )
        }
    }
}
