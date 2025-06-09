package com.numedikapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.numedikapp.data.ThemePreference
import com.numedikapp.model.UserViewModel
import com.numedikapp.ui.theme.LocalNumedikappColors
import com.numedikapp.ui.theme.NumedikappTheme
class InfosPerso : ComponentActivity() {
    private lateinit var themePreference: ThemePreference
    private lateinit var userViewModel: UserViewModel


    private val navigateBack: () -> Unit = {
        val intent = Intent(this, Dashboard::class.java).apply {
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
        userViewModel = (application as MyApplication).userViewModel


        // Initialiser les préférences de thème
        themePreference = ThemePreference(this)
        val isLightMode = themePreference.isLightMode()

        enableEdgeToEdge()
        setContent {
            NumedikappTheme(isLightMode = isLightMode) {
                val colors = LocalNumedikappColors.current

                Scaffold(
                    topBar = { NumedikappTopAppBar_settings(stringResource(R.string.title_activity_infos_perso), navigateBack, openSettings, colors.navy) },
                    containerColor = colors.pink,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    InfosPersoScreen(
                        modifier = Modifier.padding(innerPadding),
                        userViewModel = userViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun InfosPersoScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel
) {
    val user by userViewModel.userLiveData.observeAsState()
    val scrollState = rememberScrollState()
    val colors = LocalNumedikappColors.current


    // État local pour savoir si on est en mode édition
    var editMode by remember { mutableStateOf(false) }

    // États locaux pour les champs modifiables
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var taille by remember { mutableStateOf("") }
    var poids by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }

    // Mise à jour des états locaux quand l'utilisateur change
    LaunchedEffect(user) {
        user?.let {
            username = it.username
            email = it.email
            age = it.age.toString()
            taille = it.taille?.toString() ?: ""
            poids = it.poids?.toString() ?: ""
            gender = it.gender
            goal = it.goal
            profession = it.profession ?: ""
        }
    }

    // Options pour les menus déroulants
    val genderOptions = listOf("Homme", "Femme", "Autre")
    val goalOptions = listOf(
        "Cognitif", "Bien-être",
        "Performance", "Energie", "Concentration", "Mémorisation", "Humeur"
    )
    val professionOptions = listOf(
        "Étudiant", "Technicien", "Cadre d'entreprise", "Professeur",
        "Profession médicale", "Artisan", "Sportif", "Autre"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_activity_infos_perso),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (user == null) {
            Text(text = "Aucun utilisateur connecté")
        } else {
            // Formulaire d'informations personnelles
            InfosPersoFormFields(
                username = username,
                onUsernameChange = { username = it },
                email = email,
                onEmailChange = { email = it },
                age = age,
                onAgeChange = { age = it },
                taille = taille,
                onTailleChange = { taille = it },
                poids = poids,
                onPoidsChange = { poids = it },
                gender = gender,
                onGenderChange = { gender = it },
                goal = goal,
                onGoalChange = { goal = it },
                profession = profession,
                onProfessionChange = { profession = it },
                isEditable = editMode,
                genderOptions = genderOptions,
                goalOptions = goalOptions,
                professionOptions = professionOptions
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Boutons pour gérer l'édition
            if (editMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            // Annuler les modifications et revenir aux données initiales
                            user?.let {
                                username = it.username
                                email = it.email
                                age = it.age.toString()
                                taille = it.taille?.toString() ?: ""
                                poids = it.poids?.toString() ?: ""
                                gender = it.gender
                                goal = it.goal
                                profession = it.profession ?: ""
                            }
                            editMode = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.pink
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 2.dp,
                                color = colors.navy,
                                shape = RoundedCornerShape(25.dp)
                            )
                    ) {
                        Text("Annuler", color = colors.textColor)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            // Sauvegarder les modifications
                            user?.let { currentUser ->
                                val updatedUser = currentUser.copy(
                                    username = username,
                                    email = email,
                                    age = age.toIntOrNull() ?: currentUser.age,
                                    taille = taille.toDoubleOrNull(),
                                    poids = poids.toDoubleOrNull(),
                                    gender = gender,
                                    goal = goal,
                                    profession = profession
                                )

                                // Ici vous devriez appeler une méthode du ViewModel pour mettre à jour l'utilisateur
                                // userViewModel.updateUser(updatedUser)
                            }
                            editMode = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LocalNumedikappColors.current.navy
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Sauvegarder", color = colors.textColor)
                    }
                }
            } else {
                Button(
                    onClick = { editMode = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalNumedikappColors.current.navy
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier", color = colors.textColor)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfosPersoFormFields(
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    taille: String,
    onTailleChange: (String) -> Unit,
    poids: String,
    onPoidsChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    goal: String,
    onGoalChange: (String) -> Unit,
    profession: String,
    onProfessionChange: (String) -> Unit,
    isEditable: Boolean,
    genderOptions: List<String>,
    goalOptions: List<String>,
    professionOptions: List<String>
) {
    val colors = LocalNumedikappColors.current

    // Style commun pour les champs
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colors.textColor,
        unfocusedTextColor = colors.navy,
        focusedContainerColor = colors.pink,
        unfocusedContainerColor = colors.pink,
        focusedBorderColor = colors.textColor,
        unfocusedBorderColor = colors.navy,
        disabledTextColor = colors.textColor,
        disabledContainerColor = colors.pink.copy(alpha = 0.7f),
        disabledBorderColor = colors.navy.copy(alpha = 0.5f)
    )

    val textStyle = TextStyle(color = colors.textColor)

    // Nom d'utilisateur
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Nom d'utilisateur") },
        readOnly = !isEditable,
        modifier = Modifier.fillMaxWidth(),
        textStyle = textStyle,
        colors = textFieldColors
    )

    // Email
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        readOnly = !isEditable,
        modifier = Modifier.fillMaxWidth(),
        textStyle = textStyle,
        colors = textFieldColors
    )

    // Âge
    OutlinedTextField(
        value = age,
        onValueChange = onAgeChange,
        label = { Text("Âge") },
        readOnly = !isEditable,
        modifier = Modifier.fillMaxWidth(),
        textStyle = textStyle,
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    // Taille
    OutlinedTextField(
        value = taille,
        onValueChange = onTailleChange,
        label = { Text("Taille (m)") },
        readOnly = !isEditable,
        modifier = Modifier.fillMaxWidth(),
        textStyle = textStyle,
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

    // Poids
    OutlinedTextField(
        value = poids,
        onValueChange = onPoidsChange,
        label = { Text("Poids (kg)") },
        readOnly = !isEditable,
        modifier = Modifier.fillMaxWidth(),
        textStyle = textStyle,
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

    // Genre - Si éditable, affiche un menu déroulant, sinon un champ texte simple
    if (isEditable) {
        DropdownFieldWithOptions(
            label = "Genre",
            selectedOption = gender,
            options = genderOptions,
            onOptionSelected = onGenderChange,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        OutlinedTextField(
            value = gender,
            onValueChange = {},
            label = { Text("Genre") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle,
            colors = textFieldColors
        )
    }

    // Objectif - Si éditable, affiche un menu déroulant, sinon un champ texte simple
    if (isEditable) {
        DropdownFieldWithOptions(
            label = "Objectif",
            selectedOption = goal,
            options = goalOptions,
            onOptionSelected = onGoalChange,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        OutlinedTextField(
            value = goal,
            onValueChange = {},
            label = { Text("Objectif") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle,
            colors = textFieldColors
        )
    }

    // Profession - Si éditable, affiche un menu déroulant, sinon un champ texte simple
    if (isEditable) {
        DropdownFieldWithOptions(
            label = "Profession",
            selectedOption = profession,
            options = professionOptions,
            onOptionSelected = onProfessionChange,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        OutlinedTextField(
            value = profession,
            onValueChange = {},
            label = { Text("Profession") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle,
            colors = textFieldColors
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFieldWithOptions(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalNumedikappColors.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.textColor,
                focusedContainerColor = colors.pink,
                unfocusedContainerColor = colors.pink,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.navy,
                focusedLabelColor = colors.textColor,
                unfocusedLabelColor = colors.navy
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}