package com.example.yumjar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val loginButton: Button = findViewById(R.id.loginBtn)
        loginButton.setOnClickListener { openLoginActivity() }

        val registerButton: Button = findViewById(R.id.createAccountBtn)
        registerButton.setOnClickListener { openRegisterActivity() }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    private fun openRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}

