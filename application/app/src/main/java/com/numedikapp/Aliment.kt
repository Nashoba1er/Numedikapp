package com.numedikapp

import kotlin.math.*

// Liste aliments avec catégorisation par moment approprié
val aliments = mapOf(
    "myrtilles" to mapOf(
        "bienfaits" to listOf("Concentration (bonne)", "Memorisation (bonne)", "Humeur (bon)", "Bien-être", "Performance"),
        "moments" to listOf("Matin", "Collation")
    ),
    "oeufs_entiers" to mapOf(
        "bienfaits" to listOf("Concentration (bonne)", "Memorisation (bonne)", "energie (correct)", "Performance"),
        "moments" to listOf("Matin", "Midi")
    ),
    "poisson_gras" to mapOf(
        "bienfaits" to listOf("Memorisation (bonne)", "Humeur (bon)", "Stress (calme)", "Performance", "Bien-être"),
        "moments" to listOf("Midi", "Soir")
    ),
    "avocat" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Humeur (bon)", "Digestion (bonne)", "Concentration (bonne)", "Bien-être"),
        "moments" to listOf("Matin", "Midi", "Collation")
    ),
    "noix" to mapOf(
        "bienfaits" to listOf("Memorisation (bonne)", "Stress (calme)", "Humeur (bon)", "Sommeil (bon)", "Bien-être"),
        "moments" to listOf("Collation")
    ),
    "chocolat_noir" to mapOf(
        "bienfaits" to listOf("Humeur (bon)", "Concentration (en progrès)", "energie (correct)", "Stress (calme)"),
        "moments" to listOf("Collation")
    ),
    "the_vert" to mapOf(
        "bienfaits" to listOf("Concentration (en progrès)", "Stress (calme)", "energie (correct)", "Bien-être"),
        "moments" to listOf("Matin", "Collation")
    ),
    "curcuma" to mapOf(
        "bienfaits" to listOf("Memoire (en progrès)", "Stress (calme)", "Bien-être"),
        "moments" to listOf("Midi", "Soir")
    ),
    "brocolis" to mapOf(
        "bienfaits" to listOf("Memorisation (bonne)", "Bien-être", "Digestion (bonne)", "Sommeil (correct)"),
        "moments" to listOf("Midi", "Soir")
    ),
    "graines_de_chia" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Digestion (bonne)", "Sommeil (correct)", "Bien-être"),
        "moments" to listOf("Matin", "Collation")
    ),
    "avoine_complete" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Digestion (bonne)", "Humeur (bon)", "Sommeil (correct)"),
        "moments" to listOf("Matin")
    ),
    "lentilles" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Digestion (bonne)", "Sommeil (bon)", "Bien-être"),
        "moments" to listOf("Midi", "Soir")
    ),
    "banane" to mapOf(
        "bienfaits" to listOf("energie (correct)", "Humeur (bon)", "Digestion (correct)", "Sommeil (bon)"),
        "moments" to listOf("Matin", "Collation")
    ),
    "huile_d_olive" to mapOf(
        "bienfaits" to listOf("Performance", "Bien-être", "Memorisation (en progrès)"),
        "moments" to listOf("Midi", "Soir")
    ),
    "epinards" to mapOf(
        "bienfaits" to listOf("energie (correct)", "Concentration (en progrès)", "Digestion (bonne)", "Humeur (bon)"),
        "moments" to listOf("Midi", "Soir")
    ),
    "oranges" to mapOf(
        "bienfaits" to listOf("energie (correct)", "Humeur (bon)", "Bien-être"),
        "moments" to listOf("Matin", "Collation")
    ),
    "amandes" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Memorisation (en progrès)", "Stress (calme)", "Sommeil (correct)"),
        "moments" to listOf("Collation")
    ),
    "graines_de_courge" to mapOf(
        "bienfaits" to listOf("Memoire (bonne)", "Sommeil (bon)", "Humeur (bon)", "Bien-être"),
        "moments" to listOf("Collation")
    ),
    "kiwis" to mapOf(
        "bienfaits" to listOf("Sommeil (bon)", "Humeur (bon)", "Digestion (bonne)"),
        "moments" to listOf("Matin", "Collation")
    ),
    "tomates" to mapOf(
        "bienfaits" to listOf("Humeur (bon)", "Stress (calme)", "Bien-être", "Performance"),
        "moments" to listOf("Midi", "Soir")
    ),
    "patate_douce" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Digestion (bonne)", "Sommeil (bon)", "Humeur (bon)"),
        "moments" to listOf("Midi", "Soir")
    ),
    "riz_complet" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Digestion (bonne)", "Sommeil (correct)"),
        "moments" to listOf("Midi", "Soir")
    ),
    "betterave" to mapOf(
        "bienfaits" to listOf("Performance", "Concentration (bonne)", "energie (bonne)", "Bien-être"),
        "moments" to listOf("Midi", "Soir")
    ),
    "fromage_blanc" to mapOf(
        "bienfaits" to listOf("Sommeil (bon)", "Digestion (correct)", "energie (correct)", "Bien-être"),
        "moments" to listOf("Collation", "Soir")
    ),
    "yaourt_nature" to mapOf(
        "bienfaits" to listOf("Digestion (bonne)", "Humeur (bon)", "Sommeil (correct)", "Bien-être"),
        "moments" to listOf("Matin", "Collation", "Soir")
    ),
    "miel_brut" to mapOf(
        "bienfaits" to listOf("energie (correct)", "Sommeil (bon)", "Digestion (correct)"),
        "moments" to listOf("Matin", "Collation")
    ),
    "sardines" to mapOf(
        "bienfaits" to listOf("Memorisation (bonne)", "Performance", "Humeur (bon)", "Stress (calme)", "Sommeil (bon)"),
        "moments" to listOf("Midi", "Soir")
    ),
    "eau" to mapOf(
        "bienfaits" to listOf("Performance", "Concentration (bonne)", "Humeur (bon)", "energie (correct)"),
        "moments" to listOf("Matin", "Midi", "Collation", "Soir")
    ),
    "cafe" to mapOf(
        "bienfaits" to listOf("energie (bonne)", "Concentration (bonne)", "Performance", "Humeur (bon)"),
        "moments" to listOf("Matin")
    ),
    "cannelle" to mapOf(
        "bienfaits" to listOf("Memorisation (en progrès)", "Bien-être", "Humeur (bon)"),
        "moments" to listOf("Matin", "Collation")
    )
)

