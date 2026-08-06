# 🚀 RickApi — Rick and Morty Explorer

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-2.4.10-7F52FF?logo=kotlin&logoColor=white">
  <img alt="Compose" src="https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?logo=jetpackcompose&logoColor=white">
  <img alt="Architecture" src="https://img.shields.io/badge/Architecture-Clean%20%2B%20MVI-orange">
  <img alt="License" src="https://img.shields.io/badge/license-MIT-blue">
  <img alt="Stars" src="https://img.shields.io/github/stars/Orlandroid/RickApi?style=social">
</p>

<p align="center">
  Dive into the multiverse with the <strong>Rick and Morty Explorer App</strong> — an Android app
  built with <strong>Jetpack Compose</strong>, <strong>MVI</strong>, and a fully modular
  <strong>Clean Architecture</strong>, covering every character, episode, and location in the show.
</p>

<p align="center">
  <a href="#-features">Features</a> •
  <a href="#-architecture">Architecture</a> •
  <a href="#-tech-stack">Tech Stack</a> •
  <a href="#-screenshots--demo">Screenshots</a> •
  <a href="#-getting-started">Getting Started</a> •
  <a href="#-testing">Testing</a>
</p>

---

## ✨ Features

#### 👤 Character Showcase
Browse a rich catalog of characters from the Rick and Morty universe. Each entry includes:
- Name, species, and status
- Origin and last known location
- Full list of episode appearances

#### 🌍 Location Directory
Explore the bizarre and varied places featured in the series — from alien planets to alternate
dimensions, discover the multiverse where Rick and Morty's adventures unfold.

#### 📺 Episode Guide
Access every episode with:
- Title and synopsis
- Air date
- Character appearances

#### 🌐 Data Source
All content is dynamically fetched from the [Rick and Morty API](https://rickandmortyapi.com/),
ensuring up-to-date and complete information.

---

## 🏗️ Architecture

RickApi is split into independent, feature-first Gradle modules that follow Clean Architecture
boundaries — each feature module depends on `domain` and `presentation`, never directly on `data`:

```
RickApi/
├── buildSrc/          # Centralized Gradle build configuration
├── core/               # Shared UI components, base classes, utilities
├── di/                 # Dagger Hilt modules
├── domain/             # Use cases, repository interfaces, models
├── data/               # Repository implementations, Retrofit + Room
├── presentation/       # Shared MVI base (BaseViewModel, UiState, UiEffect)
├── home/                # Home feature module
├── characters/           # Character list + detail feature module
├── episodes/             # Episode list + detail feature module
├── locations/            # Location list + detail feature module
└── settings/              # App settings feature module
```

**MVI data flow:**

```
User Intent → ViewModel (extends BaseViewModel) → UseCase → Repository → UiState → Compose UI
```

---

## 🛠️ Tech Stack

The app leverages a modern Android stack:
- ✅ **Jetpack Compose** (UI)
- ✅ **MVI Architecture**
- ✅ **Clean Architecture** principles
- ✅ **Kotlin Coroutines** + **Flow**
- ✅ **Dagger Hilt** (dependency injection)
- ✅ **Retrofit** (networking)
- ✅ **Room** (local persistence)
- ✅ **Paging 3** (infinite scrolling)
- ✅ **Navigation Component** + **Safe Args**
- ✅ **Material 3** (design system)
- ✅ **BaseViewModel** for shared logic

### 🧪 Testing
- ✅ **Unit Testing** with JUnit & Coroutines Test
- ✅ **UI Testing** with Jetpack Compose Test & Espresso

---

## 📱 Screenshots & Demo

<table>
  <tr>
    <td align="center"><strong>Home</strong></td>
    <td align="center"><strong>Characters</strong></td>
    <td align="center"><strong>Episodes</strong></td>
    <td align="center"><strong>Character Detail</strong></td>
  </tr>
  <tr>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/home.png" width="100%"></td>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/caracteres.png" width="100%"></td>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/episodes.png" width="100%"></td>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/detalle.png" width="100%"></td>
  </tr>
  <tr>
    <td align="center"><strong>Episode Detail</strong></td>
    <td align="center"><strong>Locations</strong></td>
    <td align="center"><strong>Location Detail</strong></td>
    <td align="center"><strong>Settings</strong></td>
  </tr>
  <tr>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/episode_detail.png" width="100%"></td>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/locations.png" width="100%"></td>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/location_detail.png" width="100%"></td>
    <td><img src="https://raw.githubusercontent.com/Orlandroid/images_for_repos/main/rick/settings.png" width="100%"></td>
  </tr>
</table>

🎥 **Demo video:**

https://user-images.githubusercontent.com/39423969/202087739-8fd3b7a3-6280-4a81-b02c-6b8db8187bf8.mp4

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17

### Setup
```bash
git clone https://github.com/Orlandroid/RickAndMortyAndroidApp.git
cd RickAndMortyAndroidApp
```

Open the project in Android Studio and let Gradle sync — no API key or extra configuration is
required, since the Rick and Morty API is public. Run the `app` configuration on a device or
emulator (API 24+).

---

## 🧪 Testing

```bash
# Unit tests across all modules
./gradlew test

# Instrumented / UI tests
./gradlew connectedAndroidTest
```

Each feature module owns its own unit and UI tests, colocated with the code they cover.

---

## 🎨 Why Rick and Morty?

This app is more than a project — it's a tribute to the **wit, creativity, and chaos** of the
Rick and Morty universe. Built by fans, for fans.

---

## 🗺️ Roadmap

- [x] Character, episode, and location browsing
- [x] Full multi-module migration
- [x] Decouple settings from data module
- [ ] Offline-first caching for all list screens
- [ ] Widget support
- [ ] Localization (ES, PT)

## 🚧 Status

🛠 **In Progress** – continuously improving with new features, optimizations, and testing coverage.

---

## 🤝 Contributing

Contributions and issues are welcome! Fork the repo, create a feature branch, and open a pull
request. Check the [issues page](https://github.com/Orlandroid/RickApi/issues) for
`good first issue` labels if you're looking for a place to start.

## 📄 License

Distributed under the MIT License. See `LICENSE` for details.

---

<p align="center">
  If you like this project, consider giving it a ⭐ — it helps others find it!
</p>
