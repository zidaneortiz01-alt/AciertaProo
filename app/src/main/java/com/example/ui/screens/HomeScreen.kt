package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AciertaBottomNav
import com.example.ui.components.BrutalistButton
import com.example.ui.components.BrutalistCard
import com.example.ui.components.NeoBadge
import com.example.ui.components.WayuuPatternBanner
import com.example.ui.components.neoBrutalistShadow
import com.example.ui.theme.InkDark
import com.example.ui.theme.MonospaceCodeStyle
import com.example.ui.theme.PrimaryContainerGold
import com.example.ui.theme.SecondaryContainerCyan
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceCream
import com.example.ui.theme.TertiaryContainerPink
import com.example.ui.theme.TertiaryMagenta
import com.example.ui.viewmodel.AciertaProViewModel
import com.example.ui.viewmodel.UiState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AciertaProViewModel,
    onNavigateToHome: () -> Unit
) {
    // Auto-advance after 2 seconds or click stage
    LaunchedEffect(Unit) {
        delay(2200)
        onNavigateToHome()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .testTag("stage")
            .clickable { onNavigateToHome() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            WayuuPatternBanner(
                modifier = Modifier
                    .width(220.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(28.dp))

            // Neo-brutalist Emblem Box
            Box(
                modifier = Modifier
                    .scale(scale)
                    .size(110.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(PrimaryContainerGold)
                    .border(3.dp, InkDark, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Acierta Pro Logo",
                    tint = InkDark,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ACIERTA PRO",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-0.02).sp,
                color = InkDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Saber Pro · Ingeniería de Sistemas",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = InkDark.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NeoBadge(text = "OFFLINE LOCAL", containerColor = SecondaryContainerCyan, icon = Icons.Default.CloudOff)
                NeoBadge(text = "ESTRUCTURA 2026-2", containerColor = PrimaryContainerGold)
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Toca la pantalla para continuar",
                style = MonospaceCodeStyle.copy(fontSize = 12.sp, color = InkDark.copy(alpha = 0.6f))
            )
        }
    }
}

@Composable
fun HomeScreen(
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
        // Wayuu pattern accent at top
        WayuuPatternBanner()

        // App Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ACIERTA PRO",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.01).sp,
                    color = InkDark
                )
                Text(
                    text = "Práctica Saber Pro · Sistemas",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = InkDark.copy(alpha = 0.7f)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NeoBadge(
                    text = "LOCAL 2.4 MB",
                    containerColor = SecondaryContainerCyan,
                    contentColor = InkDark
                )

                IconButton(
                    onClick = { viewModel.navigateTo("ajustes") },
                    modifier = Modifier
                        .testTag("header_settings_btn")
                        .size(42.dp)
                        .neoBrutalistShadow(shadowOffset = 2.dp, cornerRadius = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLowest)
                        .border(2.dp, InkDark, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Ajustes",
                        tint = InkDark,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        // Main scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Toast indicator for exit
            if (uiState.showExitConfirmToast) {
                BrutalistCard(
                    backgroundColor = PrimaryContainerGold,
                    shadowOffset = 2.dp,
                    cornerRadius = 8.dp
                ) {
                    Text(
                        text = "Presiona Atrás nuevamente para salir de Acierta Pro",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkDark,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            // 1. Welcome Header Banner with Wayuu Motif and Estructura 2026-2 badge
            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp,
                cornerRadius = 14.dp
            ) {
                Column {
                    WayuuPatternBanner(modifier = Modifier.fillMaxWidth().height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            NeoBadge(
                                text = "Estructura 2026-2",
                                containerColor = PrimaryContainerGold,
                                contentColor = InkDark,
                                shadowOffset = 1.dp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "¡A darle al Saber Pro!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = (-0.02).sp,
                                color = InkDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Ingeniería de Sistemas • Práctica 100% offline y sin registro",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = InkDark.copy(alpha = 0.8f)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .neoBrutalistShadow(shadowOffset = 2.dp, cornerRadius = 12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SecondaryContainerCyan)
                                .border(2.dp, InkDark, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Storage,
                                contentDescription = null,
                                tint = InkDark,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            // 2. Resumable Active Exam Banner (if paused exam exists)
            val paused = uiState.activeAttempt
            if (paused != null && paused.isPaused) {
                BrutalistCard(
                    backgroundColor = SurfaceContainerLow,
                    borderWidth = 2.dp,
                    shadowOffset = 3.5.dp,
                    cornerRadius = 14.dp,
                    testTag = "card_paused_exam"
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NeoBadge(
                                text = "En pausa",
                                containerColor = PrimaryContainerGold,
                                contentColor = InkDark,
                                icon = Icons.Default.PauseCircle,
                                shadowOffset = 2.dp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = InkDark.copy(alpha = 0.8f),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Restan 2 h 44 min",
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InkDark
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Sesión 1 · 5 módulos genéricos",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                        Text(
                            text = "Módulo 02 · Razonamiento Cuantitativo",
                            fontSize = 13.sp,
                            color = InkDark.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Progress bar with hard border and subtle shadow
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Progreso del examen",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InkDark
                                )
                                Text(
                                    text = "Pregunta 14 de 30 (47%)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryTeal
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(14.dp)
                                    .neoBrutalistShadow(shadowOffset = 1.5.dp, cornerRadius = 999.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerLowest)
                                    .border(1.5.dp, InkDark, CircleShape)
                                    .padding(2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.47f)
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                        .background(SecondaryContainerCyan)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        BrutalistButton(
                            text = "Continuar intento",
                            onClick = { viewModel.resumePausedExam() },
                            leadingIcon = Icons.Default.PlayArrow,
                            containerColor = SecondaryContainerCyan,
                            contentColor = InkDark,
                            shadowOffset = 3.dp,
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "btnContinueExam"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Descartar este intento",
                                color = Color(0xFFBA1A1A),
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .testTag("btnDiscardExam")
                                    .clickable { viewModel.setDiscardDialogVisible(true) }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // 3. Hero Card: New Exam (cardNewExam)
            BrutalistCard(
                backgroundColor = PrimaryContainerGold,
                shadowOffset = 3.5.dp,
                cornerRadius = 14.dp,
                onClick = { viewModel.navigateTo("configurar") },
                testTag = "cardNewExam"
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .neoBrutalistShadow(shadowOffset = 2.dp, cornerRadius = 12.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SurfaceContainerLowest)
                                        .border(2.dp, InkDark, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = null,
                                        tint = InkDark,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Nuevo simulacro",
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = InkDark
                                    )
                                    Text(
                                        text = "Elige módulos genéricos o específicos: Diseño de Software, Formulación, Razonamiento.",
                                        fontSize = 12.5.sp,
                                        color = InkDark,
                                        fontWeight = FontWeight.Medium,
                                        lineHeight = 17.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .neoBrutalistShadow(shadowOffset = 1.5.dp, cornerRadius = 999.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerLowest)
                                    .border(2.dp, InkDark, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = InkDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    // Recommended Badge Ribbon in top right
                    NeoBadge(
                        text = "⭐ Recomendado",
                        containerColor = TertiaryMagenta,
                        contentColor = SurfaceCream,
                        shadowOffset = 1.5.dp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 8.dp, top = 8.dp)
                    )
                }
            }

            // Quick Stats Card: Last Attempt Overview
            val lastAttempt = uiState.selectedAttemptForDetail
            BrutalistCard(
                backgroundColor = SurfaceContainerLow,
                shadowOffset = 3.dp,
                cornerRadius = 14.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ÚLTIMO INTENTO",
                            style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                        )
                        Text(
                            text = "ID: ${lastAttempt.id}",
                            style = MonospaceCodeStyle.copy(fontSize = 11.sp, color = InkDark.copy(alpha = 0.7f))
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "${lastAttempt.correctAnswers}/${lastAttempt.totalQuestions}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = InkDark
                            )
                            Text(
                                text = "Aciertos (${String.format("%.1f", lastAttempt.scorePercentage)} %)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryTeal
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = lastAttempt.timeSpentFormatted,
                                fontSize = 20.sp,
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

                    Spacer(modifier = Modifier.height(12.dp))

                    BrutalistButton(
                        text = "Ver Resultados e Informe",
                        onClick = { viewModel.navigateTo("resultados") },
                        containerColor = SurfaceContainerLowest,
                        contentColor = InkDark,
                        shadowOffset = 2.5.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Row with History and Backups cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Card History (cardHistory)
                BrutalistCard(
                    modifier = Modifier.weight(1f),
                    backgroundColor = SurfaceContainerLowest,
                    shadowOffset = 3.dp,
                    cornerRadius = 14.dp,
                    onClick = { viewModel.navigateTo("historial") },
                    testTag = "cardHistory"
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .neoBrutalistShadow(shadowOffset = 1.5.dp, cornerRadius = 10.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(TertiaryContainerPink)
                                    .border(1.5.dp, InkDark, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = TertiaryMagenta,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            NeoBadge(
                                text = "5 Pruebas",
                                containerColor = SurfaceContainerHigh,
                                contentColor = InkDark,
                                shadowOffset = 0.dp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Historial de pruebas",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                        Text(
                            text = "Último: 112/135 aciertos (83.0 %) · Hace 2 días",
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.75f)
                        )
                    }
                }

                // Card Backups (cardBackups)
                BrutalistCard(
                    modifier = Modifier.weight(1f),
                    backgroundColor = SurfaceContainerLowest,
                    shadowOffset = 3.dp,
                    cornerRadius = 14.dp,
                    onClick = { viewModel.navigateTo("respaldos") },
                    testTag = "cardBackups"
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .neoBrutalistShadow(shadowOffset = 1.5.dp, cornerRadius = 10.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SecondaryContainerCyan)
                                    .border(1.5.dp, InkDark, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Storage,
                                    contentDescription = null,
                                    tint = SecondaryTeal,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            NeoBadge(
                                text = "Hace 1 día",
                                containerColor = SurfaceContainerHigh,
                                contentColor = InkDark,
                                shadowOffset = 0.dp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Respaldos y datos",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                        Text(
                            text = "Exportar / importar progreso sin usar cuentas ni internet.",
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.75f)
                        )
                    }
                }
            }

            // 4. Mandatory Disclaimer Badge
            BrutalistCard(
                backgroundColor = PrimaryContainerGold.copy(alpha = 0.2f),
                shadowOffset = 2.5.dp,
                cornerRadius = 12.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .neoBrutalistShadow(shadowOffset = 1.dp, cornerRadius = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryContainerGold)
                            .border(1.dp, InkDark, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = InkDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "HERRAMIENTA INDEPENDIENTE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark,
                            letterSpacing = 0.03.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "⚠️ Aviso: Este software es de práctica libre y autónoma. NO oficial ni afiliado al ICFES ni al Ministerio de Educación Nacional de Colombia.",
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.85f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Bottom Navigation Bar
        AciertaBottomNav(
            currentRoute = "inicio",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}