val moments = listOf("Matin", "Midi", "Collation", "Soir")

// Combinaisons thématiques logiques pour chaque repas
val themesRepas = mapOf(
    "Matin" to listOf(
        listOf("oeufs_entiers", "avocat", "cafe"),
        listOf("avoine_complete", "myrtilles", "miel_brut"),
        listOf("yaourt_nature", "kiwis", "graines_de_chia"),
        listOf("banane", "the_vert", "amandes")
    ),
    "Midi" to listOf(
        listOf("poisson_gras", "riz_complet", "epinards"),
        listOf("lentilles", "tomates", "huile_d_olive"),
        listOf("oeufs_entiers", "avocat", "betterave"),
        listOf("patate_douce", "brocolis", "huile_d_olive")
    ),
    "Collation" to listOf(
        listOf("yaourt_nature", "myrtilles", "miel_brut"),
        listOf("chocolat_noir", "noix", "banane"),
        listOf("fromage_blanc", "graines_de_courge", "oranges"),
        listOf("avocat", "graines_de_chia", "the_vert")
    ),
    "Soir" to listOf(
        listOf("poisson_gras", "patate_douce", "brocolis"),
        listOf("sardines", "riz_complet", "epinards"),
        listOf("lentilles", "tomates", "curcuma"),
        listOf("fromage_blanc", "kiwis", "miel_brut")
    )
)

// Poids de chaque constante
val poidsPerformance = mapOf(
    "sommeil" to 0.15,
    "digestion" to 0.10,
    "energie" to 0.20,
    "stress" to -0.15,
    "humeur" to 0.15,
    "concentration" to 0.20,
    "memorisation" to 0.20
)

// Calcul niveau de performance
fun calculerPerformance(etat: Map<String, Int>): Double {
    var score = 0.0
    for ((clef, valeur) in etat) {
        score += valeur * (poidsPerformance[clef] ?: 0.0)
    }
    // Pour une échelle de 0 à 2, on normalise pour obtenir un pourcentage
    val maxScore = poidsPerformance.values.sumOf { abs(it) * 2 }
    return round((score / maxScore) * 1000.0) / 10.0
}

// Normaliser les valeurs de stress (négatif dans le calcul) - Pas nécessaire en Kotlin car déjà géré dans calculerPerformance
//fun normaliserValeurs(etat: MutableMap<String, Int>): MutableMap<String, Int> {
//    val etatNormalise = etat.toMutableMap()
//    // Pour un état entre 0 et 2, stress doit être inversé pour le calcul
//    // Plus le stress est élevé (2), plus son impact est négatif sur la performance
//    return etatNormalise
//}

