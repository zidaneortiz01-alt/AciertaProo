package com.example.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InkDark
import com.example.ui.theme.MonospaceCodeStyle
import com.example.ui.theme.PrimaryContainerGold
import com.example.ui.theme.SecondaryContainerCyan
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceCream

import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset

fun Modifier.neoBrutalistShadow(
    shadowOffset: Dp = 2.5.dp,
    borderColor: Color = InkDark,
    cornerRadius: Dp = 12.dp
): Modifier = this
    .padding(end = shadowOffset, bottom = shadowOffset)
    .drawBehind {
        if (shadowOffset > 0.dp) {
            val r = if (cornerRadius >= 100.dp) size.height / 2f else cornerRadius.toPx()
            drawRoundRect(
                color = borderColor,
                topLeft = Offset(shadowOffset.toPx(), shadowOffset.toPx()),
                size = size,
                cornerRadius = CornerRadius(r, r)
            )
        }
    }

@Composable
fun BrutalistCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = SurfaceContainerLowest,
    borderColor: Color = InkDark,
    borderWidth: Dp = 2.dp,
    shadowOffset: Dp = 2.5.dp,
    cornerRadius: Dp = 12.dp,
    onClick: (() -> Unit)? = null,
    testTag: String? = null,
    content: @Composable BoxScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val pressTranslation by animateDpAsState(
        targetValue = if (isPressed && onClick != null) (shadowOffset * 0.5f).coerceAtLeast(1.dp) else 0.dp,
        animationSpec = tween(80),
        label = "card_press"
    )

    Box(
        modifier = modifier
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
            .padding(end = shadowOffset, bottom = shadowOffset)
            .drawBehind {
                if (shadowOffset > 0.dp) {
                    val r = if (cornerRadius >= 100.dp) size.height / 2f else cornerRadius.toPx()
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(shadowOffset.toPx(), shadowOffset.toPx()),
                        size = size,
                        cornerRadius = CornerRadius(r, r)
                    )
                }
            },
        propagateMinConstraints = true
    ) {
        Box(
            modifier = Modifier
                .offset(x = pressTranslation, y = pressTranslation)
                .clip(RoundedCornerShape(cornerRadius))
                .background(backgroundColor)
                .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                .then(
                    if (onClick != null) {
                        Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    tryAwaitRelease()
                                    isPressed = false
                                },
                                onTap = { onClick() }
                            )
                        }
                    } else Modifier
                ),
            content = content
        )
    }
}

@Composable
fun BrutalistButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = PrimaryContainerGold,
    contentColor: Color = InkDark,
    borderColor: Color = InkDark,
    borderWidth: Dp = 2.dp,
    shadowOffset: Dp = 2.5.dp,
    cornerRadius: Dp = 12.dp,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String? = null
) {
    var isPressed by remember { mutableStateOf(false) }
    val pressTranslation by animateDpAsState(
        targetValue = if (isPressed && enabled) (shadowOffset * 0.5f).coerceAtLeast(1.dp) else 0.dp,
        animationSpec = tween(80),
        label = "btn_press"
    )

    Box(
        modifier = modifier
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
            .padding(end = shadowOffset, bottom = shadowOffset)
            .drawBehind {
                if (enabled && shadowOffset > 0.dp) {
                    val r = if (cornerRadius >= 100.dp) size.height / 2f else cornerRadius.toPx()
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(shadowOffset.toPx(), shadowOffset.toPx()),
                        size = size,
                        cornerRadius = CornerRadius(r, r)
                    )
                }
            },
        propagateMinConstraints = true
    ) {
        Row(
            modifier = Modifier
                .offset(x = pressTranslation, y = pressTranslation)
                .clip(RoundedCornerShape(cornerRadius))
                .background(if (enabled) containerColor else SurfaceContainerHigh)
                .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                .then(
                    if (enabled) {
                        Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    tryAwaitRelease()
                                    isPressed = false
                                },
                                onTap = { onClick() }
                            )
                        }
                    } else Modifier
                )
                .padding(horizontal = 16.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(19.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = 0.01.sp
            )
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

@Composable
fun NeoBadge(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = PrimaryContainerGold,
    contentColor: Color = InkDark,
    borderColor: Color = InkDark,
    borderWidth: Dp = 1.5.dp,
    icon: ImageVector? = null,
    shadowOffset: Dp = 1.dp
) {
    Row(
        modifier = modifier
            .padding(end = shadowOffset, bottom = shadowOffset)
            .drawBehind {
                if (shadowOffset > 0.dp) {
                    val r = size.height / 2f
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(shadowOffset.toPx(), shadowOffset.toPx()),
                        size = size,
                        cornerRadius = CornerRadius(r, r)
                    )
                }
            }
            .clip(CircleShape)
            .background(containerColor)
            .border(borderWidth, borderColor, CircleShape)
            .padding(horizontal = 9.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(13.dp)
            )
        }
        Text(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 11.5.sp,
            letterSpacing = 0.02.sp,
            maxLines = 1
        )
    }
}

