package com.example.guidomia.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val lightModeColors = lightColors(
    primary = Orange,
    secondary = DarkGray,
    onSecondary = Color.White,
    surface = LightGray
)

@Composable
fun GuidomiaTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = lightModeColors,
        typography = Typography(),
        content = content
    )
}