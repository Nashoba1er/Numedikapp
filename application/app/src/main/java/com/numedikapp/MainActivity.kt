package com.numedikapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.numedikapp.ui.theme.NumedikappTheme
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import com.numedikapp.data.ThemePreference
import com.numedikapp.model.UserService
import com.numedikapp.model.UserService.idByName
import com.numedikapp.model.UserViewModel
import com.numedikapp.ui.theme.LocalNumedikappColors


class MainActivity : ComponentActivity() {
    //etape 1 pour avoir les couleurs du thème
    private lateinit var themePreference: ThemePreference

    //pour pouvoir récuper les données peu importe l'activité
    private lateinit var userViewModel: UserViewModel

    companion object {
        const val NAME_PARAM = "com.numedikapp.name.attribute"
        const val USER_PARAM = "com.numedikapp.userId.attribute"
    }

    var userId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // étape 2 : Initialiser les préférences de thème
        themePreference = ThemePreference(this)
        val isLightMode = themePreference.isLightMode()

        // Récupération du ViewModel partagé
        userViewModel = (application as MyApplication).userViewModel

        // Action à faire quand le bouton connect est cliqué
        val onConnectButtonClick: (name: String, password: String) -> Unit = { name, password ->
            if (userViewModel.authenticateUser(name, password)) {
                // Connexion réussie
                val intent = Intent(this, Dashboard2::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(baseContext, "Bonjour $name", Toast.LENGTH_LONG).show()
            } else {
                // Connexion échouée
                Toast.makeText(baseContext, "Identifiant ou mot de passe incorrect", Toast.LENGTH_LONG).show()
            }
        }

        // Action à faire quand le bouton s'inscrire est cliqué
        val onRegisterClick: () -> Unit = {
            val intent = Intent(this, Inscription::class.java).apply {}
            startActivity(intent)
            finish() // Déplacé ici pour être exécuté après startActivity
        }

        val onTestButtonClick: () -> Unit = {
            val testUsers = userViewModel.getTestUsers()
            if (testUsers.isNotEmpty()) {
                userViewModel.currentUser = testUsers[0]  // Premier utilisateur de test
                val name = userViewModel.currentUser!!.username
                val password = userViewModel.currentUser!!.password

                if (userViewModel.authenticateUser(name, password)) {
                    // Connexion réussie
                    val intent = Intent(this, Dashboard2::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(baseContext, "Bonjour $name", Toast.LENGTH_LONG).show()
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            NumedikappTheme (
                // etape 3 pour avoir les couleurs du thème
                isLightMode = isLightMode
            ){
                // etape 4 pour avoir les couleurs du thème
                val colors = LocalNumedikappColors.current

                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = colors.navy
                ) { innerPadding ->
                    Greeting(
                        onConnectClick = onConnectButtonClick,
                        onRegisterClick = onRegisterClick,
                        onTestButtonClick = onTestButtonClick,
                        modifier = Modifier.padding(innerPadding),
                        isLightMode = isLightMode,
                        )
                }
            }
        }
    }
}

// couleur du k de l'appli : ED00AA

@Composable
fun Greeting(
    onConnectClick: (name: String, password: String) -> Unit,
    onRegisterClick: () -> Unit,
    onTestButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLightMode: Boolean
    ) {
    // Utilisation de FocusRequester pour déplacer le focus
    val nameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current // Gestion du focus pour fermer le clavier

    // permet d'avoir les couleurs du thème
    val colors = LocalNumedikappColors.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppLogo(Modifier.padding(top = 20.dp).fillMaxWidth(), isLightMode)

        var name by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(50.dp)) // Ajoute un espace

        //nom d'utilisateur
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(nameFocusRequester), // FocusRequester pour ce champ
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.pink,
                focusedContainerColor = colors.navy,
                unfocusedContainerColor = colors.navy,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.pink
            ),
            placeholder = {
                Text(
                    stringResource(R.string.act_main_fill_User),
                    color = colors.pink
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    // Lorsqu'on appuie sur "Entrée", déplacer le focus vers le champ du mot de passe
                    passwordFocusRequester.requestFocus()
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next // Indique que le "Next" bouton doit être affiché sur le clavier
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester), // FocusRequester pour ce champ
            textStyle = TextStyle(color = colors.textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textColor,
                unfocusedTextColor = colors.pink,
                focusedContainerColor = colors.navy,
                unfocusedContainerColor = colors.navy,
                focusedBorderColor = colors.textColor,
                unfocusedBorderColor = colors.pink
            ),
            placeholder = {
                Text(
                    stringResource(
                        R.string.act_main_fill_Password
                    ),
                    color = colors.pink
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Ferme le clavier
                    onConnectClick(name, password) // Utilise directement la fonction de connexion
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done, // Afficher "Done" pour fermer le clavier
                keyboardType = KeyboardType.Password // Indique que c'est un champ de mot de passe
            ),
            visualTransformation = PasswordVisualTransformation() // Transformation pour masquer le texte par des points
        )

        var stayConnected by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), // Ajoute une marge à gauche
            horizontalAlignment = Alignment.Start // Aligne tout à gauche
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // Centre le texte par rapport à la case
            ) {
                Checkbox(
                    checked = stayConnected,
                    onCheckedChange = { stayConnected = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colors.pink,       // Couleur quand c'est coché
                        uncheckedColor = colors.pink,     // Couleur quand ce n'est pas coché
                        checkmarkColor = colors.textColor        // Couleur du "✔" à l'intérieur
                    )
                )
                Text(
                    text = "Rester connecté",
                    modifier = Modifier.padding(start = 8.dp), // Espacement entre la case et le texte
                    color = colors.textColor
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp)) // Ajoute un espace

        Button(
            onClick = {
                focusManager.clearFocus() // Ferme le clavier
                onConnectClick(name, password) // Utilise directement la fonction de connexion
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.pink // Change la couleur du bouton
            )
        ) {
            Text(stringResource(R.string.act_main_open), color = colors.textColor)
        }

        Text(
            text = stringResource(R.string.act_main_login),
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                    onRegisterClick() // Utilise directement la fonction d'inscription
                },
            color = colors.textColor,
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            )
        )

        Button(
            onClick = {
                onTestButtonClick() // Utilise directement la fonction de connexion
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.pink // Change la couleur du bouton
            )
        ){
            Text("Demo", color = colors.textColor)
        }
    }
}


@Composable
fun AppLogo(modifier: Modifier, isLightMode : Boolean) {
    if (isLightMode){
        Image(
            painter = painterResource(R.drawable.logo_light_mode),
            contentDescription = stringResource(R.string.app_logo_description),
            modifier = modifier.paddingFromBaseline(top = 100.dp).height(100.dp),
        )
    }
    else {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.app_logo_description),
            modifier = modifier.paddingFromBaseline(top = 100.dp).height(100.dp),
        )
    }
}
