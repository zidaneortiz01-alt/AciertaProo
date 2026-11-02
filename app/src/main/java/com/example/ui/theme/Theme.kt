package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AciertaProLightColors = lightColorScheme(
    primary = PrimaryGold,
    onPrimary = Color.White,
    primaryContainer = PrimaryContainerGold,
    onPrimaryContainer = InkDark,
    secondary = SecondaryTeal,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainerCyan,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = TertiaryMagenta,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryContainerPink,
    onTertiaryContainer = OnTertiaryContainer,
    background = SurfaceCream,
    onBackground = InkDark,
    surface = SurfaceCream,
    onSurface = InkDark,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = OnSurfaceVariant,
    outline = OutlineDark,
    outlineVariant = OutlineVariant,
    error = ErrorRed,
    errorContainer = ErrorContainerRed,
    onError = OnErrorRed
)

val AciertaProDarkColors = darkColorScheme(
    primary = PrimaryContainerGold,
    onPrimary = InkDark,
    primaryContainer = PrimaryGold,
    onPrimaryContainer = Color.White,
    secondary = SecondaryContainerCyan,
    onSecondary = InkDark,
    secondaryContainer = SecondaryTeal,
    onSecondaryContainer = Color.White,
    tertiary = TertiaryContainerPink,
    onTertiary = InkDark,
    tertiaryContainer = TertiaryMagenta,
    onTertiaryContainer = Color.White,
    background = Color(0xFF191915),
    onBackground = Color(0xFFFDF9F1),
    surface = Color(0xFF22211C),
    onSurface = Color(0xFFFDF9F1),
    surfaceVariant = Color(0xFF2E2C25),
    onSurfaceVariant = Color(0xFFDDD0D2),
    outline = OutlineVariant,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

@Composable
fun AciertaProTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) AciertaProDarkColors else AciertaProLightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
