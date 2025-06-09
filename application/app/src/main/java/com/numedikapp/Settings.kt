package com.numedikapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.numedikapp.data.ThemePreference
import com.numedikapp.ui.theme.LocalNumedikappColors
import com.numedikapp.ui.theme.NumedikappTheme

class Settings : ComponentActivity() {
    private lateinit var themePreference: ThemePreference

    private val navigateBack: () -> Unit = {
        val intent = Intent(this, Dashboard2::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    private val openInfos: () -> Unit = {
        finish()
        startActivity(Intent(baseContext, InfosPerso::class.java))
    }

    // Action à faire quand le bouton déconnexion est cliqué
    val onPowerOffClick: () -> Unit = {
        val intent = Intent(this, MainActivity::class.java).apply {}
        finish()
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialiser les préférences de thème
        themePreference = ThemePreference(this)
        val isLightMode = themePreference.isLightMode()

        enableEdgeToEdge()
        setContent {
            NumedikappTheme(isLightMode = isLightMode) {
                val colors = LocalNumedikappColors.current

                Scaffold(
                    topBar = { NumedikappTopAppBar_infos("Paramètres", navigateBack, openInfos, colors.navy) },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = colors.pink
                ) { innerPadding ->
                    SettingsScreen(
                        modifier = Modifier.padding(innerPadding),
                        onPowerOffClick = onPowerOffClick,
                        isLightMode = isLightMode,
                        onThemeChange = { newMode ->
                            themePreference.setLightMode(newMode)
                            // Redémarrer l'activité pour appliquer le nouveau thème
                            recreate()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onPowerOffClick: () -> Unit,
    isLightMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    // Récupération des couleurs du thème actuel
    val colors = LocalNumedikappColors.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // Bouton de déconnexion
        Button(
            onClick = { onPowerOffClick() },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.navy
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.act_power_off),
                    color = colors.textColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.power_off),
                    contentDescription = stringResource(R.string.act_power_off),
                    tint = colors.textColor
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Section thème
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Thème",
                color = colors.textColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Switch(
                    checked = isLightMode,
                    onCheckedChange = { onThemeChange(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.navy,
                        checkedTrackColor = colors.navy.copy(alpha = 0.5f),
                        uncheckedThumbColor = colors.navy
                    )
                )
                Text(
                    text = if (isLightMode) "Mode clair" else "Mode sombre",
                    modifier = Modifier.padding(start = 8.dp),
                    color = colors.textColor
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Section mode d'utilisation
        var selectedMode by remember { mutableStateOf("découverte") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Mode d'utilisation :",
                modifier = Modifier.padding(bottom = 8.dp),
                color = colors.textColor
            )

            listOf("free", "premium").forEach { mode ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = (selectedMode == mode),
                        onCheckedChange = { selectedMode = mode },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colors.navy,
                            uncheckedColor = colors.navy,
                            checkmarkColor = colors.textColor
                        )
                    )
                    Text(
                        text = mode,
                        modifier = Modifier.padding(start = 8.dp),
                        color = colors.textColor
                    )
                }
            }
        }
    }
}