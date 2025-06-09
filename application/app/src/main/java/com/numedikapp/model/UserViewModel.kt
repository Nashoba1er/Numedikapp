package com.numedikapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Nécessaire pour viewModelScope.launch
import com.numedikapp.healthStateDtoToEtatUtilisateur
import com.numedikapp.model.UserService.USERS
import com.numedikapp.model.UserService.idByName
import com.numedikapp.recommanderAliments
import kotlinx.coroutines.launch // Nécessaire pour viewModelScope.launch

class UserViewModel: ViewModel() {
    // Utilisateur actuellement connecté
    var currentUser: UserDto? = null // Vous pourriez envisager de rendre cela privé et de l'exposer via userLiveData

    // LiveData pour observer les changements d'utilisateur
    private val _userLiveData = MutableLiveData<UserDto?>()
    val userLiveData: LiveData<UserDto?> = _userLiveData

    // LiveData pour le plan alimentaire
    private val _planAlimentaire = MutableLiveData<Map<String, List<String>>>()
    val planAlimentaire: LiveData<Map<String, List<String>>> = _planAlimentaire

    // LiveData pour les aliments consommés
    private val _alimentsConsommes = MutableLiveData<Set<String>>()
    val alimentsConsommes: LiveData<Set<String>> = _alimentsConsommes

    init {
        // Initialiser l'ensemble des aliments consommés
        _alimentsConsommes.value = emptySet()
    }

    // Fonction pour définir l'utilisateur courant et générer son plan
    fun setCurrentUser(userId: Long) {
        val user = UserService.findById(userId)
        currentUser = user
        _userLiveData.value = user
        user?.let {
            generateAndSetPlan(it)
        } ?: run {
            _planAlimentaire.value = emptyMap() // Effacer le plan si pas d'utilisateur
        }
    }

    // Fonction pour mettre à jour le HealthState
    fun updateHealthState(updatedHealthState: HealthStateDto) {
        val user = currentUser ?: return

        val updatedUser = user.copy(healthstate = updatedHealthState)
        UserService.updateHealthState(user.id, updatedHealthState) // Met à jour dans le "service"

        currentUser = updatedUser // Met à jour la copie locale dans le ViewModel
        _userLiveData.value = updatedUser // Notifie les observateurs de userLiveData
    }

    fun authenticateUser(username: String, password: String): Boolean {
        val userId = idByName(username)
        if (userId.toInt() == -1) return false

        val user = UserService.findById(userId)
        if (user != null && user.password == password) {
            setCurrentUser(userId) // Ceci va aussi générer le plan
            return true
        }
        return false
    }

    fun getTestUsers(): List<UserDto> {
        return USERS
    }

    private val poidsPerformance = mapOf(
        "sommeil" to 0.15, "digestion" to 0.10, "energie" to 0.20,
        "stress" to -0.15, "humeur" to 0.15, "concentration" to 0.20,
        "memorisation" to 0.20
    )

    private fun calculerPerformanceCerveauCurseurs(healthState: HealthStateDto): Double {
        var score = 0.0
        score += healthState.sommeil * poidsPerformance["sommeil"]!!
        score += healthState.digestion * poidsPerformance["digestion"]!!
        score += healthState.energie * poidsPerformance["energie"]!!
        score += healthState.stress * poidsPerformance["stress"]!!
        score += healthState.humeur * poidsPerformance["humeur"]!!
        score += healthState.concentration * poidsPerformance["concentration"]!!
        score += healthState.memorisation * poidsPerformance["memorisation"]!!

        // Correction de la somme des poids pour la normalisation
        // La somme des valeurs absolues des poids est plus appropriée ici si vous voulez une échelle de 0 à max.
        // Votre calcul initial pour sommePoids était: poidsPerformance.values.sumOf { it } + 0.15
        // Si stress est négatif, sumOf {it} le soustrait. sumOf { kotlin.math.abs(it) } serait pour les valeurs absolues.
        // Pour cet exemple, je garde votre calcul original de sommePoids.
        val sommePoids = poidsPerformance.values.sumOf { it } + 0.15 // (0.15+0.10+0.20-0.15+0.15+0.20+0.20) + 0.15 = 0.85 + 0.15 = 1.0
        // Si vous voulez que le score soit entre 0 et 100, la normalisation dépendra de la plage min/max possible de 'score'.
        // Supposons que les états (sommeil, etc.) vont de 0 à N.
        // Pour l'instant, je conserve votre logique de normalisation.
        val result = ((score / sommePoids) * 50)
        return result
    }

