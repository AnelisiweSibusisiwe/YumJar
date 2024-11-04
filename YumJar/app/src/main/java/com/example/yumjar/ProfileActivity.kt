package com.example.yumjar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val mainLayout: LinearLayout = findViewById(R.id.main)
        val editProfileButton: Button = findViewById(R.id.editProfileButton)

        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Load user profile data
        db.collection("users").document("userProfile").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.getString("name") ?: "N/A"
                    val email = document.getString("email") ?: "N/A"
                    val profileImageUrl = document.getString("profileImageUrl")

                    // Display data in TextViews (create TextViews in your layout)
                    val textViewName: TextView = findViewById(R.id.textViewName)
                    val textViewEmail: TextView = findViewById(R.id.textViewEmail)
                    textViewName.text = name
                    textViewEmail.text = email

                    // Load image using a library like Glide or Picasso
                    profileImageUrl?.let {
                        // Use Glide or Picasso to load the image
                    }
                }
            }.addOnFailureListener {
                // Handle failure
            }
    }
}


