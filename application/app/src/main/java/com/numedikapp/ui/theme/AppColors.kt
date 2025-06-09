package com.numedikapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.numedikapp.R

/**
 * Objet singleton qui fournit les couleurs de l'application en fonction du mode (clair/sombre).
 * Cette version utilise les ressources de couleurs définies dans colors.xml.
 */
object AppColors {
    // Couleurs statiques qui ne proviennent pas des ressources
    val White = Color.White
    val Black = Color.Black

    /**
     * Classe de données contenant les couleurs du thème actuel
     */
    data class ThemeColors(
        val navy: Color,
        val pink: Color,
        val textColor: Color,
        val baseColor: Color
    )

    /**
     * Obtient les couleurs appropriées en fonction du mode sélectionné.
     * Cette fonction doit être utilisée dans un contexte @Composable
     * car elle accède aux ressources.
     *
     * @param isLightMode True si le mode clair est activé, false pour le mode sombre
     * @return Un objet ThemeColors contenant toutes les couleurs pour le thème actuel
     */
    @Composable
    fun getThemeColors(isLightMode: Boolean): ThemeColors {
        return if (isLightMode) {
            ThemeColors(
                navy = colorResource(id = R.color.navy_background_light),
                pink = colorResource(id = R.color.pink_primary_light),
                textColor = colorResource(id = R.color.dark_text),
                baseColor = colorResource(id = R.color.black)
            )
        } else {
            ThemeColors(
                navy = colorResource(id = R.color.navy_background),
                pink = colorResource(id = R.color.pink_primary),
                textColor = Color.White,
                baseColor = colorResource(id = R.color.white)
            )
        }
    }
}