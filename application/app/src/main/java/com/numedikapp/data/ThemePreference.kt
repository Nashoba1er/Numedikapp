package com.numedikapp.data

import android.content.Context
import android.content.SharedPreferences

class ThemePreference(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "theme_preferences", Context.MODE_PRIVATE
    )

    // Retourne true si le mode clair est activé
    fun isLightMode(): Boolean {
        return preferences.getBoolean(KEY_LIGHT_MODE, false) // Par défaut, mode sombre
    }

    // Sauvegarde le mode sélectionné
    fun setLightMode(isLightMode: Boolean) {
        preferences.edit().putBoolean(KEY_LIGHT_MODE, isLightMode).apply()
    }

    companion object {
        private const val KEY_LIGHT_MODE = "is_light_mode"
    }
}