// Fonction pour évaluer l'utilité d'un aliment pour un objectif
fun evaluerUtiliteAliment(aliment: String, objectif: String): Int {
    val objectifsDict = mapOf(
        "Performance" to listOf("Performance"),
        "Energie" to listOf("energie (correct)", "energie (bonne)"),
        "Concentration" to listOf("Concentration (bonne)", "Concentration (en progrès)"),
        "Memorisation" to listOf("Memorisation (bonne)", "Memorisation (en progrès)", "Memoire (bonne)", "Memoire (en progrès)"),
        "Humeur" to listOf("Humeur (bon)", "Stress (calme)"),
        "Bien-être" to listOf("Bien-être"),
        "Sommeil" to listOf("Sommeil (bon)", "Sommeil (correct)"),
        "Digestion" to listOf("Digestion (bonne)", "Digestion (correct)")
    )

    val cibles = objectifsDict[objectif] ?: emptyList()
    val bienfaits = aliments[aliment]?.get("bienfaits") as? List<String> ?: emptyList()

    // Compte combien de bienfaits correspondent à l'objectif
    return bienfaits.count { bienfait -> cibles.any { cible -> cible in bienfait } }
}

// Generation des aliments recommandes selon l'objectif et l'etat avec plus de variété
fun recommanderAliments(etat: Map<String, Int>, objectif: String): Map<String, List<String>> {
    val planJournalier = mutableMapOf<String, List<String>>()
    val random = java.util.Random()

    // Identifier l'objectif secondaire (domaine le plus faible)
    val objectifSecondaire = etat.entries
        .filter { it.key.capitalize() != objectif }
        .minByOrNull { it.value }?.key?.capitalize() ?: "Bien-être"

    // Pour chaque moment de la journée
    for (moment in moments) {
        // Filtrer les aliments disponibles pour ce moment
        val alimentsMoment = aliments.filter { (_, valeur) ->
            valeur["moments"]?.contains(moment) == true
        }

        // Si aucun aliment disponible pour ce moment, passer au suivant
        if (alimentsMoment.isEmpty()) {
            planJournalier[moment] = emptyList()
            continue
        }

        // Évaluer chaque aliment pour l'objectif principal et secondaire
        val scores = alimentsMoment.mapValues { (aliment, _) ->
            val scorePrincipal = evaluerUtiliteAliment(aliment, objectif) * 2  // Donner plus de poids à l'objectif principal
            val scoreSecondaire = evaluerUtiliteAliment(aliment, objectifSecondaire)
            scorePrincipal + scoreSecondaire
        }

        // Trier les aliments par score, puis ajouter un facteur aléatoire pour la variété
        val alimentsScores = alimentsMoment.keys.map { aliment ->
            val scoreBase = scores[aliment] ?: 0
            val scoreAleatoire = random.nextDouble() * 2  // Facteur aléatoire entre 0 et 2
            Triple(aliment, scoreBase, scoreBase + scoreAleatoire)
        }

        // Sélectionner les aliments selon différentes stratégies pour garantir la variété
        val selectionStrategy = random.nextInt(3)
        val selection = when (selectionStrategy) {
            // Stratégie 1: Meilleurs scores avec facteur aléatoire
            0 -> alimentsScores
                .sortedByDescending { it.third }
                .take(5)
                .shuffled()
                .take(3)
                .map { it.first }

            // Stratégie 2: Aliment de top score + aléatoire parmi les bons + aléatoire total
            1 -> {
                val meilleur = alimentsScores.maxByOrNull { it.second }?.first
                val bonScore = alimentsScores
                    .filter { it.second > 0 }
                    .shuffled()
                    .firstOrNull()?.first
                val aleatoire = alimentsMoment.keys.random()

                listOfNotNull(meilleur, bonScore, aleatoire).distinct().take(3)
            }

            // Stratégie 3: Mélange entre objectif principal et secondaire
            else -> {
                val principalAliments = alimentsScores
                    .sortedByDescending { evaluerUtiliteAliment(it.first, objectif) }
                    .take(3)
                    .map { it.first }

                val secondaireAliments = alimentsScores
                    .sortedByDescending { evaluerUtiliteAliment(it.first, objectifSecondaire) }
                    .take(3)
                    .map { it.first }

                (principalAliments + secondaireAliments).distinct().shuffled().take(3)
            }
        }

        // Assurer qu'on a bien 3 aliments (ou moins si pas assez disponibles)
        val alimentsFinaux = if (selection.size < 3 && alimentsMoment.size >= 3) {
            val manquants = alimentsMoment.keys
                .filter { it !in selection }
                .shuffled()
                .take(3 - selection.size)

            selection + manquants
        } else {
            selection
        }

        planJournalier[moment] = alimentsFinaux
    }

    return planJournalier
}

