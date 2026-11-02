package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BrutalistButton
import com.example.ui.components.BrutalistCard
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.BackupsScreen
import com.example.ui.screens.ConfigureScreen
import com.example.ui.screens.EnglishQuestionScreen
import com.example.ui.screens.EssayEvaluationScreen
import com.example.ui.screens.ExamFrameScreen
import com.example.ui.screens.ExportPdfScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InclusiveSettingsScreen
import com.example.ui.screens.ModuleCompetencyResultsScreen
import com.example.ui.screens.MultipleChoiceQuestionScreen
import com.example.ui.screens.PreDeliveryReviewScreen
import com.example.ui.screens.QuestionReviewScreen
import com.example.ui.screens.ReportPreviewScreen
import com.example.ui.screens.ResultsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.StatisticsScreen
import com.example.ui.screens.SummaryScreen
import com.example.ui.screens.WrittenTaskScreen
import com.example.ui.theme.AciertaProTheme
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.InkDark
import com.example.ui.theme.MonospaceCodeStyle
import com.example.ui.theme.PrimaryContainerGold
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceCream
import com.example.ui.theme.TertiaryContainerPink
import com.example.ui.viewmodel.AciertaProViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AciertaProApp()
        }
    }
}

@Composable
fun AciertaProApp(
    viewModel: AciertaProViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Android Hardware / Gesture Back Button Handling
    BackHandler {
        val handled = viewModel.navigateBack()
        if (!handled) {
            // Second back press on Inicio closes activity
            (context as? ComponentActivity)?.finish()
        }
    }

    AciertaProTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceCream)
        ) {
            // Main Router
            when (uiState.currentRoute) {
                "splash" -> SplashScreen(
                    viewModel = viewModel,
                    onNavigateToHome = { viewModel.navigateTo("inicio") }
                )
                "inicio" -> HomeScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "configurar" -> ConfigureScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "resumen" -> SummaryScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "marco" -> ExamFrameScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "pregunta_seleccion" -> MultipleChoiceQuestionScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "comunicacion_escrita" -> WrittenTaskScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "pregunta_ingles" -> EnglishQuestionScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "revision_previa" -> PreDeliveryReviewScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "resultados" -> ResultsScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "revision_preguntas" -> QuestionReviewScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "evaluacion_escrita" -> EssayEvaluationScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "resultados_modulo" -> ModuleCompetencyResultsScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "vista_previa_informe" -> ReportPreviewScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "exportar_pdf" -> ExportPdfScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "historial" -> HistoryScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "estadisticas" -> StatisticsScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "respaldos" -> BackupsScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "ajustes" -> InclusiveSettingsScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                "acerca" -> AboutScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                else -> HomeScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }

            // Pause Modal Dialog
            if (uiState.showPauseDialog) {
                PauseExamDialog(
                    onDismiss = { viewModel.setPauseDialogVisible(false) },
                    onSaveAndExit = { viewModel.saveProgressAndExit() },
                    onConfirmPause = { viewModel.pauseExam() },
                    onDiscard = {
                        viewModel.setPauseDialogVisible(false)
                        viewModel.setDiscardDialogVisible(true)
                    }
                )
            }

            // Question Map Modal
            if (uiState.showQuestionMapModal) {
                com.example.ui.screens.QuestionMapModal(
                    uiState = uiState,
                    viewModel = viewModel,
                    onDismiss = { viewModel.setQuestionMapModalVisible(false) }
                )
            }

            // Discard Exam Confirmation Dialog
            if (uiState.showDiscardDialog) {
                DiscardExamDialog(
                    onDismiss = { viewModel.setDiscardDialogVisible(false) },
                    onConfirmDiscard = { viewModel.discardExam() }
                )
            }

            // Deliver Exam Confirmation Dialog
            if (uiState.showDeliverConfirmDialog) {
                DeliverConfirmDialog(
                    answeredCount = uiState.userAnswers.size + (if (uiState.essayText.isNotBlank()) 1 else 0),
                    onDismiss = { viewModel.setDeliverConfirmDialogVisible(false) },
                    onConfirmDeliver = { viewModel.deliverExam() }
                )
            }

            // Delete Attempt Confirmation Dialog
            if (uiState.showDeleteAttemptDialog) {
                DeleteAttemptDialog(
                    attemptId = uiState.attemptToManage?.id ?: "",
                    onDismiss = { viewModel.dismissDeleteAttemptDialog() },
                    onConfirmDelete = { viewModel.confirmDeleteAttempt() }
                )
            }

            // Restart Attempt Confirmation Dialog
            if (uiState.showRestartAttemptDialog) {
                RestartAttemptDialog(
                    attemptId = uiState.attemptToManage?.id ?: "",
                    onDismiss = { viewModel.dismissRestartAttemptDialog() },
                    onConfirmRestart = { viewModel.confirmRestartAttempt() }
                )
            }
        }
    }
}

