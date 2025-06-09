package com.numedikapp

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.numedikapp.ui.theme.LocalNumedikappColors

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NumedikappTopAppBar_settings(title: String? = null, navigateBack: () -> Unit = {}, openSettings: () -> Unit, couleur : Color) {
    val colors = LocalNumedikappColors.current

    val topBarcolors = TopAppBarDefaults.topAppBarColors(
        containerColor = couleur, // Fond rose
        titleContentColor = colors.textColor,    // Texte blanc
        navigationIconContentColor = colors.textColor, // Icône retour blanche
        actionIconContentColor = colors.textColor       // Icônes à droite blanches aussi
    )

    // Define the actions displayed on the right side of the app bar
    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = openSettings) {
            Icon(
                painter = painterResource(R.drawable.settings),
                contentDescription = stringResource(R.string.app_go_Settings_description)
            )
        }
    }
    // Display the app bar with the title if present and actions
    if(title == null) {
        TopAppBar(
            title = { Text("") },
            colors = topBarcolors,
            actions = actions
        )
    } else {
        MediumTopAppBar(
            title = { Text(title) },
            colors = topBarcolors,
            // The title will be displayed in other screen than the main screen.
            // In this case we need to add a return action
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.app_go_back_description)
                    )
                }
            },
            actions = actions
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NumedikappTopAppBar(title: String? = null, navigateBack: () -> Unit = {}, couleur: Color ) {
    val colors = LocalNumedikappColors.current

    val topBarcolors = TopAppBarDefaults.topAppBarColors(
        containerColor = couleur, // Fond rose
        titleContentColor = colors.textColor,    // Texte blanc
        navigationIconContentColor = colors.textColor, // Icône retour blanche
        actionIconContentColor = colors.textColor       // Icônes à droite blanches aussi
    )

    // Define the actions displayed on the right side of the app bar
    val actions: @Composable RowScope.() -> Unit = {}
    // Display the app bar with the title if present and actions
    if(title == null) {
        TopAppBar(
            title = { Text("") },
            colors = topBarcolors,
            actions = actions
        )
    } else {
        MediumTopAppBar(
            title = { Text(title) },
            colors = topBarcolors,
            // The title will be displayed in other screen than the main screen.
            // In this case we need to add a return action
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.app_go_back_description)
                    )
                }
            },
            actions = actions
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NumedikappTopAppBar_settings_infos(title: String? = null, navigateBack: () -> Unit = {}, openSettings: () -> Unit, openInfos: () -> Unit, couleur : Color) {
    val colors = LocalNumedikappColors.current

    val topBarcolors = TopAppBarDefaults.topAppBarColors(
        containerColor = couleur, // Fond rose
        titleContentColor = colors.textColor,    // Texte blanc
        navigationIconContentColor = colors.textColor, // Icône retour blanche
        actionIconContentColor = colors.textColor       // Icônes à droite blanches aussi
    )

    // Define the actions displayed on the right side of the app bar
    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = openInfos) {
            Icon(
                painter = painterResource(R.drawable.infos_perso),
                contentDescription = stringResource(R.string.app_go_infos_perso_description),
                modifier = Modifier.size(48.dp) // taille standard Material
            )
        }

        IconButton(onClick = openSettings) {
            Icon(
                painter = painterResource(R.drawable.settings),
                contentDescription = stringResource(R.string.app_go_Settings_description),
                modifier = Modifier.size(24.dp) // taille standard Material
            )
        }
    }
    // Display the app bar with the title if present and actions
    if(title == null) {
        TopAppBar(
            title = { Text("") },
            colors = topBarcolors,
            actions = actions
        )
    } else {
        MediumTopAppBar(
            title = { Text(title) },
            colors = topBarcolors,
            // The title will be displayed in other screen than the main screen.
            // In this case we need to add a return action
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.app_go_back_description)
                    )
                }
            },
            actions = actions
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NumedikappTopAppBar_infos(title: String? = null, navigateBack: () -> Unit = {}, openInfos: () -> Unit, couleur : Color) {
    val colors = LocalNumedikappColors.current

    val topBarcolors = TopAppBarDefaults.topAppBarColors(
        containerColor = couleur, // Fond rose
        titleContentColor = colors.textColor,    // Texte blanc
        navigationIconContentColor = colors.textColor, // Icône retour blanche
        actionIconContentColor = colors.textColor       // Icônes à droite blanches aussi
    )

    // Define the actions displayed on the right side of the app bar
    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = openInfos) {
            Icon(
                painter = painterResource(R.drawable.infos_perso),
                contentDescription = stringResource(R.string.app_go_infos_perso_description),
                modifier = Modifier.size(48.dp) // taille standard Material
            )
        }
    }
    // Display the app bar with the title if present and actions
    if(title == null) {
        TopAppBar(
            title = { Text("") },
            colors = topBarcolors,
            actions = actions
        )
    } else {
        MediumTopAppBar(
            title = { Text(title) },
            colors = topBarcolors,
            // The title will be displayed in other screen than the main screen.
            // In this case we need to add a return action
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.app_go_back_description)
                    )
                }
            },
            actions = actions
        )
    }
}
