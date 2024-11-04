package com.example.yumjar

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var uploadPictureButton: Button
    private lateinit var deletePictureButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var pushNotificationsSwitch: Switch
    private lateinit var saveChangesButton: Button

    private val IMAGE_PICK_CODE = 1000
    private var imageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize UI components
        profileImageView = findViewById(R.id.profile_image_view)
        uploadPictureButton = findViewById(R.id.upload_picture_btn)
        deletePictureButton = findViewById(R.id.delete_picture_btn)
        nameEditText = findViewById(R.id.name)
        surnameEditText = findViewById(R.id.surname)
        emailEditText = findViewById(R.id.email)
        numberEditText = findViewById(R.id.number)
        pushNotificationsSwitch = findViewById(R.id.push_notifications)
        saveChangesButton = findViewById(R.id.save_changes_btn)

        uploadPictureButton.setOnClickListener {
            pickImageFromGallery()
        }

        deletePictureButton.setOnClickListener {
            deleteProfilePicture()
        }

        saveChangesButton.setOnClickListener {
            saveChanges()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun deleteProfilePicture() {
        profileImageView.setImageResource(R.drawable.default_profile_image)
        imageUri = null
    }

    private fun saveChanges() {
        val name = nameEditText.text.toString()
        val surname = surnameEditText.text.toString()
        val email = emailEditText.text.toString()
        val number = numberEditText.text.toString()
        val pushNotificationsEnabled = pushNotificationsSwitch.isChecked

        val userProfile = hashMapOf(
            "name" to name,
            "surname" to surname,
            "email" to email,
            "number" to number,
            "pushNotificationsEnabled" to pushNotificationsEnabled
        )

        // Upload image to Firebase Storage if available
        imageUri?.let { uri ->
            val fileReference = storageReference.child("profile_images/${System.currentTimeMillis()}.jpg")
            fileReference.putFile(uri).addOnSuccessListener {
                // Get the download URL and save to Firestore
                fileReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    userProfile["profileImageUrl"] = downloadUri.toString()
                    db.collection("users").document("userProfile")
                        .set(userProfile)
                        .addOnSuccessListener {
                            // Success message or navigate back
                        }
                }
            }.addOnFailureListener {
                // Handle failure
            }
        } ?: run {
            // Save without image
            db.collection("users").document("userProfile")
                .set(userProfile)
                .addOnSuccessListener {
                    // Success message or navigate back
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            profileImageView.setImageURI(imageUri)
        }
    }
}
