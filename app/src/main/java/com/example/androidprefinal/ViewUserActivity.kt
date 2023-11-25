package com.example.androidprefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ViewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user)

        val thisIntent: Intent = intent
        val imageString: String = thisIntent.getStringExtra("imageUrl").toString()
        val displayString: String = thisIntent.getStringExtra("displayName").toString()
        val emailString: String = thisIntent.getStringExtra("emailLabel").toString()

        if (imageString != null && displayString != null && emailString != null) {
            Log.d("jhayajy", "$imageString $displayString $emailString")

            val viewUserImage = findViewById<ImageView>(R.id.userImage)
            Glide.with(this).load(imageString).into(viewUserImage)

            val displayNameView = findViewById<TextView>(R.id.userName)
            displayNameView.text = displayString

            val emailView = findViewById<TextView>(R.id.userEmail)
            emailView.text = emailString
        } else {
            Log.d("jhayajy", "One or more values are null")
        }

    }
}

