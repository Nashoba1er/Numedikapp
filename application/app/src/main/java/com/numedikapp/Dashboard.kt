package com.numedikapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.numedikapp.data.ThemePreference
import com.numedikapp.model.HealthStateDto
import com.numedikapp.model.UserViewModel
import com.numedikapp.ui.theme.LocalNumedikappColors
import com.numedikapp.ui.theme.NumedikappTheme

class Dashboard : ComponentActivity() {
    private lateinit var themePreference: ThemePreference
    private lateinit var userViewModel: UserViewModel

    private val navigateBack: () -> Unit = {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    private val openSettings: () -> Unit = {
        finish()
        startActivity(Intent(baseContext, Settings::class.java))
    }

    private val openInfos: () -> Unit = {
        finish()
        startActivity(Intent(baseContext, InfosPerso::class.java))
    }

    val onChangeButtonClick: () -> Unit = {
        // Appeler la fonction du ViewModel pour recalculer le plan
        userViewModel.calculatePlanAlimentaire()

        // Vous pouvez également ajouter un feedback à l'utilisateur si nécessaire
        Toast.makeText(baseContext, "Nouveau plan généré", Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = (application as MyApplication).userViewModel

        // Initialiser les préférences de thème
        themePreference = ThemePreference(this)
        val isLightMode = themePreference.isLightMode()

        enableEdgeToEdge()
        setContent {

            val currentUser by userViewModel.userLiveData.observeAsState()

            NumedikappTheme (isLightMode = isLightMode){
                val colors = LocalNumedikappColors.current
                val dashboardTitle = stringResource(R.string.act_dashboard_name)
                Scaffold(
                    topBar = { NumedikappTopAppBar_settings_infos(dashboardTitle, navigateBack, openSettings, openInfos, colors.pink) },
                    containerColor = colors.navy,
                    modifier = Modifier.fillMaxSize()
                )
                { innerPadding ->
                    currentUser?.let {
                        DashboardDetail(
                            modifier = Modifier.padding(innerPadding),
                            onChangeButtonClick = onChangeButtonClick,
                            userViewModel= userViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardDetail(userViewModel: UserViewModel, modifier: Modifier = Modifier, onChangeButtonClick: () -> Unit) {
    val context = LocalContext.current
    val colors = LocalNumedikappColors.current
    val currentUser by userViewModel.userLiveData.observeAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 50) {
                        val intent = Intent(context, Dashboard2::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        }
                        context.startActivity(intent)
                        if (context is Activity) {
                            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                            context.finish()
                        }
                    } else if (dragAmount < -50) {
                        val intent = Intent(context, Dashboard2::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        }
                        context.startActivity(intent)
                        if (context is Activity) {
                            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            context.finish()
                        }
                    }
                }
            } ,
    ) {
        val planAlimentaire by userViewModel.planAlimentaire.observeAsState(initial = emptyMap())

        Text(
            text = "Objectif : " + (currentUser?.goal ?: "Non Défini"),
            color = colors.textColor,
            fontSize = 24.sp
        )

        HorizontalDivider(
            modifier = Modifier
                .width(200.dp),
            thickness = 1.dp,
            color = colors.pink
        )

        Spacer(modifier = Modifier.height(20.dp))

        PlanAliment(
            modifier = Modifier,
            taille = 90,
            moment = "Matin",
            planAlimentaire = planAlimentaire,
            userViewModel = userViewModel
        )

        Spacer(modifier = Modifier.height(10.dp))

        PlanAliment(
            modifier = Modifier,
            taille = 90,
            moment = "Midi",
            planAlimentaire = planAlimentaire,
            userViewModel = userViewModel
        )

        Spacer(modifier = Modifier.height(10.dp))

        PlanAliment(
            modifier = Modifier,
            taille = 90,
            moment = "Collation",
            planAlimentaire = planAlimentaire,
            userViewModel = userViewModel
        )
        Spacer(modifier = Modifier.height(10.dp))

        PlanAliment(
            modifier = Modifier,
            taille = 90,
            moment = "Soir",
            planAlimentaire = planAlimentaire,
            userViewModel = userViewModel
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                onChangeButtonClick() // Utilise directement la fonction de connexion
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.pink // Change la couleur du bouton
            )
        ){
            Text("Changer de plan", color = colors.textColor)
        }
    }
}

// affiche un tableau de 1x3 d'aliments (pour le moment de la journée choisi)
@Composable
fun PlanAliment(
    modifier: Modifier = Modifier,
    taille: Int,
    moment: String,
    planAlimentaire: Map<String, List<String>>,
    userViewModel: UserViewModel
) {
    val colors = LocalNumedikappColors.current
    val alimentsPourMoment = planAlimentaire[moment]
    val context = LocalContext.current
    val currentUser by userViewModel.userLiveData.observeAsState()


    // Observer les aliments consommés depuis le ViewModel
    val alimentsConsommes by userViewModel.alimentsConsommes.observeAsState(initial = emptySet())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = (taille / 20).dp)
    ) {
        Text(
            text = moment,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(bottom = (taille / 8).dp)
                .offset(y = (taille/20).dp),
            color = colors.textColor,
            fontSize = (taille / 6).sp
        )

        Column {
            Row(
                modifier = Modifier.height((taille * 0.9).dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (alimentsPourMoment != null) {
                    for (i in 0 until 3) {
                        if (i < alimentsPourMoment.size) {
                            val nomAliment = alimentsPourMoment[i]
                            val resId = context.resources.getIdentifier(
                                nomAliment.lowercase(),
                                "drawable",
                                context.packageName
                            )
                            val painter =
                                if (resId != 0) painterResource(id = resId) else painterResource(id = R.drawable.poulet)

                            // Créer un ID unique pour cet aliment
                            val alimentId = userViewModel.createAlimentId(moment, nomAliment)

                            // Vérifier si cet aliment est consommé
                            val isConsomme = alimentsConsommes.contains(alimentId)

                            // Définir la taille de l'image
                            val horizontalPadding = (taille / 40).dp

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = horizontalPadding)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                // Colonne pour contenir l'image et le texte du nom
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    // Conteneur pour l'image (prend 80% de la hauteur)
                                    Box(
                                        modifier = Modifier
                                            .weight(0.8f)
                                            .aspectRatio(1f, matchHeightConstraintsFirst = true)
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                // Basculer l'état de consommation de cet aliment dans le ViewModel
                                                userViewModel.toggleAlimentConsomme(alimentId)
                                                userViewModel.updateHealthStateAttribute("aliment",1)
                                            }
                                            .border(
                                                width = if (isConsomme) 2.dp else 0.dp,
                                                color = if (isConsomme) colors.pink else Color.Transparent,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        // Image à l'intérieur du conteneur
                                        Image(
                                            painter = painter,
                                            contentDescription = nomAliment,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                    }

                                    // Texte pour le nom de l'aliment (prend 20% de la hauteur)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = formatNomAliment(nomAliment),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = colors.textColor,
                                        fontSize = (taille / 8).sp,
                                        maxLines = 1,
                                        modifier = Modifier.weight(0.2f)
                                    )
                                }
                            }
                        } else {
                            Box(Modifier.weight(1f)) {}
                        }

                        if (i < 2 && alimentsPourMoment != null && i < alimentsPourMoment.size - 1) {
                            VerticalDivider(
                                modifier = Modifier.height((taille * 0.9).dp),
                                thickness = 1.dp,
                                color = colors.textColor
                            )
                        }
                    }
                } else {
                    for (i in 0 until 3) {
                        Box(Modifier.weight(1f)) {}
                        if (i < 2) {
                            VerticalDivider(
                                modifier = Modifier.height((taille * 0.9).dp),
                                thickness = 1.dp,
                                color = colors.textColor
                            )
                        }
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colors.textColor
            )
        }
    }
}

// Fonction pour formater le nom de l'aliment (remplace les underscores par des espaces et capitalise les mots)
fun formatNomAliment(nom: String): String {
    return nom.split("_")
        .joinToString(" ") { it.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase() else char.toString()
        }}
}

fun healthStateDtoToEtatUtilisateur(healthState: HealthStateDto): Map<String, Int> {
    return mapOf(
        "sommeil" to healthState.sommeil,
        "digestion" to when (healthState.digestion) {
            // Map digestion states to our 0-2 scale
            // You might need to adjust these mappings based on your exact definitions
            0 -> 0 // Assuming 0 represents "compliquée"
            1 -> 1 // Assuming 1 represents "correct"
            2 -> 2  // Assuming 2 represents "bonne"
            else -> 1 // Default to "correct" if value is unexpected
        },
        "energie" to when (healthState.energie) {
            0 -> 0 // Assuming 0 represents "faible"
            1 -> 1 // Assuming 1 represents "correct"
            2 -> 2  // Assuming 2 represents "bon"
            else -> 1 // Default to "correct" if value is unexpected
        },
        "stress" to when (healthState.stress) {
            0 -> 2 // Assuming 0 represents "stressé" (we want 2 for high stress)
            1 -> 1 // Assuming 1 represents "correct"
            2 -> 0  // Assuming 2 represents "calme" (we want 0 for low stress)
            else -> 1 // Default to "correct" if value is unexpected
        },
        "humeur" to when (healthState.humeur) {
            0 -> 0 // Assuming 0 represents "mauvais"
            1 -> 2  // Assuming 1 represents "bon"
            else -> 1 // Default to 1 (you might need more states for your app)
        },
        "concentration" to when (healthState.concentration) {
            0 -> 0 // Assuming 0 represents "mauvaise"
            1 -> 1 // Assuming 1 represents "en progrès"
            2 -> 2  // Assuming 2 represents "bonne"
            else -> 1 // Default to "en progrès" if value is unexpected
        },
        "memorisation" to when (healthState.memorisation) {
            0 -> 0 // Assuming 0 represents "mauvaise"
            1 -> 1 // Assuming 1 represents "en progrès"
            2 -> 2  // Assuming 2 represents "bonne"
            else -> 1 // Default to "en progrès" if value is unexpected
        }
    )
}