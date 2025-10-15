package com.example.cookbook

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class MealResponse(
    val meals: List<Meal>?
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strInstructions: String?,
    val strMealThumb: String?
)

interface RecipeApiService {

    @GET("search.php")
    fun searchMeals(@Query("s") query: String): Call<MealResponse>
}