# Numedikapp

Numedikapp est une application mobile permettant de rendre un utilisateur focalisé sur son objectif (augmenter ses performances mentales) et de mesurer sa progression. 
A l’image d’une application d’apprentissage de langues (ex. Duolingo, Mosalingua) ou d’une application sportive mesurant l’effort et le résultat (ex. VO2max), 
l’application santé mentale veut permettre à l’utilisateur d'augmenter sa performance sur ses constantes (ex. mémoire, concentration, énergie…)

---

## 📋 Fonctionnalités

- Authentification sécurisée
- Création et gestion des profils utilisateurs
- Autodiagnostique mental
- Programme nutritionnel sur mesure

---

## 🛠️ Technologies utilisées

- **Gradle** : 8 – Outil de build pour le projet Android
- **Kotlin** – Langage principal pour le développement Android
- **Android SDK** – Base de développement d'applications Android
- **Jetpack Compose** – UI moderne et déclarative

---

## ✅ Prérequis

Avant de démarrer :

- **JDK 17** ou version supérieure  
  👉 [Télécharger OpenJDK](https://adoptium.net/)
- **Android Studio Giraffe** ou version plus récente  
  👉 [Télécharger Android Studio](https://developer.android.com/studio)
- Gradle Wrapper est inclus dans le projet (pas besoin d'installation manuelle).

---

## 🚀 Démarrage rapide

### 1. Cloner le dépôt

```bash
git clone https://github.com/Nashoba1er/Numedikapp.git
cd Numedikapp
```

### 2. Ouvrir dans Android Studio

- Lance Android Studio
- Choisis "Open an existing project" et sélectionne le dossier Numedikapp
- Laisse Android Studio synchroniser Gradle automatiquement

### 3. Exécuter l’application

- Branche un appareil Android (ou utilise un émulateur)
- Clique sur ▶️ "Run"
- L’application sera installée et exécutée sur l'appareil cible

--- 

## 📁 Structure du projet

```bash
Numedikapp/
├── app/                        # Module principal Android
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/           # Code source Kotlin
│   │   │   ├── res/            # Ressources XML (UI, images)
│   │   │   └── AndroidManifest.xml
│   └── build.gradle            # Dépendances du module
├── build.gradle                # Script de build global
├── settings.gradle             # Configuration du projet
├── gradle/                     # Fichiers du Gradle Wrapper
├── .gitignore
└── README.md                   # Ce fichier
```

---

## 🤝 Contribution

Les contributions sont les bienvenues !

### 1.Fork le dépôt
Crée une branche :
```bash
git checkout -b feature/ma-fonctionnalite
```

### 2. Apporte tes modifications
Commits :
```bash
git commit -m "feat: nouvelle fonctionnalité"
```
    
Pousse vers ton fork :
```bash
git push origin feature/ma-fonctionnalite
```

Ouvre une Pull Request

Merci de suivre les conventions de style Kotlin/Android, et d’inclure des tests si possible.

--- 

👤 Contact

    Nashoba1er – https://github.com/Nashoba1er
    Email : [antoine.dumont@etu.emse.fr]
