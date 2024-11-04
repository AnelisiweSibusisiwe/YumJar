# YumJar App
Video Link: [https://youtu.be/uKOGcXNEi7w](https://youtu.be/uKOGcXNEi7w)
**YumJar** is a mobile application designed for food lovers to explore and create a variety of recipes. The app helps users to categorize recipes, search by ingredients, and manage their favorite meals. It features a user-friendly interface with multiple options to enhance the cooking experience.

## Versions
- **Android Studio**: Electric Eel or later
- **Kotlin**: 1.8.20
- **Gradle**: 8.0.0

---

## Introduction

YumJar provides users with the ability to search for and discover new recipes, manage their favorite dishes, and explore meal categories such as Breakfast, Lunch, Dinner, and Dessert. With an intuitive design, YumJar is designed to make the recipe discovery process simple, efficient, and engaging. The app also allows fingerprint login. 

---

## Description

YumJar allows users to:

- **Search Recipes**: Find recipes by entering keywords in the search bar.
- **Browse Categories**: Explore meals through predefined categories like Breakfast, Lunch, Dinner, and Dessert.
- **Personalize Profile**: Customize your profile with an avatar and settings.
- **Add Recipes**: Users can contribute by adding their own recipes to the app.
- **Must-Try Section**: Provides a curated list of featured recipes.
- **Dark Mode Toggle**: Switch between light and dark mode based on user preference.

---

## Installation

### Prerequisites

- **Android Studio Electric Eel** or later
- **Kotlin** 1.8.20 or later
- **Gradle** 8.0.0

### Steps

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/yumjar.git
    ```
2. **Open in Android Studio**:
    - Open Android Studio and select **Open an existing project**.
    - Navigate to the cloned directory and select the project folder.

3. **Sync Gradle**:
    - Ensure all dependencies are downloaded and Gradle is synced by going to **File > Sync Project with Gradle Files**.

4. **Run the App**:
    - Choose a device or emulator and click the **Run** button to install and launch the app.

---

## Setup and Configuration

### API Setup and Configuration (TheMealDB)

To integrate **TheMealDB** API into the YumJar App, follow these steps:

#### Prerequisites
- Android Studio
- Retrofit and Gson dependencies added to the project

#### 1. Add Dependencies
Add the following dependencies to your `build.gradle` file for Retrofit and Gson:
```groovy
dependencies {
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
}
```

#### 2. Define TheMealDB API Interface
Create an interface to define the API endpoints in Kotlin:

```kotlin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDBService {

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Call<MealsResponse>

    @GET("search.php")
    fun searchMeal(@Query("s") searchQuery: String): Call<MealsResponse>
}
```

#### 3. Initialize Retrofit
Initialize Retrofit in your main activity or application class:

```kotlin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val mealService = retrofit.create(TheMealDBService::class.java)
```

#### 4. Make API Requests
Call the API to fetch meals based on category or search queries, for example:

```kotlin
mealService.getMealsByCategory("Breakfast").enqueue(object : Callback<MealsResponse> {
    override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
        if (response.isSuccessful) {
            val meals = response.body()?.meals
            // Update RecyclerView with meal data
        }
    }

    override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
        // Handle error
    }
})
```

#### 5. Display API Data in RecyclerView
Parse and bind the API response (e.g., meal name, image) to your RecyclerView to display meals dynamically in the app.

This setup allows you to integrate **TheMealDB** API and fetch recipes for display in the **YumJar** app.

### RecyclerView for Recipes

YumJar uses `RecyclerView` for listing categories and recipes. Follow these steps:

1. Create an adapter for managing the dataset.
2. Use `ViewHolder` pattern to display recipe images, names, and categories.
3. Bind the `RecyclerView` with the `Adapter` to display the list of recipes dynamically.

---

## Key Features
- **Fingerprint (Biometric) authentication
- **User Authentication**: Users must register and log in to access full features of the app.
- **Recipe Search**: Search for recipes by name or ingredients.
- **Categories**: View categorized recipes (Breakfast, Lunch, Dinner, Dessert).
- **Profile Management**: Update user profile information.
- **Add Recipe**: Add new recipes with image, ingredients, and step-by-step instructions.
- **Dark Mode**: Toggle between light and dark themes from the settings menu.
- **Firebase database
---

## Frequently Asked Questions

### How do I add a new recipe?
Navigate to the profile menu, click on "Add Recipe", and fill in the required details such as the name, image, and steps.

### Can I search for a recipe by ingredients?
Yes, use the search bar to find recipes using keywords such as ingredients or recipe names.

### How do I toggle dark mode?
Access the menu from the top-right corner of the screen and switch on the dark mode toggle.

---

## Acknowledgements and References

- **Material Design for Android**: https://material.io/design
- **Android RecyclerView**: https://developer.android.com/guide/topics/ui/layout/recyclerview


## References
- FOR BIOMETRIC AUTHENTICATION: https://johncodeos.com/how-to-implement-biometric-authentication-in-android-using-kotlin/

## References (AI Tools) 
- ChatGBT

### YouTube Tutorials

- **Room Database with Kotlin**: https://www.youtube.com/watch?v=bbm6-lfO5zE
- **RecyclerView in Android**: https://www.youtube.com/watch?v=bEsy0U0Cz3I

---

## About

**YumJar** is developed as part of the Portfolio of Evidence (PoE) project. The goal is to streamline the process of managing and discovering new recipes while offering users an intuitive and engaging cooking experience.

---

## Resources

- **Icons** from Flaticon : https://www.flaticon.com/
**API** from TheMealDB : https://www.themealdb.com/api.php

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
