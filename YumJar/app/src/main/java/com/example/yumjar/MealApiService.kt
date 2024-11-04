package com.example.yumjar

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("api/json/v1/1/search.php")
    fun searchMeal(
        @Query("s") mealName: String,
        @Query("apiKey") apiKey: String = "1"
    ): Call<MealResponse>

    @GET("api/json/v1/1/lookup.php")
    fun lookupMeal(
        @Query("i") mealId: String,
        @Query("apiKey") apiKey: String = "1"
    ): Call<MealResponse>

    @GET("api/json/v1/1/random.php")
    fun getRandomMeal(
        @Query("apiKey") apiKey: String = "1"
    ): Call<MealResponse>

    @GET("api/json/v1/1/categories.php")
    fun getCategories(
        @Query("apiKey") apiKey: String = "1"
    ): Call<CategoriesResponse>
}
