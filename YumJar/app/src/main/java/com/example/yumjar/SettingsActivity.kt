package com.example.yumjar

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Language Selection
        val spinnerLanguage: Spinner = findViewById(R.id.spinner_language)
        setupLanguageSpinner(spinnerLanguage)

        // Offline Sync
        val switchOfflineSync: Switch = findViewById(R.id.switch_offline_sync)
        setupOfflineSyncSwitch(switchOfflineSync)

        // Theme Switch
        val buttonTheme: Button = findViewById(R.id.button_theme)
        setupThemeSwitch(buttonTheme)
    }

    // Language Spinner Setup
    private fun setupLanguageSpinner(spinner: Spinner) {
        val languages = listOf("English", "Zulu", "Xhosa")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val languageCode = when (position) {
                    0 -> "en"
                    1 -> "zu"
                    2 -> "xh"
                    else -> "en"
                }
                setLocale(languageCode)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Save language preference
        val sharedPreferences = getSharedPreferences("YumJarPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putString("language", languageCode).apply()

        // Restart activity to apply changes
        recreate()
    }

    // Offline Sync Switch Setup
    private fun setupOfflineSyncSwitch(switch: Switch) {
        val sharedPreferences = getSharedPreferences("YumJarPrefs", MODE_PRIVATE)
        switch.isChecked = sharedPreferences.getBoolean("offline_sync", false)

        switch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("offline_sync", isChecked).apply()
            if (isChecked) {
                // Initialize offline sync
            } else {
                // Stop offline sync
            }
        }
    }

    // Theme Switch Button Setup
    private fun setupThemeSwitch(button: Button) {
        val sharedPreferences = getSharedPreferences("YumJarPrefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        updateThemeButtonText(button, isDarkMode)

        button.setOnClickListener {
            val newTheme = !isDarkMode
            sharedPreferences.edit().putBoolean("dark_mode", newTheme).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (newTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            updateThemeButtonText(button, newTheme)
        }
    }

    private fun updateThemeButtonText(button: Button, isDarkMode: Boolean) {
        button.text = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode"
    }
}
