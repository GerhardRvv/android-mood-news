# NewsApp

![MoodNewsScreenSHotHomeScreen](https://github.com/user-attachments/assets/3db06810-8ba3-4ce5-9750-7f0cdedd588b)


#### Description:

- This app allows users to fetch top news articles using an API.
- Users can view detailed information about each article, including the title, content, author, and publication date.
- The app is designed with a focus on a smooth user experience, leveraging modern Android development practices.

## Features (How to Use)

- At the `Home Screen`:
  - The landing page: The App will fetch today's top articles
  - If articles are found, these will be displayed by category and displayed using carousels (LazyRows).
    
- At the `Search Screen`: WIP
- At the `Profile Screen`: WIP

#### Architecture
- The app is built using Clean Architecture (MVVM + UseCase + Repository), utilizing Kotlin Coroutines for background tasks.
- App is build using Modular App Architecture, in which app component is placed in its own module. This approach allows for better separation of concerns, making each feature independent of others. 
- Data is fetched from the `ApiNewsService`, which uses Retrofit to interact with the News API.
- Responses are serialized into data models, which are mapped into UI models for display.

#### UI
- The UI is built using Jetpack Compose, with the state managed through `uiState` data classes.
- Each screen and component is structured for easy maintenance and extensibility.

#### Design System
- Design system components are located at `core >> designsystem` for reusable Compose components.
- The custom theme `NewsAppTheme` resides in `core >> designsystem >> theme`, allowing easy application of colors and typography throughout the app.
- The theme supports both `Light` and `Dark` modes.

#### Navigation
- The app uses Jetpack Navigation to handle navigation between different screens.
- Navigation logic is designed to be straightforward and intuitive for users.

#### Utility Classes
- Utility functions and test data objects are located in the `utils` directory, providing reusable code and making testing easier.
- Extension functions in `core >> common >> util` simplify common data transformations and formatting.

## Build

- The app includes `development` and `debug` build variants.
- Select `development` to enable logging and testing on a device or emulator.

## Dependency Injection (DI)
- The project uses Hilt for Dependency Injection to manage dependencies across various components.
- Hilt modules configure and provide instances such as Retrofit, ViewModels, and repositories.

## Theming
Defines the visual theme of the application, including colors, drawables and typography.

## Application Entry Points
Primary starting points of the application.

- `MainActivity.kt` - The main activity serving as the app's UI entry point.
- `NewsApplication.kt` - Main application class for app-wide configurations and initializations.

---

### Development Environment

- The app uses Kotlin and requires Java 17 for compatibility.
- Compose UI components are designed using the "latest" version of Jetpack Compose.
- Uses Retrofit and OkHttp for network communication, with `ApiNewsService` handling API interactions.
- Multidex enabled to support a large number of methods.

### How to Run the Project
1. Clone the repository and open it in Android Studio.
2. Add your API key in the `local.properties` as follow  `NEWS_API_TOKEN=<your key>`.
3. Select the `development` build variant for debugging.
4. Run the app on an emulator or a physical device.

### Dependencies
- **Jetpack Compose**: For building UI components.
- **Retrofit**: For making network requests.
- **OkHttp**: For handling HTTP requests.
- **Hilt**: For dependency injection.
- **Gson**: For parsing JSON responses.

