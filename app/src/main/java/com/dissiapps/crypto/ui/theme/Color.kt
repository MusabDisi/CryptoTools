package com.dissiapps.crypto.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Orange = Color(0xFFED7E00)
val Yellow = Color(0xFFEDB200)
val OffWhite = Color(0xFFFAF9F6)
val VeryLightGray = Color(0xFFE9E9E9)

val ShimmerColorShades = listOf(
    Color.LightGray.copy(0.9f),
    Color.LightGray.copy(0.2f),
    Color.LightGray.copy(0.9f)
)

val Colors.newsSearchBarBackground: Color
    get() = if (isLight) VeryLightGray else Color.Gray

val Colors.newsSearchBarContent: Color
    get() = if (isLight) Color.Gray else Color.White

val Colors.iconTint: Color
    get() = if (isLight) Color.Black else Color.White

val Colors.text: Color
    get() = if (isLight) Color.Black else Color.White

val Colors.textInverted: Color
    get() = if (isLight) Color.White else Color.Black
