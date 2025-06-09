package com.numedikapp.model

data class UserDto(
    val id: Long,
    var username: String,
    var password: String,
    var email: String,
    var age: Int, // années
    var taille : Double?, // m
    var poids : Double?, // kg
    var gender: String, // (Homme, Femme, Autre)
    var goal: String,
    // (débutant : Cognitif, Bien-être)
    // (confirmé/expert : Performance, Energie, Concentration, Mémorisation, Humeur)
    var profession : String?,
    //(étudiant, technicien, cadre d’entreprise, professeur, profession médical, artisans, sportif, autre).
    val healthstate: HealthStateDto
    )