package com.numedikapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.numedikapp.data.ThemePreference
import com.numedikapp.model.HealthStateDto
import com.numedikapp.model.UserDto
import com.numedikapp.model.UserService
import com.numedikapp.model.UserViewModel
import com.numedikapp.ui.theme.LocalNumedikappColors
import com.numedikapp.ui.theme.NumedikappTheme
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscriptionForm(
    onSave: (UserDto) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var taille by remember { mutableStateOf("") }
    var poids by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Homme") }
    var goal by remember { mutableStateOf("Cognitif") }
    var profession by remember { mutableStateOf("Étudiant") }

    val scrollState = rememberScrollState()
    val colors = LocalNumedikappColors.current

    val genderOptions = listOf("Homme", "Femme", "Autre")
    val goalOptions = listOf("Cognitif", "Bien-être", "Performance", "Energie", "Concentration", "Mémorisation", "Humeur")
    val professionOptions = listOf(
        "Étudiant", "Technicien", "Cadre d'entreprise", "Professeur",
        "Profession médicale", "Artisan", "Sportif", "Autre"
    )

    // Utilisation de FocusRequester pour déplacer le focus
    val nameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val ageFocusRequester = remember { FocusRequester() }
    val tailleFocusRequester = remember { FocusRequester() }
    val poidsFocusRequester = remember { FocusRequester() }
    val genderFocusRequester = remember { FocusRequester() }
    val goalFocusRequester = remember { FocusRequester() }
    val professionFocusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current // Gestion du focus pour fermer le clavier


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        Spacer(modifier = Modifier.height(130.dp)) // Ajoute un espace

        // Nom d'utilisateur
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(nameFocusRequester),
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.navy,
                focusedContainerColor = colors.pink,
                unfocusedContainerColor = colors.pink,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.navy
            ),
            placeholder = {
                Text(
                    stringResource(R.string.act_main_fill_User),
                    color = colors.navy
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    passwordFocusRequester.requestFocus()
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        // Mot de passe
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.navy,
                focusedContainerColor = colors.pink,
                unfocusedContainerColor = colors.pink,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.navy
            ),
            placeholder = {
                Text(stringResource(
                    R.string.act_main_fill_Password),
                    color = colors.navy
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    emailFocusRequester.requestFocus()
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(emailFocusRequester),
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.navy,
                focusedContainerColor = colors.pink,
                unfocusedContainerColor = colors.pink,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.navy
            ),
            placeholder = {
                Text(stringResource(
                    R.string.act_inscription_fill_email),
                    color = colors.navy
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    ageFocusRequester.requestFocus()
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )

        // Âge
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(ageFocusRequester),
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.navy,
                focusedContainerColor = colors.pink,
                unfocusedContainerColor = colors.pink,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.navy
            ),
            placeholder = {
                Text(
                    stringResource(R.string.act_inscription_âge),
                    color = colors.navy
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    tailleFocusRequester.requestFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
        )

        // Taille
        OutlinedTextField(
            value = taille,
            onValueChange = { taille = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(tailleFocusRequester),
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.navy,
                focusedContainerColor = colors.pink,
                unfocusedContainerColor = colors.pink,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.navy
            ),
            placeholder = {
                Text(
                    "Taille (m)",
                    color = colors.navy
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    poidsFocusRequester.requestFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Decimal
            ),
        )

        // Poids
        OutlinedTextField(
            value = poids,
            onValueChange = { poids = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(poidsFocusRequester),
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.navy,
                focusedContainerColor = colors.pink,
                unfocusedContainerColor = colors.pink,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.navy
            ),
            placeholder = {
                Text(
                    "Poids (kg)",
                    color = colors.navy
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Decimal
            ),
        )

        // Sexe
        ExposedDropdownMenuBoxSample(
            label = "Genre",
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(genderFocusRequester),
            options = genderOptions,
            selectedOption = gender,
            onOptionSelected = { gender = it },
        )

        // Objectif
        ExposedDropdownMenuBoxSample(
            label = stringResource(R.string.act_inscription_label),
            options = goalOptions,
            selectedOption = goal,
            onOptionSelected = { goal = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(goalFocusRequester),
        )

        // Profession
        ExposedDropdownMenuBoxSample(
            label = "Profession",
            options = professionOptions,
            selectedOption = profession,
            onOptionSelected = { profession = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(professionFocusRequester),
        )

        // Bouton Enregistrer
        Button(
            onClick = {
                // Créer un nouvel utilisateur avec les données saisies
                val newUser = UserDto(
                    id = System.currentTimeMillis(), // ID temporaire basé sur le temps
                    username = username,
                    password = password,
                    email = email,
                    age = age.toIntOrNull() ?: 0,
                    taille = taille.toDoubleOrNull(),
                    poids = poids.toDoubleOrNull(),
                    gender = gender,
                    goal = goal,
                    profession = profession,
                    healthstate = HealthStateDto(
                        id = System.currentTimeMillis(),
                        brain_perf = 100,
                        progress = 0,
                        sommeil = 2,
                        digestion = 2,
                        energie = 2,
                        stress = 2,
                        humeur = 2,
                        concentration = 2,
                        memorisation = 2,
                        sport = 2
                    )
                )
                onSave(newUser)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.navy)
        ) {
            Text(
                stringResource(R.string.act_inscription_register),
                color = colors.textColor)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxSample(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    val colors = LocalNumedikappColors.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
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
                .padding(12.dp)
                .fillMaxWidth()
                .menuAnchor(),
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


class Inscription : ComponentActivity() {
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

    private fun onSave(newUser: UserDto) {
        // Ajouter le nouvel utilisateur à la liste des utilisateurs
        val maxId = UserService.USERS.maxOfOrNull { it.id } ?: 0
        val userId = maxId + 1

        // Créer l'utilisateur avec un ID unique
        val userWithId = newUser.copy(id = userId, healthstate = newUser.healthstate.copy(id = userId))

        // Ajouter l'utilisateur à la liste
        UserService.USERS.add(userWithId)

        // Définir l'utilisateur comme utilisateur courant
        userViewModel.setCurrentUser(userId)

        // Rediriger vers InfosPerso
        val intent = Intent(this, InfosPerso::class.java)
        startActivity(intent)
        finish()
    }

    private val onCancel = {
        navigateBack()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupérer le ViewModel depuis l'application
        userViewModel = (application as MyApplication).userViewModel

        // Initialiser les préférences de thème
        themePreference = ThemePreference(this)
        val isLightMode = themePreference.isLightMode()

        enableEdgeToEdge()
        setContent {
            NumedikappTheme (isLightMode = isLightMode){
                val colors = LocalNumedikappColors.current

                Scaffold(
                    topBar = { NumedikappTopAppBar(
                        stringResource(R.string.act_inscription),
                        navigateBack,
                        colors.navy) },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = colors.pink

                ) { innerPadding ->
                    InscriptionForm(
                        onSave = { newUser -> onSave(newUser) },
                        onCancel = onCancel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting3(modifier: Modifier = Modifier) {
    Text(
        text = "Page d'inscription",
        modifier = modifier
    )
}