    private fun calculerPerformanceCerveauAliments(): Double {
        val alimentsConsommesSet = _alimentsConsommes.value ?: emptySet()
        val nombreAlimentsConsommes = alimentsConsommesSet.size

        // Calculer le nombre total d'aliments dans le plan
        val planActuel = _planAlimentaire.value ?: emptyMap()
        val nombreTotalAliments = planActuel.values.sumOf { it.size }

        // Éviter la division par zéro
        return if (nombreTotalAliments > 0) {
            (nombreAlimentsConsommes.toDouble() / nombreTotalAliments) * 100
        } else {
            0.0
        }
    }

    fun updateHealthStateAttribute(attribute: String, value: Int) {
        val user = currentUser ?: return // Renommé pour éviter la confusion avec la variable globale currentUser
        val currentHealthState = user.healthstate

        val updatedHealthState = when (attribute) {
            "sommeil" -> currentHealthState.copy(sommeil = value)
            "digestion" -> currentHealthState.copy(digestion = value)
            "energie" -> currentHealthState.copy(energie = value)
            "stress" -> currentHealthState.copy(stress = value)
            "humeur" -> currentHealthState.copy(humeur = value)
            "concentration" -> currentHealthState.copy(concentration = value)
            "memorisation" -> currentHealthState.copy(memorisation = value)
            "sport" -> currentHealthState.copy(sport = value)
            "aliment" -> currentHealthState
            // Ajoutez d'autres attributs ici
            else -> {
                println("Attribut non reconnu: $attribute")
                return // Ou lancer une exception
            }
        }

        val newBrainPerfCurseurs = calculerPerformanceCerveauCurseurs(updatedHealthState)
        val newBrainPerfAliments = calculerPerformanceCerveauAliments()
        val newBrainPerf = ((0.3 * newBrainPerfAliments) + (0.7 * newBrainPerfCurseurs)).toInt().coerceIn(0, 100)
        val finalHealthState = updatedHealthState.copy(brain_perf = newBrainPerf)

        updateHealthState(finalHealthState)
    }

    // Fonction privée pour générer et mettre à jour le plan alimentaire
    private fun generateAndSetPlan(user: UserDto) {
        // Il est préférable d'exécuter la recommandation (potentiellement longue) hors du thread principal
        viewModelScope.launch {
            val etatUtilisateur = healthStateDtoToEtatUtilisateur(user.healthstate)
            val nouveauPlan = recommanderAliments(etatUtilisateur, user.goal)
            _planAlimentaire.postValue(nouveauPlan) // postValue si appelé depuis un coroutine/background thread
            // ou .value si vous êtes sûr d'être sur le MainThread

            // Réinitialiser les aliments consommés lorsqu'un nouveau plan est généré
            _alimentsConsommes.postValue(emptySet())
        }
    }

    fun calculatePlanAlimentaire() {
        _userLiveData.value?.let { user ->
            val etatMental = healthStateDtoToEtatUtilisateur(user.healthstate)
            _planAlimentaire.value = recommanderAliments(etat = etatMental, objectif = user.goal)
            // Réinitialiser les aliments consommés lorsqu'un nouveau plan est généré
            _alimentsConsommes.value = emptySet()
        }
    }

    // Fonction publique pour que l'UI demande une régénération manuelle du plan
    fun regeneratePlanManually() {
        currentUser?.let {
            generateAndSetPlan(it)
        }
    }

    // Nouvelles fonctions pour gérer les aliments consommés

    // Basculer l'état de consommation d'un aliment
    fun toggleAlimentConsomme(idAliment: String) {
        val consommes = _alimentsConsommes.value ?: emptySet()
        val nouveauConsommes = if (consommes.contains(idAliment)) {
            consommes - idAliment // Retirer l'aliment s'il était déjà consommé
        } else {
            consommes + idAliment // Ajouter l'aliment s'il n'était pas consommé
        }
        _alimentsConsommes.value = nouveauConsommes
    }

    // Vérifier si un aliment a été consommé
    fun isAlimentConsomme(idAliment: String): Boolean {
        return (_alimentsConsommes.value ?: emptySet()).contains(idAliment)
    }

    // Créer un identifiant unique pour chaque aliment en fonction de son moment et de son index
    fun createAlimentId(moment: String, nomAliment: String): String {
        return "$moment:$nomAliment"
    }
}