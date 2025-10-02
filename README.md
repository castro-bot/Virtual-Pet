# Virtual Pet Application

This repository contains the source code for **Virtual Pet Application**, a native Android application developed in Kotlin that allows users to care for and interact with a virtual pet.

## 🚀 Features

- **Virtual Pet Care:** Users can interact with their virtual pet, likely managing its needs and happiness.
- **User Authentication:** Secure user sign-up and login functionality is implemented using Firebase Authentication.
- **Cloud Data Storage:** Pet data and user progress are stored in the cloud with Firebase Firestore, allowing for data persistence across multiple devices.
- **Offline Capabilities:** The application uses the Room persistence library for local data caching, enabling offline access and a smoother user experience.
- **Modern UI:** The user interface is built with Jetpack Compose, providing a modern, declarative, and responsive design.
- **Gaming Elements:** Integration with Google Play Games Services for achievements and leaderboards.

## 🛠️ Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture:** Follows modern Android architecture principles.
- **Backend & Cloud:**
  - [Firebase Authentication](https://firebase.google.com/docs/auth)
  - [Firebase Firestore](https://firebase.google.com/docs/firestore)
- **Local Database:** [Room](https://developer.android.com/training/data-storage/room)
- **Navigation:** [Jetpack Navigation for Compose](https://developer.android.com/jetpack/compose/navigation)
- **Networking:** [OkHttp](https://square.github.io/okhttp/)
- **Build Tool:** [Gradle](https://gradle.org/)

## ⚙️ Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest version recommended)
- An Android device or emulator with API level 24 or higher.

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/castro-bot/Virtual-Pet.git
    ```
2.  **Open in Android Studio & Sync Gradle:**
    - Android Studio should automatically start syncing the Gradle project. If not, click the "Sync Project with Gradle Files" button in the toolbar.
3.  **Set up Firebase:**
    - The project includes a `google-services.json` file. To connect to your own Firebase backend, replace this file with the one from your own Firebase project configuration.
4.  **Run the application:**
    - Select a run configuration (usually `app`).
    - Choose a target device (emulator or physical device).
    - Click the "Run" button.

Alternatively, you can build the project from the command line using Gradle:

```sh
# Build a debug APK
./gradlew assembleDebug

# Install the debug APK on a connected device
./gradlew installDebug
```

## 📁 Project Structure

The project follows a standard Android application structure:

```
.
├── app/                # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/aplicacionmascotavirtual/  # Core source code
│   │   │   ├── res/                                      # Resources (layouts, drawables, etc.)
│   │   │   └── AndroidManifest.xml
│   └── build.gradle.kts  # App-level build script
│
├── gradle/             # Gradle wrapper files
└── build.gradle.kts    # Top-level build script
```

## ✍️ Authors

This app was created with ❤️ by:

- Adolfo Castro 🎷
- Axel Hernández 🥋
- Paula Márquez ❤️
- Leonela Sornoza 🎹
