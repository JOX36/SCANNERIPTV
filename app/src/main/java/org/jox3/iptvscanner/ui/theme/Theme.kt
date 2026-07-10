package org.jox3.iptvscanner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colores estilo neón / cyber (igual al diseño anterior)
private val NeonCyan = Color(0xFF00FFCC)
private val NeonMagenta = Color(0xFFFF00FF)
private val DarkBackground = Color(0xFF0A001F)
private val CardBackground = Color(0xFF1A0033)
private val TextWhite = Color(0xFFFFFFFF)
private val SuccessGreen = Color(0xFF00FF88)

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonMagenta,
    tertiary = SuccessGreen,
    background = DarkBackground,
    surface = CardBackground,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = TextWhite,
    onSurface = TextWhite,
)

@Composable
fun IPTVScannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