// Fonction pour afficher le plan alimentaire de manière plus lisible
fun afficherPlan(plan: Map<String, List<String>>, objectif: String, niveauPerf: Double) {
    println("\nObjectif principal : $objectif")
    println("Niveau de performance cérébrale estimé : ${niveauPerf}%\n")
    println("Plan alimentaire recommandé :")

    for ((moment, items) in plan) {
        val momentTitre = when (moment) {
            "Matin" -> "Petit-déjeuner"
            "Midi" -> "Déjeuner"
            "Soir" -> "Dîner"
            else -> moment.capitalize()
        }

        println("\n- $momentTitre : ${items.joinToString(", ")}")

        // Afficher les bienfaits principaux
        println("  Bienfaits :")
        val bienfaitsCombines = mutableSetOf<String>()
        for (item in items) {
            aliments[item]?.get("bienfaits")?.let { bienfaits ->
                bienfaitsCombines.addAll(bienfaits as List<String>)
            }
        }

        // Afficher les bienfaits principaux
        var bienfaitsAffiches = 0
        for (bienfait in bienfaitsCombines) {
            if (bienfaitsAffiches < 3) {
                println("  • $bienfait")
                bienfaitsAffiches++
            }
        }
    }
}


// MAIN - Interface utilisateur améliorée
fun main() {
    println("\n===== PLAN ALIMENTAIRE POUR PERFORMANCE CÉRÉBRALE =====\n")

    // Initialiser avec des valeurs par défaut
    val etatUtilisateur = mutableMapOf(
        "sommeil" to 2,
        "digestion" to 0,
        "energie" to 2,
        "stress" to 0,
        "humeur" to 2,
        "concentration" to 2,
        "memorisation" to 2
    )

    // Option pour personnaliser les paramètres
    print("Souhaitez-vous personnaliser vos paramètres? (o/n): ")
    val personnaliser = readlnOrNull()?.lowercase() == "o"

    if (personnaliser) {
        println("\nÉvaluez votre état actuel sur une échelle de 0 à 2:")
        println("0 = faible/mauvais, 1 = moyen, 2 = bon/excellent\n")

        for (param in etatUtilisateur.keys) {
            var valide = false
            while (!valide) {
                try {
                    print("${param.capitalize()}: ")
                    val valeur = readlnOrNull()?.toIntOrNull()
                    if (valeur != null && valeur in 0..2) {
                        etatUtilisateur[param] = valeur
                        valide = true
                    } else {
                        println("Veuillez entrer une valeur entre 0 et 2.")
                    }
                } catch (e: NumberFormatException) {
                    println("Veuillez entrer un nombre entier.")
                }
            }
        }
    }

    // Choix de l'objectif
    val objectifsDisponibles = listOf("Performance", "Energie", "Concentration", "Memorisation", "Humeur", "Bien-être", "Sommeil", "Digestion")
    println("\nChoisissez votre objectif principal:")
    for ((i, obj) in objectifsDisponibles.withIndex()) {
        println("${i + 1}. $obj")
    }

    var choixValide = false
    var objectif = ""
    while (!choixValide) {
        try {
            print("\nVotre choix (1-8): ")
            val choix = readlnOrNull()?.toIntOrNull()
            if (choix != null && choix in 1..objectifsDisponibles.size) {
                objectif = objectifsDisponibles[choix - 1]
                choixValide = true
            } else {
                println("Veuillez entrer un nombre entre 1 et ${objectifsDisponibles.size}.")
            }
        } catch (e: NumberFormatException) {
            println("Veuillez entrer un nombre entier.")
        }
    }

    // Calcul et affichage des résultats
    val niveauPerf = calculerPerformance(etatUtilisateur)
    val planAliments = recommanderAliments(etatUtilisateur, objectif)
    afficherPlan(planAliments, objectif, niveauPerf)

    println("\nBon appétit et bonne journée!")
}

/*

val planAlimentaire: Map<String, List<String>> = recommanderAliments(etatUtilisateur, objectif) // Supposons que vous avez déjà ces variables

val alimentsMidi: List<String>? = planAlimentaire["Midi"] // alimentsMidi est une List<String>? (nullable)

planAlimentaire["Midi"]?.forEach { aliment ->
    println("Aliment du Midi : $aliment")
} ?: println("Aucun aliment trouvé pour le Midi.")


*/