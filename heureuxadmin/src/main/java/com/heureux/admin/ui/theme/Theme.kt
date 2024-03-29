package com.heureux.admin.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = primaryColorLight,
    onPrimary = onPrimaryColorLight,
    primaryContainer = primaryContainerColorLight,
    onPrimaryContainer = onPrimaryContainerColorLight,
    secondary = secondaryColorLight,
    onSecondary = onSecondaryColorLight,
    secondaryContainer = secondaryContainerColorLight,
    onSecondaryContainer = onSecondaryContainerColorLight,
    tertiary = tertiaryColorLight,
    onTertiary = onTertiaryColorLight,
    tertiaryContainer = tertiaryContainerColorLight,
    onTertiaryContainer = onTertiaryContainerColorLight
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryColorDark,
    onPrimary = onPrimaryColorDark,
    primaryContainer = primaryContainerColorDark,
    onPrimaryContainer = onPrimaryContainerColorDark,
    secondary = secondaryColorDark,
    onSecondary = onSecondaryColorDark,
    secondaryContainer = secondaryContainerColorDark,
    onSecondaryContainer = onSecondaryContainerColorDark,
    tertiary = tertiaryColorDark,
    onTertiary = onTertiaryColorDark,
    tertiaryContainer = tertiaryContainerColorDark,
    onTertiaryContainer = onTertiaryContainerColorDark
)

@Composable
fun HeureuxPropertiesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}