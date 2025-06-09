package com.numedikapp.model

object UserService {

    private val GENDER_KIND: List<String> = listOf("Homme", "Femme", "Autre")
    private val GOAL_KIND: List<String> = listOf("Cognitif", "Bien-être")

//    private fun generateUser(id: Long): UserDto {
//        val username = "USER $id"
//        val goal = GOAL_KIND.random()
//        val gender = GENDER_KIND.random()
//        val email = "$username@etu.emse.fr"
//
//        return UserDto(
//            id = id,
//            username = username,
//            password = "1234",
//            email = email,
//            age = (15..30).random(),
//            taille = ((150..210).random()).toDouble()/100,
//            poids = ((50..150).random()).toDouble(),
//            gender = gender,
//            goal = goal,
//            profession = "Etudiant",
//            healthstate = generatehealthstate(id),
//        )
//    }

    private fun generatehealthstate(id : Long): HealthStateDto{

        return HealthStateDto(
            id = id,
            brain_perf = perf_brain(id),
            progress = (1..200).random(),
            sommeil = (1..3).random(),
            digestion = (1..3).random(),
            energie = (1..3).random(),
            stress = (1..3).random(),
            humeur = (1..2).random(),
            concentration = (1..3).random(),
            memorisation = (1..3).random(),
            sport = (1..4).random()
        )
    }

    private fun perf_brain(id: Long) : Int {
        return 100
    }



    // Create 50 users
    val USERS: MutableList<UserDto> = mutableListOf(
        UserDto(
            id = 0,
            username = "testeur",
            password = "1234",
            email = "test@example.com",
            age = 25,
            taille = 1.75,
            poids = 70.0,
            gender = "Homme",
            goal = "Cognitif",
            profession = "Étudiant",
            healthstate = HealthStateDto(
                id = 0,
                progress = 120,
                sommeil = 0,
                digestion = 0,
                energie = 0,
                stress = 0,
                humeur = 0,
                concentration = 0,
                memorisation = 0,
                sport = 3
            )
        )
    ) // (1..50).map { generateUser(it.toLong()) }.toMutableList()

    fun findAll(): List<UserDto> {
        return USERS.sortedBy { it.username }
    }

    fun findById(id: Long): UserDto? {
        return USERS.find { it.id == id }
    }

    fun findByName(name: String): UserDto? {
        return USERS.find { it.username.equals(name, ignoreCase = true) }
    }

    fun isUserName(name : String): Boolean{
        return USERS.any { it.username.equals(name, ignoreCase = true) }
    }

    fun idByName(name: String): Long {
        val user = findByName(name)
        return user?.id ?: -1
    }

    fun updateUser(id: Long, user: UserDto): UserDto {
        val existingUser = USERS.find { it.id == id }
            ?: throw IllegalArgumentException("User with id $id not found")

        existingUser.username = user.username
        existingUser.password = user.password
        existingUser.email = user.email
        existingUser.age = user.age
        existingUser.gender = user.gender
        existingUser.poids = user.poids
        existingUser.taille = user.taille
        existingUser.goal = user.goal
        existingUser.profession = user.profession
        return existingUser
    }

    fun updateHealthState(userId: Long, healthState: HealthStateDto): Boolean {
        val user = findById(userId) ?: return false

        // Trouver l'index de l'utilisateur dans la liste
        val index = USERS.indexOfFirst { it.id == userId }
        if (index == -1) return false

        // Créer un nouvel utilisateur avec le HealthState mis à jour
        val updatedUser = user.copy(healthstate = healthState)

        // Remplacer l'ancien utilisateur dans la liste
        USERS[index] = updatedUser

        return true
    }

    fun addTestUsers() {
        // S'assurer que les ID ne sont pas déjà utilisés
        val maxId = USERS.maxOfOrNull { it.id } ?: 0
        var nextId = maxId + 1

        // Ajouter des utilisateurs de test avec des informations prédéfinies
        val testUsers = listOf(
            UserDto(
                id = nextId++,
                username = "test_user",
                password = "test123",
                email = "test@example.com",
                age = 25,
                taille = 1.75,
                poids = 70.0,
                gender = "Homme",
                goal = "Cognitif",
                profession = "Étudiant",
                healthstate = HealthStateDto(
                    id = nextId - 1,
                    progress = 120,
                    sommeil = 2,
                    digestion = 3,
                    energie = 3,
                    stress = 2,
                    humeur = 2,
                    concentration = 2,
                    memorisation = 2,
                    sport = 3
                )
            ),
            UserDto(
                id = nextId++,
                username = "wellness",
                password = "wellness123",
                email = "wellness@example.com",
                age = 30,
                taille = 1.65,
                poids = 60.0,
                gender = "Femme",
                goal = "Bien-être",
                profession = "Professeur",
                healthstate = HealthStateDto(
                    id = nextId - 1,
                    progress = 90,
                    sommeil = 1,
                    digestion = 2,
                    energie = 2,
                    stress = 3,
                    humeur = 1,
                    concentration = 2,
                    memorisation = 1,
                    sport = 2
                )
            ),
            UserDto(
                id = nextId++,
                username = "cognitive",
                password = "cognitive123",
                email = "cognitive@example.com",
                age = 35,
                taille = 1.82,
                poids = 78.0,
                gender = "Homme",
                goal = "Cognitif",
                profession = "Profession médicale",
                healthstate = HealthStateDto(
                    id = nextId - 1,
                    progress = 175,
                    sommeil = 2,
                    digestion = 3,
                    energie = 2,
                    stress = 1,
                    humeur = 2,
                    concentration = 3,
                    memorisation = 3,
                    sport = 2
                )
            ),

                    UserDto(
                    id = 201,
            username = "bien_etre_test",
            password = "test123",
            email = "bienetre@test.com",
            age = 28,
            taille = 1.70,
            poids = 65.0,
            gender = "Femme",
            goal = "Bien-être",
            profession = "Professeur",
            healthstate = HealthStateDto(
                id = 201,
                progress = 90,
                sommeil = 1,
                digestion = 2,
                energie = 2,
                stress = 3,
                humeur = 1,
                concentration = 2,
                memorisation = 1,
                sport = 2
            )
        ),

        // Utilisateur expert en cognitif
        UserDto(
            id = 202,
            username = "cognitif_expert",
            password = "test123",
            email = "cognitif@test.com",
            age = 35,
            taille = 1.82,
            poids = 78.0,
            gender = "Homme",
            goal = "Cognitif",
            profession = "Profession médicale",
            healthstate = HealthStateDto(
                id = 202,
                progress = 175,
                sommeil = 2,
                digestion = 3,
                energie = 2,
                stress = 1,
                humeur = 2,
                concentration = 3,
                memorisation = 3,
                sport = 2
            )
        )
        )

// Utilisateur débutant orienté bien-être

        // Ajoutez d'autres utilisateurs selon vos besoins
    USERS.addAll(testUsers)
    }

    // Appeler cette fonction lors de l'initialisation
    init {
        addTestUsers()
    }

}
