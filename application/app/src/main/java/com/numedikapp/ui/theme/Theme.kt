package com.numedikapp.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.numedikapp.R

/**
 * Classe qui contient les couleurs du thème actuel
 */
data class NumedikappColors(
    val navy: Color,
    val pink: Color,
    val textColor: Color,
    val baseColor: Color
)

// CompositionLocal pour accéder aux couleurs partout dans l'application
val LocalNumedikappColors = compositionLocalOf<NumedikappColors> {
    error("LocalNumedikappColors n'a pas été fourni")
}

// Schémas de couleurs Material 3 pour le thème sombre
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Schémas de couleurs Material 3 pour le thème clair
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * Thème principal de l'application Numedikapp
 *
 * @param isLightMode Si true, utilise le mode clair, sinon utilise le mode sombre
 * @param useDynamicColors Si true, utilise les couleurs dynamiques sur Android 12+
 * @param content Le contenu à wrapper dans ce thème
 */
@Composable
fun NumedikappTheme(
    isLightMode: Boolean = false, // Par défaut en mode sombre
    useDynamicColors: Boolean = false, // Désactivé par défaut pour garder notre palette de couleurs
    content: @Composable () -> Unit
) {
    // 1. Obtenir les couleurs Numedikapp personnalisées
    val numedikappColors = if (isLightMode) {
        NumedikappColors(
            navy = colorResource(id = R.color.navy_background_light),
            pink = colorResource(id = R.color.pink_primary_light),
            textColor = colorResource(id = R.color.dark_text),
            baseColor = colorResource(id = R.color.black)
        )
    } else {
        NumedikappColors(
            navy = colorResource(id = R.color.navy_background),
            pink = colorResource(id = R.color.pink_primary),
            textColor = Color.White,
            baseColor = colorResource(id = R.color.white)

        )
    }

    // 2. Déterminer le schéma de couleurs Material3
    val colorScheme = when {
        useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (!isLightMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        !isLightMode -> darkColorScheme(
            primary = numedikappColors.pink,
            secondary = numedikappColors.navy,
            background = Color.Black,
            surface = Color.Black
        )
        else -> lightColorScheme(
            primary = numedikappColors.pink,
            secondary = numedikappColors.navy,
            background = Color.White,
            surface = Color.White
        )
    }

    // 3. Fournir les couleurs personnalisées via CompositionLocalProvider
    CompositionLocalProvider(LocalNumedikappColors provides numedikappColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}