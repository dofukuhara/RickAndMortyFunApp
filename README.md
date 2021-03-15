# Rick And Morty Fun App

## Purpose
Develop an Android Application (Kotlin) which will consume the API service `https://rickandmortyapi.com/` and display to the end-use the following information:
* List of Characters from Rick and Morty
* List of Episodes
* List of Locations

## Project Configuration
* Compile SDK Version: `29`
* Target SDK Version: `29`
* Min SDK Version: `21`
* Gradle Version: `4.1.2`
* App Version:
    * Version Code: `1`
    * Version Name: `1.0.0`

## Project Modules
* `:app`
    * Module that contain the _Room Database_ configuration and the _Feature Screens_ (Characters, Episodes and Locations)
* `:common`
    * Module that contain structures that are common all project, for example: Either structure, NoDaFound custom exception, Extension Function and Configuration Provider
* `:designsystem`
    * Module that contain the UI resources (resources, layouts and Custom Views) to be used in the project
* `:network`
    * Module that contain the Retrofit implementation and the NetworkErrorHandler component

## Project Structure and 3rd-Party Libraries
* Architecture used: **MVVM** with LiveData
* **Jetpack Navigation Component** for fragment navigation
* **Room** ORM for local data persistence
* **Retrofit2**/**OkHttp3** for Network communication
* **Kotlin Coroutines** for concurrency asynchronous calls
* **Glide** for image loading
* **Koin** Service Locator - used to provide Dependency Injection
* **MockK** - Mocking framework for Unit Testing

## Run Project Via Gradlew Command Line
* `./gradlew testDebug` : Run Unit Test using `Debug` BuildVariant. This project has Unit Tests in `:app`, `:common` and `:network` modules.
* `./gradlew assembleDebug` : Run a build using `Debug` BuildVariant.
* `./gradlew installDebug` : Run a build using `Debug` BuildVariant and then, if a device or emulator is attached, will install the debug apk.