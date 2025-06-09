package com.numedikapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.numedikapp.data.ThemePreference
import com.numedikapp.model.HealthStateDto
import com.numedikapp.model.UserDto
import com.numedikapp.model.UserViewModel
import com.numedikapp.ui.theme.LocalNumedikappColors
import com.numedikapp.ui.theme.NumedikappTheme


class Dashboard2 : ComponentActivity() {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupération du ViewModel partagé
        userViewModel = (application as MyApplication).userViewModel

        // Initialiser les préférences de thème
        themePreference = ThemePreference(this)
        val isLightMode = themePreference.isLightMode()

        enableEdgeToEdge()
        setContent {
            // Accès à l'utilisateur actuel
            val currentUser by userViewModel.userLiveData.observeAsState()

            userViewModel.updateHealthStateAttribute("aliment",1)


            NumedikappTheme (isLightMode = isLightMode){
                val colors = LocalNumedikappColors.current
                val dashboardTitle = stringResource(R.string.act_dashboard2_name, currentUser?.username
                    ?: "")

                Scaffold(
                    topBar = { NumedikappTopAppBar_settings_infos(dashboardTitle, navigateBack, openSettings, openInfos, colors.pink) },
                    containerColor = colors.navy,
                    modifier = Modifier.fillMaxSize()
                )
                { innerPadding ->
                    Dashboard2Detail(
                        userViewModel,
                        currentUser,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Dashboard2Detail(viewModel: UserViewModel, user: UserDto?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val colors = LocalNumedikappColors.current

    // Observer les aliments consommés
    val alimentsConsommes by viewModel.alimentsConsommes.observeAsState(initial = emptySet())
    val nombreAlimentsConsommes = alimentsConsommes.size

    // Observer le plan alimentaire pour déterminer le nombre total d'aliments
    val planAlimentaire by viewModel.planAlimentaire.observeAsState(initial = emptyMap())
    val nombreTotalAliments = planAlimentaire.values.sumOf { it.size }

    // Vérifier que l'utilisateur n'est pas null
    user?.let { currentUser ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 50) {
                            val intent = Intent(context, Dashboard::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            }
                            context.startActivity(intent)
                            if (context is Activity) {
                                context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                                context.finish()
                            }
                        } else if (dragAmount < -50) {
                            val intent = Intent(context, Dashboard::class.java).apply {
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
            SemiCircularProgressBar(
                percentage = user.healthstate.brain_perf,
                color = colors.textColor,
                backgroundColor = colors.pink,
                modifier = Modifier
                    //.fillMaxWidth(),
                    .weight(1f), // Distribuer l'espace de manière égale
                taille = 75
            )

            // Affichage du nombre d'aliments consommés
            Text(
                text = "Aliments consommés: $nombreAlimentsConsommes/$nombreTotalAliments",
                color = colors.pink,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .offset(y = (-85).dp)
            )

            HorizontalDivider(
                modifier = Modifier
                    .width(400.dp)
                    .offset(y = (-80).dp), // déplace vers le haut de 80.dp au lieu de 90.dp pour laisser de la place au texte
                thickness = 1.dp,
                color = colors.textColor
            )

            // Utiliser directement les curseurs avec le ViewModel
            HealthStateSection(
                healthState = currentUser.healthstate,
                viewModel = viewModel,
                modifier = Modifier.offset(y = (-70).dp) // déplace vers le haut de 70.dp au lieu de 75.dp
            )

        }
    } ?: run {
        // Afficher un message si l'utilisateur est null
        Text("Aucun utilisateur connecté")
    }
}

// affiche un curseur sur le label demandé et allant de 0 à max.
@Composable
fun Curseur(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    max: Int
) {
    val colors = LocalNumedikappColors.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
    ) {
        // Affiche le label avec la valeur associée
        Text(
            text = "$label :",
            color = colors.textColor,
            modifier = Modifier
                .width(150.dp) // Fixer une largeur spécifique
                .padding(end = 8.dp),
        )

        // signe « – »
        Text(
            text = "–",
            color = colors.textColor,
            modifier = Modifier.padding(end = 4.dp)
        )

        // Curseur (Slider) qui permet de modifier la valeur
        Slider(
            value = value.toFloat(),
            onValueChange = { newValue ->
                // Appel de la fonction onValueChange pour mettre à jour la valeur
                onValueChange(newValue.toInt())
            },
            valueRange = 0f..((max).toFloat()),
            steps = max-1,
            modifier = Modifier.weight(1f),   // occupe l'espace restant
            colors = SliderDefaults.colors(
                activeTrackColor = colors.pink,
                inactiveTrackColor = colors.textColor,
                thumbColor = colors.pink
            ),
        )

        // signe « + »
        Text(
            text = "+",
            color = colors.textColor,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

// affiche un curseur sur le label demandé et allant de 0 à max.
@Composable
fun CurseurStress(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    max: Int
) {
    val colors = LocalNumedikappColors.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
    ) {
        // Affiche le label avec la valeur associée
        Text(
            text = "$label :",
            color = colors.textColor,
            modifier = Modifier
                .width(150.dp) // Fixer une largeur spécifique
                .padding(end = 8.dp),
        )

        // signe « – »
        Text(
            text = "Calme",
            color = colors.textColor,
            modifier = Modifier.padding(end = 4.dp) ,
            fontSize = 12.sp,

            )

        // Curseur (Slider) qui permet de modifier la valeur
        Slider(
            value = value.toFloat(),
            onValueChange = { newValue ->
                // Appel de la fonction onValueChange pour mettre à jour la valeur
                onValueChange(newValue.toInt())
            },
            valueRange = 0f..((max).toFloat()),
            steps = max-1,
            modifier = Modifier.weight(1f),   // occupe l'espace restant
            colors = SliderDefaults.colors(
                activeTrackColor = colors.pink,
                inactiveTrackColor = colors.textColor,
                thumbColor = colors.pink
            ),
        )

        // signe « + »
        Text(
            text = "Nerveux",
            color = colors.textColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}


// affiche le pourcentage de performance du cerveau dans un demi cercle.
// le prolème de cette fonction c'est qu'elle crée un vide d'environ 100.dp en dessous du texte.
// il faut donc remonter artificiellement tous les éléments en dessous pour cacher ça.
@Composable
fun SemiCircularProgressBar(
    percentage: Int,
    modifier: Modifier = Modifier,
    color: Color,
    backgroundColor: Color,
    taille: Int //épaisseur du trait
) {
    val animatedProgress = remember { Animatable(0f) }
    val colors = LocalNumedikappColors.current


    LaunchedEffect(percentage) {
        animatedProgress.animateTo(
            targetValue = percentage / 100f,
            animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
        )
    }

    Box(
        modifier = modifier
            .aspectRatio(1f) // Demi-cercle (largeur une fois la hauteur)
            .padding((taille/4).dp), // à quel point le demi-cercle est petit dans l'espace occupé.
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val arcRect = Rect(
                offset = Offset(0f, 0f), // pas d'offset
                size = Size(size.width, size.height)
            )

            // Background arc
            drawArc(
                color = backgroundColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = arcRect.topLeft,
                size = arcRect.size,
                style = Stroke(width = ((taille/4).dp).toPx(), cap = StrokeCap.Round)
            )

            // Foreground arc
            drawArc(
                color = color,
                startAngle = 180f,
                sweepAngle = 180f * animatedProgress.value,
                useCenter = false,
                topLeft = arcRect.topLeft,
                size = arcRect.size,
                style = Stroke(width = ((taille/4).dp).toPx(), cap = StrokeCap.Round)
            )
        }

        // Percentage text
        Text(
            text = "${percentage}%",
            modifier = Modifier.offset(y = (taille*3/4).dp),
            color = colors.textColor,
            style = MaterialTheme.typography.headlineMedium,
            fontSize = (taille/2).sp
        )

        Text(
            text = "Perf générale du cerveau",
            modifier = Modifier.offset(y = (taille*3/2).dp),
            color = colors.textColor,
            style = MaterialTheme.typography.headlineMedium,
            fontSize = (taille/4).sp
        )
    }

}



@Composable
fun HealthStateSection(healthState: HealthStateDto, viewModel: UserViewModel, modifier: Modifier){
    Column (modifier = modifier) {
        Curseur(
            label = "Sommeil",
            value = healthState.sommeil,
            onValueChange = { newValue ->
                viewModel.updateHealthStateAttribute("sommeil", newValue);
            },
            max = 2,
        )

        Curseur(
            label = "Digestion",
            value = healthState.digestion,
            onValueChange = { newValue ->
                viewModel.updateHealthStateAttribute("digestion", newValue);
            },
            max = 2,
        )

        Curseur(
            label = "Energie",
            value = healthState.energie,
            onValueChange = { newValue ->
                viewModel.updateHealthStateAttribute("energie", newValue);
            },
            max = 2,
        )

        Curseur(
            label = "Humeur",
            value = healthState.humeur,
            onValueChange = { newValue ->
                viewModel.updateHealthStateAttribute("humeur", newValue);
            },
            max = 2,
        )

        Curseur(
            label = "Concentration",
            value = healthState.concentration,
            onValueChange = { newValue ->
                viewModel.updateHealthStateAttribute("concentration", newValue);
            },
            max = 2,
        )

        Curseur(
            label = "Mémorisation",
            value = healthState.memorisation,
            onValueChange = { newValue ->
                viewModel.updateHealthStateAttribute("memorisation", newValue);
            },
            max = 2,
        )

        CurseurStress(
            label = "Stress",
            value = healthState.stress,
            onValueChange = { newValue ->
                viewModel.updateHealthStateAttribute("stress", newValue);
            },
            max = 2,
        )
    }
}