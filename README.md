# News Listing App

This repository contains a News Listing App that fetches news articles from the [News API](https://newsapi.org/docs/endpoints/everything).

## Technologies Used
- **Jetpack Compose**: Modern UI toolkit for building native Android UI.
- **Kotlin Coroutines**: For asynchronous programming and managing background threads.
- **ViewModel**: Architecture component to store and manage UI-related data.
- **Paging 3**: Jetpack library for handling pagination of data.
- **Retrofit**: Type-safe HTTP client for Android and Java.
- **Hilt**: Dependency injection library for Android, built on top of Dagger 2.
- **Navigation Component**: Jetpack library for managing navigation in Android apps.
- **Coil**: Image loading library for Android.
- **Glide**: Image loading library for Android.

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository:

    ```bash
    git clone https://github.com/minaroid/news-listing.git
    ```

2. Generate an API key from [News API](https://newsapi.org/docs/endpoints/everything). You'll need this API key to fetch news articles in the app.

3. Open the `NewsRepositoryImpl.kt` file in the `data` package of your project.

4. Replace the placeholder `YOUR_API_KEY_HERE` with your actual API key.

5. Build and run the project using Android Studio or by executing the following command:

    ```bash
    ./gradlew assembleDebug
    ```

6. Enjoy browsing the latest news articles!

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