@Composable
fun PauseExamDialog(
    onDismiss: () -> Unit,
    onSaveAndExit: () -> Unit,
    onConfirmPause: () -> Unit,
    onDiscard: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkDark.copy(alpha = 0.5f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        BrutalistCard(
            backgroundColor = SurfaceCream,
            shadowOffset = 3.dp,
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = InkDark,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Pausar Simulacro",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkDark
                    )
                }

                Text(
                    text = "Tu tiempo y respuestas se guardarán de forma segura en este dispositivo. Podrás retomar el examen en cualquier momento desde el Inicio o la pantalla de Revisión.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = InkDark
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    BrutalistButton(
                        text = "Guardar progreso y salir",
                        onClick = onSaveAndExit,
                        containerColor = PrimaryContainerGold,
                        contentColor = InkDark,
                        shadowOffset = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    BrutalistButton(
                        text = "Ir a revisión previa",
                        onClick = onConfirmPause,
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        shadowOffset = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    BrutalistButton(
                        text = "Continuar respondiendo",
                        onClick = onDismiss,
                        containerColor = SurfaceCream,
                        contentColor = InkDark,
                        shadowOffset = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    BrutalistButton(
                        text = "Descartar intento",
                        onClick = onDiscard,
                        containerColor = TertiaryContainerPink,
                        contentColor = ErrorRed,
                        shadowOffset = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun DiscardExamDialog(
    onDismiss: () -> Unit,
    onConfirmDiscard: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkDark.copy(alpha = 0.5f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        BrutalistCard(
            backgroundColor = SurfaceCream,
            shadowOffset = 3.dp,
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Descartar intento en curso",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ErrorRed
                )

                Text(
                    text = "¿Estás seguro de que deseas descartar este intento? Se borrarán todas las respuestas ingresadas y el tiempo transcurrido en esta sesión.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = InkDark
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BrutalistButton(
                        text = "Cancelar",
                        onClick = onDismiss,
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        modifier = Modifier.weight(1f)
                    )

                    BrutalistButton(
                        text = "Sí, descartar",
                        onClick = onConfirmDiscard,
                        containerColor = TertiaryContainerPink,
                        contentColor = ErrorRed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun DeliverConfirmDialog(
    answeredCount: Int,
    onDismiss: () -> Unit,
    onConfirmDeliver: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkDark.copy(alpha = 0.5f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        BrutalistCard(
            backgroundColor = SurfaceCream,
            shadowOffset = 3.dp,
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Confirmar Entrega del Examen",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )

                Text(
                    text = "Has contestado $answeredCount preguntas de 135. Al entregar el simulacro, tus respuestas serán calificadas automáticamente y se generará tu reporte de competencias.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = InkDark
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    BrutalistButton(
                        text = "Entregar y calificar ahora",
                        onClick = onConfirmDeliver,
                        containerColor = PrimaryContainerGold,
                        contentColor = InkDark,
                        shadowOffset = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    BrutalistButton(
                        text = "Seguir revisando preguntas",
                        onClick = onDismiss,
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        shadowOffset = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteAttemptDialog(
    attemptId: String,
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkDark.copy(alpha = 0.5f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        BrutalistCard(
            backgroundColor = SurfaceCream,
            shadowOffset = 3.dp,
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Eliminar intento del historial",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ErrorRed
                )

                Text(
                    text = "¿Deseas eliminar definitivamente el intento $attemptId? Esta acción eliminará los registros de puntajes locales.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = InkDark
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BrutalistButton(
                        text = "Cancelar",
                        onClick = onDismiss,
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        modifier = Modifier.weight(1f)
                    )

                    BrutalistButton(
                        text = "Eliminar",
                        onClick = onConfirmDelete,
                        containerColor = TertiaryContainerPink,
                        contentColor = ErrorRed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun RestartAttemptDialog(
    attemptId: String,
    onDismiss: () -> Unit,
    onConfirmRestart: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InkDark.copy(alpha = 0.5f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        BrutalistCard(
            backgroundColor = SurfaceCream,
            shadowOffset = 3.dp,
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Reiniciar intento",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )

                Text(
                    text = "¿Deseas reiniciar el intento $attemptId? Se restablecerán tus respuestas y comenzarás el simulacro desde la pregunta 1.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = InkDark
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BrutalistButton(
                        text = "Cancelar",
                        onClick = onDismiss,
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        modifier = Modifier.weight(1f)
                    )

                    BrutalistButton(
                        text = "Reiniciar",
                        onClick = onConfirmRestart,
                        containerColor = PrimaryContainerGold,
                        contentColor = InkDark,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
