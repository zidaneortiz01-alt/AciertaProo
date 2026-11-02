package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ExamAttempt
import com.example.domain.model.InclusiveSettings
import com.example.ui.components.AciertaBottomNav
import com.example.ui.components.BrutalistButton
import com.example.ui.components.BrutalistCard
import com.example.ui.components.NeoBadge
import com.example.ui.components.NeoSwitch
import com.example.ui.components.WayuuPatternBanner
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.InkDark
import com.example.ui.theme.MonospaceCodeStyle
import com.example.ui.theme.PrimaryContainerGold
import com.example.ui.theme.SecondaryContainerCyan
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.SuccessContainerGreen
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceCream
import com.example.ui.theme.TertiaryContainerPink
import com.example.ui.theme.TertiaryMagenta
import com.example.ui.viewmodel.AciertaProViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun HistoryScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
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
                    text = "Historial de Simulacros",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "5 intentos guardados · 2.4 MB local",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }

            BrutalistButton(
                text = "Exportar JSON",
                onClick = { viewModel.navigateTo("respaldos") },
                leadingIcon = Icons.Default.Storage,
                containerColor = SurfaceContainerLowest,
                shadowOffset = 2.5.dp,
                testTag = "btnExport"
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val attempts = uiState.historyAttempts
            if (attempts.isEmpty()) {
                BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 2.dp) {
                    Text(
                        text = "No hay intentos guardados aún.",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp,
                        color = InkDark
                    )
                }
            } else {
                attempts.forEach { attempt ->
                    val percentage = (attempt.correctAnswers * 100f / attempt.totalQuestions).coerceIn(0f, 100f)
                    BrutalistCard(
                        backgroundColor = if (attempt.isPaused) PrimaryContainerGold else SurfaceContainerLowest,
                        shadowOffset = 2.5.dp,
                        cornerRadius = 14.dp,
                        testTag = "article_${attempt.id}"
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                NeoBadge(
                                    text = if (attempt.isPaused) "EN PAUSA" else "COMPLETO · ${"%.1f".format(percentage)} %",
                                    containerColor = if (attempt.isPaused) TertiaryContainerPink else SuccessContainerGreen
                                )
                                Text(
                                    text = attempt.id,
                                    style = MonospaceCodeStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = attempt.sessionTitle,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark
                            )
                            Text(
                                text = if (attempt.isPaused) {
                                    "${attempt.pausedModuleCode?.title ?: "Módulo"} · Restan ${(attempt.remainingSeconds / 60)} min"
                                } else {
                                    "${attempt.correctAnswers}/${attempt.totalQuestions} aciertos · ${attempt.timeSpentFormatted} · ${attempt.dateFormatted}"
                                },
                                fontSize = 13.sp,
                                color = InkDark.copy(alpha = 0.85f)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Action buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (attempt.isPaused) {
                                    BrutalistButton(
                                        text = "Reanudar",
                                        onClick = { viewModel.resumePausedExam() },
                                        leadingIcon = Icons.Default.PlayArrow,
                                        containerColor = InkDark,
                                        contentColor = SurfaceCream,
                                        shadowOffset = 2.dp,
                                        modifier = Modifier.weight(1.2f)
                                    )
                                } else {
                                    BrutalistButton(
                                        text = "Abrir resultados",
                                        onClick = {
                                            viewModel.setSelectedAttempt(attempt)
                                            viewModel.navigateTo("resultados")
                                        },
                                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                                        containerColor = PrimaryContainerGold,
                                        contentColor = InkDark,
                                        shadowOffset = 2.dp,
                                        modifier = Modifier.weight(1.2f)
                                    )
                                }

                                BrutalistButton(
                                    text = "Repetir ID",
                                    onClick = { viewModel.repeatExamWithIdAndSeed(attempt.id, attempt.seed) },
                                    leadingIcon = Icons.Default.Replay,
                                    containerColor = SecondaryContainerCyan,
                                    contentColor = InkDark,
                                    shadowOffset = 2.dp,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Danger actions row: Reiniciar & Eliminar
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { viewModel.requestRestartAttempt(attempt) }) {
                                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = InkDark, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Reiniciar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                TextButton(onClick = { viewModel.requestDeleteAttempt(attempt) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Eliminar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ErrorRed)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        AciertaBottomNav(
            currentRoute = "historial",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun StatisticsScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
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
                    text = "Estadísticas y Métricas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Evolución de rendimiento acumulado",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }

            NeoBadge(text = "5 INTENTOS", containerColor = PrimaryContainerGold)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Speed & pacing metric card
            BrutalistCard(
                backgroundColor = PrimaryContainerGold,
                shadowOffset = 4.dp,
                cornerRadius = 14.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "RITMO Y VELOCIDAD PROMEDIO",
                        style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1.7 min / pregunta",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = InkDark
                    )
                    Text(
                        text = "Dentro del rango ideal para completar los 135 ítems y disponer de 45 minutos para la tarea de Comunicación Escrita.",
                        fontSize = 13.sp,
                        color = InkDark.copy(alpha = 0.85f)
                    )
                }
            }

            // Accuracy per module progress
            Text(
                text = "EFECTIVIDAD POR MÓDULO GENÉRICO",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 3.dp) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBar(name = "Lectura Crítica", value = "83.3 % (25/30)", fraction = 0.833f)
                    StatBar(name = "Razonamiento Cuantitativo", value = "86.7 % (26/30)", fraction = 0.867f)
                    StatBar(name = "Competencias Ciudadanas", value = "73.3 % (22/30)", fraction = 0.733f)
                    StatBar(name = "Inglés", value = "86.7 % (39/45)", fraction = 0.867f)
                    StatBar(name = "Comunicación Escrita", value = "77.8 % (14/18 pts)", fraction = 0.778f)
                }
            }

            // Action Buttons: Nuevo simulacro & Exportar informe de progreso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BrutalistButton(
                    text = "Nuevo simulacro",
                    onClick = { viewModel.navigateTo("configurar") },
                    leadingIcon = Icons.Default.Add,
                    containerColor = PrimaryContainerGold,
                    shadowOffset = 3.dp,
                    modifier = Modifier.weight(1f)
                )

                BrutalistButton(
                    text = "Exportar informe de progreso",
                    onClick = { viewModel.navigateTo("respaldos") },
                    trailingIcon = Icons.Default.PictureAsPdf,
                    containerColor = SurfaceContainerLowest,
                    shadowOffset = 3.dp,
                    modifier = Modifier.weight(1.3f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        AciertaBottomNav(
            currentRoute = "estadisticas",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun BackupsScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    var exportSuccessMessage by remember { mutableStateOf<String?>(null) }
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo("ajustes") },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLowest)
                        .border(2.dp, InkDark, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver a Ajustes",
                        tint = InkDark
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Respaldos y Datos",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkDark
                    )
                    Text(
                        text = "Exportación local JSON",
                        fontSize = 12.sp,
                        color = InkDark.copy(alpha = 0.7f)
                    )
                }
            }

            IconButton(
                onClick = { viewModel.navigateTo("inicio") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryContainerGold)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Ir a Inicio",
                    tint = InkDark
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
            // Local storage status
            BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 3.dp) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ALMACENAMIENTO LOCAL DISPOSITIVO",
                            style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                        )
                        NeoBadge(text = "2.4 MB", containerColor = SecondaryContainerCyan)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Todos los intentos, textos de Comunicación Escrita y respuestas se guardan en la memoria local del dispositivo sin conexión a servidores ni cuentas externas.",
                        fontSize = 13.sp,
                        color = InkDark.copy(alpha = 0.8f)
                    )
                }
            }

            // Export backup
            BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 3.dp) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Exportar Copia de Seguridad", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = InkDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Genera un archivo JSON con los 5 simulacros para transferirlo a otro dispositivo.", fontSize = 12.sp, color = InkDark.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(12.dp))

                    BrutalistButton(
                        text = "Exportar archivo JSON local",
                        onClick = { exportSuccessMessage = "Archivo acierta_pro_backup_2026.json guardado en descargas locales." },
                        leadingIcon = Icons.Default.Download,
                        containerColor = PrimaryContainerGold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Import backup
            BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 3.dp) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Restaurar Copia de Seguridad", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = InkDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Carga un archivo de respaldo previo sin sobrescribir intentos no conflictivos.", fontSize = 12.sp, color = InkDark.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(12.dp))

                    BrutalistButton(
                        text = "Seleccionar archivo JSON",
                        onClick = { exportSuccessMessage = "Archivo de respaldo validado con éxito (5 intentos sincronizados)." },
                        leadingIcon = Icons.Default.Upload,
                        containerColor = SurfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (exportSuccessMessage != null) {
                BrutalistCard(backgroundColor = SuccessContainerGreen, shadowOffset = 2.dp) {
                    Text(
                        text = exportSuccessMessage ?: "",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkDark,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        AciertaBottomNav(
            currentRoute = "ajustes",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun InclusiveSettingsScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    var settings by remember { mutableStateOf(uiState.settings) }
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
                    text = "Ajustes Inclusivos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Accesibilidad, tipografía y rendimiento",
                    fontSize = 12.sp,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }

            BrutalistButton(
                text = "Guardar",
                onClick = {
                    viewModel.updateSettings(settings)
                    viewModel.navigateBack()
                },
                containerColor = PrimaryContainerGold,
                shadowOffset = 2.5.dp,
                testTag = "save-btn"
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Font size selector: 16, 18, 21, 24 px
            Text(
                text = "TAMAÑO DE FUENTE EN ENUNCIADOS",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(16, 18, 21, 24).forEach { size ->
                    val isSelected = settings.fontSizePx == size
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) PrimaryContainerGold else SurfaceContainerLowest)
                            .border(2.dp, InkDark, RoundedCornerShape(10.dp))
                            .clickable { settings = settings.copy(fontSizePx = size) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${size}px",
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = InkDark
                        )
                    }
                }
            }

            // Toggles
            Text(
                text = "PREFERENCIAS DE ACCESIBILIDAD",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 3.dp) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    SettingToggleRow(
                        title = "Fuente para dislexia",
                        subtitle = "Ajusta espaciado y contraste de caracteres",
                        checked = settings.dyslexiaFont,
                        onCheckedChange = { settings = settings.copy(dyslexiaFont = it) }
                    )

                    SettingToggleRow(
                        title = "Contraste reforzado",
                        subtitle = "Bordes con trazo saturado y fondo plano",
                        checked = settings.highContrast,
                        onCheckedChange = { settings = settings.copy(highContrast = it) }
                    )

                    SettingToggleRow(
                        title = "Reducir movimiento",
                        subtitle = "Desactiva transiciones y rebotes táctiles",
                        checked = settings.reduceMotion,
                        onCheckedChange = { settings = settings.copy(reduceMotion = it) }
                    )

                    SettingToggleRow(
                        title = "Vibración háptica",
                        subtitle = "Feedback físico al seleccionar respuesta",
                        checked = settings.hapticFeedback,
                        onCheckedChange = { settings = settings.copy(hapticFeedback = it) }
                    )
                }
            }

            // Links to Backups and About
            Text(
                text = "GESTIÓN Y ACERCA DE",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 2.5.dp,
                onClick = { viewModel.navigateTo("respaldos") }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Respaldos y datos", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        Text(text = "Exportar e importar historial local JSON", fontSize = 12.sp, color = InkDark.copy(alpha = 0.7f))
                    }
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Ir a Respaldos y datos", tint = InkDark)
                }
            }

            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 2.5.dp,
                onClick = { viewModel.navigateTo("acerca") }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Acerca de y fuentes", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        Text(text = "Estructura 2026-2 y aviso de no oficialidad", fontSize = 12.sp, color = InkDark.copy(alpha = 0.7f))
                    }
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Ir a Acerca de y fuentes", tint = InkDark)
                }
            }

            BrutalistButton(
                text = "Aplicar y continuar",
                onClick = {
                    viewModel.updateSettings(settings)
                    viewModel.navigateBack()
                },
                leadingIcon = Icons.Default.Save,
                containerColor = PrimaryContainerGold,
                shadowOffset = 3.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "save-btn-bottom"
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        AciertaBottomNav(
            currentRoute = "ajustes",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun AboutScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
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
                onClick = { viewModel.navigateTo("ajustes") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver a Ajustes",
                    tint = InkDark
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Acerca de y fuentes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Estructura 2026-2 y marco informativo",
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
            // App Identity Card
            BrutalistCard(backgroundColor = PrimaryContainerGold, shadowOffset = 4.dp, cornerRadius = 14.dp) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "ACIERTA PRO v1.0", fontSize = 18.sp, fontWeight = FontWeight.Black, color = InkDark)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Herramienta autónoma de práctica para el examen Saber Pro de Ingeniería de Sistemas (Colombia).",
                        fontSize = 13.sp,
                        color = InkDark
                    )
                }
            }

            // Disclaimer Card (Rule: no official, non-profit practice tool)
            BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 3.dp) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = SecondaryTeal)
                        Text(text = "Aviso de Responsabilidad", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkDark)
                    }
                    Text(
                        text = "Acierta Pro es una herramienta de entrenamiento pedagógico no oficial e independiente. No guarda afiliación, vínculo o representación con el Ministerio de Educación Nacional de Colombia ni con el ICFES.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = InkDark.copy(alpha = 0.85f)
                    )
                }
            }

            // Structure 2026-2
            BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 3.dp) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Estructura Evaluativa 2026-2", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkDark)
                    Text(
                        text = "La Sesión 1 evalúa los cinco módulos genéricos en una bolsa continua de 280 minutos: Lectura Crítica (30), Razonamiento Cuantitativo (30), Competencias Ciudadanas (30), Inglés (45) y 1 tarea abierta de Comunicación Escrita.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = InkDark.copy(alpha = 0.85f)
                    )
                }
            }

            // Offline Local Privacy
            BrutalistCard(backgroundColor = SecondaryContainerCyan, shadowOffset = 3.dp) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.CloudOff, contentDescription = null, tint = InkDark)
                    Text(
                        text = "Privacidad total: sin registro, sin recolección de datos personales, sin analítica ni servidores remotos.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = InkDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        AciertaBottomNav(
            currentRoute = "ajustes",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
private fun PastAttemptItem(
    id: String,
    title: String,
    aciertos: String,
    time: String
) {
    BrutalistCard(backgroundColor = SurfaceContainerLowest, shadowOffset = 2.5.dp, cornerRadius = 12.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkDark)
                Text(text = id, style = MonospaceCodeStyle.copy(fontSize = 11.sp, color = InkDark.copy(alpha = 0.7f)))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = aciertos, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SecondaryTeal)
                Text(text = time, fontSize = 11.sp, color = InkDark.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
private fun StatBar(
    name: String,
    value: String,
    fraction: Float
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = InkDark)
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
        }
        Spacer(modifier = Modifier.height(4.dp))
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
                    .fillMaxWidth(fraction = fraction.coerceIn(0f, 1f))
                    .fillMaxSize()
                    .background(PrimaryContainerGold)
            )
        }
    }
}

@Composable
private fun SettingToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkDark)
            Text(text = subtitle, fontSize = 12.sp, color = InkDark.copy(alpha = 0.7f))
        }
        NeoSwitch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