@Composable
fun WayuuPatternBanner(
    modifier: Modifier = Modifier,
    primaryColor: Color = PrimaryContainerGold,
    secondaryColor: Color = InkDark
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(9.dp)
    ) {
        val step = 14.dp.toPx()
        val h = size.height
        val w = size.width
        var x = 0f

        while (x < w) {
            val path = Path().apply {
                moveTo(x, h)
                lineTo(x + step / 2, 0f)
                lineTo(x + step, h)
                close()
            }
            drawPath(path, color = primaryColor)

            val diamond = Path().apply {
                moveTo(x + step / 2, 1.5f)
                lineTo(x + step * 0.75f, h / 2)
                lineTo(x + step / 2, h - 1.5f)
                lineTo(x + step * 0.25f, h / 2)
                close()
            }
            drawPath(diamond, color = secondaryColor)
            x += step
        }
    }
}

@Composable
fun ExamTimerBadge(
    remainingSeconds: Long,
    isPaused: Boolean = false,
    modifier: Modifier = Modifier
) {
    val hours = remainingSeconds / 3600
    val minutes = (remainingSeconds % 3600) / 60
    val seconds = remainingSeconds % 60
    val formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Row(
        modifier = modifier
            .padding(end = 2.dp, bottom = 2.dp)
            .drawBehind {
                drawRoundRect(
                    color = InkDark,
                    topLeft = Offset(2.dp.toPx(), 2.dp.toPx()),
                    size = size,
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                )
            }
            .clip(RoundedCornerShape(8.dp))
            .background(if (isPaused) SurfaceContainerHigh else PrimaryContainerGold)
            .border(2.dp, InkDark, RoundedCornerShape(8.dp))
            .padding(horizontal = 9.dp, vertical = 4.5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(if (isPaused) InkDark else Color(0xFFB7006E))
        )
        Text(
            text = if (isPaused) "PAUSADO ($formatted)" else "Restan $formatted",
            style = MonospaceCodeStyle.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = InkDark
            )
        )
    }
}

@Composable
fun NeoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val trackColor = if (checked) SecondaryContainerCyan else SurfaceContainerHigh
    val knobOffset by animateDpAsState(
        targetValue = if (checked) 26.dp else 0.dp,
        animationSpec = tween(90),
        label = "switch_knob"
    )

    Box(
        modifier = modifier
            .padding(end = 2.dp, bottom = 2.dp)
            .drawBehind {
                val r = size.height / 2f
                drawRoundRect(
                    color = InkDark,
                    topLeft = Offset(2.dp.toPx(), 2.dp.toPx()),
                    size = size,
                    cornerRadius = CornerRadius(r, r)
                )
            }
            .size(width = 58.dp, height = 32.dp)
            .clip(CircleShape)
            .background(trackColor)
            .border(2.dp, InkDark, CircleShape)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 3.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = knobOffset)
                .size(24.dp)
                .clip(CircleShape)
                .background(if (checked) SurfaceContainerLowest else InkDark)
                .border(1.5.dp, InkDark, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (checked) "ON" else "OFF",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (checked) InkDark else SurfaceCream
            )
        }
    }
}

@Composable
fun AciertaBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = InkDark, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(SurfaceCream)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Inicio",
                selected = currentRoute == "inicio",
                onClick = { onNavigate("inicio") },
                testTag = "nav_inicio"
            )
            BottomNavItem(
                icon = Icons.Default.PlayArrow,
                label = "Simulacro",
                selected = currentRoute == "configurar",
                onClick = { onNavigate("configurar") },
                testTag = "nav_simulacro"
            )
            BottomNavItem(
                icon = Icons.Default.BarChart,
                label = "Progreso",
                selected = currentRoute == "estadisticas",
                onClick = { onNavigate("estadisticas") },
                testTag = "nav_progreso"
            )
            BottomNavItem(
                icon = Icons.Default.History,
                label = "Historial",
                selected = currentRoute == "historial",
                onClick = { onNavigate("historial") },
                testTag = "nav_historial"
            )
            BottomNavItem(
                icon = Icons.Default.Settings,
                label = "Ajustes",
                selected = currentRoute == "ajustes",
                onClick = { onNavigate("ajustes") },
                testTag = "nav_ajustes"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        modifier = Modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 28.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (selected) PrimaryContainerGold else Color.Transparent)
                .then(
                    if (selected) Modifier.border(2.dp, InkDark, RoundedCornerShape(10.dp))
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = InkDark,
                modifier = Modifier.size(19.dp)
            )
        }
        Text(
            text = label,
            fontSize = 10.5.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = InkDark
        )
    }
}
