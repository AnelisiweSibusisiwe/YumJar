package com.example.yumjar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.yumjar.databinding.ActivitySearchMealBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchMealBinding
    private val preferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize theme based on saved preference
        val isNightMode = preferences.getBoolean("NIGHT_MODE", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        // Search button action
        binding.searchButton.setOnClickListener {
            val mealName = binding.mealNameEditText.text.toString().trim()
            if (mealName.isNotEmpty()) {
                fetchMealByName(mealName)
            } else {
                Toast.makeText(this, "Please enter a meal name", Toast.LENGTH_SHORT).show()
            }
        }

        // Go to Profile button action
        binding.goToProfileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchMealByName(mealName: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.searchButton.isEnabled = false

        RetrofitClient.mealApiService.searchMeal(mealName).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                binding.progressBar.visibility = View.GONE
                binding.searchButton.isEnabled = true
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        displayMeal(meals[0])
                    } else {
                        Toast.makeText(this@SearchMealActivity, "No meals found", Toast.LENGTH_SHORT).show()
                        clearMealDisplay()
                    }
                } else {
                    Toast.makeText(this@SearchMealActivity, "Error fetching meals", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                binding.searchButton.isEnabled = true
                Toast.makeText(this@SearchMealActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayMeal(meal: Meal) {
        binding.mealTitleTextView.text = meal.strMeal
        binding.mealInstructionsTextView.text = meal.strInstructions

        val mealThumb = meal.strMealThumb
        if (!mealThumb.isNullOrEmpty()) {
            Glide.with(this)
                .load(mealThumb)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.mealImageView)
        } else {
            binding.mealImageView.setImageResource(R.drawable.placeholder_image)
        }
    }

    private fun clearMealDisplay() {
        binding.mealTitleTextView.text = ""
        binding.mealInstructionsTextView.text = ""
        binding.mealImageView.setImageResource(R.drawable.placeholder_image)
    }
}
