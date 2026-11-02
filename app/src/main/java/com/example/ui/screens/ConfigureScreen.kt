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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AciertaBottomNav
import com.example.ui.components.BrutalistButton
import com.example.ui.components.BrutalistCard
import com.example.ui.components.NeoBadge
import com.example.ui.components.WayuuPatternBanner
import com.example.ui.theme.InkDark
import com.example.ui.theme.MonospaceCodeStyle
import com.example.ui.theme.PrimaryContainerGold
import com.example.ui.theme.SecondaryContainerCyan
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceCream
import com.example.ui.viewmodel.AciertaProViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun ConfigureScreen(
    uiState: UiState,
    viewModel: AciertaProViewModel
) {
    var selectedSession by remember { mutableStateOf(1) } // 1: Sesión 1, 2: Sesión 2
    var timerModeStrict by remember { mutableStateOf(true) } // Bolsa 280 min
    var randomSeed by remember { mutableStateOf("seed_84920") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCream)
            .statusBarsPadding()
    ) {
        WayuuPatternBanner()

        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo("inicio") },
                modifier = Modifier
                    .testTag("back_to_menu")
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver al menú de simulacros",
                    tint = InkDark
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            Column {
                Text(
                    text = "Configurar simulacro",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Personaliza las condiciones de práctica",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Selector de Sesión
            Text(
                text = "1. SELECCIÓN DE SESIÓN",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            // Radio Card: Sesión 1
            BrutalistCard(
                backgroundColor = if (selectedSession == 1) SurfaceContainerLowest else SurfaceContainerLow,
                shadowOffset = if (selectedSession == 1) 3.5.dp else 2.dp,
                onClick = { selectedSession = 1 },
                testTag = "mode_sesion1"
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(PrimaryContainerGold)
                                    .border(2.dp, InkDark, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = InkDark,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Sesión 1",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = InkDark
                                    )
                                    NeoBadge(
                                        text = "ACTIVO",
                                        containerColor = PrimaryContainerGold,
                                        contentColor = InkDark,
                                        shadowOffset = 1.5.dp
                                    )
                                }
                                Text(
                                    text = "Módulos genéricos",
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryTeal
                                )
                            }
                        }

                        // Radio Indicator
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (selectedSession == 1) PrimaryContainerGold else SurfaceContainerLowest)
                                .border(2.dp, InkDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedSession == 1) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(InkDark)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Genéricos Saber Pro · Lectura Crítica, Razonamiento Cuantitativo, Competencias Ciudadanas, Inglés y Comunicación Escrita.",
                        fontSize = 13.sp,
                        color = InkDark.copy(alpha = 0.85f),
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(PrimaryContainerGold)
                            .border(2.dp, InkDark, RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = InkDark,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Lectura Crítica · Raz. Cuantitativo · Ciudadanas · Inglés · Comunicación Escrita · 280 min",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkDark
                        )
                    }
                }
            }

            // Radio Card: Sesión 2 (Específica)
            BrutalistCard(
                backgroundColor = if (selectedSession == 2) SurfaceContainerLowest else SurfaceContainerLow,
                shadowOffset = if (selectedSession == 2) 3.5.dp else 2.dp,
                onClick = { selectedSession = 2 },
                testTag = "mode_sesion2"
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SecondaryContainerCyan)
                                    .border(2.dp, InkDark, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Assignment,
                                    contentDescription = null,
                                    tint = InkDark,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Sesión 2",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = InkDark
                                    )
                                    NeoBadge(
                                        text = "Estructura en definición",
                                        containerColor = PrimaryContainerGold,
                                        contentColor = InkDark,
                                        shadowOffset = 1.dp
                                    )
                                }
                                Text(
                                    text = "Ingeniería de Sistemas",
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryTeal
                                )
                            }
                        }

                        // Radio Indicator
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (selectedSession == 2) PrimaryContainerGold else SurfaceContainerLowest)
                                .border(2.dp, InkDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedSession == 2) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(InkDark)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Módulos específicos: Formulación de Proyectos de Ingeniería, Diseño de Software, Matemáticas y Estadística · 270 min.",
                        fontSize = 13.sp,
                        color = InkDark.copy(alpha = 0.85f),
                        lineHeight = 18.sp
                    )
                }
            }

            // Section 2: Reglas de Tiempo
            Text(
                text = "2. CONTROL DE TIEMPO Y BOLSA ÚNICA",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
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
                            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = InkDark)
                            Text(
                                text = "Bolsa de 280 minutos (4h 40m)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkDark
                            )
                        }
                        NeoBadge(text = "ESTRICTO", containerColor = PrimaryContainerGold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "El tiempo corre sin interrupción obligatoria por módulo. Puedes distribuir los minutos libremente entre la tarea escrita y las preguntas.",
                        fontSize = 12.sp,
                        color = InkDark.copy(alpha = 0.8f)
                    )
                }
            }

            // Section 3: Semilla Aleatoria
            Text(
                text = "3. SEMILLA DE GENERACIÓN (OFFLINE)",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Identificador de semilla",
                            fontSize = 12.sp,
                            color = InkDark.copy(alpha = 0.7f)
                        )
                        Text(
                            text = randomSeed,
                            style = MonospaceCodeStyle.copy(fontSize = 15.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        )
                    }

                    IconButton(
                        onClick = {
                            val newSeed = "seed_${(10000..99999).random()}"
                            randomSeed = newSeed
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLow)
                            .border(1.5.dp, InkDark, RoundedCornerShape(8.dp))
                    ) {
                        Icon(imageVector = Icons.Default.Shuffle, contentDescription = "Cambiar semilla", tint = InkDark)
                    }
                }
            }

            // Action Button: btn-start -> Resumen previo
            BrutalistButton(
                text = "Continuar al Resumen",
                onClick = { viewModel.navigateTo("resumen") },
                trailingIcon = Icons.Default.ArrowForward,
                containerColor = PrimaryContainerGold,
                contentColor = InkDark,
                shadowOffset = 3.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "btn-start"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        AciertaBottomNav(
            currentRoute = "configurar",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
fun SummaryScreen(
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
                onClick = { viewModel.navigateTo("configurar") },
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, InkDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Configuración",
                    tint = InkDark
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            Column {
                Text(
                    text = "Resumen previo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkDark
                )
                Text(
                    text = "Verificación de estructura y módulos",
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
            // Main structure overview card
            BrutalistCard(
                backgroundColor = PrimaryContainerGold,
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
                            text = "SESIÓN 1 · SABER PRO",
                            style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
                        )
                        NeoBadge(text = "BOLSA: 280 MIN", containerColor = SurfaceContainerLowest)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "135 preguntas + 1 tarea escrita",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = InkDark
                    )
                    Text(
                        text = "Tiempo estimado: ~1.7 min por pregunta de opción múltiple más ~35 min para Comunicación Escrita.",
                        fontSize = 13.sp,
                        color = InkDark.copy(alpha = 0.85f)
                    )
                }
            }

            // Modules Table Card
            Text(
                text = "ORDEN Y NUMERACIÓN DE MÓDULOS",
                style = MonospaceCodeStyle.copy(fontWeight = FontWeight.Bold, color = InkDark)
            )

            BrutalistCard(
                backgroundColor = SurfaceContainerLowest,
                shadowOffset = 3.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ModuleItemRow(number = "01", name = "Lectura Crítica", range = "Preguntas 1 - 30", count = "30 preg")
                    ModuleItemRow(number = "02", name = "Razonamiento Cuantitativo", range = "Preguntas 31 - 60", count = "30 preg")
                    ModuleItemRow(number = "03", name = "Competencias Ciudadanas", range = "Preguntas 61 - 90", count = "30 preg")
                    ModuleItemRow(number = "04", name = "Comunicación Escrita", range = "Texto argumentativo abierto", count = "1 tarea")
                    ModuleItemRow(number = "05", name = "Inglés", range = "Preguntas 91 - 135", count = "45 preg")
                }
            }

            // Notice about local saving
            BrutalistCard(
                backgroundColor = SecondaryContainerCyan,
                shadowOffset = 3.dp
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = InkDark)
                    Text(
                        text = "Tus respuestas se guardan automáticamente en tu dispositivo. Puedes pausar en cualquier instante y retomar más tarde.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = InkDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Primary CTA: Start exam now
            BrutalistButton(
                text = "Comenzar simulacro ahora",
                onClick = { viewModel.startNewExam() },
                trailingIcon = Icons.Default.ArrowForward,
                containerColor = InkDark,
                contentColor = SurfaceCream,
                shadowOffset = 3.dp,
                modifier = Modifier.fillMaxWidth()
            )

            // Secondary CTA: Edit config
            BrutalistButton(
                text = "Editar configuración",
                onClick = { viewModel.navigateTo("configurar") },
                containerColor = SurfaceContainerLowest,
                contentColor = InkDark,
                shadowOffset = 2.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        AciertaBottomNav(
            currentRoute = "configurar",
            onNavigate = { route -> viewModel.navigateBottomNav(route) }
        )
    }
}

@Composable
private fun ModuleItemRow(
    number: String,
    name: String,
    range: String,
    count: String
) {
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
                    text = number,
                    style = MonospaceCodeStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InkDark)
                )
            }
            Column {
                Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkDark)
                Text(text = range, fontSize = 11.sp, color = InkDark.copy(alpha = 0.7f))
            }
        }

        NeoBadge(text = count, containerColor = SurfaceContainerLow)
    }
}
