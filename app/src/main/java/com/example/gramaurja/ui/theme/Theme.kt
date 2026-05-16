package com.example.gramaurja.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = DarkGreen,
    secondary = LightGreen,
    background = Background
)

@Composable
fun GramaUrjaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        content = content
    )
}