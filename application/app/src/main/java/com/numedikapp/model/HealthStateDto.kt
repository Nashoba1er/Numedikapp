package com.numedikapp.model

data class HealthStateDto (
    val id : Long,
    val brain_perf : Int, // etat de performance du cerveau, en pourcentage.

    var progress : Int, // etat de progression (lié au respect ou non du plan annoncé)
    // Soyez plus régulier dans vos efforts (plan non suivi).
    // Vous êtes sur la bonne voie, continuez (plan partiellement suivi).
    // Quelle régularité, continuez (plan totalement respecté et suivi).

    //Etat mental :
    var sommeil: Int, // (faible, correct, bon)
    var digestion: Int, // (compliquée, correct, bonne)
    var energie : Int, // (faible, correct, bon)
    var stress : Int, // (stressé, correct, calme)
    var humeur : Int, // (mauvais, bon)
    var concentration : Int, // (mauvaise, en progrès, bonne)
    var memorisation : Int, // (mauvaise, en progrès, bonne)

    // période d'activité
    val sport: Int,
    // Temps de sport par semaine :
    //• Niveau 0: sédentaire pur (pas d’activité)
    //• Niveau 1: marche 30min environ
    //• Niveau 2: activité sportive 1h environ
    //• Niveau 3: Activité sportive supérieur ou égal à 2h
){

    // Fonction de copie qui permet de mettre à jour brain_perf
    fun copyWithNewBrainPerf(newBrainPerf: Int): HealthStateDto {
        return copy(brain_perf = newBrainPerf)
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

    // Définir les poids pour chaque attribut et les méthodes utilitaires dans un seul companion object
    companion object {
        val poidsPerformance = mapOf(
            "sommeil" to 0.15,
            "digestion" to 0.10,
            "energie" to 0.20,
            "stress" to -0.15,    // Notez que ce poids est négatif
            "humeur" to 0.15,
            "concentration" to 0.20,
            "memorisation" to 0.20
        )

        // Fonction pour calculer la performance du cerveau
        private fun calculerPerformanceCerveau(
            sommeil: Int,
            digestion: Int,
            energie: Int,
            stress: Int,
            humeur: Int,
            concentration: Int,
            memorisation: Int
        ): Int {
            var score = 0.0

            // Utiliser une map temporaire pour simplifier le calcul
            val attributs = mapOf(
                "sommeil" to sommeil,
                "digestion" to digestion,
                "energie" to energie,
                "stress" to stress,
                "humeur" to humeur,
                "concentration" to concentration,
                "memorisation" to memorisation
            )

            // Ajouter les contributions de chaque attribut
            for ((attribut, valeur) in attributs) {
                score += valeur * poidsPerformance[attribut]!!
            }

            // Calculer la somme absolue des poids (en ignorant le signe)
            val sommePoids = poidsPerformance.values.sumOf { it } + 0.15

            val result = ((score / sommePoids) * 50).toInt().coerceIn(0, 100)
            // Normaliser le score entre 0 et 100
            System.out.println("on vient de calculer brain perf et il vaut" + result)

            return result
        }
    }

    // Constructeur secondaire qui calcule brain_perf automatiquement
    constructor(
        id: Long,
        sommeil: Int,
        digestion: Int,
        energie: Int,
        stress: Int,
        humeur: Int,
        concentration: Int,
        memorisation: Int,
        sport: Int,
        progress: Int
    ) : this(
        id = id,
        sommeil = sommeil,
        digestion = digestion,
        energie = energie,
        stress = stress,
        humeur = humeur,
        concentration = concentration,
        memorisation = memorisation,
        sport = sport,
        progress = progress,
        brain_perf = calculerPerformanceCerveau(
            sommeil = sommeil,
            digestion = digestion,
            energie = energie,
            stress = stress,
            humeur = humeur,
            concentration = concentration,
            memorisation = memorisation
        )
    )
}

